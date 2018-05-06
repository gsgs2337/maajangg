package player.hand;

import java.util.ArrayList;
import java.util.List;

import pai.Pai;
import pai.PaiSet;

public class PossibleGoalHand {
	//上がりの形
	private Pai[] headArray = null;

	private List<PaiSet> paiSetList = new ArrayList<PaiSet>();	
	private List<Pai> restPaiList = new ArrayList<Pai>();
	
	private boolean isGoal;
	private boolean hasHead;
	
	public PossibleGoalHand() {
		super();
	}
	public PossibleGoalHand(Pai head1, Pai head2) {
		if(head1 != null) {
			headArray = new Pai[]{head1, head2};
			this.hasHead = true;
		}
	}
	
	public void addPaiSet(PaiSet paiSet) {
		this.paiSetList.add(paiSet);
	}
	
	public void addRest(Pai p) {
		this.restPaiList.add(p);
	}

}
