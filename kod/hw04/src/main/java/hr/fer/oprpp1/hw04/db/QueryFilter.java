package hr.fer.oprpp1.hw04.db;

import java.util.*;

public class QueryFilter implements IFilter {
	
	private List<ConditionalExpression> query;
	
	public QueryFilter(List<ConditionalExpression> query) {
		this.query = new LinkedList<>(query);
	}

	@Override
	public boolean accepts(StudentRecord record) {
		
		for (int i = 0; i < query.size(); i++) {
			ConditionalExpression exp = query.get(i);
			if (!exp.getOperator().satisfied(exp.getField().get(record), exp.getLiteral())) {
				return false;
			}
		}
		return true;
	}

}
