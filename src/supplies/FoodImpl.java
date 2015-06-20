package supplies;

/** Common parent class for all Food classes. */
public abstract class FoodImpl extends SuppliesImpl implements Food {

	/* Fields */
	private int days_till_expiration;
	private int fill;
	
	
	/* Constructors */
	protected FoodImpl(int amount, int unit_weight, 
	                   String name, int days, int fill) {
		super(amount, unit_weight, name);
		// Validate input
		if (days <= 0) throw new IllegalArgumentException("Days till expiration must be positive!");
		// Fill can be anything!
		
		// Initialize fields
		this.days_till_expiration = days;
		this.fill = fill;
	}
	
	
	/* Food methods */
	@Override
	public void consume() throws NoFoodException {
		if (getAmount() == 0) throw new NoFoodException();
		setAmount(getAmount() - 1);
	}
	
	@Override
	public int getDaysTillExpiration() {
		return days_till_expiration;
	}
	
	@Override
	public void age() throws FoodExpiredException {
		days_till_expiration -= 1;
		if (days_till_expiration <= 0) throw new FoodExpiredException();
	}
	
	@Override
	public int getFill() {
		return fill;
	}
	
	@Override
	public String toString() {
		return super.toString() + " (expires in " + getDaysTillExpiration() + " days)";
	}
}