package ru.relz.javacore2017;

public class TypesTable {
    public static void main(String [] args) {
        Double minDouble = -Double.MAX_VALUE;
        Double maxDouble = Double.MAX_VALUE;
        Float minFloat = -Float.MAX_VALUE;
        Float maxFloat = Float.MAX_VALUE;
        System.out.printf("%-9s %-20s %-22s %-4s%n", "Type", "Min", "Max", "Size");
        System.out.printf("%-9s %-20d %-22d %-4d%n", "Long", Long.MIN_VALUE, Long.MAX_VALUE, Long.SIZE);
        System.out.printf("%-9s %-20d %-22d %-4d%n", "Integer", Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.SIZE);
        System.out.printf("%-9s %-20d %-22d %-4d%n", "Short", Short.MIN_VALUE, Short.MAX_VALUE, Short.SIZE);
        System.out.printf("%-9s %-20d %-22d %-4d%n", "Byte", Byte.MIN_VALUE, Byte.MAX_VALUE, Byte.SIZE);
        System.out.printf("%-9s %-20s %-22s %-4d%n", "Double", minDouble.toString(), maxDouble.toString(), Double.SIZE);
        System.out.printf("%-9s %-20s %-22s %-4d%n", "Float", minFloat.toString(), maxFloat.toString(), Float.SIZE);
        System.out.printf("%-9s %-20d %-22d %-4d%n", "Character", (int)Character.MIN_VALUE, (int)Character.MAX_VALUE, Character.SIZE);
    }
}