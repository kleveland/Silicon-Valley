package view;

import java.util.InputMismatchException;
import java.util.Scanner;

import person.Person;
import supplies.Food;
import supplies.NoFoodException;
import supplies.Supplies;
import model.Adventure;
import model.City;
import model.InTransitException;
import model.ItemNotForSaleException;
import model.Knapsack;
import model.Squad;
import model.Store;
import model.TravelObserver;

public class AdventureConsole extends Thread implements TravelObserver {

	private Adventure adventure;

	public AdventureConsole(Adventure a) {
		adventure = a;
		adventure.addTravelObserver(this);
	}

	public void run() {
		Scanner s = new Scanner(System.in);

		String next_line = s.nextLine();
		while (!next_line.equals("exit")) {

			if (next_line.equals("status")) {
				status();
			} else if (next_line.equals("cities")) {
				list_cities();
			} else if (next_line.startsWith("travel to")) {
				travel_to(next_line);
			} else if (next_line.equals("store")) {
				list_store();
			} else if (next_line.startsWith("buy ")) {
				buy_item(next_line);
			} else if (next_line.equals("squad")) {
				squad_status();
			} else if (next_line.equals("edibles")) {
				list_edibles();
			} else if (next_line.startsWith("feed ")) {
				feed_item(next_line);
			} else {
				if (next_line.trim().equals("help")) {
					help(null);
				} else {
					help(next_line);
				}
			}

			next_line = s.nextLine();
		}
	}

	private void help(String cmd) {
		if (cmd != null) {
			System.out.println("Unrecognized command: " + cmd.trim());
		}
		System.out.println("Valid commands are: ");
		System.out.println("status");
		System.out.println("squad");
		System.out.println("cities");
		System.out.println("edibles");
		System.out.println("store");
		System.out.println("buy [COUNT] of [STORE ITEM INDEX]");
		System.out.println("travel to [CITY INDEX]");
		System.out.println("feed [EDIBLE INDEX] to [PLAYER INDEX]");
		System.out.println("help");
		System.out.println("exit");
	}
	
	private void travel_to(String travel_line) {
		try {
			int city_idx = Integer.parseInt(travel_line.substring(10));
			City[] cities = adventure.getCities();
			adventure.travel(cities[city_idx]);
		} catch (Exception e) {
		}				
	}
	
	private void feed_item(String feed_line) {
		try {
			Scanner s  = new Scanner(feed_line);

			s.next(); // "feed "
			int edible_index = s.nextInt();
			s.next(); // "to "
			int player_index = s.nextInt();

			Squad squad = adventure.getSquad();
			Knapsack k = squad.getKnapsack();
			Food[] edibles = k.getEdibleSupplies();

			squad.feed(edibles[edible_index], squad.getPlayer(player_index));
		} catch (Exception e) {
		}
	}
	
	private void list_edibles() {
		Food[] edibles = adventure.getSquad().getKnapsack().getEdibleSupplies();
		for (int i=0; i<edibles.length; i++) {
			System.out.println(i + ": " + edibles[i].toString());
		}
	}

	private void squad_status() {
		Squad s = adventure.getSquad();

		for (int i=0; i<s.getNumPlayers(); i++) {
			Person p = s.getPlayer(i);
			System.out.println(i + ": " + p.toString());
		}
		System.out.println("Balance: $" + s.getBalance());
		System.out.println("Knapsack: ");
		Knapsack k = s.getKnapsack();
		for (Supplies supply : k.getSupplies()) {
			System.out.println(supply.toString());
		}
	}

	private void buy_item(String buy_line) {
		try {
			Scanner s = new Scanner(buy_line);

			s.next(); // "buy"
			int count = s.nextInt();
			s.next(); // "of"
			int item_number = s.nextInt(); 

			Store store = adventure.getCurrentCity().getStore();
			Squad squad = adventure.getSquad();

			squad.purchaseSupply(store.getItemNames()[item_number], count, store);

		} catch(Exception e) {
		} 
	}

	private void list_store() {
		try {
			Store s = adventure.getCurrentCity().getStore();
			String[] item_names = s.getItemNames();
			for (int i=0; i < item_names.length; i++) {
				System.out.println(i + ": " + item_names[i] + " ($" + s.getPrice(item_names[i]) + ")");
			}
		} catch (InTransitException e) {
			System.out.println("No store while travelling.");
		} catch (ItemNotForSaleException e) {
		}
	}

	private void list_cities() {
		City[] cities = adventure.getCities();
		for (int i=0; i< cities.length; i++) {
			System.out.println(i + ": " + cities[i].getName());
		}
	}

	private void status() {
		try {
			String city = adventure.getCurrentCity().getName();
			System.out.println("In " + city + " on day " + adventure.getDay());
		} catch (InTransitException e) {
			System.out.println("Travelling");			
		}
	}

	@Override
	public void travelUpdate(Adventure adventure, 
			int distance_to_destination,
			City destination) {
		if (distance_to_destination == 0) {
			System.out.println("Arrived at " + destination.getName());
		} else {
			System.out.println("On day " + adventure.getDay() + " you are " + distance_to_destination + " miles from " + destination.getName());
		}
	}

}
