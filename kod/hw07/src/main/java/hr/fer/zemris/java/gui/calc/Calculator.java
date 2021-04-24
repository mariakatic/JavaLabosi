package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class Calculator extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel ekran;
	private CalcModelImpl model;
	
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		setTitle("MyCalculator :)");
		pack();
	}
	
	public void error() {
		model.clearAll();
		ekran.setText("ERROR");
	}
	
	private void initGUI() {
		model = new CalcModelImpl(); 
		
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));

		ekran = new JLabel("", SwingConstants.RIGHT);
		ekran.setBackground(Color.YELLOW);
		ekran.setFont(ekran.getFont().deriveFont(30f));
		ekran.setOpaque(true);
		ekran.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		model.addCalcValueListener(m -> {
			ekran.setText(m.toString());
		});
		
		cp.add(ekran, new RCPosition(1, 1));
		
		JButton zero = button(String.valueOf(0));
		zero.setFont(zero.getFont().deriveFont(30f));
		zero.addActionListener(m -> {
			model.insertDigit(0);
		});
		cp.add(zero, new RCPosition(5, 3));
		
		int broj = 1;
		for (int i = 4; i > 1; i--) {
			for (int j = 3; j < 6; j++) {
				final int broj1 = broj;
				JButton numb = button(String.valueOf(broj1));
				numb.setFont(numb.getFont().deriveFont(30f));
				numb.addActionListener(m -> {
					model.insertDigit(broj1);
				});
				cp.add(numb, new RCPosition(i, j));
				broj++;
			}
		}
		
		JButton dot = button(".");
		dot.addActionListener(m -> {
			model.insertDecimalPoint();
		});
		cp.add(dot, new RCPosition(5, 5));
		
		JButton swap = button("+/-");
		swap.addActionListener(m -> {
			model.swapSign();
		});
		cp.add(swap, new RCPosition(5, 4));
		
		JButton clr = button("clr");
		clr.addActionListener(m -> {
			model.freezeValue(null);
			model.clear();
		});
		cp.add(clr, new RCPosition(1, 7));
		
		JButton reset = button("reset");
		reset.addActionListener(m -> {
			model.clearAll();
		});
		cp.add(reset, new RCPosition(2, 7));
		
		JButton push = button("push");
		push.addActionListener(m -> {
			model.push();
		});
		cp.add(push, new RCPosition(3, 7));
		
		JButton pop = button("pop");
		pop.addActionListener(m -> {
			model.pop();
		});
		cp.add(pop, new RCPosition(4, 7));
		
		JButton equals = button("=");
		equals.addActionListener(l -> {
			double value = model.getValue();
			model.freezeValue(String.valueOf(value));
		});
		cp.add(equals, new RCPosition(1, 6));
		
		JCheckBox inv = new JCheckBox("Inv");
		inv.setBackground(getBackground());
		inv.setOpaque(true);
		inv.addActionListener(l -> {
			model.setReversed();
		});
		cp.add(inv, new RCPosition(5, 7));
		
		
		// BINARNE OPERACIJE
		JButton add = button("+");
		add.addActionListener(m -> {
			double value = model.getValue();
			model.setActiveOperand(value);
			model.setPendingBinaryOperation((l, r) -> {
				return l + r;
			});
			model.clear();
		});
		cp.add(add, new RCPosition(5, 6));
		
		JButton sub = button("-");
		sub.addActionListener(m -> {
			double value = model.getValue();
			model.setActiveOperand(value);
			model.setPendingBinaryOperation((l, r) -> {
				return l - r;
			});
			model.clear();
		});
		cp.add(sub, new RCPosition(4, 6));

		JButton mul = button("*");
		mul.addActionListener(m -> {
			double value = model.getValue();
			model.setActiveOperand(value);
			model.setPendingBinaryOperation((l, r) -> {
				return l * r;
			});
			model.clear();
		});
		cp.add(mul, new RCPosition(3, 6));
		
		JButton div = button("/");
		div.addActionListener(m -> {
			double value = model.getValue();
			model.setActiveOperand(value);
			model.setPendingBinaryOperation((l, r) -> {
				return l / r;
			});
			model.clear();
		});
		cp.add(div, new RCPosition(2, 6));
		
		
		// UNARNE OPERACIJE
		JButton inverse = button("1/x");
		inverse.addActionListener(l -> {
			double value = 1 / model.getValue();
			model.setValue(value);
		});
		cp.add(inverse, new RCPosition(2, 1));
		
		JButton log = button("log");
		model.addCalcValueListener(m -> {
			log.setText(!model.isReversed() ? "log" : "10^x");
		});
		log.addActionListener(l -> {
			double value = !model.isReversed() ? Math.log10(model.getValue()) : Math.pow(10., model.getValue());
			model.setValue(value);
		});
		cp.add(log, new RCPosition(3, 1));
		
		JButton ln = button("ln");
		model.addCalcValueListener(m -> {
			ln.setText(!model.isReversed() ? "ln" : "e^x");
		});
		ln.addActionListener(l -> {
			double value = !model.isReversed() ? Math.log(model.getValue()) : Math.pow(Math.E, model.getValue());
			model.setValue(value);
		});
		cp.add(ln, new RCPosition(4, 1));

		JButton pot = button("x^n");
		model.addCalcValueListener(m -> {
			pot.setText(!model.isReversed() ? "x^n" : "x^(1/n)");
		});
		pot.addActionListener(l -> {
			double value = !model.isReversed() ? Math.pow(model.getValue(), 2) : Math.pow(model.getValue(), 1/2);
			model.setValue(value);
		});
		cp.add(pot, new RCPosition(5, 1));
		
		JButton sin = button("sin");
		model.addCalcValueListener(m -> {
			sin.setText(!model.isReversed() ? "sin" : "arcsin");
		});
		sin.addActionListener(l -> {
			double value = !model.isReversed() ? Math.sin(model.getValue()) : Math.asin(model.getValue());
			model.setValue(value);
		});
		cp.add(sin, new RCPosition(2, 2));
		
		JButton cos = button("cos");
		model.addCalcValueListener(m -> {
			cos.setText(!model.isReversed() ? "cos" : "arccos");
		});
		cos.addActionListener(l -> {
			double value = !model.isReversed() ? Math.cos(model.getValue()) : Math.acos(model.getValue());
			model.setValue(value);
		});
		cp.add(cos, new RCPosition(3, 2));
		
		JButton tan = button("tan");
		model.addCalcValueListener(m -> {
			tan.setText(!model.isReversed() ? "tan" : "arctan");
		});
		tan.addActionListener(l -> {
			double value = !model.isReversed() ? Math.tan(model.getValue()) : Math.atan(model.getValue());
			model.setValue(value);
		});
		cp.add(tan, new RCPosition(4, 2));
		
		JButton ctg = button("ctg");
		model.addCalcValueListener(m -> {
			ctg.setText(!model.isReversed() ? "ctg" : "arcctg");
		});
		ctg.addActionListener(l -> {
			double value = !model.isReversed() ? 1 / Math.tan(model.getValue()) : 1 / Math.atan(model.getValue());
			model.setValue(value);
		});
		cp.add(ctg, new RCPosition(5, 2));
		
	}
	
	private JButton button(String text) {
		JButton button = new JButton(text);
		button.setBackground(getBackground());
		button.setOpaque(true);
		return button;
	}
	
	public static void main(String[] args) {
		Calculator calc = new Calculator();
		
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				calc.error();
			}
		});
		
		SwingUtilities.invokeLater(()->{
			calc.setVisible(true);
		});
	}
}



/*JButton mul = new BinaryButton(model, "*") {
private static final long serialVersionUID = 1L;
@Override
public DoubleBinaryOperator operation() {
	return new DoubleBinaryOperator() {
		@Override
		public double applyAsDouble(double left, double right) {
			return left * right;
		}
	};
}
};*/


