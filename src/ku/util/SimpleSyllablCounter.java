package ku.util;

/**
 * Count syllable in a String by break in to char.
 * 
 * @author Jirayu Laungwilawan
 * @version 31.3.17
 */
public class SimpleSyllablCounter {
	// Contains vowels to check in the isVowel()
	public static final String VOWELS = "aeiouAEIUO";
	// Contains vowels and y to check in the isVowel_Y()
	public static final String VOWELS_Y = "aeiouyAEIUOY";

	/**
	 * Method for counting the syllable using state pattern.
	 * 
	 * @param word
	 *            is String and will be trim into char and count for syllable.
	 * @return How many syllable in the word. Return 0 if it is not a word.
	 */
	public int countSyllabls(String word) {
		String text = word.trim();
		State state = State.START;
		int count = 0;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			switch (state) {
			case START:
				if (isVowelOrY(c)) {
					state = State.SINGLE_VOWEL;
					count++;
				} else if (Character.isLetter(c) || c == '\'') {
					state = State.CONSONANT;
				} else
					state = State.NONWORD;
				break;

			case SINGLE_VOWEL:
				if (isVowel(c)) {
					state = State.MULTI_VOWEL;
				} else if (Character.isLetter(c) || c == '\'') {
					state = State.CONSONANT;
				} else if (c == '-') {
					state = State.HYPHEN;
				} else
					state = State.NONWORD;
				break;

			case MULTI_VOWEL:
				if (isVowel(c)) {
					state = State.MULTI_VOWEL;
				} else if (Character.isLetter(c) || c == '\'') {
					state = State.CONSONANT;
				} else if (c == '-') {
					state = State.HYPHEN;
				} else
					state = State.NONWORD;
				break;

			case CONSONANT:
				if (i == text.length() - 1 && c == 'e' || c == 'E') {
					if (count == 0)
						count++;
					break;
				} else if (isVowelOrY(c)) {
					state = State.SINGLE_VOWEL;
					count++;
				} else if (Character.isLetter(c) || c == '\'') {
					state = State.CONSONANT;
				} else if (c == '-') {
					state = State.HYPHEN;
				} else
					state = State.NONWORD;
				break;

			case HYPHEN:
				if (isVowel(c)) {
					state = State.SINGLE_VOWEL;
					count++;
				} else if (Character.isLetter(c) || c == '\'') {
					state = State.CONSONANT;
				} else
					state = State.NONWORD;
				break;

			case NONWORD:
				count = 0;
				break;

			default:
				break;
			}
		}
		return count;
	}

	/**
	 * Check is it a vowel or not include y if it is the single vowel.
	 * 
	 * @param c
	 *            that break from the word.
	 * @return true if it is vowel or y. Otherwise false.
	 */
	private boolean isVowelOrY(char c) {
		return VOWELS_Y.indexOf(c) >= 0;
	}

	/**
	 * Check is it a vowel or not.
	 * 
	 * @param c
	 *            that break from the word.
	 * @return true if it is vowel. Otherwise false.
	 */
	private boolean isVowel(char c) {
		return VOWELS.indexOf(c) >= 0;
	}

	public static void main(String[] args) {
		String text = "the";
		SimpleSyllablCounter s = new SimpleSyllablCounter();
		System.out.printf("%s : " + s.countSyllabls(text) + " Syllables", text);
	}
}
