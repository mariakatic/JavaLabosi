package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Razred predstavlja iznimku koju baca lekser.
 * @author Maria
 *
 */
public class LexerException extends RuntimeException {
	
	private static final long serialVersionUID = 1L; 
	
	public LexerException() {
	}
	
	public LexerException(String message) {
		super(message);
	}
	
	public LexerException(Throwable cause) {
		super(cause);
	}
	
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}

}
