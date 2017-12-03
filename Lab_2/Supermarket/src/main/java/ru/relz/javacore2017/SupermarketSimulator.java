package main.java.ru.relz.javacore2017;

import main.java.ru.relz.javacore2017.model.customer.CustomerType;
import main.java.ru.relz.javacore2017.supermarket.Supermarket;
import main.java.ru.relz.javacore2017.supermarket.SupermarketWork;

import java.io.IOException;

public class SupermarketSimulator {
	private static final int workingTimeMinutes = 20;
	private static final int retiredDescountPercent = 20;

	public static void main(String[] args) throws IOException {
		Supermarket supermarket = new Supermarket(workingTimeMinutes);
		supermarket.addDiscount(CustomerType.Retired, retiredDescountPercent);
		supermarket.addCashDesk("Красная");
		supermarket.addCashDesk("Жёлтая");
		supermarket.addCashDesk("Зелёная");
		supermarket.work(new SupermarketWork());
	}
}
