package supplies;

public abstract class PowerUpImpl extends FoodImpl implements PowerUp {

	private int duration;
	
	protected PowerUpImpl(int amount, int unit_weight, 
	                         String name, int days, int fill, int duration) {
		super(amount, unit_weight, name, days, fill);
		if (duration <= 0) throw new IllegalArgumentException("Duration must be positive!");
		
		this.duration = duration;
	}

	public int getDuration() {
		return duration;
	}
}