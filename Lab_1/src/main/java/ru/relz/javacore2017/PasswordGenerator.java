package ru.relz.javacore2017;

import java.util.Random;

class PasswordGenerator {
    public static void main(String[] args) {
        if (args.length < 2) {
            printlnError("usage: PasswordGenerator <password length> <password symbols>");
        }
        int passwordLength = Integer.parseInt(args[0]);
        String passwordSymbols = args[1];
        System.out.println(generatePassword(passwordLength, passwordSymbols));
    }

    private static void printlnError(String msg) {
        System.out.println(String.format("Error: %s", msg));
        System.exit(1);
    }

    private static String generatePassword(int passwordLength, String passwordSymbols) {
        String result = "";
        Random random = new Random();
        for (int i = 0; i < passwordLength; ++i) {
            result += passwordSymbols.charAt(random.nextInt(passwordSymbols.length()));
        }

        return result;
    }
}
