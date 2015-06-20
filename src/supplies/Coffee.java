package supplies;

public class Coffee extends PowerUpImpl {
	
	/* Named Constants */
	static final int COFFEE_WEIGHT = 1;
	static final int COFFEE_FILL = 0;
	static final int COFFEE_EXPR = 100;
	static final int COFFEE_DURATION = 1;
	static final String COFFEE_NAME = "coffee";
	
	public Coffee(int amount) {
		super(amount, COFFEE_WEIGHT, COFFEE_NAME, COFFEE_EXPR, 
		      COFFEE_FILL, COFFEE_DURATION);
	}
}