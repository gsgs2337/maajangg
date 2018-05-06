package player.hand.sorter;

import java.util.Collections;
import java.util.List;

import pai.Pai;

/**
 */
public class HandSorter {

	public HandSorter() {

	}

	public void sort(List<Pai> handList) {
		if(handList == null || handList.isEmpty()) {
			System.out.println("[WARN] cannot sort my handList because it is null or empty.");
			return;
		}
		//並べ替え
		Collections.sort(handList, new PaiTypeComparator());
		Collections.sort(handList, new NumCharComparator());

	}
}
