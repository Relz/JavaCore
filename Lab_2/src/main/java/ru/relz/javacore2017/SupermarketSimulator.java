package main.java.ru.relz.javacore2017;

import java.io.IOException;

public class SupermarketSimulator {

	public static void main(String[] args) throws IOException {
		Supermarket supermarket = new Supermarket();
		supermarket.setWorkingTimeMinutes(50);
		supermarket.addCashDesk("Красная");
		supermarket.addCashDesk("Жёлтая");
		supermarket.addCashDesk("Зелёная");
		supermarket.work(new SupermarketWork());
	}
}
