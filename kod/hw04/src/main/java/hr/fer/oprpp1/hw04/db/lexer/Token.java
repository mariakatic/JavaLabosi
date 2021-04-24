package hr.fer.oprpp1.hw04.db.lexer;

/**
 * Razred predstavlja leksicku jedinicu koja grupira jedan ili vise uzastopnih znakova iz ulazng teksta.
 * @author Maria
 *
 */
public class Token {
	
	/**
	 * Vrijednost koju token predstavlja
	 */
	private Object value;
	
	/**
	 * Tip tokena
	 */
	private TokenType type;
	
	/**
	 * Stvara novi token
	 * @param type tip tokena
	 * @param value vrijendost tokena
	 */
	public Token(TokenType type, Object value) {
		this.value = value;
		this.type = type;
	}
	
	/**
	 * Vraca vrijendost tokena.
	 * @return vrijednost tokena
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Vraca tip tokena.
	 * @return tip tokena
	 */
	public TokenType getType() {
		return this.type;
	}

}

