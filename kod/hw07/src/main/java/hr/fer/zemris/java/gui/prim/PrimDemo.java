package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	public PrimDemo() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("PrimDemo");
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel modelListe = new PrimListModel();
		
		JList<Integer> lista1 = new JList<>(modelListe);
		JList<Integer> lista2 = new JList<>(modelListe);
		lista1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lista2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JPanel p = new JPanel(new GridLayout(1, 0));
		p.add(new JScrollPane(lista1));
		p.add(new JScrollPane(lista2));
		
		cp.add(p, BorderLayout.CENTER);
		
		JButton dodavanje = new JButton("Dodaj iduci prim broj!");
		cp.add(dodavanje, BorderLayout.PAGE_END);
		
		dodavanje.addActionListener(e->{
			modelListe.next();
		});
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new PrimDemo().setVisible(true);
		});
	}

}
