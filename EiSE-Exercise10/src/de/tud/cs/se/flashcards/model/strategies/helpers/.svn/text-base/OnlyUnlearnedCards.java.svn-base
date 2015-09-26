package de.tud.cs.se.flashcards.model.strategies.helpers;

import de.tud.cs.se.flashcards.model.Flashcard;
import de.tud.cs.se.flashcards.model.strategies.interfaces.FilterCriteria;

public class OnlyUnlearnedCards implements FilterCriteria {

	public boolean passes(Object o) {
		
		int count = ((Flashcard)o).getCounter();
		
		if (count == 0)
			return true;
		else
			return false;
	}
	
	
	
}
