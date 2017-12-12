package ru.relz.javacore2017.random_helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomHelperTest {
	@Test
	void getRandomNumberReturnsFromIfFromIsEqualsToTo() {
		int from = 3;
		int to = 3;
		assertEquals(from, RandomHelper.getRandomNumber(from, to));
	}
}
