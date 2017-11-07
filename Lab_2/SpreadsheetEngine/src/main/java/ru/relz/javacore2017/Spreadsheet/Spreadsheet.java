package main.java.ru.relz.javacore2017.Spreadsheet;

import main.java.ru.relz.javacore2017.Cell.*;
import main.java.ru.relz.javacore2017.Tree.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Spreadsheet {
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	private final HashMap<Row, HashMap<Column, Cell>> cells = new HashMap<>();
	private final HashSet<Cell> cellsWithFormula = new HashSet<>();

	private Column maxColumn = Column.A;
	private Row maxRow = Row.ONE;

	public void set(Position position, String value) {
		try {
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setParseBigDecimal(true);
			BigDecimal doubleValue = new BigDecimal(value);
			setCell(position, new CellNumber(doubleValue, position));
		} catch(NumberFormatException numberFormatException) {
			try {
				Date date = dateFormat.parse(value);
				setCell(position, new CellDate(date.getTime(), position));
			} catch (ParseException parseException) {
				setCell(position, new CellString(value, position));
			}
		}
	}

	// Formula
	public void setFormula(Position position, String value) {
		try {
			Tree tree = Tree.createFromScanner(new Scanner(value).useDelimiter(" "), NodeType.OPERATION);
			setCell(position, new CellFormula(tree, position));
		} catch (RuntimeException runtimeExcetion) {
			System.out.println(runtimeExcetion.getMessage());
		}
	}

	public void display() {
		if (cells.isEmpty()) {
			return;
		}
		if (!areValidFormulaCells()) {
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

	private boolean areValidFormulaCells() {
		boolean result = true;
		for (Cell cell : cellsWithFormula) {
			CellFormula cellFormula = ((CellFormula)cell);
			try {
				HashSet<CellType> determinedCellTypes = determineCellTypes(cellFormula.getTree());
				if (determinedCellTypes.size() != 1) {
					result = false;
					System.out.printf("Формула в ячейке %s совмещает в себе операции с несколькими типами: ", cellFormula.getPosition().toString());
					for (CellType determinedCellType : determinedCellTypes) {
						System.out.print(determinedCellType.toString() + ", ");
					}
					System.out.println();
				} else {
					cellFormula.getTree().setValueType(determinedCellTypes.iterator().next());
				}
			} catch (RuntimeException runtimeException) {
				result = false;
				System.out.printf("В ячейке %s: %s\n", cellFormula.getPosition().toString(), runtimeException.getMessage());
			}
		}

		return result;
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
				String cellFormulaValue = getFormulaCellValue(cell);
				System.out.printf("| %s ", cellFormulaValue);
				printSpaces(maxValueLength - cellFormulaValue.length());
				break;
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

	private void setCell(Position position, Cell cell) {
		if (position.getColumn().ordinal() > maxColumn.ordinal()) {
			maxColumn = position.getColumn();
		}
		if (position.getRow().ordinal() > maxRow.ordinal()) {
			maxRow = position.getRow();
		}
		createRowIfNotExists(cells, position.getRow());
		Cell oldCell = cells.get(position.getRow()).get(position.getColumn());
		if (oldCell != null && oldCell.getType() == CellType.FORMULA) {
			cellsWithFormula.remove(oldCell);
		}
		cells.get(position.getRow()).put(position.getColumn(), cell);
		if (cell.getType() == CellType.FORMULA) {
			cellsWithFormula.add(cell);
		}
	}

	private void createRowIfNotExists(HashMap<Row, HashMap<Column, Cell>> table, Row row) {
		if (!table.containsKey(row)) {
			table.put(row, new HashMap<>());
		}
	}

	private int getMaxValueLength() {
		int result = 1;

		for (HashMap<Column, Cell> columnsToCells : cells.values()) {
			for (Cell cell : columnsToCells.values()) {
				switch (cell.getType()) {
					case NUMBER:
						result = Math.max(result, getNumberCellValue(cell).length());
						break;
					case STRING:
						result = Math.max(result, getStringCellValue(cell).length());
						break;
					case DATE:
						result = Math.max(result, getDateCellValue(cell).length());
						break;
					case FORMULA:
						result = Math.max(result, getFormulaCellValue(cell).length());
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

	private String getFormulaCellValue(Cell cell) {
		return calculateFormula(((CellFormula)cell).getTree());
	}

	private String calculateFormula(Tree tree) {
		switch (tree.getValueType()) {
			case NUMBER:
				return calculateNumberValue(tree).toString();
			case DATE:
				return dateFormat.format(calculateDateValue(tree));
			default:
				return "";
		}
	}

	private BigDecimal calculateNumberValue(Tree tree) {
		switch (tree.getType()) {
			case OPERATION:
				BigDecimal operand0 = calculateNumberValue(tree.getLeft());
				BigDecimal operand1 = calculateNumberValue(tree.getRight());
				switch (tree.getOperation()) {
					case ADDITION:
						return operand0.add(operand1);
					case SUBTRACTION:
						return operand0.subtract(operand1);
					case MULTIPLICATION:
						return operand0.multiply(operand1);
					case DIVISION:
						return operand0.divide(operand1, RoundingMode.HALF_DOWN);
				}
			case REFERENCE:
				Position position = tree.getReference();
				Cell referencedCell = cells.get(position.getRow()).get(position.getColumn());

				return ((CellNumber)referencedCell).getValue();
			default:
				return tree.getNumberValue();
		}
	}

	private Long calculateDateValue(Tree tree) {
		switch (tree.getType()) {
			case OPERATION:
				return calculateDateValue(tree.getLeft()) + calculateDateValue(tree.getRight());
			case REFERENCE:
				Position position = tree.getReference();
				Cell referencedCell = cells.get(position.getRow()).get(position.getColumn());

				return ((CellDate)referencedCell).getValue();
			default:
				return tree.getTimestampValue();
		}
	}

	private HashSet<CellType> determineCellTypes(Tree tree) throws RuntimeException {
		HashSet<CellType> result = new HashSet<>();
		if (tree.getLeft() != null) {
			result.addAll(determineCellTypes(tree.getLeft()));
		}
		if (tree.getRight() != null) {
			result.addAll(determineCellTypes(tree.getRight()));
		}
		if (tree.getType() == NodeType.OPERATION) {
			return result;
		}
		if (tree.getType() == NodeType.REFERENCE) {
			Position reference = tree.getReference();
			if (cells.containsKey(reference.getRow()) && cells.get(reference.getRow()).containsKey(reference.getColumn())) {
				Cell referencedCell = cells.get(reference.getRow()).get(reference.getColumn());
				result.add(referencedCell.getType());
			} else {
				throw new RuntimeException("Обнаружена ссылка на неназначенную ячейку: " + reference.toString());
			}
		} else if (tree.getNumberValue() != null) {
			result.add(CellType.NUMBER);
		} else if (tree.getTimestampValue() != null) {
			result.add(CellType.DATE);
		} else {
			result.add(CellType.STRING);
		}

		return result;
	}
}
