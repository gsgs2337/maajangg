package player.hand.sorter;

import java.util.Comparator;

import pai.Pai;

public class PaiTypeComparator implements Comparator<Pai>{

	public int compare(Pai p1, Pai p2) {
		return p1.getPaiType().getindex() < p2.getPaiType().getindex() ? -1 : 1;
	}

}
