package player.hand;

import java.util.ArrayList;
import java.util.List;

public class PossibleHandList {
	private List<PossibleGoalHand> handList = new ArrayList<PossibleGoalHand>();
	
	public PossibleHandList(List<PossibleGoalHand> handList) {
		this.handList = handList;
	}
	
	//どのhandlistが得点が高いのかを判定するロジックをもつ
}
