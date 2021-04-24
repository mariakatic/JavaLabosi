package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Enumeracija definira nacin obradivanja teksta.
 * @author Maria
 *
 */
public enum LexerState {
	
	/**
	 * Nacin obrade unutar oznke {$ ... $}
	 */
	TAG,
	
	/**
	 * Nacin obrade teksta.
	 */
	TEXT

}