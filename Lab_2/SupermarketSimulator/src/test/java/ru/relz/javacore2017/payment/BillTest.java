package ru.relz.javacore2017.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.relz.javacore2017.model.product.Product;
import ru.relz.javacore2017.model.product.ProductType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BillTest {
	private static final List<Product> products = new ArrayList<>() {{
		add(createProduct(1, 10, 1, ProductType.Packed, 1));
		add(createProduct(2, 20, 5, ProductType.Packed, 10));
		add(createProduct(3, 1.5, 100, ProductType.Bulk, 15));
		add(createProduct(4, 15, 10, ProductType.Packed, 20));
		add(createProduct(5, 2.3, 150, ProductType.Bulk, 5));
		add(createProduct(6, 30, 20, ProductType.Packed, 100));
	}};
	private static Bill bill;
	private static Product createProduct(int id, double price, int amount, ProductType type, int bonus) {
		return new Product(id, "Продукт", price, amount, type, bonus, false);
	}
	@BeforeEach
	void setUp() {
		bill = new Bill();
		products.forEach(bill::add);
	}
	@Test
	void calculateTotalAmountConsiderDiscount() {
		final double totalAmount = bill.calculateTotalAmount();
		bill.setDiscount(new Discount(50));
		assertEquals(totalAmount / 2, bill.calculateTotalAmount());
	}
	@Test
	void getDiscount() {
		int discountPercentage = 50;
		assertEquals(bill.getDiscount().getPercentage(), 0);
		bill.setDiscount(new Discount(discountPercentage));
		assertEquals(bill.getDiscount().getPercentage(), discountPercentage);
	}
}
