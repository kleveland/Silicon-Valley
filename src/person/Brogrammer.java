package person;

import person.Person.Status;

// Brogrammers don't have the best Java skills, but are seemingly indestructible 
// and always step up when it's time to rally.
public class Brogrammer extends PersonImpl {
	
	static final int HP_RANGE_MIN = 110;
	static final int HP_RANGE_MAX = 180;
	static final int SKILL_RANGE_MIN = 10;
	static final int SKILL_RANGE_MAX = 40;

	public Brogrammer(String name) {
		super(name, HP_RANGE_MIN, HP_RANGE_MAX, SKILL_RANGE_MIN, SKILL_RANGE_MAX);
	}
	
}