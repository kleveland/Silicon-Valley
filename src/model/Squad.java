package model;

import java.util.Observer;

import javax.swing.ComboBoxModel;

import person.Person;
import supplies.Food;


public interface Squad extends TravelObserver {
	int getNumPlayers();
	Person getPlayer(int num);
	Knapsack getKnapsack();
	double getBalance();
	void purchaseSupply(String supply_name, int count, Store store) throws ItemNotForSaleException, InsufficientFundsException;
	void age();
	void feed(Food food, Person player);
}
