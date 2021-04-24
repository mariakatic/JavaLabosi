package hr.fer.oprpp1.hw04.db;

/**
 * Iznimka koju baca parser pri izvodenju.
 * @author Maria
 *
 */
public class QueryParserException extends RuntimeException {
	
private static final long serialVersionUID = 1L; 
	
	public QueryParserException() {
		
	}
	
	public QueryParserException(String message) {
		super(message);
	}
	
	
	public QueryParserException(Throwable cause) {
		super(cause);
	}
	
	public QueryParserException(String message, Throwable cause) {
		super(message, cause);
	}

}

