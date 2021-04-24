package hr.fer.oprpp1.custom.collections;

public interface List extends Collection {
	
	/**
	 * Dohvaca objekt sa zadane pozicije u listi.
	 * 
	 * @param index indeks objekta u listi kojeg je potrebno dohvatiti
	 * @return objekt sa zadane pozicije
	 * @throws IndexOutOfBoundsException ako indeks nije valjan
	 */
	Object get(int index);
	
	/**
	 * Umece zadanu vrijednost na zadano mjesto u listi.
	 * 
	 * @param value vrijednost koju je potrebno umetnuti u listu
	 * @param position pozicija na koju se zadana vrijednost umece
	 */
	void insert(Object value, int position);
	
	/**
	 * Dohvaca poziciju elementa lsite sa zadanom vrijednoscu.
	 * 
	 * @param value vrijednost elementa za koji se trazi indeks
	 */
	int indexOf(Object value);
	

	/**
	 * Uklanja element sa zadane pozicije.
	 * 
	 * @param index indeks elementa kojeg je potrebno ukloniti
	 */
	void remove(int index);

}
