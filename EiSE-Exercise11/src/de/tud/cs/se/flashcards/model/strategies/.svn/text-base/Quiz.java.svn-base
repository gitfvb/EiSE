package de.tud.cs.se.flashcards.model.strategies;

import java.util.ArrayList;

import de.tud.cs.se.flashcards.model.Flashcard;
import de.tud.cs.se.flashcards.model.Strategy;
import de.tud.cs.se.flashcards.model.strategies.helpers.OnlyLearnedCards;
import de.tud.cs.se.flashcards.model.strategies.interfaces.CollectionFilter;

public class Quiz  extends Strategy  {

	public Quiz() {
		super("Just learned cards");
	}

	@Override
	public void changeCollection(ArrayList<Flashcard> fs) {
		CollectionFilter.filter(fs, new OnlyLearnedCards());
	}

	

}
