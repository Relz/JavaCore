package main.java.ru.relz.javacore2017.spreadsheet;

public class Position {
	public Position(Column column, Row row) {
		this.column = column;
		this.row = row;
	}

	private final Column column;
	Column getColumn() {
		return column;
	}

	private final Row row;
	Row getRow() {
		return row;
	}

	@Override
	public String toString() {
		return String.valueOf(column.toCharacter()) + row.toCharacter();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Position)) {
			return false;
		}
		Position that = (Position)obj;

		return this.column == that.column && this.row == that.row;
	}
}
