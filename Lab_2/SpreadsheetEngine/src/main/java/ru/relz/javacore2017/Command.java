package main.java.ru.relz.javacore2017;

import java.util.HashMap;

public enum Command {
	EXIT("exit"),
	SET("set"),
	SET_FORMULA("setformula"),
	DISPLAY("display");

	private final String text;

	Command(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	private final static HashMap<String, Command> stringsToCommands = new HashMap<>() {{
		put(EXIT.toString(), EXIT);
		put(SET.toString(), SET);
		put(SET_FORMULA.toString(), SET_FORMULA);
		put(DISPLAY.toString(), DISPLAY);
	}};

	private final static HashMap<Command, Integer> commandsToMinArgumentCounts = new HashMap<>() {{
		put(EXIT, 1);
		put(SET, 3);
		put(SET_FORMULA, 3);
		put(DISPLAY, 1);
	}};

	private final static HashMap<Command, String> commandsToUsageStrings = new HashMap<>() {{
		put(EXIT, EXIT.toString());
		put(SET, SET.toString() + " <coordinate> <value>");
		put(SET_FORMULA, SET_FORMULA.toString() + " <coordinate> <formula>");
		put(DISPLAY, DISPLAY.toString());
	}};

	public boolean isEnoughArgumentCount(int argumentCount) {
		return argumentCount >= commandsToMinArgumentCounts.get(this);
	}

	public void printUsage() {
		System.out.println(commandsToUsageStrings.get(this));
	}

	public static Command createFromString(String commandString) {
		return stringsToCommands.get(commandString);
	}

	public static boolean isInvalidCoordinateFormat(String coordinate) {
		return coordinate == null || coordinate.length() != 2;
	}
}
