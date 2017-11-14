package main.java.ru.relz.javacore2017.spreadsheet;

import java.util.HashMap;

public enum Row {
	ONE('1'),
	TWO('2'),
	THREE('3'),
	FOUR('4'),
	FIVE('5'),
	SIX('6'),
	SEVEN('7'),
	EIGHT('8'),
	NINE('9');

	private final char character;

	Row(final char character) {
		this.character = character;
	}

	public char toCharacter() {
		return character;
	}

	private final static HashMap<Character, Row> charsToRows = new HashMap<>() {{
		put(ONE.character, ONE);
		put(TWO.character, TWO);
		put(THREE.character, THREE);
		put(FOUR.character, FOUR);
		put(FIVE.character, FIVE);
		put(SIX.character, SIX);
		put(SEVEN.character, SEVEN);
		put(EIGHT.character, EIGHT);
		put(NINE.character, NINE);
	}};

	private final static HashMap<Integer, Row> ordinalsToRows = new HashMap<>() {{
		put(ONE.ordinal(), ONE);
		put(TWO.ordinal(), TWO);
		put(THREE.ordinal(), THREE);
		put(FOUR.ordinal(), FOUR);
		put(FIVE.ordinal(), FIVE);
		put(SIX.ordinal(), SIX);
		put(SEVEN.ordinal(), SEVEN);
		put(EIGHT.ordinal(), EIGHT);
		put(NINE.ordinal(), NINE);
	}};

	public static Row createFromCharacter(char character) {
		return charsToRows.get(character);
	}

	public static Row createFromOrdinal(int ordinal) {
		return ordinalsToRows.get(ordinal);
	}
}
