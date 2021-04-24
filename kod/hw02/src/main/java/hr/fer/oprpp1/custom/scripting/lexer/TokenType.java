package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Enumeracija vrsta tokena.
 * @author Maria
 *
 */
public enum TokenType {
	
	/** Oznacava da vise nema tokena */
	EOF,
	
	/** Rijec */
	STRING,

	/** Konstanta tipa Double */
	CONST_DOUBLE,
	
	/** Konstanta tipa Integer */
	CONST_INTEGER,
	
	/** Naziv oznake */
	TAG,
	
	/** Simbol */
	OPERATOR,
	
	/** Funkcija */
	FUNCTION,
	
	/** Varijabla */
	VARIABLE,
	
	 /** Znak jednakosti */
	EQUAL

}

