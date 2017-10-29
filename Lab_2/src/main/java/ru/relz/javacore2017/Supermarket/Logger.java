package main.java.ru.relz.javacore2017.Supermarket;

import main.java.ru.relz.javacore2017.Payment.ProductSummator;
import main.java.ru.relz.javacore2017.Product.Product;

public class Logger {
	private ProductSummator productSummator = new ProductSummator();

	public void addProductSelling(Product product) {
		productSummator.add(product);
	}

	public void removeProductSelling(Product product) {
		productSummator.remove(product);
	}

	public void clear(Product product) {
		productSummator.clear();
	}

	public void printOut() {
		System.out.println("Отчёт по продажам:");
		System.out.println("---------------------------------------");
		System.out.printf("| %-12s | %-10s | %-7s |%n", "Имя продукта", "Количество", "Доход");
		System.out.println("---------------------------------------");
		productSummator.getProducts().forEach((Product product) ->
			System.out.printf("| %-12s | %-10d | %-7.2f |%n", product.getName(), product.getAmount(), product.getAmount() * product.getPrice())
		);
		System.out.println("---------------------------------------");
		System.out.printf("Общий доход: %.2f\n", productSummator.calculateTotalAmount());
	}
}
