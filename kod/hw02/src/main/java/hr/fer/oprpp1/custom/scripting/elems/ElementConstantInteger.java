package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred predstavlja primjerak elementa koji sadrzi vrijednost koja je tipa int.
 * @author Maria
 *
 */
public class ElementConstantInteger extends Element {

	private int value;
	
	/**
	 * Stvara primjerak elementa s int vrijednoscu.
	 * @param value vrijednost elementa
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Vraca vrijednost elementa.
	 * @return vrijednost elementa
	 */
	public int getValue() {
		return this.value;
	}
	
	@Override
	public String asText() {
		return Integer.valueOf(value).toString();
	}
	
}
