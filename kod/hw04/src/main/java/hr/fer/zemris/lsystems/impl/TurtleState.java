package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.lsystems.impl.collections.Vector2D;

/**
 * Razred predstavlja stanje kornjace koja se nalazi negdje na ekranu i moze se kretati po njemu.
 * @author Maria
 *
 */
public class TurtleState {
	
	private Vector2D currPosition;
	private Vector2D direction;
	private Color color;
	private double shiftLength;
	
	/**
	 * Stvara novo stanje kornjace.
	 * @param currPosition trenutna pozicija na kojoj se kornjaca nalazi
	 * @param direction smjer u kojem kornjaca gleda
	 * @param color boja kojom kornjaca crta
	 * @param shiftLength efektivna duljina pomaka kornjace
	 */
	public TurtleState(Vector2D currPosition, Vector2D direction, Color color, double shiftLength) {
		super();
		this.currPosition = currPosition;
		this.direction = direction;
		this.color = color;
		this.shiftLength = shiftLength;
	}
	
	/**
	 * Metoda stvara novi objekt s kopijom trenutnog stanja.
	 * @return
	 */
	public TurtleState copy() {
		return new TurtleState(currPosition.copy(), direction.copy(), color, shiftLength);
	}

	/**
	 * Vraca trenutnu poziciju na kojoj se kornjaca nalazi
	 * @return trenutnu poziciju na kojoj se kornjaca nalazi
	 */
	public Vector2D getCurrPosition() {
		return currPosition;
	}

	/**
	 * Postavlja trenutnu poziciju na kojoj se kornjaca nalazi na currPosition
	 * @param currPosition nova pozicija kornjace
	 */
	public void setCurrPosition(Vector2D currPosition) {
		this.currPosition = currPosition;
	}

	/**
	 * Dohvaca smjer u kojem kornjaca gleda.
	 * @return smjer u kojem kornjaca gleda
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Postavlja smjer u kojem kornjaca gleda na direction
	 * @param direction novi smjer u kojem kornjaca gleda
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Dohvaca boju kojom kornjaca crta
	 * @return boju kojom kornjaca crta
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Postavlja boju kojom kornjaca crta na color.
	 * @param color nova boja kojom kornjaca crta
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Dohvaca efektivnu duljinu pomaka kornjace.
	 * @return efektivnu duljinu pomaka kornjace
	 */
	public double getShiftLength() {
		return shiftLength;
	}

	/**
	 * Postavlja efektivnu duljinu pomaka kornjace na shiftLength.
	 * @param shiftLength nova efektivna duljina pomaka kornjace
	 */
	public void setShiftLength(double shiftLength) {
		this.shiftLength = shiftLength;
	}
	
	
	
	

}
