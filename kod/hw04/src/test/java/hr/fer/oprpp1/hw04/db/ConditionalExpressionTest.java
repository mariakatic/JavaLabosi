package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ConditionalExpressionTest {
	
	@Test
	public void testConditionalExpression() {
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME, "Bos*", ComparisonOperators.LIKE);
		StudentRecord record = new StudentRecord("0000000000", "Bosnić", "Andrea", 4);
		boolean recordSatisfies = expr.getOperator().satisfied(
				 expr.getField().get(record),
				 expr.getLiteral());
		assertEquals(true, recordSatisfies);
	}

}
