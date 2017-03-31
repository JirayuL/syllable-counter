package ooSyllable;

abstract class State {
	// Contains vowels to check in the isVowel()
	public static final String VOWELS = "aeiouAEIUO";
	// Contains vowels and y to check in the isVowel_Y()
	public static final String VOWELS_Y = "aeiouyAEIUOY";

	public abstract void handleChar(char c);

	public void enterState() {
	};

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
}
