package de.tud.cs.se.flashcards.model;

import static org.junit.Assert.*;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.junit.*;

public class ConditionCoverageTest {
	
	// Deklaration
	FlashcardSeries flashcardSeries;
	ListDataListener l1;
	ListDataListener l2;
	ListDataListener l3;
	ListDataListener[] listDataListener;
	
	/** 
	 * Reinladen der Daten
	 */
	@Before public void initialize() {
		flashcardSeries = new FlashcardSeries();
		
		l1 = new ListDataListener(){
			public void contentsChanged(ListDataEvent arg0) {}
			public void intervalAdded(ListDataEvent arg0) {}
			public void intervalRemoved(ListDataEvent arg0) {}};
		flashcardSeries.addListDataListener(l1);
		l2 = new ListDataListener(){
			public void contentsChanged(ListDataEvent arg0) {}
			public void intervalAdded(ListDataEvent arg0) {}
			public void intervalRemoved(ListDataEvent arg0) {}};
		flashcardSeries.addListDataListener(l2);
		l3 = new ListDataListener(){
			public void contentsChanged(ListDataEvent arg0) {}
			public void intervalAdded(ListDataEvent arg0) {}
			public void intervalRemoved(ListDataEvent arg0) {}};
		flashcardSeries.addListDataListener(l3);
		
//		listDataListener = flashcardSeries.getListDataListeners();
	}
	
	/**
	 *  Entfernen bei einer leeren Liste sollte eine Exception auslösen
	 */
	@Test public void removeListenerFromEmptyList() {
		flashcardSeries = new FlashcardSeries();
		try {
			flashcardSeries.removeListDataListener(l1);
		} catch (NegativeArraySizeException e) {
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	/**
	 *  Entfernen am Anfang der Liste
	 */
	@Test public void removeAtTheBeginning() {
		flashcardSeries.removeListDataListener(l1); // Entfernen des Elements
		listDataListener = flashcardSeries.getListDataListeners();
		assertTrue(l2.equals(listDataListener[0]));
		assertTrue(l3.equals(listDataListener[1]));
	}
	
	/**
	 *  Entfernen aus der Mitte der Liste
	 */
	@Test public void removeInTheMiddle() {
		flashcardSeries.removeListDataListener(l2); // Entfernen des Elements
		listDataListener = flashcardSeries.getListDataListeners();
		assertTrue(l1.equals(listDataListener[0]));
		assertTrue(l3.equals(listDataListener[1]));
	}
	
	/**
	 *  Entfernen am Ende der Liste
	 */
	@Test public void removeAtTheEnd() {
		flashcardSeries.removeListDataListener(l3); // Entfernen des Elements
		listDataListener = flashcardSeries.getListDataListeners();
		assertTrue(l1.equals(listDataListener[0]));
		assertTrue(l2.equals(listDataListener[1]));
	}	
	
}
