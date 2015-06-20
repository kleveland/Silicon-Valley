package model;

import supplies.Supplies;
import java.util.List;
import java.util.ArrayList;

public class PouchImpl<E extends Supplies> implements Pouch<E> {


	private List<E> items;
	
	public PouchImpl() {
		items = new ArrayList<E>();
	}
	
	@Override
	public void add(E item) {
		items.add(item);
	}

	@Override
	public void remove(E item) {
		items.remove(item);
	}
	
	@Override
	public int getTotalWeight() {
		int total = 0;
		for (int i=0; i<items.size(); i++) {
			total += items.get(i).getTotalWeight();
		}
		return total;
	}

	@Override
	public Supplies[] getItems() {
		return items.toArray(new Supplies[items.size()]);
	}

}
