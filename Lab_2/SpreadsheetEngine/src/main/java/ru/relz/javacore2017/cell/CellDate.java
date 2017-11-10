package main.java.ru.relz.javacore2017.cell;

import main.java.ru.relz.javacore2017.spreadsheet.Position;

public class CellDate extends Cell {
	public CellDate(long value, Position position) {
		super(CellType.DATE, position);
		this.value = value;
	}

	private long value;
	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
}
