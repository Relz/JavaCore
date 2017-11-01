package main.java.ru.relz.javacore2017.Cell;

public class Cell {
	public Cell(CellType type) {
		this.type = type;
	}

	private CellType type;
	public CellType getType() {
		return type;
	}

	public void setType(CellType type) {
		this.type = type;
	}
}
