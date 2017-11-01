package main.java.ru.relz.javacore2017.Cell;

import java.math.BigDecimal;

public class CellNumber extends Cell{
	public CellNumber(BigDecimal value) {
		super(CellType.NUMBER);
		this.value = value;
	}

	private BigDecimal value;
	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}
