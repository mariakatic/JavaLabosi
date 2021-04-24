package hr.fer.zemris.lsystems.impl.collections;

/**
 * Sucelje predstavlja opcu kolekciju objekata.
 * 
 * @author Maria
 *
 */
public interface Collection<T> {
	
	
	/**
	 * Metoda vraca referencu na novostvoreni objekt tipa ElementsGetter.
	 * @return referencu na novostvoreni objekt tipa ElementsGetter
	 */
	ElementsGetter<T> createElementsGetter();
	
	/**
	 * Provjera je li trenutna kolekcija prazna.
	 * 
	 * @return true ako je kolekcija prazna, inace false
	 */
	default boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * Racuna broj trenutnih elemenata u kolekciji.
	 * 
	 * @return broj trenutnih elemenata u kolekciji
	 */
	int size();
	
	/**
	 * Dodaje zadani objekt u kolekciju.
	 * 
	 * @param value objekt koji se dodaju u kolekciju
	 */
	void add(T value);
	
	/**
	 * Provjera sadrzi li kolekcija zadani objekt.
	 * 
	 * @param value objekt za koji provjeravamo nalazi li se u kolekciji
	 * @return true ako se objekt nalazi u kolekciji, inace false
	 */
	boolean contains(Object value);
	
	/**
	 * Uklanja objekt iz kolekcije ako se zadani objekt nalazi u kolekciji.
	 * 
	 * @param value objekt koji se uklanja iz kolekcije
	 * @return true ako se objekt neposredno prije poziva metode nalazio u kolekciji, inace false
	 */
	boolean remove(Object value);
	
	/**
	 * Alocira novo polje cija je velicina jednaka velicini kolekcije te puni polje elementima kolekcije.
	 *
	 * @return polje koje sadrzi elemente kolekcije
	 */
	Object[] toArray();
	
	/**
	 * Poziva se metoda processor.process(.) za svaki element trenutne kolekcije.
	 * 
	 * @param processor procesor nad kojim se poziva metoda .process(.)
	 */

	default void forEach(Processor<? super T> processor) {
		ElementsGetter<? extends T> el = this.createElementsGetter();
		while (el.hasNextElement()) {
			processor.process((T)el.getNextElement());
		}
	}
	
	/**
	 * Dodaje sve elemente iz kolekcije other u kolekciju this. Other kolekcija ostaje nepromijenjena.
	 * 
	 * @param other kolekcija ciji clanovi se dodaju this kolekciji
	 */
	default void addAll(Collection<? extends T> other) {
		
		class LocalProcessor<V> implements Processor<V> {
			
			@SuppressWarnings("unchecked")
			@Override
			public void process(V value) {
				Collection.this.add((T)value);
			}
		}
		
		LocalProcessor<T> p = new LocalProcessor<T>();
		other.forEach(p);
	}
	
	/**
	 * Uklanja sve elemente iz kolekcije.
	 * 
	 */
	void clear();
	
	/**
	 * Metoda dohvaca elemente iz kolekcije col i dodaje u trenutnu kolekciju sve elemente koje tester prihvaca.
	 * @param col kolekcija iz koje se dohvacaju elementi
	 * @param tester odreduje hoce li pojedini element biti dodan u trenutnu kolekciju
	 */
	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> el = col.createElementsGetter();
		while (el.hasNextElement()) {
			T obj = (T) el.getNextElement();
			if (tester.test(obj))
				this.add(obj);
		}
	}

}
