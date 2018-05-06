package enums;

public enum NumberType {
	ONE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5),
	SIX(6),
	SEVEN(7),
	EIGHT(8),
	NINE(9);

	private final int index;

    private NumberType(final int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

	@Override
	public String toString() {
		return name();
	}
}
