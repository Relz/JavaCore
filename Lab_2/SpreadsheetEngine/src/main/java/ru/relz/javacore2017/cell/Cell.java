package main.java.ru.relz.javacore2017.cell;

import main.java.ru.relz.javacore2017.spreadsheet.IPositioned;
import main.java.ru.relz.javacore2017.spreadsheet.Position;

public class Cell implements IPositioned {
	public Cell(CellType type, Position position) {
		this.type = type;
		this.position = position;
	}

	private CellType type;
	public CellType getType() {
		return type;
	}

	private Position position;
	public Position getPosition() {
		return position;
	}
}
