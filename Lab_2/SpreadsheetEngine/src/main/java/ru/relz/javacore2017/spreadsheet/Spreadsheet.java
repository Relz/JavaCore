package main.java.ru.relz.javacore2017.spreadsheet;

import main.java.ru.relz.javacore2017.cell.*;
import main.java.ru.relz.javacore2017.datetime.DateTimeHelper;
import main.java.ru.relz.javacore2017.tree.NodeType;
import main.java.ru.relz.javacore2017.tree.Tree;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Spreadsheet {
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	private final Table table = new Table();
	private final String divisionByZeroCellString = "Бесконечность";

	public void set(Position position, String value) {
		try {
			table.setCell(position, new CellNumber(new BigDecimal(value), position));
		} catch(NumberFormatException numberFormatException) {
			try {
				table.setCell(position, new CellDate(dateFormat.parse(value).getTime(), position));
			} catch (ParseException parseException) {
				table.setCell(position, new CellString(value, position));
			}
		}
	}

	public void setFormula(Position position, String value) throws RuntimeException {
		try {
			Tree tree = Tree.createFromScanner(new Scanner(value).useDelimiter(" "), NodeType.ANY);
			if (tree.getType() == null) {
				throw new RuntimeException("Неверно задана формула");
			}
			table.setCell(position, new CellFormula(tree, position));
		} catch (RuntimeException runtimeException) {
			Printer.printLineError(runtimeException.getMessage());
		}
	}

	public void display() {
		if (table.isEmpty()) {
			return;
		}
		if (!areValidFormulaCells()) {
			return;
		}
		int maxValueLength = getMaxValueLength();
		Printer.printTableHead(maxValueLength, table.getMaxColumn().ordinal());
		for (int rowIndex = 0; rowIndex <= table.getMaxRow().ordinal(); ++rowIndex) {
			Row row = Row.createFromOrdinal(rowIndex);
			Printer.printDashes(1, 0);
			Printer.printDashes(maxValueLength, table.getMaxColumn().ordinal());
			Printer.printLine();
			Printer.printCharacterInCell(row.toCharacter());
			for (int columnIndex = 0; columnIndex <= table.getMaxColumn().ordinal(); ++columnIndex) {
				Column column = Column.createFromOrdinal(columnIndex);
				if (!table.containsCell(new Position(column, row))) {
					Printer.printEmptyCell(maxValueLength);
				} else {
					printCell(table.getCell(new Position(column, row)), maxValueLength);
				}
			}
			Printer.printLine("|");
		}
		Printer.printDashes(1, 0);
		Printer.printDashes(maxValueLength, table.getMaxColumn().ordinal());
		Printer.printLine();
	}

	private boolean areValidFormulaCells() {
		boolean result = true;
		for (CellFormula cellFormula : table.getCellsFormula()) {
			try {
				Set<CellType> determinedCellTypes = determineCellTypes(cellFormula.getTree(), new HashSet<>());
				if (determinedCellTypes.size() == 2 && determinedCellTypes.contains(CellType.DATE) && determinedCellTypes.contains(CellType.NUMBER)) {
					cellFormula.getTree().setValueType(CellType.DATE);
				} else if (determinedCellTypes.size() != 1) {
					StringBuilder cellTypesString = new StringBuilder();
					int processedCount = 0;
					for (CellType cellType : determinedCellTypes) {
						cellTypesString.append(cellType.toString());
						++processedCount;
						if (processedCount != determinedCellTypes.size()) {
							cellTypesString.append(", ");
						}
					}
					throw new RuntimeException("совмещены операции с несколькими типами: " + cellTypesString);
				} else {
					CellType cellType = determinedCellTypes.iterator().next();
					if (cellType == CellType.DATE && cellFormula.getTree().getReference() == null) {
						throw new RuntimeException("операции даты с датой не доступны");
					} else if (cellType == CellType.STRING && cellFormula.getTree().getReference() == null) {
						throw new RuntimeException("математические операции над строками не допустимы");
					} else {
						cellFormula.getTree().setValueType(cellType);
					}
				}
			} catch (RuntimeException runtimeException) {
				result = false;
				Printer.printLineError("При расчёте формулы в ячейке " + cellFormula.getPosition().toString() + ": " + runtimeException.getMessage());
			}
		}

		return result;
	}

	private int getMaxValueLength() {
		int result = 1;

		for (Cell cell : table.getCells()) {
			switch (cell.getType()) {
				case NUMBER:
					result = Math.max(result, ((CellNumber)cell).getStringValue().length());
					break;
				case STRING:
					result = Math.max(result, ((CellString)cell).getValue().length());
					break;
				case DATE:
					result = Math.max(result, ((CellDate)cell).getStringValue(dateFormat).length());
					break;
				case FORMULA:
					result = Math.max(result, calculateFormula(((CellFormula)cell).getTree()).length());
					break;
			}
		}

		return result;
	}

	private String calculateFormula(Tree tree) {
		switch (tree.getValueType()) {
			case NUMBER:
				try {
					return calculateNumberValue(tree).toString();
				} catch (ArithmeticException arithmeticException) {
					return divisionByZeroCellString;
				}
			case DATE:
				return dateFormat.format(calculateDateValue(tree));
			case STRING:
				Position position = tree.getReference();
				Cell referencedCell = table.getCell(position);
				if (referencedCell.getType() == CellType.FORMULA) {
					return calculateFormula(((CellFormula)referencedCell).getTree());
				}

				return ((CellString)referencedCell).getValue();
		}

		return "";
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
				Cell referencedCell = table.getCell(position);
				if (referencedCell.getType() == CellType.FORMULA) {
					return calculateNumberValue(((CellFormula)referencedCell).getTree());
				}

				return ((CellNumber)referencedCell).getValue();
			default:
				return tree.getNumberValue();
		}
	}

	private Long calculateDateValue(Tree tree) {
		switch (tree.getType()) {
			case OPERATION:
				Long operand0 = calculateTreeValue(tree.getLeft());
				Long operand1 = calculateTreeValue(tree.getRight());
				switch (tree.getOperation()) {
					case ADDITION:
						return operand0 + operand1;
					case SUBTRACTION:
						return operand0 - operand1;
					case MULTIPLICATION:
						return operand0 * operand1;
					case DIVISION:
						return operand0 / operand1;
				}
			case REFERENCE:
				Position position = tree.getReference();
				Cell referencedCell = table.getCell(position);
				if (referencedCell.getType() == CellType.FORMULA) {
					return calculateDateValue(((CellFormula)referencedCell).getTree());
				}

				return ((CellDate)referencedCell).getValue();
			default:
				return tree.getTimestampValue();
		}
	}

	private Long calculateTreeValue(Tree tree) {
		if (tree.getValueType() == CellType.DATE) {
			return calculateDateValue(tree);
		} else {
			return calculateNumberValue(tree).longValue() * DateTimeHelper.MILLISECONDS_IN_DAY;
		}
	}

	private Set<CellType> determineCellTypes(Tree tree, HashSet<Cell> referencedCells) throws RuntimeException {
		HashSet<CellType> result = new HashSet<>();
		if (tree.getType() == NodeType.OPERATION) {
			if (tree.getLeft() != null) {
				result.addAll(determineCellTypes(tree.getLeft(), new HashSet<>(referencedCells)));
			}
			if (tree.getRight() != null) {
				result.addAll(determineCellTypes(tree.getRight(), new HashSet<>(referencedCells)));
			}
			tree.setValueType(determineTreeValueType(tree.getLeft(), tree.getRight()));

			return result;
		}
		if (tree.getType() == NodeType.REFERENCE) {
			Position reference = tree.getReference();
			if (table.containsCell(reference)) {
				Cell referencedCell = table.getCell(reference);
				if (referencedCells.contains(referencedCell)) {
					throw new RuntimeException("Обнаружена ссылка на саму себя");
				}
				referencedCells.add(referencedCell);
				if (referencedCell.getType() == CellType.FORMULA) {
					CellFormula referencedCellFormula = (CellFormula)referencedCell;
					Set<CellType> cellTypes = determineCellTypes(referencedCellFormula.getTree(), referencedCells);
					if (cellTypes.size() == 2 && cellTypes.contains(CellType.DATE) && cellTypes.contains(CellType.NUMBER)) {
						cellTypes.remove(CellType.NUMBER);
					}
					result.addAll(cellTypes);
					tree.setValueType(referencedCellFormula.getTree().getValueType());
				} else {
					tree.setValueType(referencedCell.getType());
				}
			} else {
				throw new RuntimeException("Обнаружена ссылка на неназначенную ячейку: " + reference.toString());
			}
		} else if (tree.getNumberValue() != null) {
			tree.setValueType(CellType.NUMBER);
		} else if (tree.getTimestampValue() != null) {
			tree.setValueType(CellType.DATE);
		} else {
			tree.setValueType(CellType.STRING);
		}
		result.add(tree.getValueType());

		return result;
	}

	private CellType determineTreeValueType(Tree left, Tree right) {
		if (left.getValueType() == right.getValueType()) {
			return left.getValueType();
		}
		if (left.getValueType() == CellType.DATE && right.getValueType() == CellType.NUMBER
				|| left.getValueType() == CellType.NUMBER && right.getValueType() == CellType.DATE) {
			return CellType.DATE;
		}

		return null;
	}

	private void printCell(Cell cell, int maxValueLength) {
		switch (cell.getType()) {
			case NUMBER:
				String cellNumberValue = ((CellNumber)cell).getStringValue();
				Printer.print("| " + cellNumberValue + " ");
				Printer.printSpaces(maxValueLength - cellNumberValue.length());
				break;
			case STRING:
				String cellStringValue = ((CellString)cell).getValue();
				Printer.print("| " + cellStringValue + " ");
				Printer.printSpaces(maxValueLength - cellStringValue.length());
				break;
			case DATE:
				String cellDateValue = ((CellDate)cell).getStringValue(Spreadsheet.dateFormat);
				Printer.print("| " + cellDateValue + " ");
				Printer.printSpaces(maxValueLength - cellDateValue.length());
				break;
			case FORMULA:
				String cellFormulaValue = calculateFormula(((CellFormula)cell).getTree());
				if (cellFormulaValue.equals(divisionByZeroCellString)) {
					Printer.printErrorStringInCell(cellFormulaValue);
				} else {
					Printer.printStringInCell(cellFormulaValue);
				}
				Printer.printSpaces(maxValueLength - cellFormulaValue.length());
				break;
		}
	}
}
