package main.java.ru.relz.javacore2017.Cell;

import main.java.ru.relz.javacore2017.Spreadsheet.Position;
import main.java.ru.relz.javacore2017.Tree.Tree;

public class CellFormula extends Cell {
	public CellFormula(Tree tree, Position position) {
		super(CellType.FORMULA, position);
		this.tree = tree;
	}

	private Tree tree;
	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}
}
