package main.java.ru.relz.javacore2017.Spreadsheet;

import main.java.ru.relz.javacore2017.Cell.*;
import main.java.ru.relz.javacore2017.Tree.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Spreadsheet {
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	private final HashMap<Row, HashMap<Column, Cell>> cells = new HashMap<>();
	private Column maxColumn = Column.A;
	private Row maxRow = Row.ONE;

	public void set(Position position, String value) {
		try {
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setParseBigDecimal(true);
			BigDecimal doubleValue = new BigDecimal(value);
			setCell(position.getColumn(), position.getRow(), new CellNumber(doubleValue));
		} catch(NumberFormatException numberFormatException) {
			try {
				Date date = dateFormat.parse(value);
				setCell(position.getColumn(), position.getRow(), new CellDate(date.getTime()));
			} catch (ParseException parseException) {
				setCell(position.getColumn(), position.getRow(), new CellString(value));
			}
		}
	}

	// Formula
	public void setFormula(Position position, String value) {
		System.out.println(value);
		try {
			Tree tree = Tree.createFromScanner(new Scanner(value).useDelimiter(" "), NodeType.OPERATION);
			cells.get(position.getRow()).put(position.getColumn(), new CellFormula(tree));
		} catch (RuntimeException runtimeExcetion) {
			System.out.println(runtimeExcetion.getMessage());
		}
	}

	public void display() {
		if (cells.isEmpty()) {
			return;
		}
		int maxValueLength = getMaxValueLength();
		printTableHead(maxValueLength);
		for (int rowIndex = 0; rowIndex <= maxRow.ordinal(); ++rowIndex) {
			Row row = Row.get(rowIndex);
			System.out.print(" ---");
			printDashes(maxValueLength);
			System.out.println();
			System.out.printf("| %c ", row.toCharacter());
			for (int columnIndex = 0; columnIndex <= maxColumn.ordinal(); ++columnIndex) {
				Column column = Column.get(columnIndex);
				if (!cells.containsKey(row) || !cells.get(row).containsKey(column)) {
					printEmptyCell(maxValueLength);
				} else {
					printCell(cells.get(row).get(column), maxValueLength);
				}
			}
			System.out.println("|");
		}
		System.out.print(" ---");
		printDashes(maxValueLength);
		System.out.println();
	}

	private void printTableHead(int maxValueLength) {
		System.out.print("    ");
		printDashes(maxValueLength);
		System.out.println();
		System.out.print("    ");
		for (int columnIndex = 0; columnIndex <= maxColumn.ordinal(); ++columnIndex) {
			char columnChar = Column.get(columnIndex).toCharacter();
			System.out.printf("| %c ", columnChar);
			printSpaces(maxValueLength - 1);
		}
		System.out.println("|");
	}

	private void printCell(Cell cell, int maxValueLength) {
		switch (cell.getType()) {
			case NUMBER:
				String cellNumberValue = getNumberCellValue(cell);
				System.out.printf("| %s ", cellNumberValue);
				printSpaces(maxValueLength - cellNumberValue.length());
				break;
			case STRING:
				String cellStringValue = getStringCellValue(cell);
				System.out.printf("| %s ", cellStringValue);
				printSpaces(maxValueLength - cellStringValue.length());
				break;
			case DATE:
				String cellDateValue = getDateCellValue(cell);
				System.out.printf("| %s ", cellDateValue);
				printSpaces(maxValueLength - cellDateValue.length());
				break;
			case FORMULA:
				try {
					String cellFormulaValue = getFormulaCellValue(cell);
					System.out.printf("| %s ", cellFormulaValue);
					printSpaces(maxValueLength - cellFormulaValue.length());
					break;
				} catch (RuntimeException runtimeException) {
					System.out.println(runtimeException.getMessage());
				}
		}
	}

	private void printEmptyCell(int maxValueLength) {
		System.out.print("|  ");
		printSpaces(maxValueLength);
	}

	private void printDashes(int maxValueLength) {
		for (int columnIndex = 0; columnIndex <= maxColumn.ordinal(); ++columnIndex) {
			System.out.print(" -");
			for (int i = 0; i < maxValueLength; ++i) {
				System.out.print("-");
			}
			System.out.print("-");
		}
	}

	private void printSpaces(int count) {
		for (int i = 0; i < count; ++i) {
			System.out.print(" ");
		}
	}

	private void setCell(Column column, Row row, Cell cell) {
		if (column.ordinal() > maxColumn.ordinal()) {
			maxColumn = column;
		}
		if (row.ordinal() > maxRow.ordinal()) {
			maxRow = row;
		}
		createRowIfNotExists(row);
		cells.get(row).put(column, cell);
	}

	private void createRowIfNotExists(Row row) {
		if (!cells.containsKey(row)) {
			cells.put(row, new HashMap<>());
		}
	}

	private int getMaxValueLength() {
		int result = 1;

		for (HashMap<Column, Cell> columnsToCells : cells.values()) {
			for (Cell cell : columnsToCells.values()) {
				switch (cell.getType()) {
					case NUMBER:
						result = Math.max(getNumberCellValue(cell).length(), result);
						break;
					case STRING:
						result = Math.max(getStringCellValue(cell).length(), result);
						break;
					case DATE:
						result = Math.max(getDateCellValue(cell).length(), result);
						break;
				}
			}
		}

		return result;
	}

	private String getNumberCellValue(Cell cell) {
		return ((CellNumber)cell).getValue().stripTrailingZeros().toString();
	}

	private String getStringCellValue(Cell cell) {
		return ((CellString)cell).getValue();
	}

	private String getDateCellValue(Cell cell) {
		return dateFormat.format(((CellDate)cell).getValue());
	}

	private String getFormulaCellValue(Cell cell) throws RuntimeException {
		CellFormula cellFormula = ((CellFormula)cell);
		CellType calculatedCellType = calculateCellType(cellFormula.getValue());
		System.out.println(calculatedCellType);

		return "";
	}

	private CellType calculateCellType(Tree tree) throws RuntimeException {
		Tree leftChild = tree;
		while (leftChild.getLeft() != null) {
			leftChild = leftChild.getLeft();
		}
		if (leftChild.getType() == NodeType.REFERENCE) {
			Position reference = leftChild.getReference();
			if (cells.containsKey(reference.getRow()) && cells.get(reference.getRow()).containsKey(reference.getColumn())) {
				return cells.get(reference.getRow()).get(reference.getColumn()).getType();
			} else {
				throw new RuntimeException("Reference to undefined cell");
			}
		}

		if (leftChild.getDoubleValue() != null) {
			return CellType.NUMBER;
		}
		if (leftChild.getDoubleValue() != null) {
			return CellType.DATE;
		}

		return CellType.STRING;
	}
}
