package ru.relz.javacore2017;

import java.lang.StringBuilder;

class CaesarCipher {
    private static final int ENGLISH_ALPHABET_SIZE = 26;
    private static final int FIRST_LETTER_CODE = (int) 'a';
    private static final int LAST_LETTER_CODE = (int) 'z';
    private static final String ENCRYPTION_OPTION = "-e";
    private static final String DECRYPTION_OPTION = "-d";

    public static void main(String[] args) {
        if (args.length < 3) {
            printlnError("CaesarCipher <encrypt|decrypt> <shift count> <input string>");
        }
        if (!args[0].equals(ENCRYPTION_OPTION) && !args[0].equals(DECRYPTION_OPTION)) {
            printlnError("Invalid option " + args[0] + ". Type " + ENCRYPTION_OPTION + " for encrypt or " + DECRYPTION_OPTION + " for decrypt");
        }
        boolean encrypt = args[0].equals(ENCRYPTION_OPTION);
        int shiftCount = 0;
        try {
            shiftCount = Integer.parseInt(args[1]);
        } catch(NumberFormatException ex) {
            printlnError("Invalid argument <shift count>, number expected");
        }
        if (!encrypt) {
            shiftCount = -shiftCount;
        }
        StringBuilder inputStringBuilder = new StringBuilder(args[2]);
        for (int i = 0; i < inputStringBuilder.length(); ++i) {
            int charPos = inputStringBuilder.codePointAt(i);
            int newCharPos = charPos + shiftCount;
            while (newCharPos < FIRST_LETTER_CODE) {
                newCharPos += ENGLISH_ALPHABET_SIZE;
            }
            while (newCharPos > LAST_LETTER_CODE) {
                newCharPos -= ENGLISH_ALPHABET_SIZE;
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
