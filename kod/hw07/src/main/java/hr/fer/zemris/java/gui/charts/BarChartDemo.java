package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class BarChartDemo extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private BarChart chart;
	private static String pathName = new String();

	public BarChartDemo(BarChart chart) {
		super();
		this.chart = chart;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("BarChart");
		setLocation(20, 20);
		setSize(new Dimension(700, 600));
		initGUI();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JLabel pathLabel = new JLabel(pathName);
		cp.add(pathLabel, BorderLayout.PAGE_START);
		
		cp.add(new BarChartComponent(chart), BorderLayout.CENTER);
	}
	
	private static List<XYValue> parse(String line) {
		List<XYValue> res = new ArrayList<>();
		String[] values = line.split(" ");
		for (int i = 0; i < values.length; i++) {
			String[] value = values[i].split(",");
			int x = 0, y = 0;
			try {
				if (value.length != 2) 
					throw new IllegalArgumentException("Neispravno zadane vrijednosti grafikona.");
				
				x = Integer.parseInt(value[0]);
				y = Integer.parseInt(value[1]);
				
			} catch(NumberFormatException ex) {
				throw new IllegalArgumentException("Neispravno zadane vrijednosti grafikona.");
			}
			res.add(new XYValue(x, y));
		}
		
		return res;
	}
	
	public static void main(String[] args) {
		if (args.length != 1)
			throw new RuntimeException("Program mora biti pozvan s tocno jednim argumentom.");
		
		pathName = args[0];
		Path path = Paths.get(pathName);
		
		if (!Files.isRegularFile(path)) {
			throw new IllegalArgumentException("U argumentu mora biti zadana datoteka.");
		}
		
		try {
			List<String> lines = Files.readAllLines(path);
			
			String xOpis = lines.get(0);
			String yOpis = lines.get(1);
			List<XYValue> bars = parse(lines.get(2));
			int minY = Integer.parseInt(lines.get(3));
			int maxY = Integer.parseInt(lines.get(4));
			int razmak = Integer.parseInt(lines.get(5));
			
			BarChart barChart = new BarChart(bars, xOpis, yOpis, minY, maxY, razmak);
			
			SwingUtilities.invokeLater(()->{
				new BarChartDemo(barChart).setVisible(true);
			});
			
		} catch (IOException | NumberFormatException e) {
			throw new IllegalArgumentException("Greska prilikom otvaranja/citanja datoteke.");
		}
	
	}

}
