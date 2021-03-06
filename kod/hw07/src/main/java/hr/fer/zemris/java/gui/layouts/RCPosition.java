package hr.fer.zemris.java.gui.layouts;

public class RCPosition {
	
	private int row;
	private int column;
	
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public static RCPosition parse(String text) {
		String parts[] = text.split(",");
		if (parts.length == 2) {
			try {
				int row, column;
				row = Integer.parseInt(parts[0]);
				column = Integer.parseInt(parts[1]);
				return new RCPosition(row, column);
			} catch(NumberFormatException ex) {
				throw new IllegalArgumentException("Neispravno zadana pozicija.");
			}
		}
		throw new IllegalArgumentException("Neispravno zadana pozicija.");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
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
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	
}
