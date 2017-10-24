package main.java.ru.relz.javacore2017;

import java.util.ArrayList;
import java.util.List;

import static main.java.ru.relz.javacore2017.RandomHelper.getRandomPaymentMethod;

class Customer {
	Customer(CustomerType type, double cash, double cardCash, double bonusCount) {
		this.type = type;
		this.cash = cash;
		this.cardCash = cardCash;
		this.bonusCount = bonusCount;
	}

	private final CustomerType type;
	CustomerType getType() {
		return type;
	}

	private double cash = 0.0;
	double getCash() {
		return cash;
	}

	void setCash(double value) {
		cash = value;
	}

	private double cardCash = 0.0;
	double getCardCash() {
		return cardCash;
	}

	void setCardCash(double value) {
		cardCash = value;
	}

	private double bonusCount = 0.0;
	double getBonusCount() {
		return bonusCount;
	}

	void setBonusCount(double value) {
		bonusCount = value;
	}

	private final Basket basket = new Basket();
	Basket getBacket() {
		return basket;
	}

	private boolean inQueue = false;
	boolean isInQueue() {
		return inQueue;
	}

	void setInQueue(boolean value) {
		inQueue = value;
	}

	PaymentMethod getDesiredPaymentMethod(double totalPaymentAmount) {
		List<PaymentMethod> availablePaymentMethods = new ArrayList<>();
		if (cash > totalPaymentAmount) {
			availablePaymentMethods.add(PaymentMethod.Cash);
		}
		if (cardCash > totalPaymentAmount) {
			availablePaymentMethods.add(PaymentMethod.Card);
		}
		if (bonusCount > totalPaymentAmount) {
			availablePaymentMethods.add(PaymentMethod.Bonuses);
		}
		if (availablePaymentMethods.isEmpty()) {
			return PaymentMethod.None;
		}

		return getRandomPaymentMethod(availablePaymentMethods);
	}
}
