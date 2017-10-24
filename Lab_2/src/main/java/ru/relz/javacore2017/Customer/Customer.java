package main.java.ru.relz.javacore2017.Customer;

import main.java.ru.relz.javacore2017.Basket.*;
import main.java.ru.relz.javacore2017.Payment.*;

import java.util.ArrayList;
import java.util.List;

import static main.java.ru.relz.javacore2017.RandomHelper.RandomHelper.*;

public class Customer implements CustomerInterface, CashHolder, CardCashHolder, BonusesHolder {
	public Customer(CustomerType type, double cash, double cardCash, double bonusCount) {
		this.type = type;
		this.cash = cash;
		this.cardCash = cardCash;
		this.bonusCount = bonusCount;
	}

	private int id;
	public int getId() {
		return id;
	}

	public void setId(int value) {
		id = value;
	}

	private final CustomerType type;
	public CustomerType getType() {
		return type;
	}

	private double cash = 0.0;
	public double getCash() {
		return cash;
	}

	public void setCash(double value) {
		cash = value;
	}

	private double cardCash = 0.0;
	public double getCardCash() {
		return cardCash;
	}

	public void setCardCash(double value) {
		cardCash = value;
	}

	private double bonusCount = 0.0;
	public double getBonusCount() {
		return bonusCount;
	}

	public void setBonusCount(double value) {
		bonusCount = value;
	}

	private final Basket basket = new Basket();
	public Basket getBacket() {
		return basket;
	}

	private boolean inQueue = false;
	public boolean isInQueue() {
		return inQueue;
	}

	public void setInQueue(boolean value) {
		inQueue = value;
	}

	/**
	 * Returns Customer name by his type and number
	 *
	 * @return Customer type and number in string typedef
	 * */
	public String getFullName() {
		return getType().toString() + " " + getId();
	}

	/**
	 * Processes payment
	 * */
	public boolean pay(double totalAmount, double possibleBonuses, PaymentMethod paymentMethod) {
		switch (paymentMethod) {
			case Cash:
				cash -= totalAmount;
				bonusCount += possibleBonuses;
				return true;
			case Card:
				cardCash -= totalAmount;
				bonusCount += possibleBonuses;
				return true;
			case Bonuses:
				bonusCount -= totalAmount;
				return true;
			default:
				return false;
		}
	}

	public PaymentMethod getDesiredPaymentMethod(double totalPaymentAmount) {
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
