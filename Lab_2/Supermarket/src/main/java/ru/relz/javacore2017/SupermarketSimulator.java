package main.java.ru.relz.javacore2017;

import main.java.ru.relz.javacore2017.Customer.CustomerType;
import main.java.ru.relz.javacore2017.Supermarket.Supermarket;
import main.java.ru.relz.javacore2017.Supermarket.SupermarketWork;

import java.io.IOException;

public class SupermarketSimulator {

	public static void main(String[] args) throws IOException {
		Supermarket supermarket = new Supermarket();
		supermarket.setWorkingTimeMinutes(20);
		supermarket.addDiscount(CustomerType.Retired, 20);
		supermarket.addCashDesk("Красная");
		supermarket.addCashDesk("Жёлтая");
		supermarket.addCashDesk("Зелёная");
		supermarket.work(new SupermarketWork());
	}
}
