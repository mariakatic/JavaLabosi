package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Razred predstavlja naredbu koja stanje s vrha stoga na kojem se nalaze stanja kornjace kopira i kopiju stavlja na stog.
 * @author Maria
 *
 */
public class PushCommand implements Command {

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState().copy();
		ctx.pushState(state);
	}

}
