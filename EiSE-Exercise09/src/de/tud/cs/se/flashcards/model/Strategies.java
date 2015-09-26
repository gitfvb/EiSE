package de.tud.cs.se.flashcards.model;

import java.util.ArrayList;

import de.tud.cs.se.flashcards.model.strategies.JustNew;
import de.tud.cs.se.flashcards.model.strategies.NewestCardsFirst;
import de.tud.cs.se.flashcards.model.strategies.OldestCardsFirst;
import de.tud.cs.se.flashcards.model.strategies.Quiz;
import de.tud.cs.se.flashcards.model.strategies.Systematic;

public class Strategies  {

	private ArrayList<Strategy> list;
	
	public Strategies() {
		
		list = new ArrayList<Strategy>();
		list.add(new NewestCardsFirst("Newest cards first"));
		list.add(new OldestCardsFirst("Oldest cards first"));
		list.add(new JustNew("Just new cards"));
		list.add(new Quiz("Just learned cards"));
		list.add(new Systematic("Systematic learning"));
		
	}
	
	public ArrayList<Strategy> getStrategies() {
		return this.list;
	}
	
	public Strategy getStrategy(String name) {
		for (Strategy s : list)
			if(s.getName() == name)
				return s;
		return null;
	}
	
}
