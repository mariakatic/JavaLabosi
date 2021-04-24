package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;

public class LSystemBuilderImplTest {
	
	@Test
	public void testGenerate0() {
		LSystemBuilderImpl builder = new LSystemBuilderImpl();
		LSystem lsys = builder.setAxiom("F").registerProduction('F', "F+F--F+F").build();
		String s = lsys.generate(0);
		assertEquals("F", s);
	}
	
	@Test
	public void testGenerate1() {
		LSystemBuilderImpl builder = new LSystemBuilderImpl();
		LSystem lsys = builder.setAxiom("F").registerProduction('F', "F+F--F+F").build();
		String s = lsys.generate(1);
		assertEquals("F+F--F+F", s);
	}
	
	@Test
	public void testGenerate2() {
		LSystemBuilderImpl builder = new LSystemBuilderImpl();
		LSystem lsys = builder.setAxiom("F").registerProduction('F', "F+F--F+F").build();
		String s = lsys.generate(2);
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", s);
	}

}
