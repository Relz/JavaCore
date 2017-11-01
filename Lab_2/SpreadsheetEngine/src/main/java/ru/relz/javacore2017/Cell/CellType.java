package main.java.ru.relz.javacore2017.Cell;

public enum CellType {
	NUMBER("число"),
	STRING("строка"),
	DATE("дата"),
	FORMULA("формула");

	private final String string;

	CellType(final String string) {
		this.string = string;
	}

	@Override
	public String toString() {
		return string;
	}
}
