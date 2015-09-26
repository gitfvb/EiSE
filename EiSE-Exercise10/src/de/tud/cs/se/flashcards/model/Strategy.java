package de.tud.cs.se.flashcards.model;

import java.util.ArrayList;

import de.tud.cs.se.flashcards.model.strategies.interfaces.StrategyInterface;

public abstract class Strategy implements StrategyInterface {

	private String name;
	
	public Strategy(String name) {
		setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return this.name;
	}
	
	public abstract void changeCollection(ArrayList<Flashcard> fs);

	
	
}
