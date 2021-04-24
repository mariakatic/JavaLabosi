package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.collections.Vector2D;

/**
 * Razred predstavlja naredbu koja mijenja poziciju kornjace.
 * @author Maria
 *
 */
public class SkipCommand implements Command {
	
	private double step;
	
	/**
	 * Mijenja poziciju kornjace.
	 * @param step broj koraka za koji se kornjaca mice
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currState = ctx.getCurrentState();
		ctx.popState();
		double x = step * currState.getDirection().getX() * currState.getShiftLength();
		double y = step * currState.getDirection().getY() * currState.getShiftLength();
		Vector2D newPosition = new Vector2D(x, y);
		Vector2D vec = currState.getCurrPosition().added(newPosition);
		TurtleState state = new TurtleState(vec, currState.getDirection(), currState.getColor(), currState.getShiftLength());
		ctx.pushState(state);
	}

}
