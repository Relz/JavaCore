package main.java.ru.relz.javacore2017.Cell;

import main.java.ru.relz.javacore2017.Spreadsheet.Position;

public class CellString extends Cell {
	public CellString(String value, Position position) {
		super(CellType.STRING, position);
		this.value = value;
	}

	private String value;
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
