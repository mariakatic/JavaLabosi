package hr.fer.oprpp1.hw04.db;

import java.util.*;
import hr.fer.oprpp1.hw04.db.lexer.*;

public class QueryParser {
	
	private List<ConditionalExpression> query;
	private Lexer lexer;
	
	public QueryParser(String body) {
		query = new LinkedList<>();
		lexer = new Lexer(body);
		parse();
	}
	
	public void parse() {
		
		try {
			IFieldValueGetter field = null;
			String literal = null;
			IComparisonOperator operator = null;
			
			while(true) {
				
				Token token = lexer.nextToken();
				
				if (lexer.getToken().getType() == TokenType.EOF) {
					if (field != null && operator != null && literal != null) {
						long count = literal.chars().filter(c -> c == '*').count();
						if (count > 0) {
							if (operator.equals(ComparisonOperators.LIKE)) {
								if (count > 1) 
									throw new QueryParserException("Ne smije se pojaviti vise od jednog znaka *.");
							} else {
								throw new QueryParserException("Ne smije se pojaviti niti jedan znak *.");
							}
						}
						ConditionalExpression exp = new ConditionalExpression(field, literal, operator);
						query.add(exp);
						break;
					} else {
						throw new QueryParserException("Nedostaje dio upita.");
					}
				}
				
				
				if (token.getType().equals(TokenType.FIELD)) {
					if (token.getValue().equals("jmbag")) {
						field = FieldValueGetters.JMBAG;
					} else if (token.getValue().equals("firstName")) {
						field = FieldValueGetters.FIRST_NAME;
					} else if (token.getValue().equals("lastName")) {
						field = FieldValueGetters.LAST_NAME;
					} else {
						throw new QueryParserException("Neispravno zadan upit.");
					}
				}
				
				if (token.getType().equals(TokenType.OPERATOR)) {
					if (token.getValue().equals("<")) {
						operator = ComparisonOperators.LESS;
					} else if (token.getValue().equals("<=")) {
						operator = ComparisonOperators.LESS_OR_EQUALS;
					} else if (token.getValue().equals(">")) {
						operator = ComparisonOperators.GREATER;
					} else if (token.getValue().equals(">=")) {
						operator = ComparisonOperators.GREATER_OR_EQUALS;
					} else if (token.getValue().equals("=")) {
						operator = ComparisonOperators.EQUALS;
					} else if (token.getValue().equals("!=")) {
						operator = ComparisonOperators.NOT_EQUALS;
					} else if (token.getValue().equals("LIKE")) {
						operator = ComparisonOperators.LIKE;
					} else {
						throw new QueryParserException("Neispravno zadan upit.");
					}
				}
				
				if (token.getType().equals(TokenType.STRING)) {
					literal = (String) token.getValue();
				}
				
				if (token.getType().equals(TokenType.AND)) {
					if (field != null && operator != null && literal != null) {
						long count = literal.chars().filter(c -> c == '*').count();
						if (count > 0) {
							if (operator.equals(ComparisonOperators.LIKE)) {
								if (count > 1) 
									throw new QueryParserException("Ne smije se pojaviti vise od jednog znaka *.");
							} else {
								throw new QueryParserException("Ne smije se pojaviti niti jedan znak *.");
							}
						}
						ConditionalExpression exp = new ConditionalExpression(field, literal, operator);
						query.add(exp);
						field = null;
						operator = null;
						literal = null;
						continue;
					} else {
						throw new QueryParserException("Nedostaje dio upita.");
					}
				}	
			}
			
		} catch (LexerException | QueryParserException ex) {
			System.out.println(ex.getMessage());
			System.exit(1);
		}
	}
	
	public boolean isDirectQuery() {
		if (query.size() == 1) {
			return (query.get(0).getField().equals(FieldValueGetters.JMBAG) && 
					query.get(0).getOperator().equals(ComparisonOperators.EQUALS));
		}
		return false;
	}
	
	public String getQueriedJMBAG() {
		if (this.isDirectQuery())
			return query.get(0).getLiteral();
		else 
			throw new IllegalStateException("Query nije direktan.");
	}
	
	public List<ConditionalExpression> getQuery() {
		return query;
	}

}
