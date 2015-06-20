package person;

import supplies.Food;
import supplies.NoFoodException;


// Hackers know everything there is to know about computers (or at least act like it), 
// but lack wilderness survival skills and require a strange diet.
public class Hacker extends PersonImpl {
	
	static final int HP_RANGE_MIN = 60;
	static final int HP_RANGE_MAX = 100;
	static final int SKILL_RANGE_MIN = 60;
	static final int SKILL_RANGE_MAX = 100;
	
	private boolean beast_mode;

	public Hacker(String name) {
		super(name, HP_RANGE_MIN, HP_RANGE_MAX, SKILL_RANGE_MIN, SKILL_RANGE_MAX);
		beast_mode = false;
	}
	
	@Override
	public void eat(Food food) throws NoFoodException {
		if (food == null) throw new IllegalArgumentException("Null argument");

		food.consume();	 // may throw NoFoodException
		current_hp -= food.getFill();
		if (current_hp > getMaxHP()) {
			current_hp = getMaxHP();
		}
		if (current_hp < 0) {
			current_hp = 0;
		}
	}
}