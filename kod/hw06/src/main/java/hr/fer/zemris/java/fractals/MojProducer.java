package hr.fer.zemris.java.fractals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.NewtonParallel.PosaoIzracuna;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Razred koji opisuje objekte sposobne generirati podatke za vizualizaciju 
 * fraktala. Očekuje se da se postupak temelji na vizualizaciji koja odgovara nekom dijelu kompleksne ravnine.
 * @author Maria
 *
 */
public class MojProducer implements IFractalProducer {
	
	private int brojRadnika;
	private int brojTraka;
	
	public MojProducer(int brojRadnika, int brojTraka) {
		this.brojRadnika = brojRadnika;
		this.brojTraka = brojTraka;
	}

	/**
	 * Metoda će se pozvati svaki puta kada je potrebno generirati podatke za fraktal.
	 */
	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax,
			int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
		System.out.println("Zapocinjem izracun...");
		int m = 16 * 16 * 16;
		short[] data = new short[width * height];
		if (brojTraka > height) brojTraka = height;
		int brojYPoTraci = height / brojTraka;
		
		final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

		Thread[] radnici = new Thread[brojRadnika];
		for(int i = 0; i < brojRadnika; i++) {
			radnici[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						PosaoIzracuna p = null;
						try {
							p = queue.take();
							if(p==PosaoIzracuna.NO_JOB) break;
						} catch (InterruptedException e) {
							continue;
						}
						p.run();
					}
				}
			});
		}
		for(int i = 0; i < radnici.length; i++) {
			radnici[i].start();
		}
		
		for(int i = 0; i < brojTraka; i++) {
			int yMin = i*brojYPoTraci;
			int yMax = (i+1)*brojYPoTraci-1;
			if(i==brojTraka-1) {
				yMax = height-1;
			}
			PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel);
			while(true) {
				try {
					queue.put(posao);
					break;
				} catch (InterruptedException e) {
				}
			}
		}
		for(int i = 0; i < radnici.length; i++) {
			while(true) {
				try {
					queue.put(PosaoIzracuna.NO_JOB);
					break;
				} catch (InterruptedException e) {
				}
			}
		}
		
		for(int i = 0; i < radnici.length; i++) {
			while(true) {
				try {
					radnici[i].join();
					break;
				} catch (InterruptedException e) {
				}
			}
		}
		
		System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
		observer.acceptResult(data, (short)(NewtonParallel.rootedPolynomial.toComplexPolynom().order()+1), requestNo);
	}
}
