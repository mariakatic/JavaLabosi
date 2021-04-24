package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred predstavlja primjerak elementa koji sadrzi vrijednost koja je tipa double.
 * @author Maria
 *
 */
public class ElementConstantDouble extends Element {

	private double value;
	
	/**
	 * Stvara primjerak elementa s double vrijednoscu.
	 * @param value vrijednost elementa
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Vraca vrijednost elementa.
	 * @return vrijednost elementa
	 */
	public double getValue() {
		return this.value;
	}
	
	@Override
	public String asText() {
		return Double.valueOf(value).toString();
	}
	
}
