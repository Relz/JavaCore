package main.java.ru.relz.javacore2017;

enum CustomerType {
	Child,
	Adult,
	Retired;

	@Override
	public String toString() {
		switch (this) {
			case Child:
				return "Ребёнок";
			case Adult:
				return "Взрослый";
			case Retired:
				return "Пенсионер";
			default:
				return "Неизвестный";
		}
	}

	static CustomerType getRandom() {
		return CustomerType.values()[(int) (Math.random() * (CustomerType.values().length - 1))];
	}
}
