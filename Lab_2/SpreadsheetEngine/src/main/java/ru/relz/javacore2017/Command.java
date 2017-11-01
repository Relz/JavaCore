package main.java.ru.relz.javacore2017;

import java.util.HashMap;

public enum Command {
	EXIT("exit"),
	SET("set"),
	SET_FORMULA("setformula"),
	DISPLAY("display");

	private final String text;

	private Command(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	private static HashMap<String, Command> stringsToCommands = new HashMap<>() {{
		put(EXIT.toString(), EXIT);
		put(SET.toString(), SET);
		put(SET_FORMULA.toString(), SET_FORMULA);
		put(DISPLAY.toString(), DISPLAY);
	}};

	private static HashMap<Command, Integer> commandsToMinArgumentCounts = new HashMap<>() {{
		put(EXIT, 1);
		put(SET, 3);
		put(SET_FORMULA, 3);
		put(DISPLAY, 1);
	}};

	private static HashMap<Command, String> commandsToUsageStrings = new HashMap<>() {{
		put(EXIT, "exit");
		put(SET, "set <coordinate> <value>");
		put(SET_FORMULA, "setformula <coordinate> <formula>");
		put(DISPLAY, "display");
	}};

	public static Command get(String commandString) {
		return stringsToCommands.get(commandString);
	}

	public static boolean isEnoughArgumentCount(Command command, int argumentCount) {
		return argumentCount >= commandsToMinArgumentCounts.get(command);
	}

	public static void printUsage(Command command) {
		System.out.println(commandsToUsageStrings.get(command));
	}

	public static boolean isValidCoordinateFormat(String coordinate) {
		return coordinate.length() == 2;
	}
}
