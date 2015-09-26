import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.tud.cs.se.flashcards.model.Flashcard;
import de.tud.cs.se.flashcards.model.FlashcardSeries;
import de.tud.cs.se.flashcards.model.Strategies;

public class StrategiesTest {

	HashMap<String, String> hm1 = new HashMap<String, String>();
	ArrayList<Flashcard> flashcardArray1 = new ArrayList<Flashcard>();
	ArrayList<Flashcard> flashcardArray2 = new ArrayList<Flashcard>();
	Strategies s;

	/**
	 * Das wird vor allen JUnit-Tests durchgeführt
	 */
	@Before
	public void createData() {
		s = new Strategies();

		hm1.put("Eins", "one");
		hm1.put("Zwei", "two");
		hm1.put("Drei", "three");

		fillFlashcardSeries(hm1, flashcardArray1, flashcardArray2);

	}

	/**
	 * 
	 * Methode zum Befüllen der Flashcard-Serien
	 * 
	 * @param hm
	 *            Die Hashmap, die das Array und die Serie befüllen
	 * @param flashcardSeries
	 *            Die zu füllende Serie
	 * @param flashcardArray
	 *            Das zu füllende Array
	 */
	private void fillFlashcardSeries(HashMap<String, String> hm,
			ArrayList<Flashcard> a, ArrayList<Flashcard> b) {
		for (String s : hm.keySet()) {
			a.add(new Flashcard(s, hm.get(s)));
			b.add(new Flashcard(s, hm.get(s)));
		}
	}

	@Test
	public void testNewestCardsFirst() {

		// die array-liste mittels der strategie sortieren
		s.getStrategy("Newest cards first").changeCollection(flashcardArray1);
		// hier nicht tun, da NewestCardsFirst keine Aenderung hervorruft.

		// hier nun vergleichen
		assertTrue(compareArrays(flashcardArray1, flashcardArray2));
	}

	@Test
	public void testOldestCardsFirst() {

		// die array-liste mittles der strategie sortieren
		s.getStrategy("Oldest cards first").changeCollection(flashcardArray1);
		// die kopie jetzt noch haendisch umstellen mittels collections.reverse
		Collections.reverse(flashcardArray2);
		// hier nun vergleichen
		assertTrue(compareArrays(flashcardArray1, flashcardArray2));
	}

	private boolean compareArrays(ArrayList<Flashcard> f1,
			ArrayList<Flashcard> f2) {
		boolean identical = true;

		for (int i = 0; i < f1.size(); i++)
			if (f1.get(i).getQuestion().equals(f2.get(i).getQuestion()) == false)
				identical = false;

		if (f1.size() != f2.size())
			identical = false;

		return identical;
	}

}
