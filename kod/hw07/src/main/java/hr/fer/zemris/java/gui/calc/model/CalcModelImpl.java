package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel {
	
	private boolean editable;
	private boolean positive;
	private String digits;
	private double numDigits;
	private String freezeValue;
	private OptionalDouble activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private List<CalcValueListener> promatraci = new ArrayList<>();
	private boolean reversed;
	private Stack<Double> stack = new Stack<>();
	
	public CalcModelImpl() {
		editable = true;
		positive = true;
		digits = "";
		numDigits = 0.;
		freezeValue = null;
		activeOperand = OptionalDouble.empty();
		pendingOperation = null;
		reversed = false;
	}
	
	public boolean isReversed() {
		return reversed;
	}

	public void setReversed() {
		reversed = !reversed;
		
		for (CalcValueListener l : promatraci) {
			l.valueChanged(this);
		}
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		promatraci.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		promatraci.remove(l);
	}

	@Override
	public double getValue() {
		if (pendingOperation != null) {
			double res = pendingOperation.applyAsDouble(activeOperand.getAsDouble(), numDigits);
			clearActiveOperand();
			setPendingBinaryOperation(null);
			setValue(res);
		}

		freezeValue = digits;
		if (numDigits == 0) return 0.;
		return numDigits;
	}

	@Override
	public void setValue(double value) {
		numDigits = value;
		digits = Double.valueOf(value).toString();
		editable = false;
		freezeValue = digits;
		
		for (CalcValueListener l : promatraci) {
			l.valueChanged(this);
		}
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		digits = "";
		numDigits = 0;
		editable = true;
		
		for (CalcValueListener l : promatraci) {
			l.valueChanged(this);
		}
	}

	@Override
	public void clearAll() {
		clear();
		freezeValue = null;
		activeOperand = OptionalDouble.empty();
		pendingOperation = null;
		
		for (CalcValueListener l : promatraci) {
			l.valueChanged(this);
		}
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!editable)
			throw new CalculatorInputException("Model nije editabilan.");
		
		positive = !positive;
		freezeValue = null;
		numDigits = 0 - numDigits;
		digits = Double.valueOf(numDigits).toString();
		for (CalcValueListener l : promatraci) {
			l.valueChanged(this);
		}
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!editable)
			throw new CalculatorInputException("Model nije editabilan.");
		
		if (digits.contains("."))
			throw new CalculatorInputException("Tocka vec postoji.");
		
		if (digits.equals("")) {
			throw new CalculatorInputException("Nepostoji nijedna znamenka.");
		}
		
		digits += ".";
		numDigits = Double.parseDouble(digits);
		freezeValue = null;
		
		for (CalcValueListener l : promatraci) {
			l.valueChanged(this);
		}
		
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!editable)
			throw new CalculatorInputException("Model nije editabilan.");
		
		try {
			String helpDigits = digits;
			if (!helpDigits.equals("0")) {
				helpDigits += Integer.valueOf(digit).toString();
				numDigits = Double.parseDouble(helpDigits);
			}
			else {
				helpDigits = Integer.valueOf(digit).toString();
				numDigits = Double.parseDouble(helpDigits);
			}
			
			if (numDigits > Double.MAX_VALUE) {
				throw new CalculatorInputException("Broj je prevelik.");
			}
			
			digits = helpDigits;
			freezeValue = null;
			
			
		} catch(NumberFormatException ex) {
			throw new CalculatorInputException("Nije moguce dodati zadanu znamenku na vec postojeci broj.");
		}
		
		for (CalcValueListener l : promatraci) {
			l.valueChanged(this);
		}

	}

	@Override
	public boolean isActiveOperandSet() {
		return !activeOperand.isEmpty();
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (activeOperand.isEmpty())
			throw new IllegalStateException("Aktivni operand nije postavljen.");
		
		return activeOperand.getAsDouble();
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = OptionalDouble.of(activeOperand);
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = OptionalDouble.empty();
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}

	@Override
	public String toString() {
		String s = "";
		//String s = positive ? "" : "-";
		if (freezeValue != null) {
			return s += freezeValue;
		} else {
			if (numDigits != 0.) return s += digits;
			else return s += "0";
		}
	}
	
	@Override
	public void freezeValue(String value) {
		freezeValue = value;
		
		for (CalcValueListener l : promatraci) {
			l.valueChanged(this);
		}
	}
	
	@Override
	public boolean hasFrozenValue() {
		return freezeValue != null;
	}

	
	
	public static void main(String[] args) {
		CalcModel model = new CalcModelImpl();
		model.insertDigit(5);
		model.insertDigit(8);
		model.setActiveOperand(model.getValue());
		model.setPendingBinaryOperation(Double::sum);
		model.clear();
		model.insertDigit(1);
		model.insertDigit(4);
		
		//double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
		double result = model.getValue();
		model.setValue(result);
		model.clearActiveOperand();
		model.setPendingBinaryOperation(null);
		System.out.println(String.valueOf(model.getValue()));
	}

	@Override
	public void push() {
		stack.push(numDigits);
	}

	@Override
	public void pop() {
		if (stack.isEmpty()) 
			freezeValue("No elements in stack.");
		else
			setValue(stack.pop());
	}
}
