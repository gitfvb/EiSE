package de.tud.cs.se.flashcards.model.helpers;

import java.util.EventObject;

public class FlashcardEvent extends EventObject {
    
	private static final long serialVersionUID = 1L;

	public FlashcardEvent(Object source) {
        super(source);
    }
}
