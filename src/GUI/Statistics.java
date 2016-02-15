package GUI;

// Import needed files
import java.util.Vector;

// Statistics tracker
public class Statistics {
	// Declare variables
	private int numCorrect;
	private int numTotal;
	
	private Vector< String > missedQuestions;
	
	// Constructor
	public Statistics() {
		// Initialize variables
		missedQuestions = new Vector< String >();
		
		clearResults();
	}
	
	// Update the number of correct answers
	public void gotCorrect() {
		numCorrect++;
		numTotal++;
	}
	
	// Update the number of incorrect answers
	public void gotIncorrect( String input, String correct ) {
		numTotal++;
		
		missedQuestions.add( input );
		missedQuestions.add( correct );
	}
	
	// Return the totals as a string
	public String getResults() {
		StringBuilder sb = new StringBuilder( 128 );
		
		sb.append( "You got " );
		sb.append( numCorrect );
		sb.append( " out of " );
		sb.append( numTotal );
		sb.append( " correct!" );
		
		return sb.toString();
	}
	
	// Return the missed questions
	public Vector<String> getMissedQuestions() {
		return missedQuestions;
	}
	
	// Reset all results to default value
	public void clearResults() {
		numCorrect = 0;
		numTotal = 0;
		
		missedQuestions.clear();
	}
}