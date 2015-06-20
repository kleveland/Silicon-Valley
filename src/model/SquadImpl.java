package model;

import java.util.Observable;

import person.Person;
import person.PersonImpl;
import supplies.Food;
import supplies.NoFoodException;
import supplies.Supplies;


public class SquadImpl implements Squad {

	private Person[] players;
	private Knapsack knapsack;
	private double balance;

	public SquadImpl(Person[] players, double balance) {
		if (players == null | players.length < 1) {
			throw new IllegalArgumentException("Must have at least 1 player on the sqaud.");
		}

		this.players = players.clone();
		knapsack = new KnapsackImpl();
		this.balance = balance;
	}

	@Override
	public int getNumPlayers() {
		return players.length;
	}

	@Override
	public Person getPlayer(int num) {
		if (num < 0 || num >= getNumPlayers()) {
			throw new IllegalArgumentException("num out of bounds");
		}
		return players[num];
	}

	@Override
	public Knapsack getKnapsack() {
		return knapsack;
	}

	@Override
	public double getBalance() {
		return balance;
	}

	@Override
	public void purchaseSupply(String supply_name, int count, Store store) throws ItemNotForSaleException, InsufficientFundsException {
		if ((count <= 0) || (supply_name == null)) {
			throw new IllegalArgumentException();
		}

		double cost = store.getPrice(supply_name)*count;
		if (cost > balance) {
			throw new InsufficientFundsException();
		}
		balance -= cost;
		Supplies[] supplies = knapsack.getSupplies();
		if(supplies.length == 0) {
			knapsack.add(store.getItem(supply_name, count));
		}
		else {
			for(int i = 0; i<supplies.length; i++) {
				if(supplies[i].getName().equals(supply_name) && !(supplies[i] instanceof Food)) {
					knapsack.remove(supplies[i]);
					knapsack.add(store.getItem(supply_name, supplies[i].getAmount() + count));
					break;
				}
				if(i == supplies.length-1) {
					knapsack.add(store.getItem(supply_name, count));
				}
			}
		}
		//knapsack.add(store.getItem(supply_name, count));
	}

	public static Squad generateSquad(int num_players) {
		Person[] players = new Person[num_players];

		for (int i=0; i<players.length; i++) {
			players[i] = PersonImpl.generatePerson();
		}

		return new SquadImpl(players, 1000.0);		
	}

	public void age() {
		for (Person p : players) {
			p.age();
		}
	}

	@Override
	public void travelUpdate(Adventure adventure, int distance_to_destination,
			City destination) {
		if (distance_to_destination != 0) {
			age();
		}
	}

	@Override
	public void feed(Food food, Person player) {
		try {
			player.eat(food);
			if (food.getAmount() == 0) {
				knapsack.remove(food);
			} 
			knapsack.notifyFoodConsumption();

		} catch (NoFoodException e) {
		}
	}
}
