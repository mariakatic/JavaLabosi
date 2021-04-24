package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred predstavlja element koji predstavlja neku varijablu. 
 * Varijable su rijeci koje pocinju slovom te nakon toga slijedi niz slova, znamenki ili _.
 * @author Maria
 *
 */
public class ElementVariable extends Element {
	
	private String name;
	
	/**
	 * Stvara novi primjerak elementa koji predstavlja varijablu.
	 * @param name ime varijable
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Vraca ime varijable.
	 * @return ime varijable
	 */
	public String getName() {
		return this.name;
	}
	
	@Override
	public String asText() {
		return this.name;
	}


}
