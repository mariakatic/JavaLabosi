package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Razred predstavlja naredbu koja efektivnu duljinu pomaka kornjace skalira s danim faktorom.
 * @author Maria
 *
 */
public class ScaleCommand implements Command {
	
	private double factor;
	
	/**
	 * Efektivnu duljinu pomaka trenutnog stanja kornjace skalira s danim faktorom.
	 * @param factor faktor koji skalira efektivnu duljinu pomaka
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		double current = ctx.getCurrentState().getShiftLength();
		ctx.getCurrentState().setShiftLength(factor * current);
	}

}
