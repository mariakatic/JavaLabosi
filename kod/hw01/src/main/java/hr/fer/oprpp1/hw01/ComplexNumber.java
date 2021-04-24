package hr.fer.oprpp1.hw01;

/**
 * Razred predstavlja nepromjenjivi kompleksni broj.
 * 
 * @author Maria
 *
 */
public class ComplexNumber {
	
	private double real;
	private double imag;
	
	/**
	 * Stvara novi komleksni broj. 
	 * @param real realni dio kompleksnog broja
	 * @param imag imaginarni dio kompleksnog broja
	 */
	public ComplexNumber(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}
	
	/**
	 * Stvara novi kompleksni broj kojem je imaginarni dio jednak 0.
	 * @param real realni dio kompleksnog broja
	 * @return novi kompleksni broj
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Stvara novi kompleksni broj kojem je realni dio jednak 0.
	 * @param imaginary imaginarni dio kompleksnog broja
	 * @return novi kompleksni broj
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Stvara novi komplekni broj pomocu duljine i kuta vektora.
	 * @param magnitude duljina vektora
	 * @param angle kut koji vektor zatvara s pozitivnim dijelom osi x
	 * @return novi kompleksni broj s izracunatom realnom i imaginarnom vrijednoscu
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	/**
	 * Pretvara string u kompleksan broj.
	 * @param s string koji je potrebno pretvoriti u kompleksan broj
	 * @return novi kompleksni broj
	 */
	public static ComplexNumber parse(String s) {
		
		//npr 4, 5 
		if (!s.contains("i")) {
			Double d = Double.parseDouble(s);
			return new ComplexNumber(d, 0);
		} 
		
		//npr 3i, 9i
		if (!(s.contains("+") || s.contains("-"))) {
			s = s.replace("i", "");
			Double d = Double.parseDouble(s);
			return new ComplexNumber(0, d);
		}
		
		//npr 3+2i, -4+8i
		if (s.contains("+")) {
			String[] tokens = s.split("\\+");
			Double r = Double.parseDouble(tokens[0]);
			tokens[1] = tokens[1].replace("i", "");
			Double im = Double.parseDouble(tokens[1]);
			return new ComplexNumber(r, im);
		}
		
		String[] tokensM = s.split("-");
		
		//npr -4-8i, -1-7i
		if (tokensM.length == 3) {
			Double r = Double.parseDouble(tokensM[1]) * -1;
			tokensM[2] = tokensM[2].replace("i", "");
			Double im = Double.parseDouble(tokensM[2]) * -1;
			return new ComplexNumber(r, im);
		}
		
		//npr -4i, -2i
		if (s.startsWith("-")) {
			s = s.replace("i", "");
			Double im = Double.parseDouble(s);
			return new ComplexNumber(0, im);
			
		//npr 3-2i, 9-4i
		} else {
			Double r = Double.parseDouble(tokensM[0]);
			tokensM[1] = tokensM[1].replace("i", "");
			Double im = Double.parseDouble(tokensM[1]) * -1;
			return new ComplexNumber(r, im);
		}
		
	}
	
	/**
	 * Vraca realni dio komleksnog broja.
	 * @return realni dio komleksnog broja
	 */
	public double getReal() {
		return this.real;
	}
	
	/**
	 * Vraca imaginarni dio komleksnog broja.
	 * @return imaginarni dio komleksnog broja
	 */
	public double getImaginary() {
		return this.imag;
	}
	
	/**
	 * Vraca udaljenost kompleksnog broja od ishodista koordinatnog sustava.
	 * @return udaljenost kompleksnog broja od ishodista koordinatnog sustava
	 */
	public double getMagnitude() {
		return Math.sqrt(this.getReal()*this.getReal() + this.getImaginary()*this.getImaginary());
	}
	
	/**
	 * Vraca kut koji vektor kompleksnog broja zatvara sa pozitivnim dijelom osi x.
	 * @return kut kompleksnog broja (u radijanima)
	 */
	public double getAngle() {
		
		//kompleksni brojevi za koje se ne moze izracunati arkus tangens
		if (this.getReal() == 0) {
			if (this.getImaginary() > 0) return Math.PI / 2;
			else if(this.getImaginary() < 0) return Math.PI * 3 / 2;
			else return 0;
		}
		
		Double arTang = Math.atan(this.getImaginary() / this.getReal());
		
		if (this.getReal() >= 0 && this.getImaginary() >= 0) 
			return arTang;
		else if (this.getReal() >= 0 && this.getImaginary() < 0) 
			return arTang + 2*Math.PI;
		else
			return arTang + Math.PI;
	}
	
	/**
	 * Metoda zbraja dva kompleksna broja.
	 * @param c kompleksni broj koji se dodaju trenutnom kompleksnom broju
	 * @return novi kompleksni broj koji je jednak zbroju prethodna dva
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.getReal() + c.getReal(), this.getImaginary() + c.getImaginary());
	}
	
	/**
	 * Metoda oduzima dva kompleksna broja.
	 * @param c kompleksni broj koji se oduzima od trenutnog kompleksnog broja
	 * @return novi kompleksni broj koji je jednak razlici prethodna dva
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.getReal() - c.getReal(), this.getImaginary() - c.getImaginary());
	}
	
	/**
	 * Metoda mnozi dva kompleksna broja.
	 * @param c kompleksni broj koji se mnozi s trenutnim kompleksnim brojem
	 * @return novi kompleksni broj koji je jednak umnosku prethodna dva
	 */
	public ComplexNumber mul(ComplexNumber c) {
		Double real = this.getReal()*c.getReal() - this.getImaginary()*c.getImaginary();
		Double imag = this.getReal()*c.getImaginary() + this.getImaginary()*c.getReal();
		return new ComplexNumber(real, imag);
	}
	
	/**
	 * Metoda dijeli dva kompleksna broja.
	 * @param c kompleksni broj koji dijeli trenutni kompleksni broj
	 * @return novi kompleksni broj koji je rezultat dijeljenja prethodna dva
	 */
	public ComplexNumber div(ComplexNumber c) {
		if (c.getReal() == 0 && c.getImaginary() == 0) throw new IllegalArgumentException("Nedopusteno dijeljenje s nulom.");
		
		Double num1 = this.getReal() * c.getReal() + this.getImaginary() * c.getImaginary();
		Double num2 = this.getImaginary() * c.getReal() - this.getReal() * c.getImaginary();
		Double num3 = c.getReal() * c.getReal() + c.getImaginary() * c.getImaginary();
		return new ComplexNumber(num1 / num3, num2 / num3);
	}
	
	/**
	 * Metoda potencira zadani kompleksni broj na potenciju n.
	 * @param n eksponent u potenciranju
	 * @return novi kompleksni broj koji je jednak trenutnom kompleksnom broju na n-tu potenciju
	 * @throws IllegalArgumentException ako je eksponent manji od 0
	 */
	public ComplexNumber power(int n) {
		if (n < 0) throw new IllegalArgumentException("Eksponent mora biti veci od nule.");
		
		double newMagn = Math.pow(this.getMagnitude(), n);
		double newAng = this.getAngle() * n;
		double newReal = newMagn * Math.cos(newAng);
		double newImag = newMagn * Math.sin(newAng);
		
		return new ComplexNumber(newReal, newImag);
	}
	
	/**
	 * Metoda vraca n-ti korijen trenutnog kompleksnog broja.
	 * @param n eksponent korijena
	 * @return novi kompleksni broj koji je jednak n-tom korijenu trenutnog kompleksnog broja
	 * @throws NullPointerException ako je n manji od 0
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) throw new IllegalArgumentException("Eksponent korijena ne smije biti manji ni jednak nuli.");
		
		ComplexNumber[] res = new ComplexNumber[n];
		
		Double newMagn = Math.pow(this.getMagnitude(), 1.0/n);
		
		for (int i = 0; i < n; i++) {
			Double newAng = (this.getAngle() + 2.0*i*Math.PI) / n;
			Double newReal = newMagn * Math.cos(newAng);
			Double newImag = newMagn * Math.sin(newAng);
			res[i] = new ComplexNumber(newReal, newImag);
		}
		
		return res;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if (this.getReal() == 0 && this.getImaginary() == 0) 
			sb.append("0");
		else if (this.getReal() == 0) {
			if (this.getImaginary() == 1) sb.append("i");
			else if (this.getImaginary() == -1) sb.append("-").append("i");
			else sb.append(this.getImaginary()).append("i");
		}
		else if (this.getImaginary() == 0)
			sb.append(this.getReal());
		else {
			sb.append(this.getReal());
			if (this.getImaginary() > 0) {
				if (this.getImaginary() == 1) sb.append("+").append("i");
				else sb.append("+").append(this.getImaginary()).append("i");
			} else {
				if (this.getImaginary() == -1) sb.append("-").append("i");
				else sb.append(this.getImaginary()).append("i");
			}
		}
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imag);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		if (!(Math.abs(imag - other.imag) < 1E-6))
			return false;
		if (!(Math.abs(real - other.real) < 1E-6))
			return false;
		return true;
	}


}
