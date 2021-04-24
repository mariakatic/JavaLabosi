package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class QueryFilterTest {
	
	@Test
	public void testQueryParser() {
		String query = "jmbag =   \"0000000003\"   AND   lastName LIKE    	\"L*\"";
		QueryParser parser = new QueryParser(query);
		List<ConditionalExpression> list = new LinkedList<>();
		list = parser.getQuery();
		QueryFilter filter = new QueryFilter(list);
		boolean b = filter.accepts(new StudentRecord("0000000003", "Lucić", "Pero", 2));
		assertEquals(true, b);
	}


}
