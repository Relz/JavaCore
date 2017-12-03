package main.java.ru.relz.javacore2017.model.customer;

import main.java.ru.relz.javacore2017.basket.Basket;

public interface CustomerInterface extends Human, CashHolder, CardCashHolder, BonusesHolder, Payeer {
	CustomerType getType();
	Basket getBacket();
	boolean isInQueue();
	void setInQueue(boolean value);
}
