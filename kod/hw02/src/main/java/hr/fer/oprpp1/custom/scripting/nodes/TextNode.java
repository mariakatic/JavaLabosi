package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Razred predstavlja tekstualni dio datoteke.
 * @author Maria
 *
 */
public class TextNode extends Node {
	
	private String text;
	
	/**
	 * Stvara novi tekstualni cvor.
	 * @param text sadrzaj tekstualnog cvora
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Dohvaca sadrzaj tekstualnog cvora.
	 * @return sadrzaj tekstualnog cvora
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return text;
	}


}
