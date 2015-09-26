/** License (BSD Style License):
 *  Copyright (c) 2010
 *  Software Engineering
 *  Department of Computer Science
 *  Technische Universität Darmstadt
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  - Neither the name of the Software Engineering Group or Technische 
 *    Universität Darmstadt nor the names of its contributors may be used to 
 *    endorse or promote products derived from this software without specific 
 *    prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.se.flashcards.model;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import de.tud.cs.se.flashcards.model.helpers.FlashcardEvent;
import de.tud.cs.se.flashcards.model.helpers.FlashcardEventListener;

/**
 * A very simple flashcard.
 * 
 * @author Michael Eichberg
 */
public class Flashcard implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The width of a flashcard.
	 */
	public static int WIDTH = 600;

	/**
	 * The height of a flashcard.
	 */
	public static int HEIGHT = 400;

	/**
	 * The dimension of flashcards.
	 * 
	 * @see #WIDTH
	 * @see #HEIGHT
	 */
	public static final Dimension FLASHCARD_DIMENSION = new Dimension(WIDTH,
			HEIGHT);

	private String question;

	private String answer;

	private int counter = 0;
	private int register = 1;
	private Date lastSuccess = null;
	
	private Date lastFail = null; //neu
	private Date dateCreated = null; //neu
	private int timesRememberedInARow = 0; //neu
	
	public Flashcard(String question, String answer) {

		this.question = question;
		this.answer = answer;
		this.dateCreated = new Date();
		
	}

	// convenience constructor
	public Flashcard() {

		this("", "");
	}

	public String getAnswer() {
		
		return answer;
	}

	public String getQuestion() {

		return question;
	}

	public void setAnswer(String answer) {
		if (!this.answer.contentEquals(answer)) fireMyEvent(new FlashcardEvent(answer));
		this.answer = answer;
	}

	public void setQuestion(String question) {
		if (!this.question.contentEquals(question)) fireMyEvent(new FlashcardEvent(question));
		this.question = question;
	}

	public int getCounter() {
		return counter;
	}
	
	public void increaseCounter() {
		counter++;
		fireMyEvent(new FlashcardEvent("counter"));
	}
	public void increaseSuccess() {
		if (register < 5)
			register++;
		lastSuccess = Calendar.getInstance().getTime();
		timesRememberedInARow++;
	}
	
	public void decreaseSuccess() {
		lastFail = Calendar.getInstance().getTime();
		timesRememberedInARow = 0;
	}

	public Date getLastSuccess() {
		return lastSuccess;
	}

	public int getRegister() {
		return register;
	}

	public Date getLastFail() {
		return lastFail;
	}
	
	public Date getDateCreated(){
		return dateCreated;
	}

	public int getTimesRememberedInARow() {
		return timesRememberedInARow;
	}
	
	// Create the listener list
    protected javax.swing.event.EventListenerList listenerList =
        new javax.swing.event.EventListenerList();

    // This methods allows classes to register for MyEvents
    public void addMyEventListener(FlashcardEventListener listener) {
        listenerList.add(FlashcardEventListener.class, listener);
    }

    // This methods allows classes to unregister for MyEvents
    public void removeMyEventListener(FlashcardEventListener listener) {
        listenerList.remove(FlashcardEventListener.class, listener);
    }

    // This private class is used to fire MyEvents
    void fireMyEvent(FlashcardEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==FlashcardEventListener.class) {
                ((FlashcardEventListener)listeners[i+1]).myEventOccurred(evt);
            }
        }
    }
	
}