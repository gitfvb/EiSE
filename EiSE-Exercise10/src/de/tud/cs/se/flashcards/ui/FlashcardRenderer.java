package de.tud.cs.se.flashcards.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.tud.cs.se.flashcards.model.Flashcard;

public class FlashcardRenderer extends JLabel implements ListCellRenderer {

	public Component getListCellRendererComponent(
												JList list,
												Object value,
												int index,
												boolean isSelected,
												boolean cellHasFocus) {
		Flashcard f = (Flashcard) value;
		setText(f.getQuestion());
		setForeground(isSelected ? Color.blue : Color.black);
		return this;
	}

}
