package hr.fer.zemris.lsystems.impl.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Razred predstavlja kolekciju u kojoj su dopusteni duplikati elemenata, ali pohrana null refernci nije dopu�tena.
 * 
 * @author Maria
 *
 */
public class ArrayIndexedCollection<T> implements List<T> {

	private int size;
	private T[] elements;
	private long modificationCount;
	
	/**
	 * Stvara polje objekata koje ima 16 mjesta.
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	
	/**
	 * Stvara polje objekata velicine initialCapacity
	 * @param initialCapacity velicina polja objekata
	 * @throws IllegalArgumentException ako je initalCapacity manji od 1
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		
		if (initialCapacity < 1) throw new IllegalArgumentException("Inicijialni kapacitet polja mora biti manji od 1.");
		
		this.size = 0;
		modificationCount = 0;
		this.elements = (T[]) new Object[initialCapacity];
	}
	
	/**
	 * Stvara novu kolekciju kojoj su elementi jednaki zadanoj kolekciji.
	 * @param collection kolekcija ciji se elementi kopiraju u novu kolekciju
	 * @throws NullPointerException ako je collection <code>null</code>
	 */
	public ArrayIndexedCollection(Collection<? extends T> collection) {
		this(collection, collection.size());
	}

	/**
	 * Stvara novu kolekciju kojoj su elementi jednaki zadanoj kolekciji, a velicina je jednaka initalCapacity.
	 * 
	 * @param collection kolekcija ciji se elementi kopiraju u novu kolekciju
	 * @throws NullPointerException ako je collection <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<? extends T> collection, int initialCapacity) {
		
		if (collection == null) throw new NullPointerException("Kolekcija ne smije biti null.");
		
		int capacity = Math.max(initialCapacity, collection.size());
		modificationCount = 0;
		size = capacity;
		this.elements = (T[]) new Object[capacity];
		
		T[] objects = (T[]) collection.toArray();
		for (int i = 0; i < capacity; i++) {
			elements[i] = objects[i];
		}
		
	}
	
	/**
	 * Dodaje zadani objekt u kolekciju.
	 * 
	 * @param value vrijednost koju je potrebno upisati na zadnje mjesto u polju
	 * @throws NullPointerException ako je value <code>null</code>
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void add(T value) {
		
		if (value == null) throw new NullPointerException("Vrijednost objekta ne smije biti null.");
		
		if (size == elements.length) {
			T[] helpArray = (T[]) new Object[elements.length];
			for (int i = 0; i < elements.length; i++) {
				helpArray[i] = elements[i];
			}
			elements = (T[]) new Object[helpArray.length * 2];
			for (int i = 0; i < helpArray.length; i++) {
				elements[i] = helpArray[i];
			}
			modificationCount++;
		}
		
		elements[size] = value;
		size++;
	}
	
	/**
	 * Dohvaca objekt sa zadane pozicije u polju.
	 * 
	 * @param index indeks objekta u polju kojeg je potrebno dohvatiti
	 * @return objekt sa zadane pozicije
	 * @throws IndexOutOfBoundsException ako indeks nije valjan
	 */
	public T get(int index) {
		if (index < 0 || index > size - 1) throw new IndexOutOfBoundsException("Indeks ne smije biti manji od nule ni veci od size - 1.");
		return elements[index];
	}
	
	/**
	 * Uklanja sve elemente iz kolekcije.
	 * 
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		modificationCount++;
		size = 0;
	}
	
	/**
	 * Umece zadanu vrijednost na zadano mjesto u polju.
	 * 
	 * @param value vrijednost koju je potrebno umetnuti u polje
	 * @param position pozicija na koju se zadana vrijednost umece
	 * @throws IndexOutOfBoundsException ako je varijabla position manja od 0 ili veca od size
	 * @throws NullPointerException ako je varijabla value <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	public void insert(T value, int position) {
		
		if (position < 0 || position > size) throw new IndexOutOfBoundsException("Pozicija ne smije biti manja od nule ni veci od size - 1.");
		if (value == null) throw new NullPointerException("Objekt ne smije biti null.");
		
		modificationCount++;
		
		if (size >= elements.length) {
			T[] helpArray = (T[]) new Object[elements.length];
			for (int i = 0; i < elements.length; i++) {
				helpArray[i] = elements[i];
			}
			elements = (T[]) new Object[helpArray.length * 2];
			for (int i = 0; i < elements.length; i++) {
				elements[i] = helpArray[i];
			}
		}
		
		int i = size - 1;
		while (i >= position) {
			elements[i + 1] = elements[i];
			i--;
		}
		elements[i + 1] = value;
		size++;
	}
	
	/**
	 * Dohvaca poziciju elementa kolekcije sa zadanom vrijednoscu.
	 * 
	 * @param value vrijednost elementa za koji se trazi indeks
	 * @return indeks elementa zadane vrijednosti ili -1 ako vrijednost ne postoji u kolekciji
	 */
	public int indexOf(Object value) {
		if (value == null) return -1;

		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) return i;
		}
		
		return -1;
	}
	
	/**
	 * Uklanja element sa zadane pozicije.
	 * 
	 * @param index indeks elementa kojeg je potrebno ukloniti
	 * @throws IndexOutOfBoundsException ako je indeks manji od 0 ili veci od size-1
	 */
	public void remove(int index) {
		
		if (index < 0 || index > size - 1) throw new IndexOutOfBoundsException("Indeks ne smije biti manji od nule ni veci od size - 1.");
		
		modificationCount++;
		int i;
		for (i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		elements[i] = null;
		size--;
	}
	
	/**
	 * Racuna broj trenutnih elemenata u kolekciji.
	 * 
	 * @return broj trenutnih elemenata u kolekciji
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Provjera sadrzi li kolekcija zadani objekt.
	 * 
	 * @param value objekt za koji provjeravamo nalazi li se u kolekciji
	 * @return true ako se objekt nalazi u kolekciji, inace false
	 */
	@Override
	public boolean contains(Object value) {
		if (this.indexOf(value) != -1) return true;
		return false;
	}
	
	/**
	 * Alocira novo polje cija je velicina jednaka velicini kolekcije te puni polje elementima kolekcije.
	 *
	 * @return polje koje sadrzi elemente kolekcije
	 * @throws UnsupportedOperationException ako je zadana kolekcija prazna
	 */
	@Override
	public Object[] toArray() {
		return elements;
	}
	
	/**
	 * Uklanja objekt iz kolekcije ako se zadani objekt nalazi u kolekciji.
	 * 
	 * @param value objekt koji se uklanja iz kolekcije
	 * @return true ako se objekt neposredno prije poziva metode nalazio u kolekciji, inace false
	 */
	@Override
	public boolean remove(Object value) {
		if (value == null) return false;
		
		modificationCount++;
		
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				elements[i] = null;
				int j;
				for (j = i; j < size - 1; j++) {
					elements[j] = elements[j + 1];
				}
				elements[j] = null;
				size--;
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Privatna staticka klasa koja implementira sucelje ElementsGetter i sluzi sa dohvacanje elemenata kolekcije ArrayIndexedCollection.
	 * @author Maria
	 *
	 */
	private static class ArrayElementsGetter<V> implements ElementsGetter<V> {

		ArrayIndexedCollection<V> col;

		private int brojac;
		private long savedModificationCount;
		
		public ArrayElementsGetter(ArrayIndexedCollection<V> col) {
			this.col = col;
			brojac = 0;
			savedModificationCount = col.modificationCount;
		}
		
		@Override
		public boolean hasNextElement() {
			if (col.modificationCount > savedModificationCount) 
				throw new ConcurrentModificationException("Kolekcija je modificrana za vrijeme dohvata elemenata.");
			
			if (brojac < col.size()) return true;
			else return false;
		}

		@Override
		public V getNextElement() {
			if (col.modificationCount > savedModificationCount) 
				throw new ConcurrentModificationException("Kolekcija je modificrana za vrijeme dohvata elemenata.");
			
			if (this.hasNextElement()) {
				V res = (V) col.get(brojac);
				brojac++;
				return res;
			}
			throw new NoSuchElementException();
		}

		
	}
	
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ArrayElementsGetter<T>(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(elements);
		result = prime * result + (int) (modificationCount ^ (modificationCount >>> 32));
		result = prime * result + size;
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
		ArrayIndexedCollection<T> other = (ArrayIndexedCollection<T>) obj;
		if (!Arrays.deepEquals(elements, other.elements))
			return false;
		if (modificationCount != other.modificationCount)
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	public Integer getAllocatedSize() {
		return elements.length;
	}

	
	
	
}
