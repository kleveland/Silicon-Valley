package model;

import java.util.HashMap;
import java.util.Map;

public class CityImpl implements City {

	private String name;
	private Map<City,Integer> distances;
	
	public CityImpl(String name) {
		this.name = name;
		distances = new HashMap<City,Integer>();
	}

	@Override
	public int getDistance(City other) {
		if (distances.containsKey(other)) {
			return distances.get(other);
		}
		throw new RuntimeException("Don't know distance to " + other.getName());
	}

	@Override
	public void setDistance(City other, int distance) {
		distances.put(other, distance);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Store getStore() {
		return new StoreImpl();
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
