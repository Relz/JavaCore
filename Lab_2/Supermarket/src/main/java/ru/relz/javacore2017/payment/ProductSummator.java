package main.java.ru.relz.javacore2017.payment;

import main.java.ru.relz.javacore2017.basket.ProductContainer;
import main.java.ru.relz.javacore2017.model.product.Product;

public class ProductSummator extends ProductContainer {
	public double calculateTotalAmount() {
		double result = 0;
		for (Product product : getProducts().values()) {
			result += product.getAmount() * product.getPrice();
		}

		return result;
	}

	public double calculateTotalBonuses() {
		double result = 0;
		for (Product product : getProducts().values()) {
			result += product.getAmount() * product.getBonus();
		}

		return result;
	}
}
