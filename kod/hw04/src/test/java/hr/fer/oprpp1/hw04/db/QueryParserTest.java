package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class QueryParserTest {
	
	@Test
	public void testQueryParser() {
		String query = "jmbag =   \"0000000003\"   AND   lastName LIKE    	\"L*\"";
		QueryParser parser = new QueryParser(query);
		List<ConditionalExpression> list = new LinkedList<>();
		list = parser.getQuery();
		assertEquals(list.get(1).getLiteral(), "L*");
	}

}
