package model;

import java.util.Observable;

import supplies.Clothing;
import supplies.Food;
import supplies.FoodExpiredException;
import supplies.PowerUp;
import supplies.Supplies;

public class KnapsackImpl extends Observable implements Knapsack {

	private Pouch<Food> food_pouch;
	private Pouch<PowerUp> powerup_pouch;
	private Pouch<Clothing> clothing_pouch;

	public KnapsackImpl() {
		food_pouch = new PouchImpl<Food>();
		powerup_pouch = new PouchImpl<PowerUp>();
		clothing_pouch = new PouchImpl<Clothing>();
	}

	@Override
	public void add(Food item) {
		if (item instanceof PowerUp) {
			powerup_pouch.add((PowerUp) item);
		} else {
			food_pouch.add(item);
		}
		setChanged();
		notifyObservers();
	}

	@Override
	public void add(PowerUp item) {
		powerup_pouch.add(item);
		setChanged();
		notifyObservers();
	}

	@Override
	public void add(Clothing item) {
		clothing_pouch.add(item);
		setChanged();
		notifyObservers();
	}

	@Override
	public void add(Supplies item) {
		if (item instanceof Clothing) {
			add((Clothing) item);
		} else if (item instanceof Food) {
			add((Food) item);
		}
	}

	@Override
	public int getTotalWeight() {
		return (food_pouch.getTotalWeight() +
				powerup_pouch.getTotalWeight() +
				clothing_pouch.getTotalWeight());
	}

	@Override
	public Supplies[] getSupplies() {
		Supplies[] food_pouch_items = food_pouch.getItems();
		Supplies[] powerup_pouch_items = powerup_pouch.getItems();
		Supplies[] clothing_pouch_items = clothing_pouch.getItems();

		Supplies[] supplies = new Supplies[food_pouch_items.length + powerup_pouch_items.length + clothing_pouch_items.length];
		int i=0;
		for (int j=0; j<food_pouch_items.length; j++) {
			supplies[i] = food_pouch_items[j];
			i++;
		}
		for (int j=0; j<powerup_pouch_items.length; j++) {
			supplies[i] = powerup_pouch_items[j];
			i++;
		}
		for (int j=0; j<clothing_pouch_items.length; j++) {
			supplies[i] = clothing_pouch_items[j];
			i++;
		}
		return supplies;
	}

	@Override
	public Food[] getEdibleSupplies() {
		Supplies[] food_pouch_items = food_pouch.getItems();
		Supplies[] powerup_pouch_items = powerup_pouch.getItems();

		Food[] edible_supplies = new Food[food_pouch_items.length + powerup_pouch_items.length];
		int i=0;
		for (int j=0; j<food_pouch_items.length; j++) {
			edible_supplies[i] = (Food) food_pouch_items[j];
			i++;
		}
		for (int j=0; j<powerup_pouch_items.length; j++) {
			edible_supplies[i] = (Food) powerup_pouch_items[j];
			i++;
		}
		return edible_supplies;
	}

	@Override
	public void travelUpdate(Adventure adventure, int distance_to_destination,
			City destination) {
		if (distance_to_destination > 0) {
			for (Food f : getEdibleSupplies()) {
				try {
					f.age();
				} catch (FoodExpiredException e) {
					remove(f);
				}
			}
			setChanged();
			notifyObservers();
		}
	}

	@Override
	public void remove(Food item) {
		if (item instanceof PowerUp) {
			powerup_pouch.remove((PowerUp) item);
		} else {
			food_pouch.remove(item);
		}
		setChanged();
		notifyObservers();
	}

	@Override
	public void remove(PowerUp item) {
		powerup_pouch.remove(item);
		setChanged();
		notifyObservers();
	}

	@Override
	public void remove(Clothing item) {
		clothing_pouch.remove(item);
		setChanged();
		notifyObservers();
	}

	@Override
	public void remove(Supplies item) {
		if (item instanceof Clothing) {
			remove((Clothing) item);
		} else if (item instanceof Food) {
			remove((Food) item);
		}
	}
	
	@Override
	public void notifyFoodConsumption() {
		setChanged();
		notifyObservers();
	}
}
