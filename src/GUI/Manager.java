package GUI;

//Import needed files
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Callbacks.*;
import Helpers.textJPtoEN;

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
	
	final private String DELIMITER = "(:|;|：|；){1}";
	
	private int NUM_QUESTIONS = 50;
	
	// Declare variables
	private int current_menu;
	
	Statistics stats;
	
	JFrame window;
	JPanel content;
	
	JButton btnMainMenu;
	JButton btnVerbTransformation;
	JButton btnQuit;
	JButton btnSubmitVerb;
	
	JTextArea txtboxError;
	JTextArea txtboxQuestionVerb;
	JTextArea txtboxInputVerb;
	
	Vector< Vector< String > > verbMatrix;
	String correctAnswer;
	int current_question;
	Random rand;
	
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
		window.setLocationRelativeTo( null );
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
				// Get a file name from the user
				String filename = JOptionPane.showInputDialog("Please input a file name to read verbs from: ");
				if ( filename == null ) {
					switchMenu( MENU_MAIN );
					return;
				}
				
				// Attach this menu's components
				content.add( txtboxQuestionVerb );
				content.add( txtboxInputVerb );
				content.add( btnSubmitVerb );
				
				current_menu = MENU_PRACTICE_VERB;
				
				// Initialize variables
				verbMatrix = new Vector< Vector< String > >();
				stats = new Statistics();
				rand = new Random();
				
				// Read a text file for questions and answers
				getVerbsFromFile( filename );
				
				// Start from the first question
				current_question = 0;
				nextVerb();
				
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
	
	// Read in the verbs from a text file
	public void getVerbsFromFile( String filename ) {
		try {
			// Open the file to read from
			BufferedReader reader = new BufferedReader( new InputStreamReader ( new FileInputStream( filename ), "UTF8" ) );
			
			// Read each line of the file
			String line;
			while ( ( line = reader.readLine() ) != null ) {
				Vector< String > verbEntry = new Vector< String >();
				
				// Split the line by tabs
				String[] conjugations = line.split( DELIMITER );
				Collections.addAll( verbEntry, conjugations );
				
				// Add the new verb to the matrix
				verbMatrix.add( verbEntry );
			}
			
			// Close the reader resource
			reader.close();
			
		} catch (FileNotFoundException e) {
			// Display error and quit
			JOptionPane.showMessageDialog(content, "File " + filename + " not found", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit( 1 );
			
		} catch (IOException e) {
			// Display error and quit
			JOptionPane.showMessageDialog(content, "An error occurred while reading " + filename, "Error", JOptionPane.ERROR_MESSAGE);
			System.exit( 1 );
		}
		
		// Make the number of questions safe
		NUM_QUESTIONS = Math.min( ( verbMatrix.size() - 1 ) * ( verbMatrix.get(0).size() - 1 ), NUM_QUESTIONS );
	}
	
	// Choose a random verb from the verb matrix
	public void nextVerb() {
		current_question++;
		
		int columnMax = verbMatrix.get(0).size();
		int rowMax = verbMatrix.size();
		
		if ( current_question <= NUM_QUESTIONS ) {
			correctAnswer = null;
			
			int row = 0;
			int column = 0;
			/*
			 * Super-inefficient algorithm that nobody will
			 * (hopefully) notice
			 */
			while (correctAnswer == null) {
				row = rand.nextInt(rowMax - 1) + 1;	// from 1 to the max number of rows
				column = rand.nextInt(columnMax - 1) + 1;	// from 1 to the max number of columns
				
				correctAnswer = verbMatrix.get( row ).get( column );
			}
			
			txtboxQuestionVerb.setText( "What is the " + verbMatrix.get( 0 ).get( column ) + " conjugation of " + verbMatrix.get( row ).get( 0 ) + "?" );
			correctAnswer = correctAnswer.replaceAll( "\\s+", "" );
			
			if ( !textJPtoEN.isEnglish( correctAnswer ) ) {
				correctAnswer = textJPtoEN.convert( correctAnswer );
			}
			
			verbMatrix.get( row ).set( column, null );
		} else {
			// go to statistics menu
			JOptionPane.showMessageDialog(content, stats.getResults(), "Results", JOptionPane.ERROR_MESSAGE);
			System.exit( 1 );
			
			// Clear the statistics for the next run
			stats.clearResults();
		}
	}
	
	// Execute the verb transformation practice
	public void checkAnswer() {
		// Get text from txtboxInputVerb
		String answer = txtboxInputVerb.getText();
		answer = answer.replaceAll( "\\s+", "" );	// Japanese has no whitespace
		if ( !textJPtoEN.isEnglish( answer ) ) {
			answer = textJPtoEN.convert( answer );	// make sure to do comparisons in EN
		}
		
		System.out.println( answer + " | " + correctAnswer );

		// Determine the results and log to statistics
		if ( answer.equals( correctAnswer ) ) {
			stats.gotCorrect();
			
		} else {
			stats.gotIncorrect( answer, correctAnswer );
		}
		
		// Clear the input box
		txtboxInputVerb.setText( "" );
		
		// Go to the next verb
		nextVerb();
	}
}