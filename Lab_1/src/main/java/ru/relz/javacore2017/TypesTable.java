package ru.relz.javacore2017;

public class TypesTable {
	public static void main(String [] args) {
		Double minDouble = Double.valueOf(Double.MIN_VALUE);
		Double maxDouble = Double.valueOf(Double.MAX_VALUE);
		Float minFloat = Float.valueOf(Float.MIN_VALUE);
		Float maxFloat = Float.valueOf(Float.MAX_VALUE);
		System.out.printf("%-9s %-20s %-22s %-4s%n", "Type", "Min", "Max", "Size");
		System.out.printf("%-9s %-20d %-22d %-4d%n", "Long", Long.MIN_VALUE, Long.MAX_VALUE, Long.SIZE);
		System.out.printf("%-9s %-20d %-22d %-4d%n", "Integer", Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.SIZE);
		System.out.printf("%-9s %-20d %-22d %-4d%n", "Short", Short.MIN_VALUE, Short.MAX_VALUE, Short.SIZE);
		System.out.printf("%-9s %-20d %-22d %-4d%n", "Byte", Byte.MIN_VALUE, Byte.MAX_VALUE, Byte.SIZE);
		System.out.printf("%-9s %-20s %-22s %-4d%n", "Double", minDouble.toString(), maxDouble.toString(), Double.SIZE);
		System.out.printf("%-9s %-20s %-22s %-4d%n", "Float", minFloat.toString(), maxFloat.toString(), Float.SIZE);
		System.out.printf("%-9s %-20d %-22d %-4d%n", "Character", Character.MIN_VALUE + 0, Character.MAX_VALUE + 0, Character.SIZE);
	}
}
