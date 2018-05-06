package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import context.GlobalContext;
import context.Yamafuda;
import data.ActionData;
import enums.ActionType;
import pai.Pai;
import player.Player;
import player.Master;
import player.thread.MasterThread;
import player.thread.PlayerThread;
import view.TableFrame;

public class Main {
	public static void main(String[] args) {
		
		startMasterPlayerThread();
		//あとでクラス分けしてstatic解除。
		init();
	}
	
	public static void startMasterPlayerThread() {
	
		Master master = new Master();

		MasterThread masterPlayerThread = 
				new MasterThread(master);
		Thread masterThread = new Thread(masterPlayerThread);
		masterThread.start();
	}


	public static void init() {

		//TableFlameVer.
//		TestTableFrame tableFrame = new TestTableFrame("麻雀");
//	    tableFrame.setLocation(100, 30);
//	    tableFrame.setSize(800, 600);
//	    tableFrame.setBackground(Color.GREEN);
//	    tableFrame.setVisible(true);

		//別のTableFlame
//		TableFrame swing = new TableFrame();
//	    swing.setVisible(true);

//	    List<Pai> list = player.getHandList();

//		for(int i=0; i<list.size(); i++) {
//			System.out.printf("your hand is %d : %s\n", i, list.get(i).toString());
		    // イメージを反映
//			System.out.println("index: " + i + " = " + list.get(i));
//			tableFrame.addImage(list.get(i).getImage());
//			swing.setImage(i, list.get(i).getImage());
//		}
//	    swing.setVisible(true);
//		tableFrame.setVisible(true);

//	    try {
//			String input =  new BufferedReader(new InputStreamReader(System.in)).readLine();
//			int discardPaiIndex = Integer.parseInt(input);
//			player.discard(player.getHandList().get(discardPaiIndex));
//		} catch (IOException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
	}

}
