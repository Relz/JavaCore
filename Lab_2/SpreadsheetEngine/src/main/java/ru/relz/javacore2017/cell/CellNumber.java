package main.java.ru.relz.javacore2017.cell;

import main.java.ru.relz.javacore2017.spreadsheet.Position;

import java.math.BigDecimal;

public class CellNumber extends Cell {
	public CellNumber(BigDecimal value, Position position) {
		super(CellType.NUMBER, position);
		this.value = value;
	}

	private BigDecimal value;
	public BigDecimal getValue() {
		return value;
	}

	public String getStringValue() {
		return value.stripTrailingZeros().toString();
	}
}
