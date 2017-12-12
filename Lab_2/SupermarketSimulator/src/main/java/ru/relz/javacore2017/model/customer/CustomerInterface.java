package ru.relz.javacore2017.model.customer;

import ru.relz.javacore2017.model.Identified;
import ru.relz.javacore2017.model.Named;
import ru.relz.javacore2017.model.bucket.Bucket;

interface CustomerInterface extends Identified, Named, Payer, BonusesHolder, CashHolder, CardCashHolder {
	CustomerType getType();

	Bucket getBucket();

	boolean isInQueue();

	void setInQueue(boolean value);
}
