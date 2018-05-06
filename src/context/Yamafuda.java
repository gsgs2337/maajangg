package context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import enums.CharType;
import enums.NumberType;
import enums.PaiType;
import pai.Pai;

/**
 * Singleton. Note! Multi-Thread Programming is needed in the future!
 */
public final class Yamafuda {

	private static final Yamafuda instance = new Yamafuda();
	private List<Pai> paiList = new ArrayList<Pai>();
	public static byte[] mutex = new byte[0];

	private Yamafuda() {

	}

	public static Yamafuda getInstance() {
		return instance;
	}

	public boolean init() {
		boolean result = false;

		for(int i =0; i<4; i++) {
			for(PaiType paiType : PaiType.values()) {
				switch(paiType) {
				//字牌を格納
				case CHARACTER:
					for(CharType charType: CharType.values()) {
						instance.paiList.add(new Pai(paiType, charType));
					}
				break;
				//数牌を格納
				case MAN:
				case PIN:
				case SOU:
					for(NumberType number: NumberType.values()) {
						instance.paiList.add(new Pai(paiType, number));
					}
				break;
				default:
					System.out.println("[FATAL] system  error.");
				break;
				}
			}
		}

		Collections.shuffle(instance.paiList);

		if(instance.paiList.size() == 136) {
			result = true;
		}

		System.out.println("[DEGUB] craete pai list:size = "+instance.paiList.size());

		//ここはboolで返すべきか、Exceptionを投げるべきか？
		return result;
	}

	public List<Pai> distributeInitialList(){
		List<Pai> initialList = new ArrayList<Pai>();
		for(int i=0; i< 13; i++) {
			synchronized (mutex) {
				initialList.add(instance.paiList.get(i));
				instance.paiList.remove(i);				
			}
		}
		return initialList;
	}

	public Pai distribute() {
		//とってきて、もとのListから削除、ってメソッドない？
		Pai p = instance.paiList.get(0);
		instance.paiList.remove(0);
		return p;
	}

}
