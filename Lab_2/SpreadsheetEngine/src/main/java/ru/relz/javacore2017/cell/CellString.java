package main.java.ru.relz.javacore2017.cell;

import main.java.ru.relz.javacore2017.spreadsheet.Position;

public class CellString extends Cell {
	public CellString(String value, Position position) {
		super(CellType.STRING, position);
		this.value = value;
	}

	private String value;
	public String getValue() {
		return value;
	}
}
