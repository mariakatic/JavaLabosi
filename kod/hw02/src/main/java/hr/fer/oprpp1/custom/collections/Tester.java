package hr.fer.oprpp1.custom.collections;

/**
 * Sucelje modelira objekte koji ispitivaju je li primljeni objekt prihvatljiv ili nije.
 * @author Maria
 *
 */
public interface Tester {
	
	/**
	 * Metoda ispituje je li dani objekt prihvatljiv.
	 * @param obj objekt za koji se ispituje je li prihvatljiv
	 * @return true ako je objekt prihvatljiv, inace false
	 */
	boolean test(Object obj);

}
