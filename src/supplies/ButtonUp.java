package supplies;

public class ButtonUp extends ClothingImpl {
	
	/* Named Constants */
	static final int BUTTON_UP_WEIGHT = 5;
	static final Clothing.Warmth BUTTON_UP_WARMTH = Clothing.Warmth.MEDIUM;
	static final String BUTTON_UP_NAME = "button-up";
	
	public ButtonUp(int amount) {
		super(amount, BUTTON_UP_WEIGHT, BUTTON_UP_NAME, BUTTON_UP_WARMTH);
	}
}