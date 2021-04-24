package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Razred predstavlja kolekciju u kojoj su dopusteni duplikati te nisu dopustene null reference. Elementi kolekcije nalaze se u cvorovima liste.
 * 
 * @author Maria
 *
 */
public class LinkedListIndexedCollection implements List {
	
	/**
	 * Staticki ugnijezdeni razred koji predstavlja jedan element zadane kolekcije. Svaki element kolekcije sadrzi pokazivace na prethodni i iduci element.
	 * 
	 * @author Maria
	 *
	 */
	private static class ListNode {
		private Object value;
		private ListNode next;
		private ListNode prev;
		
		/*
		 * Stvara novi primjerak cvora.
		 * @param value vrijendot cvora
		 */
		ListNode(Object value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value.toString();
		}
		
		
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	private long modificationCount;
	
	/**
	 * Stvara praznu kolekciju te automatski postavlja vrijednost cvorcova first i last na <code>null</code>.
	 */
	public LinkedListIndexedCollection() {
		this.size = 0;
		modificationCount = 0;
		this.first = null;
		this.last = null;
	}
	
	/**
	 * Stvara kolekciju ciji elementi su jednaki elementima zadane kolekcije collection.
	 * 
	 * @param collection kolekcija ciji elementi se kopiraju u novu kolekciju
	 */
	public LinkedListIndexedCollection(Collection collection) {
		this.size = 0;
		modificationCount = 0;
		this.addAll(collection);
	}
	
	/**
	 * Dodaje novi objekt na kraj kolekcije.
	 * 
	 * @param value objekt koji se dodaje u kolekciju
	 * @throws NullPointerException ako je value <code>null</code>
	 */
	@Override
	public void add(Object value) {
		if (value == null) throw new NullPointerException("Objekt ne smije biti null.");
		
		modificationCount++;
		ListNode node = new ListNode(value);
		
		if (first == null) {
			first = last = node;
			first.prev = null;
		} else {
			last.next = node;
			node.prev = last;
			last = node;
		}
		last.next = null;
		size++;
	}
	
	/**
	 * Dohvaca objekt koji se nalazi na kraju povezane liste.
	 * 
	 * @param index indeks s kojeg je potrebno dohvatiti objekt
	 * @return element kolekcije sa zadanog indeksa
	 * @throws IndexOutOfBoundsException ako indeks nije izmedu 0 i size-1
	 * 
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1) throw new IndexOutOfBoundsException("Indeks mora biti izmedu 0 i size - 1.");
		
		ListNode temp;
		
		if (index < size / 2) {
			temp = first;
			int i = 0;
			while (i < index) {
				temp = temp.next;
				i++;
			}
		} else {
			temp = last;
			int i = size - 1;
			while (i > index) {
				temp = temp.prev;
				i--;
			}
		}
		return temp.value;
	}
	
	/**
	 * Uklanja sve elemente iz kolekcije.
	 * 
	 */
	@Override
	public void clear() {
		first = last = null;
		modificationCount++;
		size = 0;
	}
	
	/**
	 * Umece zadanu vrijendost na zadano mjesto u kolekciji. 
	 * 
	 * @param value objekt koji se umece u kolekciju
	 * @param position indeks mjesta na koje je potrebno umetnuti objekt
	 * @throws IndexOutOfBoundsException ako je varijabla position manja od 0 ili veca od size
	 * @throws NullPointerException ako je varijabla value <code>null</code>
	 */
	public void insert(Object value, int position) {
		
		if (position < 0 || position > size) throw new IndexOutOfBoundsException("Pozicija mora biti izmedu 0 i size.");
		if (value == null) throw new NullPointerException("Objekt ne smije biti null.");
		
		modificationCount++;
		ListNode node = new ListNode(value);
		
		//dodavanje na pocetnu poziciju liste
		if (position == 0) {
			node.next = first;
			node.prev = null;
			if (first != null) first.prev = node;
			first = node;
			
		} else {
		
			int idx = 0;
			ListNode temp = first;
			
			while (idx < position - 1) {
				temp = temp.next;
				idx++;
			}
			
			node.next = temp.next;
			temp.next = node;
			node.prev = temp;
			if (node.next != null) node.next.prev = node;
			else last = node;

		}
		
		size++;
		
	}
	
	/**
	 * Dohvaca indeks zadanog objekta u kolekciji.
	 * 
	 * @param value objekt za koji je potrebno pronaci indeks
	 * @return indeks objekta u kolekciji
	 */
	public int indexOf(Object value) {
		if (value == null) return -1;
		
		ListNode temp = first;
		int idx = 0;
		
		while (!temp.value.equals(value) && idx < size) {
			temp = temp.next;
			idx++;
		}
		
		if (idx < size) return idx;
		else return -1;
	}
	
	/**
	 * Uklanja element sa zadane pozicije.
	 * 
	 * @param index indeks elementa kojeg je potrebno ukloniti
	 * @throws IndexOutOfBoundsException ako je indeks manji od 0 ili veci od size-1
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1) throw new IndexOutOfBoundsException("Indeks mora biti izmedu 0 i size - 1.");
		
		modificationCount++;
		int i = 0;
		ListNode temp = first;
		
		while (i < index) {
			temp = temp.next;
			i++;
		}
		
		if (i == 0) first = temp.next;
		if (temp.next != null) temp.next.prev = temp.prev;
		if (temp.prev != null) temp.prev.next = temp.next;
		
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
		
		Object[] arr = new Object[size];
		ListNode node = first;
		int i = 0;
		
		while(i < size) {
			arr[i] = node.value;
			node = node.next;
			i++;
		}
		
		return arr;
	}
	
	/**
	 * Uklanja objekt iz kolekcije ako se zadani objekt nalazi u kolekciji.
	 * 
	 * @param value objekt koji se uklanja iz kolekcije
	 * @return true ako se objekt neposredno prije poziva metode nalazio u kolekciji, inace false
	 */
	@Override
	public boolean remove(Object value) {
		ListNode node = first;
		int i = 0;
		modificationCount++;
		
		while (!node.value.equals(value) && i < size) {
			node = node.next;
			i++;
		}
		
		if (i < size) {
			this.remove(i);
			return true;
		}
		
		return false;
	}
	

	/**
	 * Privatna staticka klasa koja implementira sucelje ElementsGetter i sluzi sa dohvacanje elemenata kolekcije LinkedListIndexedCollection.
	 * @author Maria
	 *
	 */
	private static class LinkedListElementsGetter implements ElementsGetter {
		
		LinkedListIndexedCollection col;
		private ListNode node;
		private long savedModificationCount;
		
		public LinkedListElementsGetter(LinkedListIndexedCollection col) {
			this.col = col;
			node = col.first;
			savedModificationCount = col.modificationCount;
		}
		
		@Override
		public boolean hasNextElement() {
			if (col.modificationCount > savedModificationCount) 
				throw new ConcurrentModificationException();
			
			return node != null;
		}

		@Override
		public Object getNextElement() {
			if (col.modificationCount > savedModificationCount) 
				throw new ConcurrentModificationException("Kolekcija je modificrana za vrijeme dohvata elemenata.");
			
			if (this.hasNextElement()) {
				Object res = node.value;
				node = node.next;
				return res;
			}
			throw new NoSuchElementException("Kolekcija je modificrana za vrijeme dohvata elemenata.");
		}
	}
	
	@Override
	public ElementsGetter createElementsGetter() {
		return new LinkedListElementsGetter(this);
	}

}
