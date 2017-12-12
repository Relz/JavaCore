package ru.relz.javacore2017.supermarket;

import ru.relz.javacore2017.model.product.Product;

public interface ReporterInterface {
	void addProductSelling(Product product);

	void print();
}
