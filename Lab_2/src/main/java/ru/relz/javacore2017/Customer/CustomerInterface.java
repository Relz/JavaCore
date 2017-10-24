package main.java.ru.relz.javacore2017.Customer;

import main.java.ru.relz.javacore2017.Basket.Basket;
import main.java.ru.relz.javacore2017.Payment.PaymentMethod;

public interface CustomerInterface {
	int getId();
	CustomerType getType();
	Basket getBacket();
	boolean isInQueue();
	void setInQueue(boolean value);

	/**
	 * Decrease customer's cash, card cash or bonuses in depends of payment method.
	 * Increase customer's bonuses if payment method in not bonuses
	 *
	 * @return {@code true} if customer has enough cash, card cash or bonuses depends of payment method
	 *         {@code false} if customer hasn't enough cash, card cash or bonuses depends of payment method
	 * */
	boolean pay(double totalAmount, double possibleBonuses, PaymentMethod paymentMethod);
}
