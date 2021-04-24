package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Vrsta cvora koja predstavlja neki dokument.
 * @author Maria
 *
 */
public class DocumentNode extends Node {
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.numberOfChildren(); i++) {
			sb.append(this.getChild(i).toString());
		}
		
		return sb.toString();
	}
	
	

}
