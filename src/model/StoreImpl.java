package model;

import java.util.HashMap;
import java.util.Map;

import supplies.*;

public class StoreImpl implements Store {

	private Map<String, Double> prices;
	
	public StoreImpl() {
		prices = new HashMap<String, Double>();
		
		prices.put("bison jerky", 2.0);
		prices.put("burrito", 4.0);
		prices.put("button-up", 10.0);
		prices.put("coffee", 2.50);
		prices.put("fleece", 12.00);
		prices.put("ramen noodles", 0.50);
		prices.put("Red Bison", 3.25);
		prices.put("sushi", 3.75);
		prices.put("t-shirt", 5.00);
	}

	@Override
	public double getPrice(String item_name) throws ItemNotForSaleException {
		if (!prices.containsKey(item_name)) {
			throw new ItemNotForSaleException();
		}
		return prices.get(item_name);
	}

	@Override
	public String[] getItemNames() {
		return prices.keySet().toArray(new String[0]);
	}

	@Override
	public Supplies getItem(String supply_name, int count) throws ItemNotForSaleException {
		switch (supply_name) {
		case "bison jerky":
			return new BisonJerky(count);
			
		case "burrito":
			return new Burrito(count);
			
		case "button-up":
			return new ButtonUp(count);
			
		case "coffee":
			return new Coffee(count);
			
		case "fleece":
			return new Fleece(count);
			
		case "ramen noodles":
			return new Ramen(count);
			
		case "Red Bison":
			return new RedBison(count);
			
		case "sushi":
			return new Sushi(count);
			
		case "t-shirt":
			return new TShirt(count);
		}
		throw new ItemNotForSaleException();
	}
}
