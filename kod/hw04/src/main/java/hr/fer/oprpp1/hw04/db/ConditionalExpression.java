package hr.fer.oprpp1.hw04.db;

public class ConditionalExpression {
	
	private IFieldValueGetter field;
	private String literal;
	private IComparisonOperator operator;
	
	public ConditionalExpression(IFieldValueGetter field, String literal, IComparisonOperator operator) {
		super();
		this.field = field;
		this.literal = literal;
		this.operator = operator;
	}

	public IFieldValueGetter getField() {
		return field;
	}

	public String getLiteral() {
		return literal;
	}

	public IComparisonOperator getOperator() {
		return operator;
	}
	
	

}
