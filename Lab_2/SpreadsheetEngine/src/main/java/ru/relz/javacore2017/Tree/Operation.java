package main.java.ru.relz.javacore2017.Tree;

import main.java.ru.relz.javacore2017.Spreadsheet.Column;

import java.util.HashMap;

public enum Operation {
	ADDITION('+'),
	SUBTRACTION('-'),
	MULTIPLICATION('*'),
	DIVISION('/');

	private final char character;

	Operation(final char character) {
		this.character = character;
	}
	
	public char toCharacter() {
		return character;
	}

	private static HashMap<Character, Operation> charsToOperations = new HashMap<>() {{
		put(ADDITION.character, ADDITION);
		put(SUBTRACTION.character, SUBTRACTION);
		put(MULTIPLICATION.character, MULTIPLICATION);
		put(DIVISION.character, DIVISION);
	}};

	public static Operation get(char character) {
		return charsToOperations.get(character);
	}
}
