package de.tud.cs.se.flashcards.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.junit.*;


public class FlashcardSeriesTest {

	// Deklaration
	FlashcardSeries flashcardSeries = new FlashcardSeries(); // Eine neue Flashcard-Serie
	HashMap<String,String> hm = new HashMap<String,String>(); // Hier werden die Inhalte vorgespeichert
	List<Flashcard> flashcardArray = new ArrayList<Flashcard>(); // Eine Arraylist zur Prüfung der Flashcard-Serie
	ListDataEvent event = null;
	
	/**
	 * Das wird vor allen JUnit-Tests durchgeführt
	 */
	@Before public void initialize() {
		hm.put("lose Kopplung", "loose coupling");
		hm.put("hoher Zusammenhalt", "high cohesion");
		hm.put("Stellvertreter", "Proxy");
		hm.put("Entwurfsmuster", "Design Pattern");
		hm.put("Beispiel", "Example");
		hm.put("Haus", "House");
		hm.put("Hund", "Dog");
		
		// Füllen der Flashcard-Container
		for (String s : hm.keySet()) {
			flashcardArray.add(new Flashcard(s, hm.get(s)));
			flashcardSeries.addCard(new Flashcard(s, hm.get(s)));
		}
		
		event = null;
		
	}
	
	/**
	 * Checkt, ob das Karten-Deck beim initialisieren auch befüllt wird
	 */
	@Test public void createInitialFlashcardSeries() {
		assertTrue(FlashcardSeries.createInitialFlashcardSeries().getSize() > 0);
	}
	
	/**
	 * Gleicht die Größe des Karten-Decks mit der anfangs erstellten Hashmap ab
	 */
	@Test public void getSize() {
		assertEquals(flashcardArray.size(),flashcardSeries.getSize());
	}
	
	/**
	 * Vergleich zwei Arrays Position für Position
	 * @return true, wenn beide Arrays gleich sind
	 */
	public boolean compareArray(List<Flashcard> a1, FlashcardSeries a2) {
		boolean identical = true;
		for (int i = 0; i < a2.getSize(); i++) {
			if (a1.get(i).equals(a2.getElementAt(i))) identical = false;
		}
		return identical;
	}
	
	/**
	 * Prüft alle Karten durch, ob die auch in der ursprünglichen Hashmap enthalten sind
	 */
	@Test public void getElementAt() {
		assertTrue(compareArray(flashcardArray, flashcardSeries));
	}
	
	/**
	 * Prüft, ob die richtige Karte entfernt wurde und ob die Anzahl stimmt
	 */
	@Test public void removeCards() {
		int i[] = {0};
		flashcardArray.remove(0); // Entfernen wir mal die erste Karte
		flashcardSeries.removeCards(i);
		assertTrue(compareArray(flashcardArray, flashcardSeries));
	}
	
	/**
	 * Hinzufügen von DataListener
	 */
	@Test public void addListDataListener() {
		
		// Größe vorher speichern
		int i = flashcardSeries.getListDataListeners().length;
		
		// Erstellen und Hinzufügen eines Listeners
		ListDataListener l = new ListDataListener(){
			public void contentsChanged(ListDataEvent arg0) {}
			public void intervalAdded(ListDataEvent arg0) {}
			public void intervalRemoved(ListDataEvent arg0) {}};
		flashcardSeries.addListDataListener(l);
		
		// Prüfen, ob der Listener korrekt am Ende hinzugefügt wurde
		ListDataListener[] listDataListener = flashcardSeries.getListDataListeners();
		assertTrue(l.equals(listDataListener[listDataListener.length - 1]));
		assertEquals(i+1, listDataListener.length);
	}
	
	/**
	 * Löschen von DataListener
	 */
	@Test public void removeListDataListener() {

		// Größe vorher speichern
		int i = flashcardSeries.getListDataListeners().length;
		
		// Hinzufügen von 3 Data-Listeners
		ListDataListener l1 = new ListDataListener(){
			public void contentsChanged(ListDataEvent arg0) {}
			public void intervalAdded(ListDataEvent arg0) {}
			public void intervalRemoved(ListDataEvent arg0) {}};
		flashcardSeries.addListDataListener(l1);
		ListDataListener l2 = new ListDataListener(){
			public void contentsChanged(ListDataEvent arg0) {}
			public void intervalAdded(ListDataEvent arg0) {}
			public void intervalRemoved(ListDataEvent arg0) {}};
		flashcardSeries.addListDataListener(l2);
		ListDataListener l3 = new ListDataListener(){
			public void contentsChanged(ListDataEvent arg0) {}
			public void intervalAdded(ListDataEvent arg0) {}
			public void intervalRemoved(ListDataEvent arg0) {}};
		flashcardSeries.addListDataListener(l3);
		
		// Jetzt den ersten wieder löschen
		flashcardSeries.removeListDataListener(l2);
		
		// Jetzt noch prüfen, ob die Elemente noch die gleichen sind
		ListDataListener[] listDataListener = flashcardSeries.getListDataListeners();
		assertTrue(l1.equals(listDataListener[listDataListener.length - 2]));
		assertTrue(l3.equals(listDataListener[listDataListener.length - 1]));
		assertEquals(i+2, listDataListener.length);
	}
	
	/**
	 * Prüft, ob beim Hinzufügen das gewünscht Event ausgelöst wird
	 */
	@Test public void fireIntervalAdded() {
		
		ListDataListener l = new ListDataListener(){
			public void contentsChanged(ListDataEvent arg0) {}
			public void intervalAdded(ListDataEvent arg0) { event = arg0; }
			public void intervalRemoved(ListDataEvent arg0) {}};
		flashcardSeries.addListDataListener(l);
		flashcardSeries.fireIntervalAdded(this, 0, 1);
		
		assertEquals(event.getIndex0(), 0);
		assertEquals(event.getIndex1(), 1);
		
	}
	
	/**
	 * Prüft, ob beim Löschen das gewünschte Event ausgelöst wurde
	 */
	@Test public void fireIntervalRemoved() {
		
		ListDataListener l = new ListDataListener(){
			public void contentsChanged(ListDataEvent arg0) {}
			public void intervalAdded(ListDataEvent arg0) {}
			public void intervalRemoved(ListDataEvent arg0) { event = arg0; }};
		flashcardSeries.addListDataListener(l);
		flashcardSeries.fireIntervalRemoved(this, 0, 1);
		
		assertEquals(event.getIndex0(), 0);
		assertEquals(event.getIndex1(), 1);
		
	}
	
	
	
	
}
