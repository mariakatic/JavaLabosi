package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
	
	@Test 
	public void testCreateEmptyArrayIndexedCollection() {
		ArrayIndexedCollection result = new ArrayIndexedCollection();
		Object[] expected = new Object[16];
		assertArrayEquals(expected, result.toArray());
	}
	
	@Test 
	public void testArrayIndexedCollectionSizeLessThenOneShouldThrow() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-2));
	}
	
	@Test
	public void testNewArrayIndexedCollection() {
		ArrayIndexedCollection result = new ArrayIndexedCollection(5);
		assertEquals(5, result.toArray().length);
	}
	
	@Test
	public void testNewArrayIndexedCollectionUsingCollection() {
		Collection col = new ArrayIndexedCollection(3);
		col.add("1");
		col.add("2");
		col.add("3");
		ArrayIndexedCollection result = new ArrayIndexedCollection(col);
		assertArrayEquals(result.toArray(), col.toArray());
	}
	
	@Test
	public void testNewArrayEmptyCollection() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	}
	
	@Test
	public void testNewArrayEmptyCollectionWithCapacity() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null, 10));
	}
	
	@Test
	public void testAddValueNull() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> col.add(null));
	}
	
	@Test
	public void testDoublingCapacityOfArray() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add("1");
		col.add("2");
		col.add("3");
		assertEquals(col.toArray().length, 4);
	}
	
	@Test
	public void testAdd() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add("1");
		col.add("2");
		col.add("3");
		Object[] expected = {"1", "2", "3"};
		assertArrayEquals(expected, col.toArray());
	}
	
	@Test
	public void testGet() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("1");
		col.add("2");
		col.add("3");
		Object obj = col.get(1);
		assertEquals(obj.toString(), "2");
	}
	
	@Test
	public void testGetIndexTooHigh() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("1");
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(1));
	}
	
	@Test
	public void testGetIndexTooLow() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("1");
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(-1));
	}
	
	@Test
	public void testClear() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("1");
		col.clear();
		assertEquals(col.size(), 0);
	}
	
	@Test
	public void testInsertNullObject() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> col.insert(null, 0));
	}
	
	@Test
	public void testInsertInvalidPositionTooHigh() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("1");
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert("3", 2));
	}
	
	@Test
	public void testInsertInvalidPositionTooLow() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert("3", -1));
	}
	
	@Test
	public void testInsert() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(4);
		col.add("1");
		col.add("1");
		col.add("1");
		col.insert("2", 1);
		Object[] expected = {"1", "2", "1", "1"};
		assertArrayEquals(expected, col.toArray());
	}
	
	@Test
	public void testRemoveIndex() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add("1");
		col.add("2");
		col.add("3");
		col.remove(1);
		Object[] expected = {"1", "3", null};
		assertArrayEquals(expected, col.toArray());
	}
	
	@Test
	public void testRemoveIndexTooHigh() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("1");
		assertThrows(IndexOutOfBoundsException.class, () -> col.remove(1));
	}
	
	@Test
	public void testRemoveIndexTooLow() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("1");
		assertThrows(IndexOutOfBoundsException.class, () -> col.remove(-1));
	}
	
	@Test 
	public void testSize() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("1");
		col.add("2");
		col.add("3");
		assertEquals(col.size(), 3);
	}
	
	@Test
	public void testContains() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("1");
		col.add("2");
		col.add("3");
		boolean b = true;
		assertEquals(col.contains("2"), b);
	}
	
	@Test
	public void testContainsNull() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("1");
		col.add("2");
		col.add("3");
		boolean b = false;
		assertEquals(col.contains(null), b);
	}
	
	@Test
	public void testToArray() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add("1");
		col.add("2");
		col.add("3");
		Object[] expected = {"1", "2", "3"};
		assertArrayEquals(expected, col.toArray());
	}
	
	@Test 
	public void testRemoveValue() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add("1");
		col.add("2");
		col.add("3");
		col.remove("2");
		Object[] expected = {"1", "3", null};
		assertArrayEquals(expected, col.toArray());
	}
	
	
	
	
	
}
