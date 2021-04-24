package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {
	
	@Test
	public void testForJMBAG() {
		List<String> list = new LinkedList<>();
		list.add("0000000001	Akšamović	Marin	2");
		list.add("0000000002	Bakamović	Petra	4");
		list.add("0000000003	Bosnić	Andrea	4");
		StudentDatabase sd = new StudentDatabase(list);
		StudentRecord r = sd.forJMBAG("0000000002");
		assertEquals("Petra", r.getFirstName());
	}
	
	@Test
	public void testFilterTrue() {
		List<String> list = new LinkedList<>();
		list.add("0000000001	Akšamović	Marin	2");
		list.add("0000000002	Bakamović	Petra	4");
		list.add("0000000003	Bosnić	Andrea	4");
		StudentDatabase sd = new StudentDatabase(list);
		List<StudentRecord> res = sd.filter(r -> true);
		assertEquals(3, res.size());
	}
	
	@Test
	public void testFilterFalse() {
		List<String> list = new LinkedList<>();
		list.add("0000000001	Akšamović	Marin	2");
		list.add("0000000002	Bakamović	Petra	4");
		list.add("0000000003	Bosnić	Andrea	4");
		StudentDatabase sd = new StudentDatabase(list);
		List<StudentRecord> res = sd.filter(r -> false);
		assertEquals(0, res.size());
		
	}

}
