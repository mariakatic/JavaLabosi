package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	private BarChart chart;
	private int fontSize = 15;
	private int numbSize = 15;
	private int gap = 10;
	
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		Dimension dimension = getSize();
		Font font = new Font(" Helvetica Bold", Font.PLAIN, fontSize);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		List<XYValue> bars = chart.getBars();
		
		//y os
		g2d.drawLine(fontSize + numbSize + 3 * gap, (int)dimension.getHeight() - fontSize - numbSize - 2 * gap, fontSize + numbSize + 3 * gap, gap);
		int[] xPoints1 = {fontSize + numbSize + 3 * gap, fontSize + numbSize + 3 * gap - 4, fontSize + numbSize + 3 * gap + 4};
		int[] yPoints1 = {gap, gap + 4, gap + 4};
		g2d.drawPolygon(xPoints1, yPoints1, 3);
		
		//x os
		g2d.drawLine(fontSize + numbSize + 3 * gap, (int)dimension.getHeight() - fontSize - numbSize - 2 * gap, (int)dimension.getWidth() - gap / 2, (int)dimension.getHeight() - fontSize - numbSize - 2 * gap);
		int[] xPoints2 = {(int)dimension.getWidth() - gap / 2, (int)dimension.getWidth() - gap / 2 - 4, (int)dimension.getWidth() - gap / 2 - 4};
		int[] yPoints2 = {(int)dimension.getHeight() - fontSize - numbSize - 2 * gap, (int)dimension.getHeight() - fontSize - numbSize - 2 * gap - 4, (int)dimension.getHeight() - fontSize - numbSize - 2 * gap + 4};
		g2d.drawPolygon(xPoints2, yPoints2, 3);
		
		//brojevi na y osi
		int idx = chart.getMinY();
		int numOfBars = chart.getMaxY() - chart.getMinY();
		int barHeight = ((int)dimension.getHeight() - fontSize - numbSize - 4 * gap) / numOfBars;
		int yFirstBar = (int)dimension.getHeight() - fontSize - numbSize - 2 * gap;
		while(idx <= chart.getMaxY()) {
			g2d.drawString(String.valueOf(idx), fontSize + numbSize, yFirstBar - (idx/chart.getRazmak()) * barHeight * 2 + fm.getAscent() / 3);
			g2d.drawLine(fontSize + numbSize + 2 *gap, yFirstBar - (idx/chart.getRazmak()) * barHeight * 2, fontSize + numbSize + 3 *gap, yFirstBar - (idx/chart.getRazmak()) * barHeight * 2);
			idx += chart.getRazmak();
		}
		
		//brojevi na x osi
		int barWidth = ((int)dimension.getWidth() - fontSize - numbSize - 3 * gap - gap/2) / bars.size();
		for(int i = 0; i < bars.size(); i++) {
			int idxWidth = (barWidth - fm.stringWidth(String.valueOf(bars.get(i).getX()))) / 2;
			g.drawString(String.valueOf(bars.get(i).getX()), fontSize + numbSize + 3 * gap + barWidth * i + idxWidth, (int)dimension.getHeight() - fontSize - 2 * gap);
		}
		
		//opis x i y osi
		g2d.drawString(chart.getxOpis(), (int)(dimension.getWidth() - fm.stringWidth(chart.getxOpis())) / 2, (int)dimension.getHeight() - fm.getAscent());
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);
		g2d.drawString(chart.getyOpis(), (int)(dimension.getHeight() + fm.stringWidth(chart.getyOpis())) / (-2), fm.getAscent() + gap / 2);
		at.rotate(Math.PI / 2);
		g2d.setTransform(at);
		
		//bars
		int startX = fontSize + numbSize + 3 * gap + gap/3;
		for(int i = 0; i < bars.size(); i++) {
			int y = bars.get(i).getY();
			g2d.setColor(new Color(255, 255, 153));
			g2d.fillRect(startX + barWidth * i, yFirstBar + gap + 5 - (y /chart.getRazmak()) * barHeight * 2, barWidth - gap / 2, barHeight * y);
			g2d.setColor(new Color(255, 204, 51));
			g2d.drawRect(startX + barWidth * i, yFirstBar + gap + 5 - (y /chart.getRazmak()) * barHeight * 2, barWidth - gap / 2, barHeight * y);
		}
		

	}
}
