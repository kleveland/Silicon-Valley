package supplies;

public class Sushi extends FoodImpl {
	
	/* Named Constants */
	static final int SUSHI_WEIGHT = 3;
	static final int SUSHI_FILL = 15;
	static final int SUSHI_EXPR = 20;
	static final String SUSHI_NAME = "sushi";
	
	public Sushi(int amount) {
		super(amount, SUSHI_WEIGHT, SUSHI_NAME, SUSHI_EXPR, SUSHI_FILL);
	}
}