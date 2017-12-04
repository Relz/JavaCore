package ru.relz.javacore2017.model.holder;

public class Holder implements HolderInterface {
	private double value = 0;

	public Holder() {}
	public Holder(double value) {
		this.value = value;
	}

	public void increase(double value) {
		this.value += value;
	}

	public void decrease(double value) {
		this.value -= value;
	}

	public double getValue() {
		return value;
	}
}
