package hr.fer.zemris.java.fractals;

import hr.fer.zemris.math.Complex;

/**
 * Razred se koristi prilikom parsiranja kompleksnih brojeva.
 * @author Maria
 *
 */
public class Parser {
	
	/**
	 * Metoda koja parsira kompleksni broj
	 * @return stvoreni kompleksni broj
	 * @throws NumberFormatException ako se linija ne moze parsirati
	 */
	public static Complex parse(String s) {
		
		if (s.endsWith("i"))
			s += "1";
		
		//npr 4, 5 
		if (!s.contains("i")) {
			Double d = Double.parseDouble(s);
			return new Complex(d, 0);
		} 
		
		//npr 3i, 9i
		if (!(s.contains("+") || s.contains("-"))) {
			s = s.replace("i", "");
			Double d = Double.parseDouble(s);
			return new Complex(0, d);
		}
		
		//npr 3+2i, -4+8i
		if (s.contains("+")) {
			String[] tokens = s.split("\\+");
			Double r = Double.parseDouble(tokens[0]);
			tokens[1] = tokens[1].replace("i", "");
			Double im = Double.parseDouble(tokens[1]);
			return new Complex(r, im);
		}
		
		String[] tokensM = s.split("-");
		
		//npr -4-8i, -1-7i
		if (tokensM.length == 3) {
			Double r = Double.parseDouble(tokensM[1]) * -1;
			tokensM[2] = tokensM[2].replace("i", "");
			Double im = Double.parseDouble(tokensM[2]) * -1;
			return new Complex(r, im);
		}
		
		//npr -4i, -2i
		if (s.startsWith("-")) {
			s = s.replace("i", "");
			Double im = Double.parseDouble(s);
			return new Complex(0, im);
			
		//npr 3-2i, 9-4i
		} else {
			Double r = Double.parseDouble(tokensM[0]);
			tokensM[1] = tokensM[1].replace("i", "");
			Double im = Double.parseDouble(tokensM[1]) * -1;
			return new Complex(r, im);
		}
		
	}
}
