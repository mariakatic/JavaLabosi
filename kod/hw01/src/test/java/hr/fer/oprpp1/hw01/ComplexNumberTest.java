package hr.fer.oprpp1.hw01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ComplexNumberTest {
	
	@Test
	public void testFromReal() {
		ComplexNumber result = ComplexNumber.fromReal(2);
		assertEquals(result, new ComplexNumber(2, 0));
	}
	
	@Test
	public void testFromImaginary() {
		ComplexNumber result = ComplexNumber.fromImaginary(2);
		assertEquals(result, new ComplexNumber(0, 2));
	}
	
	@Test
	public void testFromMagnitudeAndAngle() {
		ComplexNumber result = ComplexNumber.fromMagnitudeAndAngle(2, Math.PI*2);
		ComplexNumber expected = new ComplexNumber(2, 0);
		assertEquals(result, expected);
	}
	
	@Test
	public void testParseStringReal() {
		ComplexNumber result = ComplexNumber.parse("4");
		ComplexNumber expected = new ComplexNumber(4, 0);
		assertEquals(result, expected);
	}
	
	@Test
	public void testParseStringImagPos() {
		ComplexNumber result = ComplexNumber.parse("3i");
		ComplexNumber expected = new ComplexNumber(0, 3);
		assertEquals(result, expected);
	}
	
	@Test
	public void testParseStringRealImag() {
		ComplexNumber result = ComplexNumber.parse("-3+2i");
		ComplexNumber expected = new ComplexNumber(-3, 2);
		assertEquals(result, expected);
	}
	
	@Test
	public void testParseStringRealImagNeg() {
		ComplexNumber result = ComplexNumber.parse("-4-8i");
		ComplexNumber expected = new ComplexNumber(-4, -8);
		assertEquals(result, expected);
	}
	
	@Test
	public void testParseStringImagNeg() {
		ComplexNumber result = ComplexNumber.parse("-4i");
		ComplexNumber expected = new ComplexNumber(0, -4);
		assertEquals(result, expected);
	}
	
	@Test
	public void testParseStringRealImagPosNeg() {
		ComplexNumber result = ComplexNumber.parse("9-4i");
		ComplexNumber expected = new ComplexNumber(9, -4);
		assertEquals(result, expected);
	}
	
	@Test
	public void testGetReal() {
		ComplexNumber result = new ComplexNumber(3, 4);
		assertEquals(3, result.getReal());
	}
	
	@Test
	public void testGetImaginary() {
		ComplexNumber result = new ComplexNumber(3, 4);
		assertEquals(4, result.getImaginary());
	}
	
	@Test
	public void testGetMagnitude() {
		ComplexNumber result = new ComplexNumber(3, 4);
		assertEquals(5, result.getMagnitude());
	}
	
	@Test
	public void testGetAngle() {
		ComplexNumber result = new ComplexNumber(2, -2);
		assertEquals(Math.PI * 7 / 4, result.getAngle());
	}
	
	@Test
	public void testAngleNinetyDegrees() {
		ComplexNumber c = new ComplexNumber(0, 1);
		assertEquals(c.getAngle(), Math.PI/2);
	}
	
	@Test
	public void testAddComplex() {
		ComplexNumber c1 = new ComplexNumber(3, 4);
		ComplexNumber c2 = new ComplexNumber(5, 6);
		ComplexNumber result = c1.add(c2);
		assertEquals(result, new ComplexNumber(8, 10));
	}
	
	@Test
	public void testSubComplex() {
		ComplexNumber c1 = new ComplexNumber(3, 4);
		ComplexNumber c2 = new ComplexNumber(5, 6);
		ComplexNumber result = c1.sub(c2);
		assertEquals(result, new ComplexNumber(-2, -2));
	}
	
	@Test
	public void testMulComplex() {
		ComplexNumber c1 = new ComplexNumber(3, 4);
		ComplexNumber c2 = new ComplexNumber(5, 6);
		ComplexNumber result = c1.mul(c2);
		assertEquals(result, new ComplexNumber(-9, 38));
	}
	
	@Test
	public void testDivComplex() {
		ComplexNumber c1 = new ComplexNumber(3, 1);
		ComplexNumber c2 = new ComplexNumber(2, 2);
		ComplexNumber result = c1.div(c2);
		assertEquals(result, new ComplexNumber(1, -0.5));
	}
	
	@Test
	public void testDividingWithZero() {
		ComplexNumber c1 = new ComplexNumber(3, 4);
		ComplexNumber c2 = new ComplexNumber(0, 0);
		assertThrows(IllegalArgumentException.class, () -> c1.div(c2));
	}
	
	@Test
	public void testPowerComplex() {
		ComplexNumber c1 = new ComplexNumber(3, 4);
		ComplexNumber result = c1.power(3);
		assertEquals(result, new ComplexNumber(-117, 44));
	}
	
	@Test
	public void testPowerNegativeExponent() {
		ComplexNumber c = new ComplexNumber(3, 4);
		assertThrows(IllegalArgumentException.class, () -> c.power(-1));
	}
	
	@Test
	public void testRootComplex() {
		ComplexNumber c1 = new ComplexNumber(-1, 0);
		ComplexNumber result = c1.root(2)[0];
		ComplexNumber expected = new ComplexNumber(Math.cos(Math.PI/2), Math.sin(Math.PI/2));
		assertEquals(expected, result);
	}
	
	@Test
	public void testRootNegativeExponent() {
		ComplexNumber c = new ComplexNumber(3, 4);
		assertThrows(IllegalArgumentException.class, () -> c.root(-1));
	}
	
	@Test
	public void testToStringComplex() {
		ComplexNumber c = new ComplexNumber(-51, 25);
		assertTrue(c.toString().equals("-51.0+25.0i"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
