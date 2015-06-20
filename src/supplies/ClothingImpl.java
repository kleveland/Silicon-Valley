package supplies;

public abstract class ClothingImpl extends SuppliesImpl implements Clothing {
	
	private Clothing.Warmth warmth;
	
	protected ClothingImpl(int amount, int unit_weight, String name, Clothing.Warmth warmth) {
		super(amount, unit_weight, name);
		this.warmth = warmth;
	}
	
	public Clothing.Warmth getWarmth() {
		return warmth;
	}
}