package main.java.ru.relz.javacore2017.Cell;

public class CellDate extends Cell {
	public CellDate(long value) {
		super(CellType.DATE);
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
