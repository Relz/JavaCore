package main.java.ru.relz.javacore2017.RandomHelper;

import main.java.ru.relz.javacore2017.Payment.PaymentMethod;
import main.java.ru.relz.javacore2017.Product.*;

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
	public static PaymentMethod getRandomPaymentMethod(List<PaymentMethod> paymentMethods) {
		return paymentMethods.get(getRandomNumber(0, paymentMethods.size() - 1));
	}
}
