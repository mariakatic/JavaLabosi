package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Razred predstavlja naredbu koja mijenja boju kornjace.
 * @author Maria
 *
 */
public class ColorCommand implements Command {
	
	private Color color;
	
	/**
	 * U trenutno stanje kornjace zapisuje predanu boju.
	 * @param color predana boja
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}
