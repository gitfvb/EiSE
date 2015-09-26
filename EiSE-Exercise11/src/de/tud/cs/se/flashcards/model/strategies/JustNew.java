package de.tud.cs.se.flashcards.model.strategies;

import java.util.ArrayList;

import de.tud.cs.se.flashcards.model.Flashcard;
import de.tud.cs.se.flashcards.model.Strategy;
import de.tud.cs.se.flashcards.model.strategies.helpers.OnlyUnlearnedCards;
import de.tud.cs.se.flashcards.model.strategies.interfaces.CollectionFilter;

public class JustNew extends Strategy  {

	public JustNew() {
		super("Just new cards");
	}

	@Override
	public void changeCollection(ArrayList<Flashcard> fs) {
		CollectionFilter.filter(fs, new OnlyUnlearnedCards());
	}

	

}
