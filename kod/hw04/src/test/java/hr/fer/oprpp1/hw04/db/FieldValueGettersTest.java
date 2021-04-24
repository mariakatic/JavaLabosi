package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FieldValueGettersTest {
	
	@Test
	public void testFieldValueGetterLastName() {
		IFieldValueGetter field = FieldValueGetters.LAST_NAME;
		StudentRecord r = new StudentRecord("0000000002", "Ivic", "Ivica", 3);
		assertEquals("Ivic", field.get(r));
	}
	
	@Test
	public void testFieldValueGetterFirstName() {
		IFieldValueGetter field = FieldValueGetters.FIRST_NAME;
		StudentRecord r = new StudentRecord("0000000002", "Ivic", "Ivica", 3);
		assertEquals("Ivica", field.get(r));
	}
	
	@Test
	public void testFieldValueGetterJMBAG() {
		IFieldValueGetter field = FieldValueGetters.JMBAG;
		StudentRecord r = new StudentRecord("0000000002", "Ivic", "Ivica", 3);
		assertEquals("0000000002", field.get(r));
	}
	
	
	

}
