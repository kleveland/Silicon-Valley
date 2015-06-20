package supplies;

/** Root for supplies hierarchy */
public interface Supplies {
	int getAmount();
	void setAmount(int amount);
	
	int getPoundsPerItem();
	int getTotalWeight();
	
	String getName();
}