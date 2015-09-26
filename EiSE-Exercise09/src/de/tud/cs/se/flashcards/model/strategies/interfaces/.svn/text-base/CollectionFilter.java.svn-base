package de.tud.cs.se.flashcards.model.strategies.interfaces;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionFilter {
	
	ArrayList<FilterCriteria> filterList = new ArrayList<FilterCriteria>();
	
	public void filter(Collection<?> c) {
		Collection<Object> del = new ArrayList<Object>();
		
		for (Object o : c)
			for (FilterCriteria f : filterList) 
				if (f.passes(o) == false) {
					del.add(o);
					break;
				}
		
		c.removeAll(del);
	}
	
	public void addFilterCriteria(FilterCriteria filterCriteria) {
		filterList.add(filterCriteria);
	}
	
	
	public static void filter(Collection<?> c, FilterCriteria filterCriteria) {
		CollectionFilter cFilter = new CollectionFilter();
		cFilter.addFilterCriteria(filterCriteria);
		cFilter.filter(c);
	}
	
	
	
	
}
