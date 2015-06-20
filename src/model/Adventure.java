package model;

import java.util.ArrayList;
import java.util.List;

public class Adventure {
	private static final int TRAVEL_DELAY = 100;
	private static final int MILES_PER_DAY = 100;
	
	public enum Difficulty {EASY, MEDIUM, HARD};

	private Squad squad;
	private City[] cities;
	private City current_city;
	private int day;
	private boolean travelling;
	Difficulty mode;
	List<TravelObserver> travel_observers;

	public Adventure(int num_players) {
		this(Difficulty.HARD, SquadImpl.generateSquad(num_players));
	}

	public Adventure(Difficulty mode, Squad squad) {
		this.mode = mode;
		this.squad = squad;

		travel_observers = new ArrayList<TravelObserver>();

		City chapel_hill = new CityImpl("Chapel Hill");
		City charlotte = new CityImpl("Charlotte");
		City atlanta = new CityImpl("Atlanta");
		City dc = new CityImpl("Washington D.C.");
		City new_york = new CityImpl("New York");
		City chicago = new CityImpl("Chicago");
		City austin = new CityImpl("Austin");
		City minneapolis = new CityImpl("Minneapolis");
		City seattle = new CityImpl("Seattle");
		City sf = new CityImpl("San Francisco");
		City palo_alto = new CityImpl("Palo Alto");

		City[] all_cities = new City[] {chapel_hill, charlotte, atlanta, dc, new_york, chicago, austin, minneapolis, seattle, sf, palo_alto};

		set_distances(chapel_hill, all_cities, new int[] {0, 140, 380, 271, 500, 784, 1300, 1194, 2849, 2813, 2787});
		set_distances(charlotte,   all_cities, new int[] {140, 0, 200, 398, 625, 756, 1164, 1166, 2795, 2708, 2682});
		set_distances(atlanta,     all_cities, new int[] {380, 200, 0, 639, 864, 717, 921, 1112, 2636, 2472, 2446});
		set_distances(dc,          all_cities, new int[] {271, 298, 639, 0, 225, 699, 1524, 1106, 2761, 2817, 2844});
		set_distances(new_york,    all_cities, new int[] {500, 625, 864, 225, 0, 790, 1742, 1197, 2852, 2569, 2562});
		set_distances(chicago,     all_cities, new int[] {784, 756, 717, 699, 790, 0, 1120, 404, 2064, 2131, 2158});
		set_distances(austin,      all_cities, new int[] {1300, 1164, 921, 1524, 1742, 1120, 0, 1173, 2127, 1759, 1733});
		set_distances(minneapolis, all_cities, new int[] {1194, 1166, 1112, 1106, 1197, 404, 1173, 0, 1656, 1977, 2004});
		set_distances(seattle,     all_cities, new int[] {2849, 2795, 2636, 2761, 2852, 2064, 2127, 1656, 0, 808, 835});
		set_distances(sf,          all_cities, new int[] {2813, 2708, 2472, 2817, 2569, 2131, 1759, 1977, 808, 0, 33});
		set_distances(palo_alto,   all_cities, new int[] {2787, 2682, 2446, 2844, 2562, 2158, 1733, 2004, 835, 33, 0});

		switch (mode) {
		case EASY:
			cities = new City[4];
			break;
		case MEDIUM:
			cities = new City[7];
			break;
		case HARD:
			cities = new City[11];
		}

		for (int i=0; i<cities.length; i++) {
			cities[i] = all_cities[i];
		}

		travelling = false;
		current_city = chapel_hill;
		day = 0;

		addTravelObserver(squad.getKnapsack());
		addTravelObserver(squad);
	}

	private static void set_distances(City city, City[] all_cities, int[] distances) {
		for (int i=0; i<all_cities.length; i++) {
			city.setDistance(all_cities[i], distances[i]);
		}
	}

	public void addTravelObserver(TravelObserver observer) {
		travel_observers.add(observer);
	}

	public void removeTravelObserver(TravelObserver observer) {
		travel_observers.remove(observer);
	}

	public void travel(City next_city) {
		travel(next_city, TRAVEL_DELAY);
	}

	public void travel(City next_city, int delay) {
		travelling = true;

		final City destination = next_city;
		final int fin_delay = delay;
		final Adventure fin_adventure = this;
		
		Thread travel_thread = new Thread(new Runnable() {
			public void run() {
				int distance = current_city.getDistance(destination);
				while (distance > 0) {
					for (TravelObserver o : travel_observers) {
						o.travelUpdate(fin_adventure, distance, destination);
					}
					distance -= MILES_PER_DAY;
					day++;
					try {
						Thread.sleep(fin_delay);
					} catch (InterruptedException e) {
					}
				}
				current_city = destination;
				travelling = false;
				for (TravelObserver o : travel_observers) {
					o.travelUpdate(fin_adventure, 0, current_city);
				}
			}
		});
		travel_thread.start();
	}

	public City getCurrentCity() throws InTransitException {
		if (!travelling) {
			return current_city;
		} else {
			throw new InTransitException();
		}
	}

	public Difficulty getDifficulty() {
		return mode;
	}

	public int getDay() {
		return day;
	}

	public City[] getCities() {
		return cities.clone();
	}

	public boolean isTravelling() {
		return travelling;
	}

	public Squad getSquad() {
		return squad;
	}
}
