package main.java.ru.relz.javacore2017.cell;

import main.java.ru.relz.javacore2017.spreadsheet.Position;

public class Cell {
	public Cell(CellType type, Position position) {
		this.type = type;
		this.position = position;
	}

	private CellType type;
	public CellType getType() {
		return type;
	}

	public void setType(CellType value) {
		type = value;
	}

	private Position position;
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position value) {
		position = value;
	}
}
