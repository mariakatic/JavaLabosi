package hr.fer.oprpp1.custom.collections;

/**
 * Razred predstavlja kolekciju koja nalikuje na stog.
 * 
 * @author Maria
 */
public class ObjectStack {
	
	private ArrayIndexedCollection col;
	
	/**
	 * Stvara novu kolekciju koja nalikuje na stog.
	 */
	public ObjectStack() {
		col = new ArrayIndexedCollection();
	}
	
	/**
	 * Provjera je li stog prazan.
	 * 
	 * @return true ako je stog prazan, inace false
	 */
	public boolean isEmpty() {
		return col.isEmpty();
	}
	
	/**
	 * Racuna broj trenutnih elemenata na stogu.
	 * 
	 * @return broj trenutnih elemenata na stogu
	 */
	public int size() {
		return col.size();
	}
	
	/**
	 * Stavlja zadani objekt na stog.
	 * 
	 * @param value vrijednost koju je potrebno staviti na stog
	 * @throws NullPointerException ako je value <code>null</code>
	 */
	public void push(Object value) {
		col.add(value);
	}
	
	/**
	 * Uklanja element s vrha stoga i vraca ga.
	 * 
	 * @return element s vrha stoga
	 * @throws EmptyStackExpression ako je stog prazan
	 */
	public Object pop() {
		int size = col.size();
		if (size == 0) throw new EmptyStackException("Stog je prazan.");
		Object obj = col.get(size - 1);
		col.remove(size - 1);
		return obj;
	}
	
	/**
	 * Vraca element s vrha stoga.
	 * 
	 * @return element s vrha stoga
	 * @throws EmptyStackExpression ako je stog prazan
	 */
	public Object peek() {
		if (this.isEmpty()) throw new EmptyStackException("Stog je prazan.");
		return col.get(this.size() - 1);
	}
	
	/**
	 * Uklanja sve elemente sa stoga.
	 * 
	 */
	void clear() {
		col.clear();
	}
	
	

}
