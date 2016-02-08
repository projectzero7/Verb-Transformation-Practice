package GUI;

//Import needed files
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Callbacks.*;

// Control the program flow
public class Manager {
	// Declare constants
	final public static int MENU_UNKNOWN = -1;
	final public static int MENU_MAIN = 0;
	final public static int MENU_PRACTICE_VERB = 1;
	final public static int MENU_STATISTICS_VERB = 2;
	
	final private int SCREEN_WIDTH = 800;
	final private int SCREEN_HEIGHT = 600;
	
	final private int BUTTON_WIDTH = 300;
	final private int BUTTON_HEIGHT = 50;
	
	// Declare variables
	private int current_menu;
	
	JFrame window;
	JPanel content;
	
	JButton btnMainMenu;
	JButton btnVerbTransformation;
	JButton btnQuit;
	JButton btnSubmitVerb;
	
	JTextArea txtboxError;
	JTextArea txtboxQuestionVerb;
	JTextArea txtboxInputVerb;
	
	// Constructor
	public Manager() {
		/* I am aware that the Java convention is to use
		 * a layout manager, but I could not get that to
		 * work. Just going to use absolute coordinates
		 * for now.
		 */
		
		// Initialize all components
		int button_align_x = ( SCREEN_WIDTH / 2 ) - ( BUTTON_WIDTH / 2 );
		Font fnt = new Font( Font.SANS_SERIF, 3, 20 );
		
		btnMainMenu = new JButton( "Main Menu" );
		btnMainMenu.setBounds( button_align_x, ( SCREEN_HEIGHT / 2 ) - BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT );
		btnMainMenu.setFont( fnt );
		ButtonMainMenu callbackMainMenu = new ButtonMainMenu( this );
		btnMainMenu.addActionListener( callbackMainMenu );
		
		btnVerbTransformation = new JButton( "Verb Transformation" );
		btnVerbTransformation.setBounds( button_align_x, 200, BUTTON_WIDTH, BUTTON_HEIGHT );
		btnVerbTransformation.setFont( fnt );
		ButtonVerbTransformation callbackVerb = new ButtonVerbTransformation( this );
		btnVerbTransformation.addActionListener( callbackVerb );
		
		btnQuit = new JButton( "Quit" );
		btnQuit.setBounds( button_align_x, 300, BUTTON_WIDTH, BUTTON_HEIGHT );
		btnQuit.setFont( fnt );
		ButtonQuit callbackQuit = new ButtonQuit( this );
		btnQuit.addActionListener( callbackQuit );
		
		btnSubmitVerb = new JButton( "Submit" );
		btnSubmitVerb.setBounds( SCREEN_WIDTH - BUTTON_WIDTH - 60, 450, BUTTON_WIDTH, BUTTON_HEIGHT );
		btnSubmitVerb.setFont( fnt );
		ButtonVerbSubmit callbackVerbSubmit = new ButtonVerbSubmit( this );
		btnSubmitVerb.addActionListener( callbackVerbSubmit );
		
		txtboxError = new JTextArea( "An error has occurred" );
		txtboxError.setBounds( button_align_x, 150, BUTTON_WIDTH, BUTTON_HEIGHT );
		txtboxError.setFont( fnt );
		txtboxError.setEditable( false );
		
		txtboxQuestionVerb = new JTextArea( "" );
		txtboxQuestionVerb.setBounds( 30, 100, SCREEN_WIDTH - 90, 25 );
		txtboxQuestionVerb.setFont( fnt );
		txtboxQuestionVerb.setEditable( false );
		
		txtboxInputVerb = new JTextArea( "" );
		txtboxInputVerb.setBounds( 30, 300, SCREEN_WIDTH - 90, 25 );
		txtboxInputVerb.setFont( fnt );
		txtboxInputVerb.setEditable( true );
		
		// Initialize component handler
		content = new JPanel();
		content.setLayout( null );
		
		// Initialize the window
		window = new JFrame();
		window.setContentPane( content );
		window.setSize( SCREEN_WIDTH, SCREEN_HEIGHT );
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.setVisible( true );
		
		// Attach main menu components
		switchMenu( MENU_MAIN );
	}
	
	// Switch menus and attach components
	public void switchMenu( int nextMenu ) {
		// Remove all content on the panel
		content.removeAll();
		
		// Add components according to menu location
		switch ( nextMenu ) {
			case MENU_MAIN:
				// Attach this menu's components
				content.add( btnVerbTransformation );
				content.add( btnQuit );
				
				current_menu = MENU_MAIN;
				
				break;
				
			case MENU_PRACTICE_VERB:
				// Attach this menu's components
				content.add( txtboxQuestionVerb );
				content.add( txtboxInputVerb );
				content.add( btnSubmitVerb );
				
				current_menu = MENU_PRACTICE_VERB;
				
				break;
				
			default:
				// Display an error message
				content.add( txtboxError );
				
				// Add a button to go back to the main menu
				content.add( btnMainMenu );
				
				current_menu = MENU_UNKNOWN;
				
				break;
		}
		
		// Re-attach the content panel
		content.revalidate();
		content.repaint();
	}
	
	// Execute the verb transformation practice
	public void runVerbTransformation() {
		System.out.println( "Moving on..." );	// DEBUG
		
		// get text from txtboxInputVerb
		
		// compare to stored answer
			// log to Statistics
		
		// Clear the input box
		txtboxInputVerb.setText( "" );
		
		// go to next verb
	}
}