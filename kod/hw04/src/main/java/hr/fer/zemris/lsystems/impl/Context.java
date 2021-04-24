package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.impl.collections.ObjectStack;

/**
 * Razred omogucuje izvodenje postupka prikazivanja fraktala koristeci interni stog za pohranu stanja kornjace.
 * @author Maria
 *
 */
public class Context {
	
	private ObjectStack<TurtleState> stack;
	
	/**
	 * Stvara novi stog.
	 */
	public Context() {
		this.stack = new ObjectStack<>();
	}
	
	/**
	 * Vraca stanje kornjace koje je na vrhu stoga, ali ne uklanja ga.
	 * @return
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * Stavlja zadano stanje kornjace na vrh stoga.
	 * @param state stanje kornjace koje se stavlja na vrh stoga
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Brise jedno stanje kornjace s vrha stoga.
	 */
	public void popState() {
		stack.pop();
	}

}
