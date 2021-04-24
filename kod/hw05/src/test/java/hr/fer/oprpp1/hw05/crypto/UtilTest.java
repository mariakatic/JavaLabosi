package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UtilTest {
	
	@Test
	public void testHextobyte() {
		byte[] array = {1, -82, 34};
		String s = "01aE22";
		byte[] array2 = Util.hextobyte(s);
		for (int i = 0; i < array.length; i++) {
			assertEquals(array[i], array2[i]);
		}
	}
	
	@Test
	public void testBytetohex() {
		byte[] array = {1, -82, 34};
		String s = "01ae22";
		String s2 = Util.bytetohex(array);
		assertEquals(s, s2);
	}

}
