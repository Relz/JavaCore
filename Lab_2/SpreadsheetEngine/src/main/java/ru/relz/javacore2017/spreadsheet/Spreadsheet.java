package main.java.ru.relz.javacore2017.spreadsheet;

import main.java.ru.relz.javacore2017.cell.*;
import main.java.ru.relz.javacore2017.datetime.DateTimeHelper;
import main.java.ru.relz.javacore2017.tree.NodeType;
import main.java.ru.relz.javacore2017.tree.Tree;

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

	private final String divisionByZeroCellString = "Бесконечность";

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

	public void setFormula(Position position, String value) throws RuntimeException {
		try {
			Tree tree = Tree.createFromScanner(new Scanner(value).useDelimiter(" "), NodeType.ANY);
			if (tree.getType() == null) {
				throw new RuntimeException("Неверно задана формула");
			}
			setCell(position, new CellFormula(tree, position));
		} catch (RuntimeException runtimeExcetion) {
			System.out.println(runtimeExcetion.getMessage());
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

	public void display() {
		if (cells.isEmpty()) {
			return;
		}
		if (!areValidFormulaCells()) {
			return;
		}
		int maxValueLength = getMaxValueLength();
		Printer.printTableHead(maxValueLength, maxColumn.ordinal());
		for (int rowIndex = 0; rowIndex <= maxRow.ordinal(); ++rowIndex) {
			Row row = Row.get(rowIndex);
			System.out.print(" ---");
			Printer.printDashes(maxValueLength, maxColumn.ordinal());
			System.out.println();
			System.out.printf("| %c ", row.toCharacter());
			for (int columnIndex = 0; columnIndex <= maxColumn.ordinal(); ++columnIndex) {
				Column column = Column.get(columnIndex);
				if (!cells.containsKey(row) || !cells.get(row).containsKey(column)) {
					Printer.printEmptyCell(maxValueLength);
				} else {
					printCell(cells.get(row).get(column), maxValueLength);
				}
			}
			System.out.println("|");
		}
		System.out.print(" ---");
		Printer.printDashes(maxValueLength, maxColumn.ordinal());
		System.out.println();
	}

	private boolean areValidFormulaCells() {
		boolean result = true;
		for (Cell cell : cellsWithFormula) {
			CellFormula cellFormula = ((CellFormula)cell);
			try {
				Set<CellType> determinedCellTypes = determineCellTypes(cellFormula.getTree(), new HashSet<>());
				if (determinedCellTypes.size() == 2 && determinedCellTypes.contains(CellType.DATE) && determinedCellTypes.contains(CellType.NUMBER)) {
					cellFormula.getTree().setValueType(CellType.DATE);
				} else if (determinedCellTypes.size() != 1) {
					result = false;
					System.out.printf("Формула в ячейке %s совмещает в себе операции с несколькими типами: ", cellFormula.getPosition().toString());
					for (CellType determinedCellType : determinedCellTypes) {
						System.out.print(determinedCellType.toString() + ", ");
					}
					System.out.println();
				} else {
					CellType cellType = determinedCellTypes.iterator().next();
					if (cellType == CellType.DATE && cellFormula.getTree().getReference() == null) {
						result = false;
						System.out.println("Операции даты с датой не доступны");
					} else if (cellType == CellType.STRING && cellFormula.getTree().getReference() == null) {
						result = false;
						System.out.println("Математические операции над строками не допустимы");
					} else {
						cellFormula.getTree().setValueType(cellType);
					}
				}
			} catch (RuntimeException runtimeException) {
				result = false;
				System.out.printf("При расчёте формулы в ячейке %s: %s\n", cellFormula.getPosition().toString(), runtimeException.getMessage());
			}
		}

		return result;
	}

	private int getMaxValueLength() {
		int result = 1;

		for (HashMap<Column, Cell> columnsToCells : cells.values()) {
			for (Cell cell : columnsToCells.values()) {
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
				Cell referencedCell = cells.get(position.getRow()).get(position.getColumn());
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
				Cell referencedCell = cells.get(position.getRow()).get(position.getColumn());
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
						return (tree.getLeft().getValueType() == CellType.NUMBER) ? operand1 - operand0 : operand0 - operand1;
					case MULTIPLICATION:
						return operand0 * operand1;
					case DIVISION:
						return operand0 / operand1;
				}
			case REFERENCE:
				Position position = tree.getReference();
				Cell referencedCell = cells.get(position.getRow()).get(position.getColumn());
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
			if (cells.containsKey(reference.getRow()) && cells.get(reference.getRow()).containsKey(reference.getColumn())) {
				Cell referencedCell = cells.get(reference.getRow()).get(reference.getColumn());
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
				System.out.printf("| %s ", cellNumberValue);
				Printer.printSpaces(maxValueLength - cellNumberValue.length());
				break;
			case STRING:
				String cellStringValue = ((CellString)cell).getValue();
				System.out.printf("| %s ", cellStringValue);
				Printer.printSpaces(maxValueLength - cellStringValue.length());
				break;
			case DATE:
				String cellDateValue = ((CellDate)cell).getStringValue(Spreadsheet.dateFormat);
				System.out.printf("| %s ", cellDateValue);
				Printer.printSpaces(maxValueLength - cellDateValue.length());
				break;
			case FORMULA:
				String cellFormulaValue = calculateFormula(((CellFormula)cell).getTree());
				System.out.print("| ");
				if (cellFormulaValue.equals(divisionByZeroCellString)) {
					System.out.printf("\033[31m%s\033[37m", cellFormulaValue);
				} else {
					System.out.printf("%s", cellFormulaValue);
				}
				System.out.print(" ");
				Printer.printSpaces(maxValueLength - cellFormulaValue.length());
				break;
		}
	}
}
