package main.java.ru.relz.javacore2017.model.product;

import main.java.ru.relz.javacore2017.model.Identified;
import main.java.ru.relz.javacore2017.model.Named;

interface ProductInterface extends Identified, Named, Priceble {
	ProductType getType();
	int getAmount();
	double getBonus();
	boolean isForAdult();
}
