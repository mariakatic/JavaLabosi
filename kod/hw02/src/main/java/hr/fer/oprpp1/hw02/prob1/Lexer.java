package hr.fer.oprpp1.hw02.prob1;
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
	 * Nacin na koji lekser obraduje tekst.
	 */
	private LexerState state;
	
	/**
	 * Stvara novi lekser koji prima ulazni tekst koji se tokenizira.
	 * @param text ulazni tekst koji se tokenizira
	 */ 
	public Lexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
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
		
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		if (data[currentIndex] == Character.valueOf('-') ||
				data[currentIndex] == Character.valueOf('.') ||
				data[currentIndex] == Character.valueOf('?') ||
				data[currentIndex] == Character.valueOf('#') ||
				data[currentIndex] == Character.valueOf('!') ||
				data[currentIndex] == Character.valueOf(';') ) {
				
				token = new Token(TokenType.SYMBOL, data[currentIndex]);
				currentIndex++;
				return token;
			}
		
		if (this.state == LexerState.BASIC) {
			
			if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
				StringBuilder sb = new StringBuilder();
				if (data[currentIndex] == '\\') {
					currentIndex++;
					if (currentIndex == data.length || Character.isLetter(data[currentIndex]))
						throw new LexerException();
				}
				sb.append(data[currentIndex]);
				currentIndex++;
				while (currentIndex < data.length && (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\')) {
					if (data[currentIndex] == '\\')  {
						currentIndex++;
						if (currentIndex == data.length || Character.isLetter(data[currentIndex]))
							throw new LexerException();
					}
					sb.append(data[currentIndex]);
					currentIndex++;
				}
				String value = sb.toString();
				token = new Token(TokenType.WORD, value);
				return token;
			}
			
			if (Character.isDigit(data[currentIndex])) {
				StringBuilder sb = new StringBuilder();
				sb.append(data[currentIndex]);
				currentIndex++;
				while (currentIndex < data.length && (Character.isDigit(data[currentIndex]))) {
					sb.append(data[currentIndex]);
					currentIndex++;
				}
				try {
					long value = Long.parseLong(sb.toString());
					token = new Token(TokenType.NUMBER, value);
					return token;
				} catch (NumberFormatException ex) {
					throw new LexerException("Broj je predug.");
				}
				
			}
			
		} else {
			StringBuilder sb = new StringBuilder();
			while (currentIndex < data.length && 
				(Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\' || Character.isDigit(data[currentIndex]))) {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
			String value = sb.toString();
			token = new Token(TokenType.WORD, value);
			return token;
			
		}
		

		return null;
	}
	
	/**
	 * Metoda dohvaca zadnji generirani token. Uzastopnim pozivanjem ne pokrece se generiranje sljedeceg tokena.
	 * @return zadnji generirani token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Metoda omogucava postavljanje zeljenog nacina na koji lekser obraduje tekst.
	 * @param state nacin na koji lekser obraduje tekst
	 * @throws NullPointerException ako je stanje <code>null</code>
	 */
	public void setState(LexerState state) {
		if (state == null) throw new NullPointerException("Stanje leksera ne smije biti null.");
		this.state = state;
	}
	


}
