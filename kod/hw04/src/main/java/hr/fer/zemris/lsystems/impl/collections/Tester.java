package hr.fer.zemris.lsystems.impl.collections;

/**
 * Sucelje modelira objekte koji ispitivaju je li primljeni objekt prihvatljiv ili nije.
 * @author Maria
 *
 */
public interface Tester<T> {
	
	/**
	 * Metoda ispituje je li dani objekt prihvatljiv.
	 * @param obj objekt za koji se ispituje je li prihvatljiv
	 * @return true ako je objekt prihvatljiv, inace false
	 */
	boolean test(T obj);

}
