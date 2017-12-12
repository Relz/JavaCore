package ru.relz.javacore2017.model.customer;

import ru.relz.javacore2017.model.basket.Basket;
import ru.relz.javacore2017.model.Identified;
import ru.relz.javacore2017.model.Named;

public interface CustomerInterface extends Identified, Named, Payeer, BonusesHolder, CashHolder, CardCashHolder {
	CustomerType getType();
	Basket getBacket();
	boolean isInQueue();
	void setInQueue(boolean value);
}
