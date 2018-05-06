package player.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

import context.GlobalContext;
import context.Yamafuda;
import data.ActionData;
import enums.ActionType;
import pai.Pai;
import player.Player;
import player.hand.simulator.HandSimulator;

/**
 * @author kei
 *
 */
public class PlayerThread implements Runnable{
	
	private Player player;
	private LinkedTransferQueue<ActionData> playerQueue;
	private GlobalContext context;
	private HandSimulator simulator;
	
	public PlayerThread(Player player, LinkedTransferQueue<ActionData> queue) {
		super();
		this.player = player;
		this.playerQueue = queue;
		this.context = GlobalContext.getInstance();
		this.simulator = new HandSimulator();
	}

	public void run() {
		
		if(this.player == null) {
			System.out.println("[FATAL] player is null");
			return;
		}
		
		System.out.printf("PlayerThread of %s start.\n",this.player.getName());
		
		while(!Thread.currentThread().isInterrupted()) {
			try {
				ActionData data = this.playerQueue.take();
				execute(data);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * MasterThreadから受け取った通知に従い、プレイヤーの挙動を決定。
	 * @param data
	 */
	private void execute(ActionData data) {
		switch(data.getActionType()) {
		case STARTGAME:
			//山札から13枚もらいなさい。配牌。
		    this.player.getInitialHand();
			System.out.printf("%s get initial hand.\n", this.player.toString());
			break;
		case DRAW:
			//(1)ツモ開始しなさい。時間制限あり。masterに対してdiscard/ankan/tsumo通知を行うまでの処理を行う。
			//(2)だれかがツモを開始しました。読み捨て。
			reactToDrawNotice(data);
			break;
		case DISCARD:
		case REACH:
			//(1)他人の捨牌に対するアクションを決定しなさい。時間制限あり。
			//(2)あなたが牌を捨てたことをmasterが全員に通知しました。読み捨て。
			//リーチの時は特殊処理が必要かも。
			reactToDiscardNotice(data);
			break;
		case CHI:
			//(1)だれかがチーしたよ。
			//(2)あなたのチー要求が成功したよ。
			reactToChiNotice(data);
			break;
		case PON:
			//(1)だれかがポンしたよ。
			//(2)あなたのポン要求が成功したよ。
			reactToPonNotice(data);
			break;
		case MINKAN:
			//(1)だれかが明槓したよ。
			//(2)あなたの明槓要求が成功したよ。
			reactToMinKanNotice(data);
			break;
		case RON:
		case TSUMO:
			//だれかがロンしたよ。
			break;
		case ANKAN:
			//(1)だれかが暗槓したよ。国士の特殊ロンのケースのみ処理が必要。それ以外は読み捨てる。
			//(2)あなたの暗槓要求が成功したよ。
			break;
			default:
				System.out.println("[FATAL] system error. ");
				break;
		}
	}
	
	private void reactToDrawNotice(ActionData data) {
		//(2)だれかがツモを開始しました。読み捨て。
		if(!data.getTriggerPlayer().equals(this.player)) {
			//読み捨て。
			return;
		}

		//(1)ツモ開始。
		System.out.printf("%s start to draw...\n", this.player.toString());
		Pai drawPai = Yamafuda.getInstance().distribute();
		
		//ツモ牌に対するアクションを決定。
		doTsumoAction(drawPai);
		}
		
	private void reactToDiscardNotice(ActionData data) {
		//(2)あなたが牌を捨てたことをmasterが全員に通知しました。読み捨て。
		if(data.getTriggerPlayer().equals(this.player)) {
			//読み捨て。
			return;
		}
		//(1)他人の捨牌に対するアクションを決定。時間制限あり。
		System.out.printf("%s reacting to other's discard...\n", this.player.toString());
		ActionType reaction = doReaction(data);
		try {
			context.getMasterQueue().transfer(new ActionData(this.player, reaction, null));
			initializePlayerReactionStatus();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void reactToChiNotice(ActionData data) {
		//(1)だれかがチーしたよ。
		if(!data.getTriggerPlayer().equals(this.player)) {
			//読み捨て。
			return;
		}
		//(2)あなたのチー要求が成功したよ。
		System.out.printf("%s start to process CHI action...\n", this.player.toString());
		//チー処理。

		//捨牌を決定し、masterに通知。
		doDiscardAction();
	}
	
	private void reactToPonNotice(ActionData data) {
		//(1)だれかがポンしたよ。
		if(!data.getTriggerPlayer().equals(this.player)) {
			//読み捨て。
			return;
		}
		//(2)あなたのポン要求が成功したよ。
		System.out.printf("%s start to process PON action...\n", this.player.toString());
		//ポン処理。

		//捨牌を決定し、masterに通知。
		doDiscardAction();
	}
	private void reactToMinKanNotice(ActionData data) {
		//(1)だれかが明槓したよ。
		if(!data.getTriggerPlayer().equals(this.player)) {
			//読み捨て。
			return;
		}
		//(2)あなたの明槓要求が成功したよ。
		System.out.printf("%s start to process MINKAN action...\n", this.player.toString());
		//明槓処理。

		//捨牌を決定し、masterに通知。
		doDiscardAction();
	}
	
	
	/**
	 * チー/ポン/カンができるかどうかのステータスを初期化する。
	 */
	private void initializePlayerReactionStatus() {
		this.player.setCanChi(false);
		this.player.setCanPon(false);
		this.player.setCanMinKan(false);
	}
	
	//他playerのアクションに対する、playerのreactionを返す。
	private ActionType doReaction(ActionData data) {
		ActionType reaction = ActionType.DONOTHING;
		simulator.simulate(this.player, data);
		//playerと牌を引数に、ポン、チー、カン、ロン(ツモ）が可能かを設定。
		if(this.player.canChi()) {
			System.out.printf("%s can Chi.\n",this.player);
		}
		if(this.player.canPon()) {
			System.out.printf("%s can Pon.\n",this.player);
		}
		if(this.player.canMinKan()) {
			System.out.printf("%s can Kan.\n",this.player);
		}
		return reaction;
	}
	
	/**
	 * 捨牌を決定し、masterにそれを通知。
	 * 
	 */
	private void doDiscardAction() {
//		streamなんか使えないから一旦無視。
//		this.player.getHandList().stream().forEach(System.out::println);
		
		int i = 0;
		for(Pai p: this.player.getHandList()) {
			System.out.printf("%d: %s\n", i,p.toString());
			++i;
		}
		
		//getPlayersReaction();
		//これで得たActionに応じて、捨てる、ポンして捨てる、カンするを決めるので、全部Facade化出来る。
	    try {
			System.out.printf("%s: which to discard? >>", this.player);

			String input =  new BufferedReader(new InputStreamReader(System.in)).readLine();
			int discardPaiIndex = Integer.parseInt(input);
			Pai discardPai = player.discard(player.getHandList().get(discardPaiIndex));
			//masterに捨牌を通知。
			context.getMasterQueue().transfer(new ActionData(this.player, ActionType.DISCARD, discardPai));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void doTsumoAction(Pai pai) {
		
		List<Pai> handList = this.player.getHandList();

		int i = 0;
		for(Pai p: handList) {
			System.out.printf("%d: %s\n", i,p.toString());
			++i;
		}
		System.out.println(pai);

		simulator.simulate(this.player);

		//いつ手牌に加える？？
		player.addPaiToHand(pai);		
		//捨牌を決定し、masterに通知。
		doDiscardAction();

	}

}
