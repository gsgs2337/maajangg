package pai;

import javax.swing.ImageIcon;

import enums.CharType;
import enums.NumberType;
import enums.PaiType;


public class Pai{

	/** 萬・筒・索・字 */
	private PaiType paiType;

	private CharType charType;

	private NumberType numberType;

	private ImageIcon image;

	private ImageIcon imageYoko;
	
	private boolean isYaochu;

	private final String imagePath = "C:\\pleiades\\workspace\\Mar-jangg\\image\\pai-images\\pai-images\\";
	private final String imageNameSuffix = "-66-90-s.png";
	private final String imageNameYokoSuffix = "-66-90-s-yoko.png";

	private Pai() {
		super();
	}

	public Pai(PaiType paiType, NumberType numberType) {
		this();
		this.paiType = paiType;
		this.numberType = numberType;
		this.image = new ImageIcon(imagePath + paiType.getCode() + numberType.getIndex() + imageNameSuffix);
		this.imageYoko = new ImageIcon(imagePath + paiType.getCode() + numberType.getIndex() + imageNameYokoSuffix);
		if(numberType.getIndex() == 1 || numberType.getIndex() == 9) {
			this.isYaochu = true;
		}
	}

	public Pai(PaiType paiType, CharType charType) {
		this();
		this.paiType = paiType;
		this.charType = charType;
		this.image = new ImageIcon(imagePath + paiType.getCode() + charType.getIndex() + imageNameSuffix);
		this.imageYoko = new ImageIcon(imagePath + paiType.getCode() + charType.getIndex() + imageNameYokoSuffix);
		this.isYaochu = true;
	}
	
	public PaiType getPaiType() {
		return paiType;
	}

	public void setPaiType(PaiType paiType) {
		this.paiType = paiType;
	}

	public CharType getCharType() {
		return charType;
	}

	public void setCharType(CharType charType) {
		this.charType = charType;
	}

	public NumberType getNumberType() {
		return numberType;
	}

	public void setNumberType(NumberType numberType) {
		this.numberType = numberType;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public boolean isYaochu() {
		return this.isYaochu;		
	}

	@Override
	public String toString() {
		String format = "type: "+this.paiType + ", contents: ";

		if(this.charType != null) {
			return format + this.charType.toString();
		}else if(this.numberType != null) {
			return format + this.numberType.toString();
		}
		return format + "?";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((charType == null) ? 0 : charType.hashCode());
		result = prime * result + ((numberType == null) ? 0 : numberType.hashCode());
		result = prime * result + ((paiType == null) ? 0 : paiType.hashCode());
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
		Pai other = (Pai) obj;
		if (charType != other.charType)
			return false;
		if (numberType != other.numberType)
			return false;
		if (paiType != other.paiType)
			return false;
		return true;
	}
	


}
