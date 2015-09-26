package de.tud.cs.se.flashcards.model.strategies;

import java.util.ArrayList;
import java.util.Collections;

import de.tud.cs.se.flashcards.model.Flashcard;
import de.tud.cs.se.flashcards.model.Strategy;
import de.tud.cs.se.flashcards.model.strategies.helpers.SystematicComparator;

public class Systematic  extends Strategy  {
	
	public Systematic() {
		super("Systematic learning");
	}

	@Override
	public void changeCollection(ArrayList<Flashcard> fs) {
		Collections.sort(fs, new SystematicComparator());
	}

	
}
