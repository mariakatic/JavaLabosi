package hr.fer.oprpp1.custom.collections;

/**
 * Razred predstavlja opcu kolekciju objekata.
 * 
 * @author Maria
 *
 */
public class Collection {
	
	/**
	 * Stvara novu opcenitu koleciju.
	 */
	protected Collection() {
		super();
	}
	
	/**
	 * Provjera je li trenutna kolekcija prazna.
	 * 
	 * @return true ako je kolekcija prazna, inace false
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * Racuna broj trenutnih elemenata u kolekciji.
	 * Napomena: Metoda nema funkcionalnosti za objekte iz razreda Collection.
	 * 
	 * @return broj trenutnih elemenata u kolekciji
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Dodaje zadani objekt u kolekciju.
	 * Napomena: Metoda nema funkcionalnosti za objekte iz razreda Collection.
	 * 
	 * @param value objekt koji se dodaju u kolekciju
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Provjera sadrzi li kolekcija zadani objekt.
	 * Napomena: Metoda nema funkcionalnosti za objekte iz razreda Collection.
	 * 
	 * @param value objekt za koji provjeravamo nalazi li se u kolekciji
	 * @return true ako se objekt nalazi u kolekciji, inace false
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Uklanja objekt iz kolekcije ako se zadani objekt nalazi u kolekciji.
	 * Napomena: Metoda nema funkcionalnosti za objekte iz razreda Collection.
	 * 
	 * @param value objekt koji se uklanja iz kolekcije
	 * @return true ako se objekt neposredno prije poziva metode nalazio u kolekciji, inace false
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Alocira novo polje cija je velicina jednaka velicini kolekcije te puni polje elementima kolekcije.
	 * Napomena: Metoda nema funkcionalnosti za objekte iz razreda Collection.
	 *
	 * @return polje koje sadrzi elemente kolekcije
	 * @throws UnsupportedOperationException ako je zadana kolekcija prazna
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Poziva se metoda processor.process(.) za svaki element trenutne kolekcije.
	 * Napomena: Metoda nema funkcionalnosti za objekte iz razreda Collection.
	 * 
	 * @param processor procesor nad kojim se poziva metoda .process(.)
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Dodaje sve elemente iz kolekcije other u kolekciju this. Other kolekcija ostaje nepromijenjena.
	 * 
	 * @param other kolekcija ciji clanovi se dodaju this kolekciji
	 */
	public void addAll(Collection other) {
		
		class LocalProcessor extends Processor {
			
			@Override
			public void process(Object value) {
				Collection.this.add(value);
			}
		}
		
		LocalProcessor p = new LocalProcessor();
		other.forEach(p);
	}
	
	/**
	 * Uklanja sve elemente iz kolekcije.
	 * Napomena: Metoda nema funkcionalnosti za objekte iz razreda Collection.
	 * 
	 */
	public void clear() {
		
	}

}
