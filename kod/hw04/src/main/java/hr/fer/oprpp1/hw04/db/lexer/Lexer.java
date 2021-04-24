package hr.fer.oprpp1.hw04.db.lexer;

/**
 * Razred predstavlja implementaciju jedostavnog leksera.
 * @author Maria
 *
 */
public class Lexer {
	
	/**
	 * Ulazni tekst koji se obraduje.
	 */
	private char[] data; 
	
	/**
	 * Trenutni token dobiven iz ulaznog teksta.
	 */
	private Token token;
	
	/**
	 * Indeks prvog neobradenog znaka.
	 */
	private int currentIndex; 
	
	/**
	 * Stvara novi lekser koji prima ulazni tekst koji se tokenizira.
	 * @param text ulazni tekst koji se tokenizira
	 */ 
	public Lexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
	}
	

	/**
	 * Generira i vraca sljedeci token.
	 * @return sljedeci token
	 * @throws LexerException ako dode do pogreske
	 */
	public Token nextToken() {
		
		if (token != null && token.getType() == TokenType.EOF)
			throw new LexerException();
		
		//skipping blanks
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex++;
				continue;
			}
			break;
		}
		
		//end
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		//field or AND or LIKE
		if (Character.isLetter(data[currentIndex])) {
			StringBuilder sb = new StringBuilder();
			while (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
			String value = sb.toString();
			
			if (value.toUpperCase().equals("AND")) {
				token = new Token(TokenType.AND, value.toUpperCase());
				return token;
			} else if (value.toUpperCase().equals("LIKE")) {
				token = new Token(TokenType.OPERATOR, value.toUpperCase());
				return token;
			}
			
			token = new Token(TokenType.FIELD, value);
			return token;
		}
		
		//operator
		if (data[currentIndex] == Character.valueOf('<') ||
				data[currentIndex] == Character.valueOf('>') ||
				data[currentIndex] == Character.valueOf('=') ||
				data[currentIndex] == Character.valueOf('!')) {
			
			String operator = String.valueOf(data[currentIndex]);
			currentIndex++;
			if (data[currentIndex] == Character.valueOf('=')) {
				operator += String.valueOf(data[currentIndex]);
				currentIndex++;
			}
			
			token = new Token(TokenType.OPERATOR, operator);
			return token;
		}
		
		//string
		if (data[currentIndex] == '\"') {
			currentIndex++;
			StringBuilder sb = new StringBuilder();
			while (currentIndex < data.length && data[currentIndex] != '\"') {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
			if (currentIndex == data.length)
				throw new LexerException("Netocno zadan string.");
			
			currentIndex++;
			String value = sb.toString();
			token = new Token(TokenType.STRING, value);
			return token;
		}
		
		throw new LexerException("Nepodrzan tekst.");
	}
	
	/**
	 * Metoda dohvaca zadnji generirani token. Uzastopnim pozivanjem ne pokrece se generiranje sljedeceg tokena.
	 * @return zadnji generirani token
	 */
	public Token getToken() {
		return token;
	}
}