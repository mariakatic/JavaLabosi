package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred predstavlja nepromjenjivi kompleksni broj.
 * 
 * @author Maria
 *
 */
public class Complex {
	
	private double re;
	private double im;
	
	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);
	
	public Complex() {
		this.re = 1;
		this.im = 1;
	}  
	

	/**
	 * Stvara novi komleksni broj. 
	 * @param re realni dio kompleksnog broja
	 * @param im imaginarni dio kompleksnog broja
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Metoda vraca realni dio kompleksnog broja.
	 * @return realni dio kompleksnog broja
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Metoda vraca imaginarni dio kompleksnog broja.
	 * @return imagnarni dio kompleksnog broja
	 */
	public double getIm() {
		return im;
	}

	/**
	 * Metoda racuna modul kompleksnog broja.
	 * @return modul kompleksnog broja
	 */
	public double module() {
		return Math.sqrt(Math.pow(re, 2) + Math.pow(im, 2));
	}
	
	/**
	 * Mnozi dva kompleksna broja.
	 * @param c kompleksni broj koji se mnozi s trenutnim kompleksnim brojem
	 * @return novi kompleksni broj koji je jednak umnosku dva kompleksna broja
	 */
	public Complex multiply(Complex c) {
		double real = re * c.getRe() - im * c.getIm();
		double imag = re * c.getIm() + im * c.getRe();
		return new Complex(real, imag);
	}
	
	/**
	 * Metoda dijeli dva kompleksna broja.
	 * @param c kompleksni broj s kojim se dijeli zadani kompleksni broj
	 * @return novi kompleksni broj
	 */
	public Complex divide(Complex c) {
		if (c.getRe() == 0 && c.getIm() == 0)
			throw new IllegalArgumentException("Nazivnik ne smije biti nula.");
		
		Double x1 = re * c.getRe() + im * c.getIm();
		Double x2 = im * c.getRe() - re * c.getIm();
		Double x3 = c.getRe() * c.getRe() + c.getIm() * c.getIm();
		return new Complex(x1/x3, x2/x3);
	}
	
	/**
	 * Zbraja dva kompleksna broja.
	 * @param c kompleksni broj koji se zbraja s trenutnim kompleksnim brojem
	 * @return novi kompleksni broj koji je jednak zbroju dva kompleksna broja
	 */
	public Complex add(Complex c) {
		return new Complex(re + c.getRe(), im + c.getIm());
	}
	
	/**
	 * Oduzima dva kompleksna broja.
	 * @param c kompleksni broj koji se oduzima od trenutnog kompleksnog broja
	 * @return novi kompleksni broj koji je jednak razlici dva kompleksna broja
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.getRe(), im - c.getIm());
	}
	
	/**
	 * Negira trenutni kompleksni broj
	 * @return novi kompleksni broj koji je jednak negiranom trenutnom kompl. broju
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}
	
	/**
	 * Metoda racuna kut zadanog komplesnog broja.
	 * @return kut kompleksnog broja
	 */
	public double angle() {
		if (this.getRe() == 0) {
			if (this.getIm() > 0) return Math.PI / 2;
			else if(this.getIm() < 0) return Math.PI * 3 / 2;
			else return 0;
		}
		
		Double arTang = Math.atan(this.getIm() / this.getRe());
		if (this.getRe() >= 0 && this.getIm() >= 0) 
			return arTang;
		else if (this.getRe() >= 0 && this.getIm() < 0) 
			return arTang + 2*Math.PI;
		else
			return arTang + Math.PI;
	}
	
	/**
	 * Metoda racuna n-tu potenciju kompleksnog broja
	 * @param n stupanj potencije 
	 * @return novi kompleksni broj
	 */
	public Complex power(int n) {
		if (n < 0) throw new IllegalArgumentException("Eksponent mora biti veci ili jednak nuli.");
		
		double newMagn = Math.pow(this.module(), n);
		double newAng = this.angle() * n;
		double newReal = newMagn * Math.cos(newAng);
		double newImag = newMagn * Math.sin(newAng);
		
		return new Complex(newReal, newImag);
	}
	
	/**
	 * Metoda racuna n-ti korijen kompleksnog broja.
	 * @param n stupanj korijena
	 * @return novi kompleksni broj
	 */
	public List<Complex> root(int n) {
		if (n <= 0) throw new IllegalArgumentException("Eksponent korijena ne smije biti manji ni jednak nuli.");
		
		List<Complex> lista = new ArrayList<>();
		Double newMagn = Math.pow(this.module(), 1.0/n);
		for (int i = 0; i < n; i++) {
			Double newAng = (this.angle() + 2.0*i*Math.PI) / n;
			Double newReal = newMagn * Math.cos(newAng);
			Double newImag = newMagn * Math.sin(newAng);
			lista.add(new Complex(newReal, newImag));
		}
		return lista;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("(").append(this.getRe());
		if (this.getIm() >= 0) sb.append("+").append("i").append(this.getIm()).append(")");
		else sb.append("-").append("i").append(-this.getIm()).append(")");
		
		return sb.toString();
	}
	


}
