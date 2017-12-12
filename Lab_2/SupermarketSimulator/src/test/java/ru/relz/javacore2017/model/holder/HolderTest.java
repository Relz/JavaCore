package ru.relz.javacore2017.model.holder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HolderTest {
	private Holder holder;

	@BeforeEach
	void setUp() {
		holder = new Holder(100);
	}

	@Test
	void increase() {
		holder.increase(10);
		assertEquals(110, holder.getValue());
		holder.increase(10);
		assertEquals(120, holder.getValue());
	}

	@Test
	void decrease() {
		holder.decrease(10);
		assertEquals(90, holder.getValue());
		holder.decrease(10);
		assertEquals(80, holder.getValue());
	}
}
