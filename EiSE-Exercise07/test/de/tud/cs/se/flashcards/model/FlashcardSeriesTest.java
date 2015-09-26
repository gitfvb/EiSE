package de.tud.cs.se.flashcards.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.junit.Before;
import org.junit.Test;


public class FlashcardSeriesTest {

	// Deklaration
	
	HashMap<String,String> hm1 = new HashMap<String,String>(); // Hier werden die Inhalte vorgespeichert
	HashMap<String,String> hm2 = new HashMap<String,String>(); // Hier werden die Inhalte vorgespeichert
	HashMap<String,String> hm3 = new HashMap<String,String>(); // Hier werden die Inhalte vorgespeichert
	HashMap<String,String> hmMerged = new HashMap<String,String>(); // Hier werden die Inhalte vorgespeichert
	
	FlashcardSeries flashcardSeries1 = new FlashcardSeries(); // Eine neue Flashcard-Serie
	FlashcardSeries flashcardSeries2 = new FlashcardSeries(); // Eine neue Flashcard-Serie
	FlashcardSeries flashcardSeries3 = new FlashcardSeries(); // Eine neue Flashcard-Serie
	FlashcardSeries flashcardSeriesMerged = new FlashcardSeries(); // Eine neue Flashcard-Serie
	
	List<Flashcard> flashcardArray1 = new ArrayList<Flashcard>(); // Eine Arraylist zur Prüfung der Flashcard-Serie
	List<Flashcard> flashcardArray2 = new ArrayList<Flashcard>(); // Eine Arraylist zur Prüfung der Flashcard-Serie
	List<Flashcard> flashcardArray3 = new ArrayList<Flashcard>(); // Eine Arraylist zur Prüfung der Flashcard-Serie
	List<Flashcard> flashcardArrayMerged = new ArrayList<Flashcard>(); // Eine Arraylist zur Prüfung der Flashcard-Serie
	
	int collisions = 0;
	
	/**
	 * Das wird vor allen JUnit-Tests durchgeführt
	 */
	@Before public void initialize() {
		
		// Erstellen der Flashcards
		
		// Erstes Deck - Grundlagen-Deck
		hm1.put("Eins", "one");
		hm1.put("Zwei", "zwei");
		hm1.put("Drei", "drei");
		
		// Zweites Deck - Kollidiert nicht mit dem ersten Deck
		hm2.put("Vier", "four");
		hm2.put("Fünf", "five");
		hm2.put("Sechs", "six");
		
		// Drittes Deck - Enthält Dubletten und noch unbekannte Teile
		hm3.put("Sieben", "seven");
		hm3.put("Zwei", "deux");
		hm3.put("Vier", "quatre");
		
		// Zusammenfügen der Decks (Dubletten werden nicht eingefügt)
		hmMerged.putAll(hm1);
		hmMerged.putAll(hm2); // sollte keine Kollisionen verursachen
		for (String s : hm3.keySet()) {
			if (hmMerged.containsKey(s) == false) {
				hmMerged.put(s, hm3.get(s));
			} else {
				collisions++; // Setzt den Zähler hoch, dass eine Kollision stattgefunden hat
			}
		}
		
		// Füllen der Flashcard-Container
		fillFlashcardSeries(hm1, flashcardSeries1, flashcardArray1);
		fillFlashcardSeries(hm2, flashcardSeries2, flashcardArray2);
		fillFlashcardSeries(hm3, flashcardSeries3, flashcardArray3);
		fillFlashcardSeries(hmMerged, flashcardSeriesMerged, flashcardArrayMerged);
	}
	
	/**
	 * 
	 * Methode zum Befüllen der Flashcard-Serien
	 * 
	 * @param hm Die Hashmap, die das Array und die Serie befüllen
	 * @param flashcardSeries Die zu füllende Serie
	 * @param flashcardArray Das zu füllende Array
	 */
	private void fillFlashcardSeries(HashMap<String,String> hm, FlashcardSeries flashcardSeries, List<Flashcard> flashcardArray) {
		for (String s : hm.keySet()) {
			flashcardArray.add(new Flashcard(s, hm.get(s)));
			flashcardSeries.addCard(new Flashcard(s, hm.get(s)));
		}
	}
	
	
	/**
	 * Prüft, ob der Import ohne Kollisionen korrekt war
	 */
	@Test public void importFlashcardSeriesWithoutCollisions() {
		int size1 = flashcardSeries1.getSize();
		int size2 = flashcardSeries2.getSize();
		
		// Decks zusammenfügen
		flashcardSeries1.importFlashcardSeries(flashcardSeries2);
		
		// Größe prüfen nach Import
		assertEquals(size1 + size2, flashcardSeries1.getSize());
		
	}
	
	/**
	 * Prüft, ob der Import mit Kollisionen korrekt war
	 */
	@Test public void importFlashcardSeriesWithCollisions() {
		// Decks zusammenfügen
		flashcardSeries1.importFlashcardSeries(flashcardSeries2);
		flashcardSeries1.importFlashcardSeries(flashcardSeries3);
		
		// Größe vergleichen zwischen der importierten Serie und der bereits bestehenden Serie aus dem Before-Teil
		assertEquals(flashcardSeries1.getSize(), flashcardSeriesMerged.getSize());

		// Prüfen, ob noch alle ursprünglichen Karten enthalten sind
		assertTrue(compareArray(flashcardArrayMerged, flashcardSeries1));
		
	}
	
	/**
	 * Gleicht die Größe des Karten-Decks gegenseitig ab
	 */
	@Test public void getSize() {
		assertEquals(flashcardArray1.size(),flashcardSeries1.getSize());
		assertEquals(flashcardArray2.size(),flashcardSeries2.getSize());
		assertEquals(flashcardArray3.size(),flashcardSeries3.getSize());
		assertEquals(flashcardArrayMerged.size(),flashcardSeriesMerged.getSize());
		assertEquals(hm1.size() ,flashcardSeries1.getSize());
		assertEquals(hm2.size(),flashcardSeries2.getSize());
		assertEquals(hm3.size(),flashcardSeries3.getSize());
		assertEquals(hmMerged.size(),flashcardSeriesMerged.getSize());
	}
	
	/**
	 * Vergleich zwei Arrays Position für Position
	 * @return true, wenn beide Arrays gleich sind
	 */
	private boolean compareArray(List<Flashcard> a1, FlashcardSeries a2) {
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
		assertTrue(compareArray(flashcardArray1, flashcardSeries1));
		assertTrue(compareArray(flashcardArray2, flashcardSeries2));
		assertTrue(compareArray(flashcardArray3, flashcardSeries3));
		assertTrue(compareArray(flashcardArrayMerged, flashcardSeriesMerged));
	}	
	
}
