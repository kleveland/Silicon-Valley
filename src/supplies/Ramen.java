package supplies;

public class Ramen extends FoodImpl {
	
	/* Named Constants */
	static final int RAMEN_WEIGHT = 1;
	static final int RAMEN_FILL = 1;
	static final int RAMEN_EXPR = 1000;
	static final String RAMEN_NAME = "ramen noodles";
	
	public Ramen(int amount) {
		super(amount, RAMEN_WEIGHT, RAMEN_NAME, RAMEN_EXPR, RAMEN_FILL);
	}
}