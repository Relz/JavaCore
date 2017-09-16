package ru.relz.javacore2017;

import java.lang.StringBuilder;

class CaesarCipher {
	private static int englishAlphabetSize = 26;
	private static int firstLetterCode = (int) 'a';
	private static int lastLetterCode = (int) 'z';
	private static String encryptOption = "-e";
	private static String decryptOption = "-d";

	public static void main(String[] args) {
		if (args.length < 3) {
			printlnError("CaesarCipher <encrypt|decrypt> <shift count> <input string>");
		}
		if (args[0].equals(encryptOption) && args[0].equals(decryptOption)) {
			printlnError("Invalid option " + args[0] + ". Type " + encryptOption + " for encrypt or " + decryptOption + " for decrypt");
		}
		boolean encrypt = args[0].equals(encryptOption);
		int shiftCount = Integer.parseInt(args[1]);
		if (!encrypt) {
			shiftCount = -shiftCount;
		}
		StringBuilder inputStringBuilder = new StringBuilder(args[2]);
		for (int i = 0; i < inputStringBuilder.length(); ++i) {
			int charPos = inputStringBuilder.codePointAt(i);
			int newCharPos = charPos + shiftCount;
			if (newCharPos < firstLetterCode) {
				newCharPos += englishAlphabetSize;
			} else if (newCharPos > lastLetterCode) {
				newCharPos -= englishAlphabetSize;
			}
			inputStringBuilder.setCharAt(i, (char) (newCharPos));
		}
		System.out.println(inputStringBuilder);
	}

	private static void printlnError(String msg) {
		System.out.println(String.format("Error: %s", msg));
		System.exit(1);
	}
}

