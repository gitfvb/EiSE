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
		setOpaque(true);
		
		Color background = Color.white;
		Color foreground = Color.black;
		
		// yellow
		if (f.getLastSuccess() != null) {
			background = Color.yellow;
			foreground = Color.black;
		}
		
		// green
		if (f.getTimesRememberedInARow() >= 2) {
			background = Color.green;
			foreground = Color.black;
		}
		
		// rot
		if (f.getLastFail() != null && f.getLastSuccess() != null) {
			if (f.getLastFail().after(f.getLastSuccess())) {
				background = Color.red;
				foreground = Color.black;
			}
		} else if (f.getLastFail() != null && f.getLastSuccess() == null) {
			background = Color.red;
			foreground = Color.black;
		}
		
		setBackground(isSelected ? Color.blue : background);
		setForeground(isSelected ? background : foreground);
		
		return this;
	}

}
