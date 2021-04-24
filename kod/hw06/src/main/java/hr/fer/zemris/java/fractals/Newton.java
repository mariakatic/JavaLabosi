package hr.fer.zemris.java.fractals;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.*;

/**
 * Potpora za prikaz Newtonovog fraktala slijedno.
 * @author Maria
 *
 */
public class Newton {
	
	private static ComplexRootedPolynomial rootedPolynomial;
	
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		int idx = 1;
		String line;
		List<Complex> roots = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		while (true) {
			try {
			System.out.print("Root " + idx + "> ");
			line = sc.nextLine();
			if (line.equals("done")) {
				System.out.println("Image of fractal will appear shortly. Thank you.");
				break;
			}
			
			Complex c = Parser.parse(line);
			roots.add(c);
			idx++;
			} catch (IllegalArgumentException ex) {
				System.out.println("Unknown argument.");
				break;
			}
		}
		sc.close();
		Complex[] complRoots = new Complex[roots.size()];
		for (int i = 0; i < roots.size(); i++) {
			complRoots[i] = roots.get(i);
		}
		
		rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, complRoots);
		FractalViewer.show(new MojProducer());
	}
	
	public static class MojProducer implements IFractalProducer {
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			System.out.println("Zapocinjem izracun...");
			
			int m = 16*16*16;
			short[] data = new short[width * height];
			
			Calculate.calculate(reMin, reMax, imMin, imMax, width, height, m, 0, height-1, data, cancel, rootedPolynomial);
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(rootedPolynomial.toComplexPolynom().order()+1), requestNo);
		}
	}

}
