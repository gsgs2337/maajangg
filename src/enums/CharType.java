package enums;

public enum CharType {
	TON(1),
	NAN(2),
	SHAH(3),
	PEH(4),
	HAKU(5),
	HATSU(6),
	CHUN(7);

	private final int index;

    private CharType(final int id) {
        this.index = id;
    }

    public int getIndex() {
        return index;
    }
	@Override
	public String toString() {
		return name();
	}
}
