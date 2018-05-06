package enums;

public enum PaiType {
	MAN(1, "man"),
	PIN(2, "pin"),
	SOU(3, "sou"),
	CHARACTER(4, "ji");

	private final int index;
	private final String code;

	private PaiType(int index, String code) {
		this.index= index;
		this.code = code;
	}

	public int getindex() {
		return this.index;
	}

	public String getCode() {
		return this.code;
	}

	/** Image画像名と連携させるために必要。	 */
	@Override
	public String toString() {
		return name();
	}
}
