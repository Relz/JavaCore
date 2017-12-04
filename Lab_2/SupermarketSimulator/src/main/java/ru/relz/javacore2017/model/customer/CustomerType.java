package ru.relz.javacore2017.model.customer;

public enum CustomerType {
	Child("Ребёнок"),
	Adult("Взрослый"),
	Retired("Пенсионер");

	private final String name;

	CustomerType(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public static CustomerType getRandom() {
		return CustomerType.values()[(int) (Math.random() * (CustomerType.values().length))];
	}
}
