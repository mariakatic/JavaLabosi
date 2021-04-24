package hr.fer.zemris.java.gui.charts;

import java.util.List;

public class BarChart {
	
	private List<XYValue> bars;
	private String xOpis;
	private String yOpis;
	private int minY;
	private int maxY;
	private int razmak;
	
	public BarChart(List<XYValue> bars, String xOpis, String yOpis, int minY, int maxY, int razmak) {
		this.bars = bars;
		this.xOpis = xOpis;
		this.yOpis = yOpis;
		this.minY = minY;
		this.maxY = maxY;
		this.razmak = razmak;
		
		if (minY < 0 || maxY <= minY)
			throw new IllegalArgumentException();
		
		if ((maxY - minY) % razmak != 0) {
			this.maxY = (int) (Math.ceil((maxY - minY) / razmak) * razmak);
		}
	}

	public List<XYValue> getBars() {
		return bars;
	}

	public String getxOpis() {
		return xOpis;
	}

	public String getyOpis() {
		return yOpis;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getRazmak() {
		return razmak;
	}
	
	

}
