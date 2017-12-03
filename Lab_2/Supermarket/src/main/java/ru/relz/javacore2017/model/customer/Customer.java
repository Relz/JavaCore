package main.java.ru.relz.javacore2017.model.customer;

import main.java.ru.relz.javacore2017.basket.*;
import main.java.ru.relz.javacore2017.model.customer.holder.Holder;
import main.java.ru.relz.javacore2017.payment.*;

import java.util.ArrayList;
import java.util.List;

import static main.java.ru.relz.javacore2017.random_helper.RandomHelper.*;

public class Customer implements CustomerInterface {
	public Customer(CustomerType type, double cash, double cardCash, double bonusCount) {
		this.type = type;
		this.cashHolder.increase(cash);
		this.cardCashHolder.increase(cardCash);
		this.bonusHolder.increase(bonusCount);
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

	private Holder cashHolder = new Holder();
	public double getCash() {
		return cashHolder.getValue();
	}

	private Holder cardCashHolder = new Holder();
	public double getCardCash() {
		return cardCashHolder.getValue();
	}

	private Holder bonusHolder = new Holder();
	public double getBonusCount() {
		return bonusHolder.getValue();
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
	 * Returns customer name by his type and number
	 *
	 * @return customer type and number in string typedef
	 * */
	public String getName() {
		return getType().toString() + " " + getId();
	}

	/**
	 * Processes payment
	 * */
	public boolean pay(Bill bill) {
		double totalAmount = bill.calculateTotalAmount();
		double totalBonuses = bill.calculateTotalBonuses();
		PaymentMethod paymentMethod = getDesiredPaymentMethod(totalAmount);
		switch (paymentMethod) {
			case Cash:
				cashHolder.decrease(totalAmount);
				bonusHolder.increase(totalBonuses);
				return true;
			case Card:
				cardCashHolder.decrease(totalAmount);
				bonusHolder.increase(totalBonuses);
				return true;
			case Bonuses:
				bonusHolder.decrease(totalAmount);
				return true;
			default:
				return false;
		}
	}

	public PaymentMethod getDesiredPaymentMethod(double totalPaymentAmount) {
		List<PaymentMethod> availablePaymentMethods = new ArrayList<>();
		if (cashHolder.getValue() > totalPaymentAmount) {
			availablePaymentMethods.add(PaymentMethod.Cash);
		}
		if (cardCashHolder.getValue() > totalPaymentAmount) {
			availablePaymentMethods.add(PaymentMethod.Card);
		}
		if (bonusHolder.getValue() > totalPaymentAmount) {
			availablePaymentMethods.add(PaymentMethod.Bonuses);
		}
		if (availablePaymentMethods.isEmpty()) {
			return null;
		}

		return getRandomPaymentMethod(availablePaymentMethods);
	}

	private static PaymentMethod getRandomPaymentMethod(List<PaymentMethod> paymentMethods) {
		return paymentMethods.get(getRandomNumber(0, paymentMethods.size() - 1));
	}
}
