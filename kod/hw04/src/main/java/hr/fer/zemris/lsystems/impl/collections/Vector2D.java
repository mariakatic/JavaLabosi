package hr.fer.zemris.lsystems.impl.collections;

/**
 * Razred modelira 2D vektor.
 * @author Maria
 *
 */
public class Vector2D {
	
	/** x komponenta vektora */
	private double x;
	
	/** y komponenta vektora */
	private double y;
	

	/**
	 * Stvara novi vektor.
	 * @param x x komponenta vektora
	 * @param y y komponenta vektora
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Dohvaca x komponentu vektora.
	 * @return x komponentu vektora
	 */
	public double getX() {
		return x;
	}

	/**
	 * Dohvaca y komponentu vektora.
	 * @return y komponentu vektora
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Trenutni vektor uvecava se za predani vektor.
	 * @param offset vektor koji se dodaje trenutnom vektoru
	 */
	public void add(Vector2D offset) {
		x += offset.getX();
		y += offset.getY();
	}

	/**
	 * Trenutni vektor zbraja s vektorom offset.
	 * @param offset vektor s kojim se zbraja trenutni vektor
	 * @return novi vektor jednak zbroju trenutnog vektora i vektora offset
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}
	
	/**
	 * Rotira trenutni vektor za kut iznosa angle.
	 * @param angle kut za koji se rotira trenutni vektor
	 */
	public void rotate(double angle) {
		double newX = Math.cos(angle) * this.getX() - Math.sin(angle) * this.getY();
		double newY = Math.sin(angle) * this.getX() + Math.cos(angle) * this.getY();
		x = newX;
		y = newY;
	}
	
	/**
	 * Stvara novi vektor jednak trenutnom vektoru rotiranom za kut angle.
	 * @param angle kut za koji se rotira trenutni vektor
	 * @return novi vektor jednak trenutnom vektoru rotiranom za kut angle
	 */
	public Vector2D rotated(double angle) {
		Vector2D newVector = new Vector2D(this.getX(), this.getY());
		newVector.rotate(angle);
		return newVector;
	}
	
	/**
	 * Skalira trenutni vektor za iznos scale.
	 * @param scaler iznos kojim se skalira trenutni vektor
	 */
	public void scale(double scaler) {
		x = scaler * x;
		y = scaler * y;
	}
	
	/**
	 * Stvara novi vektor koji je jednak trenutnom vektoru skaliranom za scaler.
	 * @param scaler iznos kojim se skalira trenutni vektor
	 * @return novi vektor koji je jednak trenutnom vektoru skaliranom za scaler
	 */
	public Vector2D scaled(double scaler) {
		Vector2D newVector = new Vector2D(this.getX(), this.getY());
		newVector.scale(scaler);
		return newVector;
	}
	
	/**
	 * Stvara novi vektor kojem su komponente jednake trenutnom vektoru.
	 * @return novi vektor kojem su komponente jednake trenutnom vektoru
	 */
	public Vector2D copy() {
		Vector2D newVector = new Vector2D(this.getX(), this.getY());
		return newVector;
	}
	
	

}
