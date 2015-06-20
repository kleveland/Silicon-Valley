package supplies;


public abstract class SuppliesImpl implements Supplies {
	
	/* Fields */
	private int amount;
	private int pounds_per_item;
	private String name;
	
	
	/* Constructors */
	
	// Protected because this is an "abstract class"
	protected SuppliesImpl(int amount, int unit_weight, String name) {
		// Validate input
		if (amount < 0) throw new IllegalArgumentException("Amount must be non-negative!");
		if (unit_weight < 0) throw new IllegalArgumentException("Unit weight must be non-negative!");
		if (name == null) throw new IllegalArgumentException("Name must be non-null!");
		
		// Initialize object fields
		this.amount = amount;
		this.pounds_per_item = unit_weight;
		this.name = name;
	}
	
	
	/* Supplies methods */
	@Override
	public int getAmount() {
		return amount;
	}
	
	@Override
	public void setAmount(int amount) {
		if (amount < 0) throw new IllegalArgumentException("Amount must be non-negative!");
		this.amount = amount;
	}
	
	@Override
	public int getPoundsPerItem() {
		return pounds_per_item;
	}
	
	@Override
	public int getTotalWeight() {
		return amount*pounds_per_item;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return amount + " of " + name;
	}
}