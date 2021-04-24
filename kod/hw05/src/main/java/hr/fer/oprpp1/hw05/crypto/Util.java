package hr.fer.oprpp1.hw05.crypto;

public class Util {
	
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("Neispravna velicina stringa");
		}
		
		if (!keyText.matches("-?[0-9a-fA-F]+"))
			throw new IllegalArgumentException("Neispravni znakovi unutar stringa.");
		
		int len = keyText.length() / 2;
		byte[] array = new byte[len];
		for (int i = 0; i < 2 * len; i += 2) {
			array[i/2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4)
								+ Character.digit(keyText.charAt(i+1), 16));
		}
		return array;
	}
	
	public static String bytetohex(byte[] array) {
		StringBuilder sb = new StringBuilder();
		for (byte b : array) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
	

}
