package main.java.ru.relz.javacore2017;

import main.java.ru.relz.javacore2017.Spreadsheet.*;

import java.util.Scanner;

public class SpreadsheetEngine {
	private static final String coordinateFormat = "Coordinate: <column><row>";
	private static final String columnFormat = "Column: <A-Z>";
	private static final String rowFormat = "Row: <1-9>";

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		keyboard.useDelimiter("\n");
		Spreadsheet spreadsheet = new Spreadsheet();

		System.out.println("Welcome to spreadsheet engine!");
		boolean work = true;
		while (work) {
			System.out.print("> ");
			String[] splitInput = keyboard.next().split(" ");
			Command command = Command.get(splitInput[0]);
			if (command == null) {
				System.out.printf("Unknown command: %s\n", splitInput[0]);
				continue;
			}
			if (!Command.isEnoughArgumentCount(command, splitInput.length)) {
				Command.printUsage(command);
				continue;
			}

			switch (command) {
				case EXIT: {
					work = false;
					break;
				}
				case SET: {
					String coordinate = splitInput[1];
					if (!Command.isValidCoordinateFormat(coordinate)) {
						System.out.println(coordinateFormat);
						continue;
					}
					Column column = Column.get(coordinate.charAt(0));
					if (column == null) {
						System.out.println(columnFormat);
						continue;
					}
					Row row = Row.get(coordinate.charAt(1));
					if (row == null) {
						System.out.println(rowFormat);
						continue;
					}
					spreadsheet.set(new Position(column, row), splitInput[2]);
					break;
				}
				case SET_FORMULA: {
					String coordinate = splitInput[1];
					if (!Command.isValidCoordinateFormat(coordinate)) {
						System.out.println(coordinateFormat);
						continue;
					}
					Column column = Column.get(coordinate.charAt(0));
					if (column == null) {
						System.out.println(columnFormat);
						continue;
					}
					Row row = Row.get(coordinate.charAt(1));
					if (row == null) {
						System.out.println(rowFormat);
						continue;
					}
					spreadsheet.setFormula(new Position(column, row), buildFormula(splitInput).toString());
					break;
				}
				case DISPLAY: {
					spreadsheet.display();
					break;
				}
			}
		}
		System.out.println("See you!");
	}

	private static StringBuilder buildFormula(String[] splitInput) {
		StringBuilder result = new StringBuilder();
		for (int argumentIndex = 2; argumentIndex < splitInput.length; ++argumentIndex) {
			for (int i = 0; i < splitInput[argumentIndex].length(); ++i) {
				char character = splitInput[argumentIndex].charAt(i);
				if (character == '(' || character == ')') {
					character = ' ';
					if (i == splitInput[argumentIndex].length() - 1) {
						break;
					}
				}
				result.append(String.valueOf(character));
			}
			result.append(" ");
		}
		result.deleteCharAt(result.length() - 1);

		return result;
	}
}
