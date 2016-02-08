package GUI;

// Import needed files
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Callbacks.*;

// Control the program flow
public class Manager implements Runnable {
	// Declare constants
	final public static int MENU_UNKNOWN = -1;
	final public static int MENU_MAIN = 0;
	final public static int MENU_PRACTICE_VERB = 1;
	final public static int MENU_STATISTICS_VERB = 2;
	
	// Declare variables
	private int current_menu;
	
	JFrame window;
	JPanel content;
	JButton buttonQuit;
	JButton buttonMainMenu;
	
	// Constructor
	public Manager() {
		// Initialize all components
		buttonQuit = new JButton( "Quit" );
		ButtonQuit callbackQuit = new ButtonQuit( this );
		buttonQuit.addActionListener( callbackQuit );
		
		buttonMainMenu = new JButton( "Main Menu" );
		ButtonMainMenu callbackMainMenu = new ButtonMainMenu( this );
		buttonMainMenu.addActionListener( callbackMainMenu );
		
		// Initialize component handler
		content = new JPanel();
		
		// Initialize the window
		window = new JFrame();
		window.setContentPane( content );
		window.setSize( 800, 600 );
		window.setVisible( true );
		
		// Attach main menu components
		switchMenu( MENU_UNKNOWN );
	}
	
	// Switch menus and attach components
	public void switchMenu( int nextMenu ) {
		// Remove all content on the panel
		content.removeAll();
		
		// Add components according to menu location
		switch ( nextMenu ) {
			case MENU_MAIN:
				// Attach this menu's components
				content.add( buttonQuit );
				
				current_menu = MENU_MAIN;
				
				break;
				
			case MENU_PRACTICE_VERB:
				// Attach this menu's components
				
				current_menu = MENU_PRACTICE_VERB;
				
				break;
				
			default:
				// TODO: Display an error message
				
				// Add a button to go back to the main menu
				content.add( buttonMainMenu );
				
				current_menu = MENU_UNKNOWN;
				
				break;
		}
		
		// Re-attach the content panel
		content.revalidate();
		content.repaint();
	}
	
	// Execute the program
	@Override
	public void run() {
		System.out.println("hi");
		
		// Attach main menu components
			// read from file
		
			// what is the PAST AFFIRMATIVE for たべる?
		
			// たべる -> 
		
			// attach text box
		
			// attach Submit button
				// check answer against answer key
		
				// log to statistics
		
				// goto read from file
	}
}