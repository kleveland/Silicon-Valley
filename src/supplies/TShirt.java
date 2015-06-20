package supplies;

public class TShirt extends ClothingImpl {
	
	/* Named Constants */
	static final int TSHIRT_WEIGHT = 1;
	static final Clothing.Warmth TSHIRT_WARMTH = Clothing.Warmth.LIGHT;
	static final String TSHIRT_NAME = "t-shirt";
	
	public TShirt(int amount) {
		super(amount, TSHIRT_WEIGHT, TSHIRT_NAME, TSHIRT_WARMTH);
	}
}