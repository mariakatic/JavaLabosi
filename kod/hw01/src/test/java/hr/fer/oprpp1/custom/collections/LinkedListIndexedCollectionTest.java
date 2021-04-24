package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListIndexedCollectionTest {
	
	@Test
	public void testNewLinkedListIndexedCollection() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertEquals(0, col.size());
	}
	
	@Test
	public void testNewLinkedListIndexedCollectionUsingCollection() {
		Collection col = new ArrayIndexedCollection(3);
		col.add("1");
		col.add("2");
		col.add("3");
		LinkedListIndexedCollection list = new LinkedListIndexedCollection(col);
		Object[] expected = {"1", "2", "3"};
		assertArrayEquals(expected, list.toArray());
	}
	
	@Test
	public void testAddObjectToList() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("1");
		col.add("2");
		col.add("3");
		Object[] expected = {"1", "2", "3"};
		assertArrayEquals(expected, col.toArray());
	}
	
	@Test
	public void testAddNullObject() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> col.add(null));
	}
	
	@Test
	public void testGetObjectByIndex() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("1");
		col.add("2");
		col.add("3");
		Object obj = col.get(1);
		assertEquals(obj.toString(), "2");
	}
	
	@Test
	public void testGetIndexTooHigh() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("1");
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(1));
	}
	
	@Test
	public void testGetIndexTooLow() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("1");
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(-1));
	}
	
	@Test
	public void testClear() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("1");
		col.clear();
		assertEquals(col.size(), 0);
	}
	
	@Test 
	public void testInsert() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("1");
		col.add("1");
		col.add("1");
		col.insert("2", 1);
		Object[] expected = {"1", "2", "1", "1"};
		assertArrayEquals(expected, col.toArray());
	}
	
	@Test
	public void testInsertNullObject() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> col.insert(null, 0));
	}
	
	@Test
	public void testInsertInvalidPositionTooHigh() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("1");
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert("3", 2));
	}
	
	@Test
	public void testInsertInvalidPositionTooLow() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert("3", -1));
	}
	
	@Test
	public void testRemoveIndex() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("1");
		col.add("2");
		col.add("3");
		col.remove(1);
		Object[] expected = {"1", "3"};
		assertArrayEquals(expected, col.toArray());
	}
	
	@Test
	public void testRemoveIndexTooHigh() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("1");
		assertThrows(IndexOutOfBoundsException.class, () -> col.remove(1));
	}
	
	@Test
	public void testRemoveIndexTooLow() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("1");
		assertThrows(IndexOutOfBoundsException.class, () -> col.remove(-1));
	}
	
	@Test 
	public void testSize() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("1");
		col.add("2");
		col.add("3");
		assertEquals(col.size(), 3);
	}
	
	@Test
	public void testContains() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("1");
		col.add("2");
		col.add("3");
		boolean b = true;
		assertEquals(col.contains("2"), b);
	}
	
	@Test
	public void testContainsNull() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("1");
		col.add("2");
		col.add("3");
		boolean b = false;
		assertEquals(col.contains(null), b);
	}
	
	@Test
	public void testToArray() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("1");
		col.add("2");
		col.add("3");
		Object[] expected = {"1", "2", "3"};
		assertArrayEquals(expected, col.toArray());
	}
	
	@Test 
	public void testRemoveValue() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("1");
		col.add("2");
		col.add("3");
		col.remove("2");
		Object[] expected = {"1", "3"};
		assertArrayEquals(expected, col.toArray());
	}
	

}
