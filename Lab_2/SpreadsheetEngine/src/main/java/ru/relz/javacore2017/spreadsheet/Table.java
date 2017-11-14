package main.java.ru.relz.javacore2017.spreadsheet;

import main.java.ru.relz.javacore2017.cell.Cell;
import main.java.ru.relz.javacore2017.cell.CellFormula;
import main.java.ru.relz.javacore2017.cell.CellType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class Table {
	private final HashMap<Row, HashMap<Column, Cell>> cells = new HashMap<>();
	private final HashSet<Cell> cellsFormula = new HashSet<>();

	private Column maxColumn = Column.A;
	Column getMaxColumn() {
		return maxColumn;
	}

	private Row maxRow = Row.ONE;
	Row getMaxRow() {
		return maxRow;
	}

	boolean isEmpty() {
		return cells.isEmpty();
	}

	boolean containsCell(Position position) {
		return cells.containsKey(position.getRow())
				&& cells.get(position.getRow()).containsKey(position.getColumn());
	}

	Cell getCell(Position position) {
		return cells.get(position.getRow()).get(position.getColumn());
	}

	void setCell(Position position, Cell cell) {
		maxColumn = Column.max(maxColumn, position.getColumn());
		maxRow = Row.max(maxRow, position.getRow());
		cells.putIfAbsent(position.getRow(), new HashMap<>());
		Cell oldCell = cells.get(position.getRow()).get(position.getColumn());
		if (oldCell != null && oldCell.getType() == CellType.FORMULA) {
			cellsFormula.remove(oldCell);
		}
		if (cell.getType() == CellType.FORMULA) {
			cellsFormula.add(cell);
		}
		cells.get(position.getRow()).put(position.getColumn(), cell);
	}

	Set<CellFormula> getCellsFormula() {
		Set<CellFormula> result = new HashSet<CellFormula>();
		for (Cell cell : cellsFormula) {
			result.add((CellFormula)cell);
		}

		return result;
	}

	Set<Cell> getCells() {
		Set<Cell> result = new HashSet<>();
		for (HashMap<Column, Cell> columnCellHashMap : cells.values()) {
			result.addAll(columnCellHashMap.values());
		}

		return result;
	}
}
