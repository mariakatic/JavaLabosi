package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Razred predstavlja naredbu koja trenutno stanje kornjace modificira tako da vektor smjera rotira za zadani kut.
 * @author Maria
 *
 */
public class RotateCommand implements Command {
	
	private double angle;
	
	/**
	 * Vektor smjera kornjace koja predstavlja trenutno stanje rotira se za kut angle.
	 * @param angle kut za koji se rotira vektor smjera kornjace
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getDirection().rotate(angle * Math.PI / 180.0);
	}

}
