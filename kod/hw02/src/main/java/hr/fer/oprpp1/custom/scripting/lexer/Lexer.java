package hr.fer.oprpp1.custom.scripting.lexer;


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
		state = LexerState.TEXT;
	}
	

	/**
	 * Generira i vraca sljedeci token.
	 * @return sljedeci token
	 * @throws LexerException ako dode do pogreske
	 */
	public Token nextToken() {
		
		if (token != null && token.getType() == TokenType.EOF)
			throw new LexerException();

		
		if (this.state == LexerState.TEXT) {
			
			if (currentIndex >= data.length) {
				token = new Token(TokenType.EOF, null);
				return token;
			}
			
			StringBuilder sb = new StringBuilder();
			
			while (currentIndex < data.length) {
				
				if (data[currentIndex] == '\\') {
					currentIndex++;
					if (currentIndex == data.length || (data[currentIndex] != '\\' && data[currentIndex] != '{'))
						throw new LexerException();
					sb.append(data[currentIndex]);
					currentIndex++;
				}
				
				if (currentIndex + 1 < data.length) {
					if (data[currentIndex] == '{' && data[currentIndex+1] == '$') {
						break;
					}
				}
				
				if (currentIndex < data.length && data[currentIndex] != '\\') {
					sb.append(data[currentIndex]);
					currentIndex++;
				} 
				
				
			}
			
			token = new Token(TokenType.STRING, sb.toString());
			return token;
			
			
			
		} else {
			
			
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
			
			if (currentIndex + 1 < data.length) {
				if ((data[currentIndex] == '{' && data[currentIndex + 1] == '$') || (data[currentIndex] == '$' && data[currentIndex + 1] == '}')) {
					StringBuilder sb = new StringBuilder();
					sb.append(data[currentIndex]).append(data[currentIndex+1]);
					currentIndex += 2;
					token = new Token(TokenType.TAG, sb.toString());
					return token;
				}
			}
			
			if (data[currentIndex] == '\\') {
				currentIndex++;
				if (currentIndex == data.length || (data[currentIndex] != '\\' && data[currentIndex] != '\"' && data[currentIndex] != 'n' && data[currentIndex] != 'r' && data[currentIndex] != 't'))
					throw new LexerException();
				token = new Token(TokenType.STRING, data[currentIndex]);
				currentIndex++;
				return token;
			}
			
			if (Character.isDigit(data[currentIndex]) || (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1]))) {
				StringBuilder sb = new StringBuilder();
				if (data[currentIndex] == '-') {
					sb.append(data[currentIndex]);
					currentIndex++;
				}
				while (Character.isDigit(data[currentIndex])) {
					sb.append(data[currentIndex]);
					currentIndex++;
				}
				if (data[currentIndex] == '.') {
					sb.append(data[currentIndex]);
					currentIndex++;
					while (Character.isDigit(data[currentIndex])) {
						sb.append(data[currentIndex]);
						currentIndex++;
					}
					token = new Token(TokenType.CONST_DOUBLE, Double.parseDouble(sb.toString()));
					return token;
				}
				token = new Token(TokenType.CONST_INTEGER, Integer.parseInt(sb.toString()));
				return token;
				
			}
			
			if (data[currentIndex] == Character.valueOf('=')) {
				token = new Token(TokenType.EQUAL, data[currentIndex]);
				currentIndex++;
				return token;
			}
			
			if (data[currentIndex] == Character.valueOf('+') ||
					data[currentIndex] == Character.valueOf('-') ||
					data[currentIndex] == Character.valueOf('*') ||
					data[currentIndex] == Character.valueOf('/') ||
					data[currentIndex] == Character.valueOf('^')) {
					
				token = new Token(TokenType.OPERATOR, data[currentIndex]);
				currentIndex++;
				return token;
			}
			
			if (data[currentIndex] == '@') {
				StringBuilder sb = new StringBuilder();
				currentIndex++;
				while (currentIndex < data.length && (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex]) || data[currentIndex] == '_')) {
					sb.append(data[currentIndex]);
					currentIndex++;
				}
				if (sb.isEmpty()) throw new LexerException("Ime funkcije nije valjano.");
				token = new Token(TokenType.FUNCTION, sb.toString());
				return token;
			}
			
			if (data[currentIndex] == '"') {
				currentIndex++;
				StringBuilder sb = new StringBuilder();
				while (data[currentIndex] != '"') {
					if (currentIndex >= data.length) {
						throw new LexerException();
					}
					if (data[currentIndex] == '\\') {
						currentIndex++;
						if (currentIndex >= data.length || (data[currentIndex] != '\\' && data[currentIndex] != '\"' && data[currentIndex] != 'n' && data[currentIndex] != 'r' && data[currentIndex] != 't'))
							throw new LexerException();
						sb.append(data[currentIndex]);
					}
					sb.append(data[currentIndex]);
					currentIndex++;
				} 
				currentIndex++;
				token = new Token(TokenType.STRING, sb.toString());
				return token;
			}
			
			if (Character.isLetter(data[currentIndex])) {
				StringBuilder sb = new StringBuilder();
				sb.append(data[currentIndex]);
				currentIndex++;
				while (currentIndex < data.length) {
					char c = data[currentIndex];
					if (c != ' ' && c != '\t' && c != '\r' && c != '\n' && c != '\\' && (
						Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex]) || data[currentIndex] == '_')) {
						sb.append(c);
						currentIndex++;
					} else {
						break;
					}
				}
				token = new Token(TokenType.VARIABLE, sb.toString());
				return token; 
			}
			
			throw new LexerException();
			
		}
		
	}
	
	/**
	 * Metoda dohvaca zadnji generirani token. Uzastopnim pozivanjem ne pokrece se generiranje sljedeceg tokena.
	 * @return zadnji generirani token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Dohvaca trenutno stanje leksera.
	 * @return stanje leksera
	 */
	public LexerState getState() {
		return this.state;
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