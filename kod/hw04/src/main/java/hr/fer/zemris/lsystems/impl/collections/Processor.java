package hr.fer.zemris.lsystems.impl.collections;

/**
 * Sucelje predstavlja model objekta sposobnog za izvodenje neke operacije na proslijedenom objektu.
 * 
 * @author Maria
 */

public interface Processor<T> {
		
	/**
	 * Metoda izvodi neku operaciju nas predanim objektom.
	 * 
	 * @param value objekt nad kojim se izvodi operacija
	 */
	void process(T value);
	
}
