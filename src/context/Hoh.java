package context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pai.Pai;
import player.Player;
/**
 * Singleton. Note!Multi Thread Programming is needed in the future!
 */

public final class Hoh {
		private static final Hoh instance = new Hoh();
		private Map<Player, List<Pai>> hohMap = new HashMap<Player, List<Pai>>();

		private Hoh() {
		}

		public static Hoh getInstance() {
			return instance;
		}

		public void init(Player player) {
			List<Pai> hohList = hohMap.get(player);
			if( hohList == null || hohList.isEmpty()) {
				hohList = new ArrayList<Pai>();
				hohMap.put(player, hohList);
			}
		}

		public void addPai(Player player, Pai p) {
			//Playerの河に捨て牌を追加。
			hohMap.get(player).add(p);
			//他Playerに通知。これはmasterで一元管理することにしよう。
			//notice(player, p);
		}

}
