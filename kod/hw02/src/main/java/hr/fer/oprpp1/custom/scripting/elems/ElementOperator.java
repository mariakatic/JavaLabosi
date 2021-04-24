package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred predstavlja primjerak elementa koji sadrzi vrijednost koja predstavlja neki operator.
 * @author Maria
 *
 */
public class ElementOperator extends Element {

	private String symbol;
	
	/**
	 * Stvara novi primjerak elementa.
	 * @param symbol operator koji element predstavlja
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Vraca operator koju ovaj element predstavlja. 
	 * @return operator
	 */
	public String getSymbol() {
		return this.symbol;
	}
	
	@Override
	public String asText() {
		return symbol;
	}
	
}
