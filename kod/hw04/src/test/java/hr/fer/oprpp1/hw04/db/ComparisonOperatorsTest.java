package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {
	
	@Test
	public void testCompareLess() {
		IComparisonOperator op = ComparisonOperators.LESS;
		boolean b = op.satisfied("a", "abc");
		assertEquals(true, b);
	}
	
	@Test
	public void testCompareLessOrEquals() {
		IComparisonOperator op = ComparisonOperators.LESS_OR_EQUALS;
		boolean b = op.satisfied("a", "abc");
		assertEquals(true, b);
	}
	
	@Test
	public void testCompareGreater() {
		IComparisonOperator op = ComparisonOperators.GREATER;
		boolean b = op.satisfied("a", "abc");
		assertEquals(false, b);
	}
	
	@Test
	public void testCompareGreaterOrEquals() {
		IComparisonOperator op = ComparisonOperators.GREATER_OR_EQUALS;
		boolean b = op.satisfied("abc", "abc");
		assertEquals(true, b);
	}
	
	@Test
	public void testCompareEquals() {
		IComparisonOperator op = ComparisonOperators.EQUALS;
		boolean b = op.satisfied("abc", "abc");
		assertEquals(true, b);
	}
	
	@Test
	public void testCompareNotEquals() {
		IComparisonOperator op = ComparisonOperators.NOT_EQUALS;
		boolean b = op.satisfied("abc", "abc");
		assertEquals(false, b);
	}
	
	@Test
	public void testCompareLike() {
		IComparisonOperator op = ComparisonOperators.LIKE;
		boolean b = op.satisfied("AAA", "AA*AA");
		assertEquals(false, b);
	}

}
