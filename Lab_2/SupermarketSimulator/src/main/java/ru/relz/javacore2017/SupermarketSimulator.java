package ru.relz.javacore2017;

import ru.relz.javacore2017.model.customer.CustomerType;
import ru.relz.javacore2017.supermarket.Supermarket;
import ru.relz.javacore2017.supermarket.SupermarketWork;

import java.io.IOException;
import java.sql.SQLException;

public class SupermarketSimulator {
	private static final int workingTimeMinutes = 20;
	private static final int retiredDescountPercent = 20;

	public static void main(String[] args) throws IOException {
		Supermarket supermarket;
		try {
			supermarket = new Supermarket(workingTimeMinutes);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
		supermarket.addDiscount(CustomerType.Retired, retiredDescountPercent);
		supermarket.addCashDesk("Красная");
		supermarket.addCashDesk("Жёлтая");
		supermarket.addCashDesk("Зелёная");
		supermarket.work(new SupermarketWork());
	}
}
