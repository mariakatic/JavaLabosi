package hr.fer.zemris.java.gui.calc;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;
import javax.swing.JButton;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

@SuppressWarnings("unused")
public abstract class BinaryButton extends JButton {

	private static final long serialVersionUID = 1L;
	private CalcModel model;
	private String function;
	
	public BinaryButton(CalcModel model, String function) {
		this.model = model;
		this.function = function;
		this.setText(function);
		this.setBackground(getBackground());
		this.setPreferredSize(new Dimension(60, 60));
		this.setOpaque(true);
		this.addActionListener(e -> {
			double value = model.getValue();
			model.setActiveOperand(value);
			model.setPendingBinaryOperation(this.operation());
			model.clear();
		});
	}

	public abstract DoubleBinaryOperator operation();



}
