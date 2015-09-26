package de.tud.cs.se.flashcards.model.strategies;

import java.util.ArrayList;

import de.tud.cs.se.flashcards.model.Flashcard;
import de.tud.cs.se.flashcards.model.Strategy;

public class NewestCardsFirst extends Strategy {

	public NewestCardsFirst() {
		super("Newest cards first");
	}

	@Override
	public void changeCollection(ArrayList<Flashcard> fs) {
		
	}

}
