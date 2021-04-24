package hr.fer.oprpp1.custom.collections;

/**
 * Razred koji pod zadanim kljucem pamti zadanu vrijednost.
 * @author Maria
 *
 * @param <K> tip kljuca
 * @param <V> tip vrijednosti
 */
public class Dictionary<K, V> {
	
	/**
	 * Privatna klasa koja modelira par koji se sastoji od kljuca i vrijednosti.
	 * @author Maria
	 *
	 * @param <F> tip kljuca
	 * @param <S> tip vrijednosti
	 */
	private class Pair<F, S> {

	    private final F key;
	    private final S value;

	    public Pair(F key, S value) {
	    	if (key == null) throw new IllegalArgumentException("Kljuc ne smije biti null.");
	        this.key = key;
	        this.value = value;
	    }

	    public F getKey() {
	        return key;
	    }

	    public S getValue() {
	        return value;
	    }


	}
	
	/**
	 * Kolekcija u koju se spremaju parovi razreda Dictionary.
	 */
	ArrayIndexedCollection<Pair<K, V>> col;
	
	/**
	 * Stvara novu mapu.
	 */
	public Dictionary() {
		col = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Provjera je li trenutna mapa prazna.
	 * @return
	 */
	public boolean isEmpty() {
		return col.isEmpty();
	}
	
	/**
	 * Vraca velicinu trenutne mape.
	 * @return velicinu mape
	 */
	public int size() {
		return col.size();
	}
	
	/**
	 * Brise sve parove mape.
	 */
	public void clear() {
		col.clear();
	}
	
	/**
	 * Dodaje novi par u mapu. Ako u mapi vec postoji par koji ima isti kljuc, onda se vrijednost novog para pise preko vrijednost starog para.
	 * @param key kljuc novog para
	 * @param value vrijednost novog para
	 * @return vrijednost starog para ako se preko njega upisao novi par, inace null
	 */
	public V put(K key, V value) {
		ElementsGetter<Pair<K, V>> getter = (ElementsGetter<Dictionary<K, V>.Pair<K, V>>) col.createElementsGetter();
		Pair<K, V> newPair = new Pair<>(key, value);
		int idx = 0;
		while (getter.hasNextElement()) {
			Pair<K, V> oldPair = getter.getNextElement();
			if (oldPair.getKey().equals(newPair.getKey())) {
				col.insert(newPair, idx);
				return oldPair.getValue();
			}
			idx++;
		}
		col.add(newPair);
		return null;
	}
	
	/**
	 * Dohvaca par sa zadanim kljucem.
	 * @param key kljuc pomocu kojeg se dohvaca par iz mape
	 * @return vrijednost para koji je dohvacen
	 */
	public V get(Object key) {
		ElementsGetter<Pair<K, V>> getter = (ElementsGetter<Dictionary<K, V>.Pair<K, V>>) col.createElementsGetter();
		while (getter.hasNextElement()) {
			Pair<K, V> pair = getter.getNextElement();
			if (pair.getKey().equals(key)) {
				return pair.getValue();
			}
		}
		return null;
	}
	
	/**
	 * Uklanja par sa zadanim kljucem.
	 * @param key kljuc para koji se uklanja iz mape
	 * @return vrijendost uklonjenog para
	 */
	public V remove(K key) {
		ElementsGetter<Pair<K, V>> getter = (ElementsGetter<Dictionary<K, V>.Pair<K, V>>) col.createElementsGetter();
		while (getter.hasNextElement()) {
			Pair<K, V> pair = getter.getNextElement();
			if (pair.getKey().equals(key)) {
				col.remove(pair);
				return pair.getValue();
			}
		}
		return null;
	}

}
