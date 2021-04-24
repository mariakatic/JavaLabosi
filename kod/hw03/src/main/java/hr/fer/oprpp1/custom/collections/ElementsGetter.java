package hr.fer.oprpp1.custom.collections;

/**
 * Sucelje cija je zadaca korisniku vracati element po element, na korisnikov zahtjev.
 * @author Maria
 *
 */
public interface ElementsGetter<T> {
	
	/**
	 * Metoda provjera postoji li sljedeci element u kolekciji.
	 * @return true ako postoji sljedeci element u kolekciji, inace false
	 */
	boolean hasNextElement();
	
	/**
	 * Metoda dohvaca sljedeci element u kolekciji.
	 * @return sljedeci element u kolekciji
	 */
	T getNextElement();
	
	/**
	 * Obraduje preostale elemente kolekcije.
	 * @param p obraduje preostale elemente kolekcije
	 */
	default void processRemaining(Processor<? super T> p) {
		
		while (this.hasNextElement()) {
			p.process(this.getNextElement());
		}
		
	}
	
}
