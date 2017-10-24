package main.java.ru.relz.javacore2017;

import java.util.ArrayList;
import java.util.List;

import static main.java.ru.relz.javacore2017.RandomHelper.getRandomPaymentMethod;

class Customer {
	Customer(CustomerType type, double cash, double cardCash, double bonusCount) {
		_type = type;
		_cash = cash;
		_cardCash = cardCash;
		_bonusCount = bonusCount;
	}

	private final CustomerType _type;
	CustomerType getType() {
		return _type;
	}

	private double _cash = 0.0;
	double getCash() {
		return _cash;
	}

	void setCash(double value) {
		_cash = value;
	}

	private double _cardCash = 0.0;
	double getCardCash() {
		return _cardCash;
	}

	void setCardCash(double value) {
		_cardCash = value;
	}

	private double _bonusCount = 0.0;
	double getBonusCount() {
		return _bonusCount;
	}

	void setBonusCount(double value) {
		_bonusCount = value;
	}

	private final Basket _basket = new Basket();
	Basket getBacket() {
		return _basket;
	}

	private boolean _inQueue = false;
	boolean isInQueue() {
		return _inQueue;
	}

	void setInQueue(boolean value) {
		_inQueue = value;
	}

	PaymentMethod getDesiredPaymentMethod(double totalPaymentAmount) {
		List<PaymentMethod> availablePaymentMethods = new ArrayList<>();
		if (_cash > totalPaymentAmount) {
			availablePaymentMethods.add(PaymentMethod.Cash);
		}
		if (_cardCash > totalPaymentAmount) {
			availablePaymentMethods.add(PaymentMethod.Card);
		}
		if (_bonusCount > totalPaymentAmount) {
			availablePaymentMethods.add(PaymentMethod.Bonuses);
		}
		if (availablePaymentMethods.isEmpty()) {
			return PaymentMethod.None;
		}

		return getRandomPaymentMethod(availablePaymentMethods);
	}
}
