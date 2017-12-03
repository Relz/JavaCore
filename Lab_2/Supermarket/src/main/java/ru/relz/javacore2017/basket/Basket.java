package main.java.ru.relz.javacore2017.basket;

import main.java.ru.relz.javacore2017.model.product.Product;

import java.util.Iterator;
import java.util.function.Consumer;

public class Basket extends ProductContainer {
	/**
	 * Performs the given action for each element of the basket
	 *
	 * @param action callback to call every product iterator
	 * */
	public void forEachProduct(Consumer<Iterator<Product>> action) {
		Iterator<Product> productIterator = getProducts().values().iterator();
		while (productIterator.hasNext()) {
			action.accept(productIterator);
		}
	}
}
