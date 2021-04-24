package hr.fer.oprpp1.hw04.db.lexer;

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

