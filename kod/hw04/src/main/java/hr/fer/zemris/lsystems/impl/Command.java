package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Razred predstavlja opcenitu naredbu koja se vrsi nad stanjem kornjace.
 * @author Maria
 *
 */
public interface Command {
	
	/**
	 * Izvrsava zadanu naredbu.
	 * @param ctx kontekst na kojem se nalaze stanja kornjace
	 * @param painter sluzi za crtanje po ekranu
	 */
	void execute(Context ctx, Painter painter);

}
