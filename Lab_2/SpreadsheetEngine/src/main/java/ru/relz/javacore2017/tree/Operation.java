package main.java.ru.relz.javacore2017.tree;

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

	private final static HashMap<Character, Operation> charsToOperations = new HashMap<>() {{
		put(ADDITION.character, ADDITION);
		put(SUBTRACTION.character, SUBTRACTION);
		put(MULTIPLICATION.character, MULTIPLICATION);
		put(DIVISION.character, DIVISION);
	}};

	public static Operation createFromCharacter(char character) {
		return charsToOperations.get(character);
	}
}
