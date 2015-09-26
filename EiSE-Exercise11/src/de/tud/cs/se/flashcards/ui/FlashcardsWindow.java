/** License (BSD Style License):
 *  Copyright (c) 2010
 *  Software Engineering
 *  Department of Computer Science
 *  Technische UniversitÃ¤t Darmstadt
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
 *    UniversitÃ¤t Darmstadt nor the names of its contributors may be used to 
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
package de.tud.cs.se.flashcards.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.tud.cs.se.flashcards.model.Flashcard;
import de.tud.cs.se.flashcards.model.FlashcardSeries;
import de.tud.cs.se.flashcards.model.Strategies;
import de.tud.cs.se.flashcards.persistence.Store;

/**
 * A Frame is always associated with exactly one document and it is the parent
 * of all related dialogs etc.
 * 
 * @author Michael Eichberg
 * @author Ralf Mitschke
 */
public class FlashcardsWindow {

	// The UI components:

	private final JFrame frame;

	private final JMenuBar menuBar;

	private final JMenu fileMenu;

	private final JMenuItem newFileMenuItem;

	private final JMenuItem openFileMenuItem;

	private final JMenuItem importFileMenuItem;
	
	private final JMenuItem saveFileMenuItem;

	private final JMenuItem saveAsFileMenuItem;

	private final JMenuItem closeFileMenuItem;

	private final JToolBar toolbar;

	private final JButton addButton;

	private final JButton removeButton;

	private final JButton editButton;

	private final JButton playButton;

	private final JScrollPane listScrollPane;

	private final JList list;

	private final FlashcardEditor flashcardEditor;

	private final LearnDialog learnDialog;

	private final FileDialog fileDialog; 
	
	private boolean contentsChanged = false;
	
//	--------------------------------------------------------------------------
	JLabel dateCreatedLabel;
	JLabel lastTimeNotRemeberedLabel;
	JLabel lastTimeRememberedLabel;
	JLabel numberOfTimesShownLabel;
//	JLabel numberOfTimesRememberedInARowLabel;

	// State of the editor:

	private final FlashcardSeries series;
	private FlashcardSeries seriesToLearn;

	private File file;
	private final String emptyLabel = "...";
	
	protected FlashcardsWindow(File file) throws IOException {
		this(Store.openSeries(file));

		this.file = file;
		Utilities.setFrameTitle(frame, file);
	}

	public boolean importFlashcards(File file) {
		try {
			series.importFlashcardSeries(Store.openSeries(file));
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"The document \"" + file.getName()
							+ "\" could not be read." + "\n"
							+ e.getLocalizedMessage(), "",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
	
	public static boolean createFlashcardsEditor(File file) {
		try {
			new FlashcardsWindow(file);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"The document \"" + file.getName()
							+ "\" could not be read." + "\n"
							+ e.getLocalizedMessage(), "",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}

	public FlashcardsWindow(FlashcardSeries series) {

		/*
		 * General Design Decision(s):
		 * 
		 * ActionListener do not contain domain logic; they always delegate to
		 * corresponding methods.
		 * 
		 * All errors are handled as early as possible.
		 * 
		 * A Frame is associated with exactly one FlashcardSeries.
		 */

		this.series = series;

		// setup of this frame; we need to do it here since the rootpane's
		// client property has to set before the other components are created
		frame = new JFrame();
		frame.getRootPane().putClientProperty("apple.awt.brushMetalLook",
				java.lang.Boolean.TRUE);

		Utilities.setFrameTitle(frame, file);

		// dialogs and other components that are related to this frame
		flashcardEditor = new FlashcardEditor(this);

		learnDialog = new LearnDialog(this);

		fileDialog = new java.awt.FileDialog(frame);
		fileDialog.setFilenameFilter(new FilenameFilter() {

			public boolean accept(File directory, String name) {
				return name.endsWith(Store.FILE_ENDING);
			}
		});

		// setup the menu and its listeners
		newFileMenuItem = new JMenuItem("New");
		newFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		//----------------------------------------------------
		newFileMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				closeFlashcardEditor();
				FlashcardsWindow f = new FlashcardsWindow(new FlashcardSeries());
				f.createFlashcard();
			}
		});
		//----------------------------------------------------
		
		importFileMenuItem = new JMenuItem("Import File...");
		importFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		importFileMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				importFlashcardSeries();
			}
		});
		
		
		
		openFileMenuItem = new JMenuItem("Open File...");
		openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		openFileMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				openFlashcardSeries();
			}
		});

		saveFileMenuItem = new JMenuItem("Save");
		saveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		saveFileMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				saveFlashcardSeries();
			}
		});

		saveAsFileMenuItem = new JMenuItem("Save As...");
		saveAsFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				(java.awt.event.InputEvent.SHIFT_MASK | Toolkit
						.getDefaultToolkit().getMenuShortcutKeyMask())));
		saveAsFileMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				saveAsFlashcardSeries();
			}
		});

		closeFileMenuItem = new JMenuItem("Close Window");
		closeFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		closeFileMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				closeFlashcardEditor();
			}
		});

		fileMenu = new JMenu("File");
		fileMenu.add(newFileMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(openFileMenuItem);
		fileMenu.add(importFileMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(saveFileMenuItem);
		fileMenu.add(saveAsFileMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(closeFileMenuItem);

		menuBar = new JMenuBar();
		menuBar.add(fileMenu);

		addButton = Utilities.createToolBarButton(" Create ", "list-add.png",
				"create new flashcard");
		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {

				createFlashcard();
			}
		});

		removeButton = Utilities.createToolBarButton(" Delete ",
				"list-remove.png", "remove selected flashcards");
		removeButton.setEnabled(false);
		removeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {

				removeFlashcards();
			}
		});

		editButton = Utilities.createToolBarButton(" Edit ",
				"accessories-text-editor.png", "edit selected flashcard");
		editButton.setEnabled(false);
		editButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {

				editFlashcard();
			}
		});

		list = new JList(series);

		//----------------------------------------------------
		list.setCellRenderer(new FlashcardRenderer());
		//----------------------------------------------------
		
		list.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent event) {
				// Only GUI related functionality:
				if (list.getSelectedIndex() != -1) {
					removeButton.setEnabled(true);
					editButton.setEnabled(true);
					metaData();
				} else {
					removeButton.setEnabled(false);
					editButton.setEnabled(false);
				}
			}
		});

		listScrollPane = new JScrollPane(list);
		listScrollPane.setBorder(BorderFactory.createEmptyBorder());
		listScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		playButton = Utilities.createToolBarButton(" Learn ",
				"media-playback-start.png", "learn flashcards");
		playButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {

				learn();
			}
		});
		
		
		//----------------------------------------------------
		if (getSeries().getSize() == 0) playButton.setEnabled(false);
		series.addListDataListener( new ListDataListener() {
		
			public void contentsChanged(ListDataEvent e) {
				contentsChanged = true;
			}

			public void intervalAdded(ListDataEvent e) {
				if (getSeries().getSize() > 0) playButton.setEnabled(true);
				contentsChanged = true;
			}
			
			public void intervalRemoved(ListDataEvent e) {
				if (getSeries().getSize() == 0) playButton.setEnabled(false);
				contentsChanged = true;
			}
		});
		//----------------------------------------------------
		

		toolbar = new JToolBar();
		toolbar.putClientProperty("JToolBar.isRollover", Boolean.FALSE);
		toolbar.add(addButton);
		toolbar.add(removeButton);
		toolbar.addSeparator();
		toolbar.add(editButton);
		toolbar.add(Box.createHorizontalGlue());
		toolbar.add(playButton);
		toolbar.setFloatable(false);
		
		
		
		
		//----------------------------------------------------
		JPanel infoPanel = new JPanel();
		// configure the infoPanel
		infoPanel.setOpaque(true);
		infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		infoPanel.setBackground(new Color(220, 220, 250));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		
		// create labels for metadata
		dateCreatedLabel = createInfoPaneLabel(infoPanel, "Date Created:");
		lastTimeNotRemeberedLabel = createInfoPaneLabel(infoPanel, "Last Time Not Remembered:");
		lastTimeRememberedLabel = createInfoPaneLabel(infoPanel, "Last Time Remembered:");
		numberOfTimesShownLabel = createInfoPaneLabel(infoPanel, "Number of Times Shown:");
//		numberOfTimesRememberedInARowLabel = createInfoPaneLabel(infoPanel,"Number of Times Remembered in a Row:");
		
		// create the split pane with list on left and info on right
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(1.0d);
		splitPane.setContinuousLayout(true);
		splitPane.setDividerSize(1);
		splitPane.setLeftComponent(listScrollPane);
		splitPane.setRightComponent(infoPanel);
		//----------------------------------------------------
		
		
		
		frame.setJMenuBar(menuBar);
//		frame.getContentPane().add(listScrollPane);
		frame.getContentPane().add(splitPane);
		frame.getContentPane().add(toolbar, BorderLayout.NORTH);
		frame.setSize(640, 480);
		frame.setLocationByPlatform(true);
		//frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent event) {
				closeFlashcardEditor();
			}
			
			@Override
			public void windowClosed(WindowEvent event) {
							
				SwingUtilities.invokeLater(new Runnable() {

					public void run() {
						// we have to make sure that the JFrame object will be
						// collected...
						// (the VM terminates if all frames are disposed and
						// finally collected)
						System.gc();
					}
				});
			} 

		});

		// Everything is setup; show the window:
		frame.setVisible(true);
		
	}

	// Implementation of the "logic":

	public FlashcardSeries getSeries() {
		return series;
	}

	public JFrame getFrame() {
		return frame;
	}

	protected void openFlashcardSeries() {
		fileDialog.setMode(FileDialog.LOAD);
		fileDialog.setVisible(true);
		String filename = fileDialog.getFile();
		if (filename != null) {
			if (!filename.endsWith(Store.FILE_ENDING))
				filename += Store.FILE_ENDING;
			File file = new File(fileDialog.getDirectory(), filename);
			createFlashcardsEditor(file);
		}
	}
	
	
	protected void importFlashcardSeries() {
		fileDialog.setMode(FileDialog.LOAD);
		fileDialog.setVisible(true);
		String filename = fileDialog.getFile();
		if (filename != null) {
			if (!filename.endsWith(Store.FILE_ENDING))
				filename += Store.FILE_ENDING;
			File file = new File(fileDialog.getDirectory(), filename);
			importFlashcards(file);
		}
	}

	protected void saveFlashcardSeries() {
		if (file == null)
			saveAsFlashcardSeries();
		else
			doSave(file);
	}

	protected void saveAsFlashcardSeries() {
		fileDialog.setMode(FileDialog.SAVE);
		fileDialog.setVisible(true);
		String filename = fileDialog.getFile();
		if (filename != null) {
			if (!filename.endsWith(Store.FILE_ENDING))
				filename += Store.FILE_ENDING;

			File newFile = new File(fileDialog.getDirectory(), filename);
			if (newFile.exists()) {
				if (JOptionPane
						.showConfirmDialog(
								frame,
								"The file with the name:\n"
										+ filename
										+ "\nalready exists.\nDo you want to overwrite the file?",
								"Warning", JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION)
					return;
			}
			doSave(newFile);
		}

	}

	protected void doSave(File file) {
		try {

			Store.saveSeries(series, file);

			// Saving the file was successful:
			this.file = file;
			Utilities.setFrameTitle(frame, file);

		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Could not save flashcards",
					"Saving the flashcards to :\n" + file.getName()
							+ "\nfailed.", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void learn() {
		
		Strategies strategies = new Strategies();
		
		Object message = JOptionPane.showInputDialog(
				frame,
				"Please choose the learning strategy:", "", JOptionPane.INFORMATION_MESSAGE, null,
				strategies.getStrategies().toArray(),
				null);
		if (message != null) {
			
			// copy cards
			ArrayList<Flashcard> flashcards = new ArrayList<Flashcard>();
			for (int i = series.getSize()-1; i >= 0; i--)
				flashcards.add(series.getElementAt(i));
			
			// change contents of copy
			strategies.getStrategy(message.toString()).changeCollection(flashcards);
			
			if (flashcards.size() > 0) {
				// write changed in copy new flashcardseries
				seriesToLearn = new FlashcardSeries();
				for (Flashcard f : flashcards)
					seriesToLearn.addCard(f);
				
				learnDialog.show();
			}
			
		}
		
	}
	
	

	protected void closeFlashcardEditor() {
		boolean reallyClose = false;
		
		if (contentsChanged) {
			if (JOptionPane.showConfirmDialog(frame, "Ihr Dokument enthält ungespeicherte Änderungen, dennoch schließen?") == 0)
				reallyClose = true;
		} else {
			reallyClose = true;
		}
		
		if (reallyClose) {
			frame.setVisible(false);
			frame.dispose(); // required to give up all resources
		}
		
	}

	protected void createFlashcard() {
		Flashcard card = new Flashcard();
		if (flashcardEditor.edit(card))
			series.addCard(card);
	}

	protected void removeFlashcards() {
		FlashcardsWindow.this.series.removeCards(list.getSelectedIndices());
		list.clearSelection();
	}

	protected void editFlashcard() {
		int index = list.getSelectedIndex();
		flashcardEditor.edit(series.getElementAt(index));
	}

	public void setSeriesToLearn(FlashcardSeries seriesToLearn) {
		this.seriesToLearn = seriesToLearn;
	}

	public FlashcardSeries getSeriesToLearn() {
		return seriesToLearn;
	}
	
	protected void metaData() {
		Flashcard f = series.getElementAt((list.getSelectedIndices()[0]));
		dateCreatedLabel.setText(f.getDateCreated().toString());
		lastTimeNotRemeberedLabel.setText(f.getLastFail() != null ? f.getLastFail().toString() : emptyLabel);
		lastTimeRememberedLabel.setText(f.getLastSuccess() != null ? f.getLastSuccess().toString() : emptyLabel);
		numberOfTimesShownLabel.setText(String.valueOf(f.getCounter()));
	}
	
	private JLabel createInfoPaneLabel(JPanel panel, String title) {
		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(UIManager.getFont("TableHeader.font"));
		Box titleBox = Box.createHorizontalBox();
		titleBox.add(titleLabel);
		titleBox.add(Box.createHorizontalGlue());
		JLabel contentLabel = new JLabel(emptyLabel);
		contentLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		contentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		contentLabel.setFont(UIManager.getFont("List.font"));
		Box contentBox = Box.createHorizontalBox();
		contentBox.add(Box.createHorizontalGlue());
		contentBox.add(contentLabel);
		panel.add(titleBox);
		panel.add(contentBox);
		panel.add(Box.createVerticalStrut(15));
		return contentLabel;
	}

}