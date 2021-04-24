package hr.fer.zemris.lsystems.impl.collections;

/**
 * Razred predstavlja iznimku koja se javlja ako se zovu nedopu�tene metode nad praznim stogom.
 * 
 * @author Maria
 *
 */
public class EmptyStackException extends RuntimeException {
	
	private static final long serialVersionUID = 1L; 

	/**
	 * Stvara novu iznimku nastalu pozivanjem metoda nad praznim stogom.
	 */
	public EmptyStackException() {
	}
	
	/**
	 * Stvara novu iznimku nastalu pozivanjem metoda nad praznim stogom.
	 * @param msg
	 */
	public EmptyStackException(String msg) {
		super(msg);
	}
	
	/**
	 * Stvara novu iznimku nastalu pozivanjem metoda nad praznim stogom.
	 * @param cause
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Stvara novu iznimku nastalu pozivanjem metoda nad praznim stogom.
	 * @param msg
	 * @param cause
	 */
	public EmptyStackException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
