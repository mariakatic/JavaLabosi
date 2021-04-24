package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * Razred modelira polinom nad kompleksnim brojevima.
 * @author Maria
 *
 */
public class ComplexRootedPolynomial {
	
	/**
	 * konstanta kojom se mnozi polinom
	 */
	private Complex constant;
	
	/**
	 * korijeni (nultocke) polinoma
	 */
	private Complex[] roots;
	
	/**
	 * Stvara novi polinom.
	 * @param constant konstanta kojom se mnozi polinom
	 * @param roots nultocke polinoma
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = Arrays.copyOf(roots, roots.length);
	}
	
	/**
	 * Metoda racuna koju vrijednost ima trenutni polinom u tocki z.
	 * @param z kompleksni broj za koji se racuna vrijednost polinoma
	 * @return novi kompleksni broj jednak vrijednosti polinoma u tocki z
	 */
	public Complex apply(Complex z) {
		Complex c = constant;
		
		if (roots.length != 0) {
			for (int i = 0; i < roots.length; i++) {
				c = c.multiply(z.sub(roots[i]));
			}
		}
		
		return c;
	
	}
	
	/**
	 * Metoda stvara kompleksni polinom na temelju zadanog polinoma pomocu nultocaka.
	 * @return novi kompleksni polinom
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] factors = new Complex[roots.length + 1];
		Arrays.fill(factors, Complex.ZERO);
		factors[roots.length] = constant;
		
		for (int i = 1; i < roots.length + 1; i++) {
			for (int j = roots.length - i; j < roots.length + 1; j++) {
				if (j != 0)
					factors[j-1] = factors[j-1].add(factors[j].multiply(roots[i - 1].negate()));
			}
		}
		
		return new ComplexPolynomial(factors);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(constant).append("*");
		for (int i = 0; i < roots.length; i++) {
			sb.append("(z-" + roots[i] + ")");
			if (i != roots.length-1)
				sb.append("*");
		}
		
		return sb.toString();
	}
	
	/**
	 * Metoda pronalazi indeks najblizeg korijena za zadani kompleksni broj z koji je unutar praga.
	 * @param z kompleksni broj
	 * @param treshold prag
	 * @return indeks korijena polinoma
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int idx = -1;
		Complex dist = z.sub(roots[0]);
		for (int i = 0; i < roots.length; i++) {
			if (z.sub(roots[i]).module() <= dist.module() && Math.abs(z.sub(roots[i]).module()) <= treshold) {
				dist = z.sub(roots[i]);
				idx = i;
			}
		}
		
		return idx;
	}

}
