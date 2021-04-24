package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Razred predstavlja tablicu rasprsenog adresiranja koja omogucava pohranu uredenih parova (kljuc, vrijednost).
 * @author Maria
 *
 * @param <K> tip kljuca
 * @param <V> tip vrijednosti
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>>{
	
	/**
	 * Razred modelira jedan slot u tablici.
	 * @author Maria
	 *
	 * @param <K> tip kljuca
	 * @param <V> tip vrijednosti
	 */
	public static class TableEntry<K, V> {
		
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		/**
		 * Predstavlja jedan slot u tablici
		 * @param key kljuc
		 * @param value vrijednost
		 * @param next iduci element
		 * @throws IllegalArgumentException ako je kljuc <code>null</code>
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if (key == null)
				throw new IllegalArgumentException("Kljuc ne smije biti null.");
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		public V getValue() {
			return value;
		}
		public void setValue(V value) {
			this.value = value;
		}
		public TableEntry<K, V> getNext() {
			return next;
		}
		public void setNext(TableEntry<K, V> next) {
			this.next = next;
		}
		public K getKey() {
			return key;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TableEntry<K,V> other = (TableEntry<K,V>) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}
		
		
		
		
	}
	
	private TableEntry<K, V>[] table;
	private int size;
	private int modificationCount;
	
	/**
	 * Stvara novu tablicu raspresenog adresiranja s 16 slobodnih mjesta.
	 */
	public SimpleHashtable() {
		this(16);
	}
	
	/**
	 * Stvara novu tablicu raspresenog adresiranja.
	 * @param number broj slobodnih mjesta u tablici
	 * @throws IllegalArgumentException ako je number manji od 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int number) {
		if (number < 1) 
			throw new IllegalArgumentException("Kapacitet tablice ne smije biti manji od 1.");
		
		int i = 0;
		while (true) {
			if (Math.pow(2, i) >= number) {
				number = (int) Math.pow(2, i);
				break;
			}
			i++;
		}
		table = (TableEntry<K, V>[]) new TableEntry[number];
		size = 0;
		modificationCount = 0;
	}
	

	
	/**
	 * Metoda dodaje novi element u tablicu.
	 * @param key kljuc elementa koji se dodaje u tablicu
	 * @param value vrijednost elementa koja se dodaje u tablicu
	 * @return vrijednost prethodnog elementa iz tablice ako je u tablici vec postojao element s kljucem key, inace <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		if (key == null)
			throw new NullPointerException("Kljuc ne smije biti null.");
		
		if ((size / table.length) >= 0.75 * table.length) {
			
			modificationCount++;
			TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[table.length * 2];
			for (int i = 0; i < table.length; i++) {
				if (table[i] != null) {
					TableEntry<K,V> entry = table[i];
					while (entry != null) {
						int idx = Math.abs(entry.getKey().hashCode() % newTable.length);
						
						if (newTable[idx] == null) {
							newTable[idx] = new TableEntry<>(entry.getKey(), entry.getValue(), null);
						} else {
							TableEntry<K,V> currEntry = newTable[idx];
							while(true) {
								if (currEntry.getKey().equals(entry.getKey())) {
									currEntry.setValue(value);
									break;
								} 
								if (currEntry.getNext() == null) {
									currEntry.setNext(new TableEntry<>(entry.getKey(), entry.getValue(), null));
									break;
								} 
								
								currEntry = currEntry.getNext();
							}
						}
						entry = entry.getNext();
						
					}
				}
			}
			
			table = newTable;

		}

		int idx = Math.abs(key.hashCode() % table.length);
		if (table[idx] == null) {
			table[idx] = new TableEntry<>(key, value, null);
			modificationCount++;
			size++;
			return null;
		} else {
			TableEntry<K,V> entry = table[idx];
			while(true) {
				if (entry.getKey().equals(key)) {
					V res = entry.getValue();
					entry.setValue(value);
					return res;
				} 
				if (entry.getNext() == null) {
					entry.setNext(new TableEntry<>(key, value, null));
					size++;
					modificationCount++;
					return null;
				} 
				
				entry = entry.getNext();
			}
		} 
		
	}
	
	/**
	 * Dohvaca element s zadanim kljucem.
	 * @param key kljuc pomocu kojeg se dohvaca element 
	 * @return vrijednost elementa u tablici s zadanim kljucem, ili <code>null</code> ako se element ne nalazi u tablici
	 */
	public V get(Object key) {
		if (!this.containsKey(key))
			return null;
		
		int idx = Math.abs(key.hashCode() % table.length);
		TableEntry<K,V> entry = table[idx];
		while (true) {
			if (entry == null) {
				return null;
			} 
			if (entry.getKey().equals(key)) {
				return entry.getValue();
			}
			entry = entry.getNext();
		}
	}
	
	/**
	 * Vraca broj elementa u tablici.
	 * @return broj elementa u tablici
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Metoda provjerava nalazi li se u tablici element kojem je kljuc jednak predanom kljucu.
	 * @param key kljuc elementa
	 * @return true ako se element nalazi u tablici, inace false
	 */
	public boolean containsKey(Object key) {
		int idx = Math.abs(key.hashCode() % table.length);
		TableEntry<K,V> entry = table[idx];
		while (true) {
			if (entry == null) {
				return false;
			} 
			if (entry.getKey().equals(key)) {
				return true;
			}
			entry = entry.getNext();
		}
	}
	
	/**
	 * Metoda provjerava nalazi li se u tablici element kojem je vrijednost jednaka predanoj vrijednosti.
	 * @param value vrijednost elementa
	 * @return true ako se element nalazi u tablici, inace false
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			TableEntry<K,V> entry = table[i];
			while (true) {
				if (entry == null) {
					break;
				}
				if (entry.getValue().equals(value)) {
					return true;
				}
				entry = entry.getNext();
			}
		}
		return false;
	}
	
	/**
	 * Uklanja element sa zadanim kljucem iz tablice.
	 * @param key kljuc elementa koji se uklanja
	 * @return vrijednost elementa ako se element s zadanim kljucem nalazio u tablici, inace <code>null</code>
	 */
	public V remove(Object key) {
		int idx = Math.abs(key.hashCode() % table.length);
		TableEntry<K,V> entry = table[idx];
		if (entry.getKey().equals(key)) {
			table[idx] = entry.getNext();
			size--;
			modificationCount++;
			return entry.getValue();
		}
		while (true) {
			if (entry.getNext() == null) {
				return null;
			} 
			if (entry.getNext().getKey().equals(key)) {
				V res = entry.getNext().getValue();
				entry.setNext(entry.getNext().getNext());
				size--;
				modificationCount++;
				return res;
			}
			entry = entry.getNext();
		}
	}
	
	/**
	 * Provjera ima li elemenata u tablici
	 * @return true ako je tablica prazna, inace false
	 */
	public boolean isEmpty() {
		if (size == 0) return true;
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int idx = 0;
		sb.append("[");
		for (int i = 0; i < table.length; i++) {
			TableEntry<K,V> entry = table[i];
			while (true) {
				if (entry == null) {
					break;
				}
				sb.append(entry.toString());
				if (idx < size - 1) sb.append(", ");
				idx++;
				entry = entry.getNext();
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Pretvara tablicu u polje slotova.
	 * @return polje
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K, V>[] toArray() {
		TableEntry<K,V>[] res = new TableEntry[size];
		int idx = 0;
		for (int i = 0; i < table.length; i++) {
			TableEntry<K,V> entry = table[i];
			while (true) {
				if (entry == null) {
					break;
				}
				res[idx] = entry;
				idx++;
				entry = entry.getNext();
			}
		}
		return res;
	}
	
	/**
	 * Brise sve elemente iz tablice.
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
	}

	/**
	 * Stvara novi iterator za iteriranje po tablici.
	 */
	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new SimpleHashtableIterator();

	}
	
	/**
	 * Razred predstavlja implementaciju iteratora koji sluzi za iteriranje po tablici SimpleHashtable.
	 * @author Maria
	 *
	 */
	class SimpleHashtableIterator implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		
		private int currentIdx;
		private int tableIdx;
		private TableEntry<K, V> currentEntry;
		private TableEntry<K, V> nextEntry;
		private int savedModificationCount;
		
		/**
		 * Stvara novi iterator.
		 */
		public SimpleHashtableIterator() {
			currentIdx = 0;
			tableIdx = 0;
			currentEntry = null;
			savedModificationCount = modificationCount;
			
			for (int i = 0; i < table.length; i++) {
				if (table[i] != null) {
					nextEntry = table[i];
					break;
				}
				tableIdx++;
			}
		}
		
		/**
		 * Provjera postoji li iduci element.
		 * @throws ConcurrentModificationException ako je tablica mijenjana za vrijeme iteriranja.
		 */
		@Override
		public boolean hasNext() {
			if (modificationCount > savedModificationCount) 
				throw new ConcurrentModificationException("Kolekcija je modificrana za vrijeme dohvata elemenata.");
			
			return currentIdx < size;
		}
		
		/**
		 * Dohvaca iduci element.
		 * @throws ConcurrentModificationException ako je tablica mijenjana za vrijeme iteriranja.
		 */
		@Override
		public TableEntry<K, V> next() {
			if (modificationCount > savedModificationCount) 
				throw new ConcurrentModificationException("Kolekcija je modificrana za vrijeme dohvata elemenata.");
			
			if (currentIdx >= size) {
				throw new NoSuchElementException("Nema vise elemenata.");
			}
			
			currentEntry = nextEntry;
			currentIdx++;
			
			if (nextEntry.getNext() != null) {
				nextEntry = nextEntry.getNext();
			} else {
				
				for (int i = tableIdx; i < table.length; i++) {
					if (this.hasNext()) {
						tableIdx++;
						if (table[tableIdx] != null) {
							nextEntry = table[tableIdx];
							break;
						}
					}
				}
			}
			
			
			return currentEntry;
		}
		
		/**
		 * Uklanja trenutni element.
		 * @throws IllegalStateException ako se pozove dva puta nad istim elementom.
		 * @throws ConcurrentModificationException ako je tablica mijenjana za vrijeme iteriranja.
		 */
		@Override
		public void remove() {
			if (modificationCount > savedModificationCount) 
				throw new ConcurrentModificationException("Kolekcija je modificrana za vrijeme dohvata elemenata.");
			
			if (currentEntry == null) throw new IllegalStateException();
			
			SimpleHashtable.this.remove(currentEntry.getKey());
			currentIdx--;
			currentEntry = null;
			savedModificationCount++;
		}
	}
}
