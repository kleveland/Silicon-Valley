package supplies;

public class Fleece extends ClothingImpl {
	
	/* Named Constants */
	static final int FLEECE_WEIGHT = 10;
	static final Clothing.Warmth FLEECE_WARMTH = Clothing.Warmth.WARM;
	static final String FLEECE_NAME = "fleece";
	
	public Fleece(int amount) {
		super(amount, FLEECE_WEIGHT, FLEECE_NAME, FLEECE_WARMTH);
	}
}