package de.tud.cs.se.flashcards.model;

import static org.junit.Assert.*;

import org.junit.*;

/**
 * 
 * Hier ist schonmal eine erste Version, um sich in JUnit schon mal wieder einzufinden. Man sieht ganz einfach,
 * wie man die Tests machen kann. Und auch die Coverage beträgt bei diesem Test schon 100 %.
 * 
 * @author flow
 *
 */
public class FlashcardTest {
	
	Flashcard f = new Flashcard();
	String answer = "antwort";
	String question = "frage";

	/**
	 * Testet, ob die Antwort bei Initialisierung auch leer ist
	 */
	@Test public void getInitializedFlashcardAnswer() {
		assertEquals("", f.getAnswer());
	}
	
	/**
	 * Testet, ob die Frage bei Initialisierung auch leer ist
	 */
	@Test public void getInitializedFlashcardQuestion() {
		assertEquals("", f.getQuestion());
	}
	
	/**
	 * Speichern, Laden und Prüfen der Antwort
	 */
	@Test public void getAnswer() {
		f.setAnswer(answer);
		assertEquals(answer, f.getAnswer());
	}

	/**
	 * Speichern, Laden und Prüfen der Frage
	 */
	@Test public void getQuestion() {
		f.setQuestion(question);
		assertEquals(question, f.getQuestion());
	}
	
}
