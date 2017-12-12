package ru.relz.javacore2017.model.product;

import ru.relz.javacore2017.model.Identified;
import ru.relz.javacore2017.model.Named;

interface ProductInterface extends Identified, Named, Priceble {
	ProductType getType();

	int getAmount();

	void setAmount(int value);

	double getBonus();

	boolean isForAdult();

	int getRandomProductAmount(int maxProductAmount);
}
