package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

/**
 * Razred predstavlja kosntrukciju for petlje.
 * @author Maria
 *
 */
public class ForLoopNode extends Node {
	
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
	/**
	 * Stvara novi cvor koji prestavlja for-petlju.
	 * @param variable varijabla po kojoj se iterira
	 * @param startExpression pocetni izraz
	 * @param endExpression zavrsni izraz
	 * @param stepExpression medukorak
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,  Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Dohvaca varijablu po kojoj se iterira.
	 * @return varijablu for petlje
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Dohvaca pocetni izraz for petlje.
	 * @return pocetni izraz for petlje
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Dohvaca zavrsni izraz for petlje.
	 * @return zavrsni izraz for petlje
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Dohvaca medukorak for petlje.
	 * @return medukorak for petlje
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$").append("FOR");
		sb.append(" ").append(variable.asText()).append(" ").append(startExpression.asText()).append(" ");
		if (stepExpression != null)
			sb.append(stepExpression.asText()).append(" ");
		sb.append(endExpression.asText()).append("$}");
		
		if (this.numberOfChildren() > 0) {
			for (int i = 0; i < this.numberOfChildren(); i++) {
				sb.append(this.getChild(i).toString());
			}
		}
		
		sb.append("{$END$}");
		
		return sb.toString();
	}

	
	

}
