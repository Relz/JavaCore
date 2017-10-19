package main.java.ru.relz.javacore2017;

public class SupermarketSimulator {

	public static void main(String[] args) {
		Supermarket supermarket = new Supermarket();
		supermarket.setWorkingTimeMinutes(10);
		supermarket.work(new SupermarketWork());
	}
}
