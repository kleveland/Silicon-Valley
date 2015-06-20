package model;

public interface City {
	int getDistance(City other);
	void setDistance(City other, int distance);
	String getName();
	Store getStore();
}