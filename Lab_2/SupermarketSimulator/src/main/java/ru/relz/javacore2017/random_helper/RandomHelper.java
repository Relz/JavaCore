package ru.relz.javacore2017.random_helper;

public final class RandomHelper {
	public static int getRandomNumber(int from, int to) {
		return (int) (Math.round(Math.random() * (to - from)) + from);
	}
}
