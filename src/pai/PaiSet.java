package pai;

import enums.PaiType;

public class PaiSet {
	private Pai p1 = null;
	private Pai p2 = null;
	private Pai p3 = null;
	private int point;
	private boolean isShuntsu;
	private boolean isKotsu;
	private boolean includeChar;
	private boolean hasYaochu;

	public PaiSet() {
		super();
	}
	
	public PaiSet(Pai p1, Pai p2, Pai p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		if(p1.isYaochu() || p2.isYaochu() || p3.isYaochu()) {
			this.hasYaochu = true;
		}
		if(p1.getPaiType() == PaiType.CHARACTER) {
			this.includeChar = true;
		}
		if(p1.equals(p2)){
			this.isKotsu = true;
		}else {
			this.isShuntsu = true;
		}
	}

	//TODO いらんメソッドは消す！！
	public Pai getP1() {
		return p1;
	}

	public void setP1(Pai p1) {
		this.p1 = p1;
	}

	public Pai getP2() {
		return p2;
	}

	public void setP2(Pai p2) {
		this.p2 = p2;
	}

	public Pai getP3() {
		return p3;
	}

	public void setP3(Pai p3) {
		this.p3 = p3;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public boolean isIncludeChar() {
		return includeChar;
	}

	public boolean isHasYaochu() {
		return hasYaochu;
	}

	public boolean isShuntsu() {
		return isShuntsu;
	}

	public boolean isKotsu() {
		return isKotsu;
	}

	public void setKotsu(boolean isKotsu) {
		this.isKotsu = isKotsu;
	}

}
