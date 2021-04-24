package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred predstavlja primjerak elementa koji sadrzi vrijednost tipa string.
 * @author Maria
 *
 */
public class ElementString extends Element {
	
	private String value;
	
	/**
	 * Stvara novi element s vrijednoscu tipa string.
	 * @param value vrijednost elementa
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * Vraca vrijednost elementa.
	 * @return vrijendost elementa
	 */
	public String getValue() {
		return this.value;
	}
	
	@Override
	public String asText() {
		return value;
	}

}
