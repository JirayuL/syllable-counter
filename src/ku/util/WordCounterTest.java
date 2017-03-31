package ku.util;

import java.net.URL;
import java.util.*;
import java.io.*;

/**
 * Some words with known syllable count, to check your countSyllables method. It
 * reads words from a file containing a string and syllable count (one per
 * line). Lines beginning with '#' and blank lines are ignored.
 */
public class WordCounterTest {

	/**
	 * Read lines from a file containing "word syllable_count" and add them to
	 * map of words.
	 * 
	 * @param in
	 *            InputStream to read from
	 */
	private static Map<String, Integer> loadWords(InputStream in) throws IOException {
		Map<String, Integer> words = new HashMap<String, Integer>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String inline = null;
		int linecount = 0;
		while ((inline = reader.readLine()) != null) {
			linecount++;
			inline = inline.trim();
			if (inline.startsWith("#"))
				continue; // skip lines starting with #
			if (inline.isEmpty())
				continue;
			String[] args = inline.split("\\s+");
			if (args.length != 2) {
				System.out.printf("Invalid line %d: %s\n", linecount, inline);
				continue;
			}
			try {
				int count = Integer.parseInt(args[1]);
				words.put(args[0], count);
			} catch (NumberFormatException nfe) {
				System.out.printf("Invalid line %d: %s\n", linecount, inline);
			}
		}
		try {
			reader.close();
		} catch (IOException ioe) {
			/* ignore */ }

		return words;
	}

	private static InputStream getInputStream(String urlname) throws IOException {
		if (urlname.matches("\\w+:/.*")) {
			// open it as a URL0
			URL url = new URL(urlname);
			return url.openStream();
		} else {
			// treat it as a classpath resource
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			return loader.getResourceAsStream(urlname);
		}
	}

	/**
	 * Test the syllable counter using words from a file.
	 * 
	 * @param args
	 *            not used
	 * @throws IOException
	 *             if file cannot be read
	 */
	public static void runTests() {
		Map<String, Integer> words = null;
		try {
			InputStream in = getInputStream(URLNAME);
			if (in == null)
				throw new IOException("Resource does not exist: " + URLNAME);
			words = loadWords(in);
		} catch (Exception e) {
			System.out.println("Exception reading wordlist file " + URLNAME);
			System.out.println(e.getMessage());
			System.exit(1);
		}

		SimpleSyllablCounter counter = new SimpleSyllablCounter();
		int correct = 0;
		int incorrect = 0;
		for (String word : words.keySet()) {
			System.out.printf("%-24s", word);
			int expect = words.get(word);
			int actual = counter.countSyllabls(word);
			System.out.print(actual);
			if (expect == actual) {
				correct++;
				System.out.println(" Correct");
			} else {
				incorrect++;
				System.out.printf(" Incorrect: should be %d\n", expect);
			}
		}
		System.out.printf("Correct: %d  Incorrect: %d\n", correct, incorrect);
	}

	/**
	 * Count words in file or URL
	 * 
	 * @param filename
	 *            file, URL, or resoruce name
	 */
	public static void countWordsInFile(String filename) {
		System.out.println("Counting words in " + filename);
		SimpleSyllablCounter counter = new SimpleSyllablCounter();
		BufferedReader reader = null;
		try {
			InputStream in = getInputStream(filename);
			if (in == null)
				throw new IOException("Resource does not exist: " + URLNAME);
			reader = new BufferedReader(new InputStreamReader(in));
		} catch (Exception e) {
			System.out.println("Exception reading wordlist file " + URLNAME);
			System.out.println(e.getMessage());
			System.exit(1);
		}

		int wordCount = 0;
		int syllableCount = 0;
		int count = 0;
		String word;
		try {
			while ((word = reader.readLine()) != null) {
				word = word.trim();
				// Print 10 words for debugging.
//				if (count++ < 10) System.out.println(word);
				if (word.isEmpty())
					continue;
				int syllables = counter.countSyllabls(word);
				syllableCount += syllables;
				if (syllables > 0)
					wordCount++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("Number of words: %,d\nNumber of syllables: %,d\n", wordCount, syllableCount);
	}

	public static void main(String[] args) {
		String dict = "http://se.cpe.ku.ac.th/dictionary.txt";
		countWordsInFile(dict);
	}

	// URL of the file containing sample words.
	// The test will be faster if you download the file and change this
	// to a local URL ("file:/path/testwords.txt")
	// or file on this project's classpath ("testwords.txt").
	// static final String URLNAME = "testwords.txt";
	static final String URLNAME = "http://se.cpe.ku.ac.th/testwords.txt";

}