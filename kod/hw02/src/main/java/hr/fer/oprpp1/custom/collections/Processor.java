package hr.fer.oprpp1.custom.collections;

/**
 * Sucelje predstavlja model objekta sposobnog za izvodenje neke operacije na proslijedenom objektu.
 * 
 * @author Maria
 */

public interface Processor {
		
	/**
	 * Metoda izvodi neku operaciju nas predanim objektom.
	 * 
	 * @param value objekt nad kojim se izvodi operacija
	 */
	void process(Object value);
	
}
