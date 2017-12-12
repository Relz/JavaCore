package ru.relz.javacore2017.model.bucket;

import ru.relz.javacore2017.model.ProductContainer.ProductContainer;
import ru.relz.javacore2017.model.product.Product;

import java.util.Iterator;
import java.util.function.Consumer;

public class Bucket extends ProductContainer {
	/**
	 * Performs the given action for each element of the bucket
	 *
	 * @param action callback to call every product iterator
	 */
	public void forEachProduct(Consumer<Iterator<Product>> action) {
		Iterator<Product> productIterator = getProducts().values().iterator();
		while (productIterator.hasNext()) {
			action.accept(productIterator);
		}
	}
}
