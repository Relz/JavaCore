package main.java.ru.relz.javacore2017.Cell;

import main.java.ru.relz.javacore2017.Spreadsheet.Position;
import main.java.ru.relz.javacore2017.Tree.Tree;

public class CellFormula extends Cell {
	public CellFormula(Tree value, Position position) {
		super(CellType.FORMULA, position);
		this.value = value;
	}

	private Tree value;
	public Tree getValue() {
		return value;
	}

	public void setValue(Tree value) {
		this.value = value;
	}

	public String calculate() {

		return "";
	}
}
