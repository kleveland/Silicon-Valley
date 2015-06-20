package person;



// Students are well-rounded and strike a balance between work and play.
public class Student extends PersonImpl {
	
	static final int HP_RANGE_MIN = 80;
	static final int HP_RANGE_MAX = 120;
	static final int SKILL_RANGE_MIN = 30;
	static final int SKILL_RANGE_MAX = 70;

	public Student(String name) {
		super(name, HP_RANGE_MIN, HP_RANGE_MAX, SKILL_RANGE_MIN, SKILL_RANGE_MAX);
	}
}