package de.tud.cs.se.flashcards.model.strategies.helpers;

import java.util.Comparator;
import java.util.Date;

import de.tud.cs.se.flashcards.model.Flashcard;

public class SystematicComparator implements Comparator<Object> {

	public int compare(Object o1, Object o2) {
		
		Flashcard f1 = ((Flashcard)o1);
		Flashcard f2 = ((Flashcard)o2);
		
		int r1 = f1.getRegister();
		int r2 = f2.getRegister();
		
		Date t1 = f1.getLastSuccess();
		Date t2 = f2.getLastSuccess();
		
		// auf jeden Fall nach Register sortieren
		if (r1 < r2)
			return 1;
		else if (r1 > r2)
			return -1;
		else {
			// falls das Datum gesetzt ist, auch noch das Datum vergleichen
			if (f1.getLastSuccess() != null && f2.getLastSuccess() != null) {
				if (t1.after(t2))
					return 1;
				else if (t1.before(t2))
					return -1;
				else
					return 0;
			} else
				return 0;
		}
		
	}      
	
}
