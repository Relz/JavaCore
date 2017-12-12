package ru.relz.javacore2017.supermarket;

import ru.relz.javacore2017.model.product.Product;
import ru.relz.javacore2017.payment.ProductAdder;

public class Reporter implements ReporterInterface {
	private final ProductAdder productAdder = new ProductAdder();

	public void addProductSelling(Product product) {
		productAdder.add(product);
	}

	public void print() {
		System.out.println("Отчёт по продажам:");
		System.out.println("---------------------------------------");
		System.out.printf("| %-12s | %-10s | %-7s |%n", "Имя продукта", "Количество", "Доход");
		System.out.println("---------------------------------------");
		productAdder.getProducts().values().forEach((Product product) ->
				System.out.printf("| %-12s | %-10d | %-7.2f |%n",
						product.getName(), product.getAmount(), product.getAmount() * product.getPrice())
		);
		System.out.println("---------------------------------------");
		System.out.printf("Общий доход: %.2f\n", productAdder.calculateTotalAmount());
	}
}
