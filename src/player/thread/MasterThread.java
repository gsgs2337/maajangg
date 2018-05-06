package player.thread;

import java.util.ArrayList;
import java.util.List;

import context.GlobalContext;
import context.Yamafuda;
import data.ActionData;
import enums.ActionType;
import pai.Pai;
import player.Player;
import player.Master;

/**
 * @author kei
 *
 */
public class MasterThread implements Runnable{
	
	private GlobalContext context = GlobalContext.getInstance();
	private Master master;
	
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	
	private Player processingPlayer;

	private List<Player> playerList = new ArrayList<Player>();
	
	private int processingCount = 0;
	private ActionData processingData = null;
	
	public MasterThread(Master master) {
		super();
		this.master = master;
	}


	public void run() {
		
		if(master == null) {
			System.out.println("[FATAL] master is null");
			return;
		}
		System.out.printf("MasterThead start.\n");
		
		//4 playersを作成＆ thread start.
		initializeGamePlayers();
		
		//親決め
		setPlayerOrder();
		
		//山札の初期化
		initializeYamafuda();
		
		//全playerに、局開始指示。配牌の開始。
		notifyPlayerAction(null, ActionType.STARTGAME, null);
		
		//配牌完了後、親からツモ開始。
		notifyPlayerAction(this.processingPlayer, ActionType.DRAW, null);
			

		while(!Thread.currentThread().isInterrupted()) {
			try {
				//master専用queueからイベントを取り出して実行。
				ActionData data = context.getMasterQueue().take();
				execute(data);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void execute(ActionData data) {
		switch(data.getActionType()) {

		case DISCARD:			//対象playerが牌を捨てたよ。			
		case REACH:			//リーチの時は特殊処理が必要。
		case ANKAN:			//対象playerが暗槓したよ。
		case TSUMO:			//対象playerがツモあがりしたよ。
			notifyPlayerAction(data.getTriggerPlayer(),data.getActionType(), data.getPai());
			break;
		case DONOTHING:
		case CHI:
		case PON:
		case MINKAN:
		case RON:
			//対象playerが、だれかの捨牌に対するReactionを返したよ。
			//3 player分のReactionを、ここで一つ一つ処理。
			processDiscardReaction(data);
			break;
		default:
			//ありえない。
			System.out.println("[FATAL] system error. ");
			break;
		}
	}
	
	private void initializeGamePlayers() {
		
		player1 = new Player("player1");
		player2 = new Player("player2");
		player3 = new Player("player3");
		player4 = new Player("player4");

		//これいる？
		this.playerList.add(player1);
		this.playerList.add(player2);
		this.playerList.add(player3);
		this.playerList.add(player4);
		
		//contextにplayer-queueの設定
		context.createGamePlayerQueue(player1);
		context.createGamePlayerQueue(player2);
		context.createGamePlayerQueue(player3);
		context.createGamePlayerQueue(player4);
		
		PlayerThread pth1 = new PlayerThread(player1, context.getGamePlayerQueue(player1));
		Thread playerThread1 = new Thread(pth1);
		playerThread1.start();
		
		PlayerThread pth2 = new PlayerThread(player2, context.getGamePlayerQueue(player2));
		Thread playerThread2 = new Thread(pth2);
		playerThread2.start();
		
		PlayerThread pth3 = new PlayerThread(player3, context.getGamePlayerQueue(player3));
		Thread playerThread3 = new Thread(pth3);
		playerThread3.start();
		
		PlayerThread pth4 = new PlayerThread(player4, context.getGamePlayerQueue(player4));
		Thread playerThread4 = new Thread(pth4);
		playerThread4.start();		
	}
	
	private void initializeYamafuda() {
	    Yamafuda yamafuda = Yamafuda.getInstance();
	    boolean ret = yamafuda.init();
	    if(!ret) {
			System.out.println("[FATAL] create yamafuda failed.");
			//これはどうなの？
			System.exit(1);
	    }
	}
	
	private void setPlayerOrder() {
		//どうやって親を決めるかはあとで考える。
		for(int i = 0; i < 4 ; i++) {
			//set prevPlayer
			if(i - 1 < 0) {
				this.playerList.get(i).setPrevPlayer(this.playerList.get(i + 3));
			}else {
				this.playerList.get(i).setPrevPlayer(this.playerList.get(i - 1));
			}
			//set nextPlayer
			if(3 < i + 1) {
				this.playerList.get(i).setNextPlayer(this.playerList.get(i - 3));
			}else {
				this.playerList.get(i).setNextPlayer(this.playerList.get(i + 1));
			}
		}
		//これもあとで考える。
		processingPlayer = this.player1;
	}
	
	private void notifyDiscardAction(Player triggerPlayer, Pai discardPai) {

	}
	
	private void processDiscardReaction(ActionData data) {
		System.out.printf("[Master] %s react: %s\n", data.getTriggerPlayer(), data.getActionType());

		++ this.processingCount;
		
		switch(data.getActionType()) {
		case DONOTHING:
			break;
		case CHI:
			//他のplayerのアクションが無い場合のみ、有効。
			if(this.processingData == null) {
				this.processingData = data;
			}
			break;
		case PON:
		case MINKAN:
			//他のplayerのアクションが「ロン」でない場合のみ、有効
			if(this.processingData == null 
				|| this.processingData.getActionType() != ActionType.RON) {
				this.processingData = data;
			}
			break;
		case RON:
			//TODO 頭ハネは一旦考慮しない。つまりダブロン化。
			this.processingData = data;
			break;
		default:
				System.out.println("[FATAL] system error. ");
		break;
		}
		//全playerのリアクションが完了
		if(this.processingCount == 3) {
			//何らかのリアクションがある場合
			if(this.processingData != null){
				
				System.out.printf("[Master] all reacted. action = %s: %s\n", this.processingData.getTriggerPlayer(), this.processingData.getActionType());
				//次に捨てるplayerを変更。
				this.processingPlayer = this.processingData.getTriggerPlayer();
				//特定のplayerのアクション開始を、全プレイヤーに通知。
				notifyPlayerAction(this.processingData.getTriggerPlayer(), this.processingData.getActionType(), this.processingData.getPai());

			}else {
				System.out.printf("[Master] all reacted. there is no action. next Player is %s\n", this.processingPlayer.getNextPlayer());
				//次に捨てるplayerを変更。
				this.processingPlayer = this.processingPlayer.getNextPlayer();
				//次に捨てるplayerに、ツモ指示。
				notifyPlayerAction(this.processingPlayer, ActionType.DRAW,null);
			}
			//処理中のリアクションフラグを初期化
			this.processingCount = 0;
			this.processingData = null;
		}
	}

	/**
	 * ex. playerに対し、「だれかがチーに成功したよ」通知を行う。
	 * @param triggerPlayer startgame時のみ、null可。
	 * @param action null不可
	 * @param p draw, startgame時のみ、null可。
	 */
	private void notifyPlayerAction(Player triggerPlayer, ActionType action, Pai p) {
		System.out.printf("[Master] notify: %s %s %s\n", triggerPlayer,action, p);
		for(Player eachPlayer : this.playerList) {
			try {
				context.getGamePlayerQueue(eachPlayer).transfer(new ActionData(triggerPlayer, action, p));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
