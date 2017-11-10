package main.java.ru.relz.javacore2017.cell;

import main.java.ru.relz.javacore2017.spreadsheet.Position;
import main.java.ru.relz.javacore2017.tree.Tree;

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
