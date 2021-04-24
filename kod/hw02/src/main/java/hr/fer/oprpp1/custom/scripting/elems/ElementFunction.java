package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred predstavlja primjerak elementa koji sadrzi vrijednost koja predstavlja neku funkciju.
 * @author Maria
 *
 */
public class ElementFunction extends Element {

	private String name;
	
	/**
	 * Stvara novi primjerak elementa.
	 * @param name ime funkcije koju element predstavlja
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/**
	 * Vraca ime funkcije.
	 * @return ime funkcije
	 */
	public String getName() {
		return this.name;
	}
	
	@Override
	public String asText() {
		return "@" + name;
	}
	
}
