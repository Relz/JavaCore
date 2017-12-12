package ru.relz.javacore2017.model.customer;

import ru.relz.javacore2017.model.basket.Basket;
import ru.relz.javacore2017.model.holder.Holder;
import ru.relz.javacore2017.payment.Bill;
import ru.relz.javacore2017.payment.PaymentMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.relz.javacore2017.random_helper.RandomHelper.getRandomNumber;

public class Customer implements CustomerInterface {
	private final HashMap<PaymentMethod, Holder> paymentMethodToHolder = new HashMap<>();

	public Customer(CustomerType type, double cash, double cardCash, double bonusCount) {
		this.type = type;

		bonusHolder = new Holder(bonusCount);
		cashHolder = new Holder(cash);
		cardCashHolder = new Holder(cardCash);
		paymentMethodToHolder.put(PaymentMethod.Cash, cashHolder);
		paymentMethodToHolder.put(PaymentMethod.Card, cardCashHolder);
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

	private Holder bonusHolder;
	public double getBonusCount() {
		return bonusHolder.getValue();
	}

	private Holder cashHolder;
	public double getCash() {
		return cashHolder.getValue();
	}

	private Holder cardCashHolder;
	public double getCardCash() {
		return cardCashHolder.getValue();
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
		if (getBonusCount() >= totalAmount) {
			bonusHolder.decrease(totalAmount);
		} else {
			PaymentMethod paymentMethod = getDesiredPaymentMethod(totalAmount);
			if (paymentMethod == null) {
				return false;
			}
			paymentMethodToHolder.get(paymentMethod).decrease(totalAmount);
			bonusHolder.increase(totalBonuses);
		}
		return true;
	}

	public PaymentMethod getDesiredPaymentMethod(double totalPaymentAmount) {
		if (bonusHolder.getValue() >= totalPaymentAmount) {
			return PaymentMethod.Bonuses;
		}
		List<PaymentMethod> possiblePaymentMethods = new ArrayList<>();
		for (Map.Entry<PaymentMethod, Holder> paymentMethodHolderEntry : paymentMethodToHolder.entrySet()) {
			PaymentMethod paymentMethod = paymentMethodHolderEntry.getKey();
			Holder holder = paymentMethodHolderEntry.getValue();

			if (holder.getValue() >= totalPaymentAmount) {
				possiblePaymentMethods.add(paymentMethod);
			}
		}
		if (possiblePaymentMethods.isEmpty()) {
			return null;
		}

		return getRandomPaymentMethod(possiblePaymentMethods);
	}

	private static PaymentMethod getRandomPaymentMethod(List<PaymentMethod> paymentMethods) {
		return paymentMethods.isEmpty() ? null : paymentMethods.get(getRandomNumber(0, paymentMethods.size() - 1));
	}
}