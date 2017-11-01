package main.java.ru.relz.javacore2017.Cell;

public class CellString extends Cell {
	public CellString(String value) {
		super(CellType.STRING);
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
