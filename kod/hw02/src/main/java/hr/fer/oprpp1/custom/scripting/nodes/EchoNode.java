package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Razred predstavlja naredbu koja dinamicki generira neki tekstualni izraz.
 * @author Maria
 *
 */
public class EchoNode extends Node {
	
	private Element[] elements;
	
	/**
	 * Stvara novi EchoNode.
	 * @param elements elementi cvora
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}
	
	/**
	 * Dohvaca elemente EchoNode-a.
	 * @return elemente cvora
	 */
	public Element[] getElements() {
		return this.elements;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$").append("=");
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] == null)
				break;
			sb.append(elements[i].asText()).append(" ");
		}
		sb.append("$}");
		
		return sb.toString();
	}


}
