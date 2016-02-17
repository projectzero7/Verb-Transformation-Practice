package Helpers;

// Import needed files
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 
 * A static class that converts Japanese text
 * (hiragana, katakana) to English (romaji).
 * 
 * Note this class does not handle kanji input
 * or particles.
 *
 */
public final class textJPtoEN {
	// Declare variables
	private final static String[] syllablesJpNormal = {
		"あ",	"い",	"う",		"え",	"お",
		"か",	"き",	"く",		"け",	"こ",
		"さ",	"し",	"す",	"せ",	"そ",
		"た",	"ち",	"つ",	"て",	"と",
		"な",	"に",	"ぬ",	"ね",	"の",
		"は",	"ひ",	"ふ",	"へ",	"ほ",
		"ま",	"み",	"む",	"め",	"も",
		"や",			"ゆ",			"よ",
		"ら",	"り",		"る",	"れ",	"ろ",
		"わ",							"を",
										"ん"
	};
	private final static String[] syllablesJpDiacritical = {
		"が",	"ぎ",	"ぐ",		"げ",	"ご",
		"ざ",	"じ",	"ず",	"ぜ",	"ぞ",
		"だ",	"ぢ",	"づ",	"で",	"ど",
		"ば",	"び",	"ぶ",	"べ",	"ぼ",
		"ぱ",	"ぴ",	"ぷ",	"ぺ",	"ぽ"
	};
	private final static String[] syllablesJpSmall = {
		"きゃ",			"きゅ",			"きょ",
		"しゃ",			"しゅ",			"しょ",
		"ちゃ",			"ちゅ",			"ちょ",
		"にゃ",			"にゅ",			"にょ",
		"ひゃ",			"ひゅ",			"ひょ",
		"みゃ",			"みゅ",			"みょ",
		"りゃ",			"りゅ",			"りょ",
		"ぎゃ",			"ぎゅ",			"ぎょ",
		"じゃ",			"じゅ",			"じょ",
		"ぢゃ",			"ぢゅ",			"ぢょ",
		"びゃ",			"びゅ",			"びょ",
		"ぴゃ",			"ぴゅ",			"ぴょ"
	};
	// TODO: katakana
	private final static String[] syllablesEnNormal = {
		"a",	"i",	"u",	"e",	"o",
		"ka",	"ki",	"ku",	"ke",	"ko",
		"sa",	"shi",	"su",	"se",	"so",
		"ta",	"chi",	"tsu",	"te",	"to",
		"na",	"ni",	"nu",	"ne",	"no",
		"ha",	"hi",	"fu",	"he",	"ho",
		"ma",	"mi",	"mu",	"me",	"mo",
		"ya",			"yu",			"yo",
		"ra",	"ri",	"ru",	"re",	"ro",
		"wa",							"wo",
										"n"
	};
	private final static String[] syllablesEnDiacritical = {
		// TODO: double-check Genki I for these pronunciations
		"ga",	"gi",	"gu",	"ge",	"go",
		"za",	"ji",	"zu",	"ze",	"zo",
		"da",	"chi",	"dzu",	"de",	"do",
		"ba",	"bi",	"bu",	"be",	"bo",
		"pa",	"pi",	"pu",	"pe",	"po"
	};
	private final static String[] syllablesEnSmall = {
		// TODO: double-check Genki I for these pronunciations
		"kya",			"kyu",			"kyo",
		"sha",			"shu",			"sho",
		"cha",			"chu",			"cho",
		"nya",			"nyu",			"nyo",
		"hya",			"hyu",			"hyo",
		"mya",			"myu",			"myo",
		"rya",			"ryu",			"ryo",
		"gya",			"gyu",			"gyo",
		"jya",			"jyu",			"jyo",
		"jya",			"jyu",			"jyo",
		"bya",			"byu",			"byo",
		"pya",			"pyu",			"pyo"
	};
	
	// Constructor
	private textJPtoEN() {}
	
	// Convert Japanese to English
	public static String convert( String input ) {
		// Split input into characters
		String[] splitInput = input.split("");
		
		// Join small characters to the previous character
		String temp = "";
		String[] tempInput = new String[ splitInput.length ];
		boolean augmentedSyllable = false;
		for ( int i = splitInput.length - 1; i >= 0; i-- ) {
			if ( splitInput[ i ].equals("ゃ") || splitInput[ i ].equals("ゅ") || splitInput[ i ].equals("ょ") ) {
				// Store the small syllable for the next iteration
				temp = splitInput[ i ];
				augmentedSyllable = true;
				continue;
			}
			
			if (augmentedSyllable) {
				augmentedSyllable = false;
				
				// Take the current syllable
				String syllable = splitInput[ i ];

				// Combine the two syllables
				tempInput[ i ] = syllable + temp;
				temp = "";
				
				continue;
			}
			
			// Add normal syllables
			tempInput[ i ] = splitInput[ i ];
		}
		
		// Remove blank spaces from tempInput
		List<String> cleanInput = new ArrayList<String>( Arrays.asList( tempInput ) );
		cleanInput.removeAll( Collections.singleton( null ) );
		splitInput = cleanInput.toArray( new String[ cleanInput.size() ] );

		// Declare variables
		boolean found;
		boolean doubleLetter = false;
		int inputLength = splitInput.length;
		int syllablesNormalLength = syllablesJpNormal.length;
		int syllablesDiacriticalLength = syllablesJpDiacritical.length;
		int syllablesSmallLength = syllablesJpSmall.length;
		StringBuilder sb = new StringBuilder(16);
		String syllable;
		
		// Convert from Japanese to English
		for ( int i = 0; i < inputLength; i++ ) {
			syllable = "";
			found = false;
			
			// TODO: handle katakana
			
			// Handle 小さい　つ
			if ( splitInput[ i ].equals("っ") ) {
				doubleLetter = true;
				continue;
			}
			
			// Search the small hiragana syllables
			if (!found) {
				for ( int j = 0; j < syllablesSmallLength; j++ ) {
					if ( splitInput[ i ].equals( syllablesJpSmall[ j ] ) ) {
						syllable = syllablesEnSmall[ j ];
						found = true;
						break;
					}
				}
			}
			
			// Search the diacritical hiragana syllables
			if (!found) {
				for ( int j = 0; j < syllablesDiacriticalLength; j++ ) {
					if ( splitInput[ i ].equals( syllablesJpDiacritical[ j ] ) ) {
						syllable = syllablesEnDiacritical[ j ];
						found = true;
						break;
					}
				}
			}
			
			// Search the normal hiragana syllables
			if (!found) {
				for ( int j = 0; j < syllablesNormalLength; j++ ) {
					if ( splitInput[ i ].equals( syllablesJpNormal[ j ] ) ) {
						syllable = syllablesEnNormal[ j ];
						found = true;
						break;
					}
				}
			}
			
			// We could not find that syllable
			if (!found) {
				syllable = splitInput[ i ];
			}
			
			// Double the first letter if we have a 小さい　つ
			if (doubleLetter) {
				syllable = syllable.substring( 0, 1 ) + syllable;
				doubleLetter = false;
			}
			
			// Append to the end
			sb.append( syllable );
		}
		
		return sb.toString();
	}
	
	// Check if a string is already converted
	public static boolean isEnglish( String input ) {
		/*
		 * We will perform a quick check to see if either the
		 * first two or three letters in the input match anything
		 * in the English syllable tables. If there is a match,
		 * it must already be in English.
		 * 
		 * Ignore the possibility of mixed case or languages other
		 * than EN and JP.
		 */
		String check1 = input.substring( 0, Math.min( 2, input.length() ) );
		String check2 = input.substring( 0, Math.min( 3, input.length() ) );
		
		for ( String s : syllablesEnNormal ) {
			if ( check1.equals( s )  || check2.equals( s ) ) {
				return true;
			}
		}
		
		for ( String s : syllablesEnDiacritical ) {
			if ( check1.equals( s )  || check2.equals( s ) ) {
				return true;
			}
		}
		
		for ( String s : syllablesEnSmall ) {
			if ( check1.equals( s )  || check2.equals( s ) ) {
				return true;
			}
		}
		
		return false;
	}
}
