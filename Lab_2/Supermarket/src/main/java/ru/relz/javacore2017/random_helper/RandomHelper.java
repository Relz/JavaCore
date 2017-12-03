package main.java.ru.relz.javacore2017.random_helper;

import main.java.ru.relz.javacore2017.payment.PaymentMethod;
import main.java.ru.relz.javacore2017.model.product.*;

import java.util.List;

public final class RandomHelper {
	public static int getRandomNumber(int from, int to) {
		return (int) (Math.round(Math.random() * (to - from)) + from);
	}
	public static int getRandomProductAmount(Product product, int productMaxAmount) {
		switch (product.getType()) {
			case Packed:
				return getRandomNumber(1, productMaxAmount);
			case Bulk:
				return getRandomNumber(100, productMaxAmount);
			default:
				return 1;
		}
	}
}
