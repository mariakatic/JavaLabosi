package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.*;
import org.junit.jupiter.api.Test;

public class CalcLayoutTest {
	
	@Test
	public void invalidRow() {
		JPanel p = new JPanel(new CalcLayout());
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "0,1"));
	}
	
	@Test
	public void invalidColumn() {
		JPanel p = new JPanel(new CalcLayout());
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), "1,2"));
	}
	
	@Test
	public void componentAlreadyExists() {
		JPanel p = new JPanel(new CalcLayout());
		p.add(new JLabel("x"), "1,1");
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("y"), "1,1"));
	}

}
