//package ooSyllable;
//
//public class SingleVowelState extends State{
//
//	@Override
//	public void handleChar(char c) {
//		if (isVowel(c)) setState(MULTIVOWEL);
//		else if (Character.isLetter(c)) setState(CONSONANT);
//		else if (c == '-') setState(HYPHEN);
//		else setState(NONWORD);
//	}
//	
//}