package main.java.ru.relz.javacore2017.spreadsheet;

final class Printer {
	static void printTableHead(int maxValueLength, int maxColumn) {
		System.out.print("    ");
		printDashes(maxValueLength, maxColumn);
		System.out.println();
		System.out.print("    ");
		for (int columnIndex = 0; columnIndex <= maxColumn; ++columnIndex) {
			char columnChar = Column.get(columnIndex).toCharacter();
			System.out.printf("| %c ", columnChar);
			printSpaces(maxValueLength - 1);
		}
		System.out.println("|");
	}

	static void printEmptyCell(int maxValueLength) {
		System.out.print("|  ");
		printSpaces(maxValueLength);
	}

	static void printDashes(int maxValueLength, int maxColumn) {
		for (int columnIndex = 0; columnIndex <= maxColumn; ++columnIndex) {
			System.out.print(" -");
			for (int i = 0; i < maxValueLength; ++i) {
				System.out.print("-");
			}
			System.out.print("-");
		}
	}

	static void printSpaces(int count) {
		for (int i = 0; i < count; ++i) {
			System.out.print(" ");
		}
	}
}
