package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Glavni razred za sve cvorove.
 * @author Maria
 *
 */
public class Node {
	
	/**
	 * Kolekcija djece jednog cvora.
	 */
	private ArrayIndexedCollection children;
	
	/**
	 * Metoda dodaje dijete kolekciji trenutnog cvora.
	 * @param child dijete koje se dodaje cvoru.
	 */
	public void addChildNode(Node child) {
		if (children == null) 
			children = new ArrayIndexedCollection();
		
		children.add(child);
	}
	
	/**
	 * Metoda vraca broj (direktne) djece nekog cvora.
	 * @return broj djece cvora
	 */
	public int numberOfChildren() {
		return children.size();
	}
	
	/**
	 * Dohvaca dijete trenutnog cvora sa zadane pozicije.
	 * @param index pozicija sa koje se dohvaca dijete
	 * @return cvor koji se nalazi na toj poziciji
	 * @throws IndexOutOfBoundsException ako indeks nije valjan
	 */
	public Node getChild(int index) {
		return (Node)children.get(index);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.numberOfChildren(); i++) {
			sb.append(this.getChild(i).toString()).append('\n');
		}
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		return true;
	}
	
	
	
	
	
	


}
