package main.java.ru.relz.javacore2017.spreadsheet;

import main.java.ru.relz.javacore2017.cell.CellType;

import java.util.Set;

final class Printer {
	static void printTableHead(int maxValueLength, int maxColumn) {
		printSpaces(4);
		printDashes(maxValueLength, maxColumn);
		printLine();
		printSpaces(4);
		for (int columnIndex = 0; columnIndex <= maxColumn; ++columnIndex) {
			printCharacterInCell(Column.createFromOrdinal(columnIndex).toCharacter());
			printSpaces(maxValueLength - 1);
		}
		printLine("|");
	}

	static void printEmptyCell(int maxValueLength) {
		print("|  ");
		printSpaces(maxValueLength);
	}

	static void printCharacterInCell(char character) {
		printStringInCell(String.valueOf(character));
	}

	static void printStringInCell(String string) {
		print("| " + string + " ");
	}

	static void printErrorStringInCell(String string) {
		print("| ");
		printError(string);
		print(" ");
	}

	static void printDashes(int maxValueLength, int maxColumn) {
		for (int columnIndex = 0; columnIndex <= maxColumn; ++columnIndex) {
			print(" -");
			for (int i = 0; i < maxValueLength; ++i) {
				print("-");
			}
			print("-");
		}
	}

	static void printSpaces(int count) {
		for (int i = 0; i < count; ++i) {
			print(" ");
		}
	}

	static void print(String string) {
		System.out.printf("%s", string);
	}

	static void printError(String string) {
		System.out.printf("\033[31m%s\033[37m", string);
	}

	static void printLine() {
		System.out.println();
	}

	static void printLine(String string) {
		System.out.println(string);
	}

	static void printLineError(String string) {
		Printer.printError(string);
		Printer.printLine();
	}
}
