package main.java.ru.relz.javacore2017;

import java.util.List;

class RandomHelper {
	static int getRandomNumber(int from, int to) {
		return (int) (Math.round(Math.random() * (to - from)) + from);
	}
	static int getRandomProductAmount(Product product, int productMaxAmount) {
		switch (product.getType()) {
			case Packed:
				return getRandomNumber(1, productMaxAmount);
			case Bulk:
				return getRandomNumber(100, productMaxAmount);
			default:
				return 1;
		}
	}
	static PaymentMethod getRandomPaymentMethod(List<PaymentMethod> paymentMethods) {
		return paymentMethods.get(getRandomNumber(0, paymentMethods.size() - 1));
	}
}
