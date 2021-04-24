package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * Razred predstavlja polinom nad komplekssnim brojevima.
 * @author Maria
 *
 */
public class ComplexPolynomial {
	
	/**
	 * faktori u polinomu
	 */
	private Complex[] factors;
	
	/**
	 * Stvara novi polinom s danim faktorima.
	 * @param factors faktori polinoma
	 */
	public ComplexPolynomial(Complex ...factors) {
		this.factors = Arrays.copyOf(factors, factors.length);
	}
	
	/**
	 * Vraca faktore polinoma.
	 * @return polje faktora
	 */
	public Complex[] getFactors() {
		return factors;
	}

	/**
	 * Vraca stupanj polinoma.
	 * @return stupanj polinoma
	 */
	public short order() {
		return (short) (factors.length-1);
	}
	
	/**
	 * Metoda mnozi dva polinoma.
	 * @param p polinom s kojim se mnozi zadani polinom.
	 * @return novi polinom jednak umnsoku dva polinoma
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] mulFactors = new Complex[this.getFactors().length + p.getFactors().length - 1];
		Arrays.fill(mulFactors, Complex.ZERO);
		for (int i = 0; i < this.getFactors().length; i++) {
			for (int j = 0; j < p.getFactors().length; j++) {
				mulFactors[i + j] = mulFactors[i + j].add(this.getFactors()[i].multiply(p.getFactors()[j]));
				System.out.println(mulFactors[i + j]);
			}
		}
		return new ComplexPolynomial(mulFactors);
	}
	
	/**
	 * Metoda derivira trenutni polinom.
	 * @return novi polinom jednak deriviranom trenutnom polinomu
	 */
	public ComplexPolynomial derive() {
		Complex[] derived = new Complex[factors.length-1];
		for (int i = 1; i < factors.length; i++) {
			double reDe = factors[i].getRe() * i;
			double imDe = factors[i].getIm() * i;
			derived[i-1] = new Complex(reDe, imDe);
		}
		return new ComplexPolynomial(derived);
	}
	
	/**
	 * Metoda racuna koju vrijednost ima trenutni polinom u tocki z.
	 * @param z kompleksni broj za koji se racuna vrijednost polinoma
	 * @return novi kompleksni broj jednak vrijednosti polinoma u tocki z
	 */
	public Complex apply(Complex z) {
		Complex res = factors[0];
		Complex pot = new Complex(1, 0);
		for (int i = 1; i < factors.length; i++) {
			pot = pot.multiply(z); 
			res = res.add(factors[i].multiply(pot));
		}
		return res;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = factors.length-1; i > 0; i--) {
			sb.append(factors[i]).append("*z");
			if (i != 1) sb.append("^");
			sb.append(i).append("+");
		}
		sb.append(factors[0]);
		return sb.toString();
	}
	



}
