package de.tud.cs.se.flashcards.model.helpers;

import java.util.EventListener;

public interface FlashcardEventListener extends EventListener {
	public void myEventOccurred(FlashcardEvent evt);
}
