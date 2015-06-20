package supplies;

public class RedBison extends PowerUpImpl {
	
	/* Named Constants */
	static final int RED_BISON_WEIGHT = 2;
	static final int RED_BISON_FILL = -10;
	static final int RED_BISON_EXPR = 100;
	static final int RED_BISON_DURATION = 5;
	static final String RED_BISON_NAME = "Red Bison";
	
	public RedBison(int amount) {
		super(amount, RED_BISON_WEIGHT, RED_BISON_NAME, 
		              RED_BISON_EXPR, RED_BISON_FILL, RED_BISON_DURATION);
	}
}