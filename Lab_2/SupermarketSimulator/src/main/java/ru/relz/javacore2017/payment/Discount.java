package ru.relz.javacore2017.payment;

public class Discount {
	public Discount(int percentage) {
		this.percentage = percentage;
	}

	private int percentage = 0;
	public int getPercentage() {
		return percentage;
	}
}
