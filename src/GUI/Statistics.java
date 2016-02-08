package GUI;
// Statistics tracker
public final class Statistics {
	// Declare variables
	private static int num_correct;
	private static int num_incorrect;
	private static int num_total;
	
	// Constructor
	private Statistics() {
		// Initialize variables
		num_correct = 0;
		num_incorrect = 0;
		num_total = 0;
	}
	
	// Update the number of correct answers
	public static void got_correct() {
		num_correct++;
		num_total++;
	}
	
	// Update the number of incorrect answers
	public static void got_incorrect() {
		num_incorrect++;
		num_total++;
	}
	
	// Return the totals as a string
	public static String get_results() {
		StringBuilder sb = new StringBuilder( 128 );
		
		sb.append("Correct: ");
		sb.append( num_correct );
		sb.append( '\n' );
		
		sb.append("Incorrect: ");
		sb.append( num_incorrect );
		sb.append( '\n' );
		
		sb.append("Total: ");
		sb.append( num_total );
		sb.append( '\n' );
		
		return sb.toString();
	}
}