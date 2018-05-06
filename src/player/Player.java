package player;

import java.util.ArrayList;
import java.util.List;

import context.Hoh;
import context.Yamafuda;
import pai.Pai;
import player.hand.sorter.HandSorter;

public class Player {

	private String name;
	
	private boolean canChi;
	private boolean canPon;
	private boolean canMinKan;
	private boolean canRon;
	//これいる？
	private boolean canTsumo;
	//これもシャンテン数でいける？
	private boolean canReach;

	private Player prevPlayer;
	private Player nextPlayer;
	private HandSorter handSorter;
	private List<Pai> handList;
	private Hoh hoh = Hoh.getInstance();

	private int shantenNum;

	public Player(String name) {
		this.name = name;
		handSorter = new HandSorter();
		handList = new ArrayList<Pai>();
		hoh.init(this);
	}
	
	public void getInitialHand() {
	    //13枚山札から取ってくる。
		handList = Yamafuda.getInstance().distributeInitialList();
		sort(handList);
	}

	public void addPaiToHand(Pai pai) {
		handList.add(pai);
	}

	public Pai discard(Pai p) {
		hoh.addPai(this, p);
		System.out.printf("%s discarded: %s\n", this.name, p.toString());
		this.handList.remove(p);
		sort(handList);
		return p;
	}

	private void sort(List<Pai> handList) {
		if(handList == null || handList.isEmpty()) {
			System.out.println("[WARN] cannot sort my handList because it is null or empty.");
			return;
		}

		handSorter.sort(handList);
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Pai> getHandList() {
		return handList;
	}

	public Player getPrevPlayer() {
		return prevPlayer;
	}

	public void setPrevPlayer(Player prevPlayer) {
		this.prevPlayer = prevPlayer;
	}

	public Player getNextPlayer() {
		return nextPlayer;
	}

	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}
	
	public boolean canChi() {
		return canChi;
	}

	public void setCanChi(boolean canChi) {
		this.canChi = canChi;
	}

	public boolean canPon() {
		return canPon;
	}

	public void setCanPon(boolean canPon) {
		this.canPon = canPon;
	}

	public boolean canMinKan() {
		return canMinKan;
	}

	public void setCanMinKan(boolean canKan) {
		this.canMinKan = canKan;
	}

	public boolean canRon() {
		return canRon;
	}

	public void setCanRon(boolean canRon) {
		this.canRon = canRon;
	}

	public boolean canTsumo() {
		return canTsumo;
	}

	public void setCanTsumo(boolean canTsumo) {
		this.canTsumo = canTsumo;
	}

	public boolean canReach() {
		return canReach;
	}

	public void setCanReach(boolean canReach) {
		this.canReach = canReach;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", shantenNum=" + shantenNum + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getName().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (handList == null) {
			if (other.handList != null)
				return false;
		} else if (!handList.equals(other.handList))
			return false;
		if (this.getName().equals(other.getName()))
			return false;
		return true;
	}

}
