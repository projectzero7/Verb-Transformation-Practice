package Callbacks;

// Import needed files
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.Manager;

// Callback for the main menu button
public class ButtonVerbSubmit implements ActionListener {
	// Declare variables
	Manager myManager;
	
	// Constructor
	public ButtonVerbSubmit( Manager manager ) {
		// Call the super class's constructor
		super();
		
		// Store the reference to the Manager for use in callback
		myManager = manager;
	}
	
	// Handle button being pressed
	@Override
	public void actionPerformed( ActionEvent e ) {
		// Have the Manager go to the main menu
		myManager.checkAnswer();
	}
}