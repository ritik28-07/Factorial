package uk.org.winder.maths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.function.LongFunction;
import java.math.BigInteger;

@RunWith(Parameterized.class)
public final class Test_Factorial_JUnit_Java_Negative {

	private static final Object[][] algorithms = new Object[][]{
			{(LongFunction<BigInteger>) Factorial::iterative, "iterative"},
			{(LongFunction<BigInteger>) Factorial::reductive, "reductive"},
			{(LongFunction<BigInteger>) Factorial::reductive, "reductive"},
			{(LongFunction<BigInteger>) Factorial::naïveRecursive, "naïveRecursive"},
			{(LongFunction<BigInteger>) Factorial::tailRecursive, "tailRecursive"}
	};

	private static final Object[] values = {-1, -2, -5, -10, -20, -100};

	@Parameters(name = "{1}({2})")
	public static Iterable<Object[]> data() {
		final var data = new ArrayList<Object[]>();
		for (var a: algorithms) {
			if (a.length != 2) {
				throw new RuntimeException("algorithms array borked.");
			}
			for (var v: values) {
				data.add(new Object[]{a[0], a[1], v});
			}
		}
		return data;
	}

	private final LongFunction<BigInteger> a;
	private final String name;
	private final Integer n;

	public Test_Factorial_JUnit_Java_Negative(final LongFunction<BigInteger> a, final String name, final Integer n) {
		this.a = a;
		this.name = name;
		this.n = n;
	}

	@Test(expected = RuntimeException.class)
	public void test() {
		a.apply(n);
	}

}
