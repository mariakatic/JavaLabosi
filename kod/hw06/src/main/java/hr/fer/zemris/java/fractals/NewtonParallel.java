package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Potpora za prikaz Newtonovog fraktala uz pomoc vise dretvi.
 * @author Maria
 *
 */
public class NewtonParallel {
	
	private static int brojTraka;
	private static int brojRadnika;
	private static boolean flagTraka = false;
	private static boolean flagRadnik = false;
	
	static ComplexRootedPolynomial rootedPolynomial;
	
	public static void main(String[] args) {
		try {
			parseArguments(args);
		} catch (IllegalArgumentException ex) {
			System.out.println("Neispravno zadani argumenti.");
			return;
		}
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Number of threads: " + brojRadnika);
		System.out.println("Number of tracks: " + brojTraka);
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
				sc.close();
				return;
			}
		}
		sc.close();
		Complex[] complRoots = new Complex[roots.size()];
		for (int i = 0; i < roots.size(); i++) {
			complRoots[i] = roots.get(i);
		}
		
		rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, complRoots);
		FractalViewer.show(new MojProducer(brojRadnika, brojTraka));
	}
	
	/**
	 * Metoda parsira argumente zadane preko naredbenog retka.
	 * @param args argumenti zadani preko naredbenog retka
	 */
	public static void parseArguments(String[] args) {
		if (args.length == 0) {
			if (!flagRadnik) brojRadnika = Runtime.getRuntime().availableProcessors();
			if (!flagTraka) brojTraka = 4 * Runtime.getRuntime().availableProcessors();
			if (brojTraka < 1)
				throw new IllegalArgumentException();
			return;
			
		} else {
			if (args[0].startsWith("--workers=")) {
				if (!flagRadnik) {
					brojRadnika = Integer.parseInt(args[0].substring(10));
					flagRadnik = true;
					parseArguments(Arrays.copyOfRange(args, 1, args.length));
					return;
				}
			} else if (args[0].startsWith("--tracks=")) {
				if (!flagTraka) {
					brojTraka = Integer.parseInt(args[0].substring(9));
					flagTraka = true;
					parseArguments(Arrays.copyOfRange(args, 1, args.length));
					return;
				}
			} else if (args[0].equals("-w")) {
				if (args.length > 1 && !flagRadnik) {
					brojRadnika = Integer.parseInt(args[1]);
					flagRadnik = true;
					parseArguments(Arrays.copyOfRange(args, 2, args.length));
					return;
				} 
				
			} else if (args[0].equals("-t")) {
				if (args.length > 1 && !flagTraka) {
					brojTraka = Integer.parseInt(args[1]);
					flagTraka = true;
					parseArguments(Arrays.copyOfRange(args, 2, args.length));
					return;
				} 
			} 
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Posao koji svaka dretva mora napraviti.
	 * @author Maria
	 *
	 */
	public static class PosaoIzracuna implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		public static PosaoIzracuna NO_JOB = new PosaoIzracuna();
		
		private PosaoIzracuna() {
		}
		
		public PosaoIzracuna(double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel) {

			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
		}
		
		@Override
		public void run() {
			
			Calculate.calculate(reMin, reMax, imMin, imMax, width, height, m, yMin, yMax, data, cancel, rootedPolynomial);
			
		}
	}

}
