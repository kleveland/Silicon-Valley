package person;

import java.util.Observable;

import supplies.Food;
import supplies.NoFoodException;

public abstract class PersonImpl extends Observable implements Person {
	
	/* Instance variables */
	private Person.Status status;
	protected int current_hp;  // Between 0 and maxHP
	private int max_hp;
	protected int skill;       // The person's Java Skill
	private String name;
	
	/* Constructors */
	
	// Initializes max_hp and skill to a random value in the given range
	protected PersonImpl(String name, int hp_range_min, int hp_range_max, 
	                     int skill_range_min, int skill_range_max) {										 
		if (name == null
				|| hp_range_min < 0
				|| hp_range_max < 0
				|| skill_range_min < 0
				|| skill_range_max < 0
				|| hp_range_max < hp_range_min  
				|| skill_range_max < skill_range_min)
			throw new IllegalArgumentException();
			
		this.name = name;
		max_hp = randomInt(hp_range_min, hp_range_max);
		skill  = randomInt(skill_range_min, skill_range_max);
		current_hp = max_hp;
		status = Person.Status.HEALTHY;
	}
	
	
	/* Person interface methods */
	public int getCurrentHP() {
		return current_hp;
	}
	
	public int getMaxHP() {
		return max_hp;
	}
	
	public int getSkill() {
		return skill;
	}
	
	public void eat(Food food) throws NoFoodException {
		if (food == null) throw new IllegalArgumentException("Null argument");

		food.consume();	 // may throw NoFoodException
		current_hp += food.getFill();

		if (current_hp > getMaxHP()) {
			current_hp = getMaxHP();
		} else if (current_hp < 0) {
			current_hp = 0;
		}
		if (current_hp == getMaxHP()) {
			status = Person.Status.HEALTHY;
		}		
		setChanged();
		notifyObservers();
	}
	
	public Person.Status getStatus() {
		return status;
	}
	
	public void setStatus(Person.Status new_status) {
		status = new_status;
		setChanged();
		notifyObservers();
	}
	
	public String getName() {
		return name;
	}

	public void age() {
		if (Math.random() < 0.5) {
			current_hp--;
			setChanged();
		}
		if (status == Person.Status.HEALTHY) {
			if (current_hp < 50) {
				if (Math.random() < 0.1) {
					status = Person.Status.NOROVIRUS;
					setChanged();
				}
			}
		} else if (status == Person.Status.NOROVIRUS) {
			if (current_hp < 20) {
				if (Math.random() < 0.2) {
					status = Person.Status.DYSENTERY;
					setChanged();
				}
			}
		}
		notifyObservers();
	}	

	// Helper method for generating a random int in [a, b]
	private static int randomInt(int a, int b) {
		if (b > a) return randomInt(b, a);
		
		double x = Math.random();
		// Map [0, 1) to [a, b+1) linearly and round down
		int diff = Math.abs(b-a);
		return (int) Math.floor(diff*x + a);
	}


	public static Person generatePerson() {
		String[] first_names = {
				"Alice",
				"Bob",
				"Chetan",
				"Wei",
				"Min-Soo",
				"Mary",
				"Dave",
				"Mike",
				"Lachina"};
		String[] last_names = {
				"Perry",
				"Jones",
				"Carmichael",
				"Shah",
				"Kum",
				"Lee",
				"Carter",
				"Au",
				"Wilson"};
		
		String name = first_names[(int)(Math.random()*first_names.length)] 
				+ " " + last_names[(int)(Math.random()*last_names.length)];
		
		switch ((int) (Math.random()*3)) {
		case 0:
			return new Student(name);
		case 1:
			return new Brogrammer(name);
		default:
			return new Hacker(name);
		}
	}	
	
	@Override
	public String toString() {
		return "<html><u><b>" + getName() + "</b></u> (<FONT COLOR=red>HP: " + getCurrentHP() + "/" + getMaxHP() + "</FONT>, <FONT COLOR=blue>Skill: " + getSkill() + "</FONT>, <FONT COLOR=green>" + getStatus().toString() + "</FONT>)</html>";
	}
}