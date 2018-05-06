package player.hand.sorter;

import java.util.Comparator;

import pai.Pai;

public class NumCharComparator implements Comparator<Pai>{

		public int compare(Pai p1, Pai p2) {
			//字牌同士、萬子同士の場合のみ、比較を行う。
			if(p1.getPaiType().getindex() == p2.getPaiType().getindex()) {
				switch(p1.getPaiType()) {
				case CHARACTER:
					return p1.getCharType().getIndex() < p2.getCharType().getIndex() ? -1 : 1;
				case MAN:
				case PIN:
				case SOU:
					return p1.getNumberType().getIndex() < p2.getNumberType().getIndex() ? -1 : 1;
					default:
						break;
				}
			}
			return 0;
		}
}
