package hr.fer.zemris.java.fractals;

import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Calculate {
	
	private static final double rootTreshold = 0.002;
	private static final double convergenceTreshold = 0.001;
	
	/**
	 * Slijedna implementacija koja računa Newtonov fraktal. Omogućava da se u predano polje pohrane rezultati samo za zadani raspon y-koordinata (ostatak polja se ne dira).
	 * @param reMin minimalna vrijednost po realnoj osi
	 * @param reMax maksimalna vrijednost po realnoj osi
	 * @param imMin minimalna vrijednost po imaginarnoj osi
	 * @param imMax maksimalna vrijednost po imaginarnoj osi
	 * @param width širina zaslona na kojem se prikazuje fraktal
	 * @param height visina zaslona na kojem se prikazuje fraktal
	 * @param m broj pokušaja otkrivanja divergencije
	 * @param ymin y-linija od koje se popunjava polje (uključiva)
	 * @param ymax y-linija do koje se popunjava polje (uključiva)
	 * @param data polje u koje treba pohraniti rezultat
	 * @param cancel zastavica čije postavljanje na true označava da se izračun treba prekinuti
	 * @param rootedPolynomial polinom nad komplesnim brojevima
	 */
	public static void calculate(double reMin, double reMax, double imMin, 
			double imMax, int width, int height, int m, int ymin, int ymax, 
			short[] data, AtomicBoolean cancel, ComplexRootedPolynomial rootedPolynomial) {
	
		int offset = ymin * width;
		for(int y = ymin; y <= ymax; y++) {
			if(cancel.get()) break;
			for(int x = 0; x < width; x++) {
				double cre = x / (width-1.0) * (reMax - reMin) + reMin;
				double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
				Complex zn = new Complex(cre, cim);
				Complex zold, numerator, denominator, fraction;
				double module = 0;
				int iters = 0;
				do {
					numerator = rootedPolynomial.toComplexPolynom().apply(zn);
					denominator = rootedPolynomial.toComplexPolynom().derive().apply(zn);
					zold = zn;
					fraction = numerator.divide(denominator);
					zn = zn.sub(fraction);
					module = zold.sub(zn).module();
					iters++;
				} while(iters < m && module > convergenceTreshold);
				int index = rootedPolynomial.indexOfClosestRootFor(zn, rootTreshold);
				data[offset++]= (short) (index + 1);
			}
		}
	}

}
