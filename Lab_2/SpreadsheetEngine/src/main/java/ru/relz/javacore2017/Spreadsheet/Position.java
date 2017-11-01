package main.java.ru.relz.javacore2017.Spreadsheet;

public class Position {
	public Position(Column column, Row row) {
		this.column = column;
		this.row = row;
	}

	private Column column;
	public Column getColumn() {
		return column;
	}

	private Row row;
	public Row getRow() {
		return row;
	}

	@Override
	public String toString() {
		return String.valueOf(column.toCharacter()) + row.toCharacter();
	}
}
