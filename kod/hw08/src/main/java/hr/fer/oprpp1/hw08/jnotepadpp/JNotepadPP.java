package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.CloseDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.CopyAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.CreateDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.CutAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.ExitAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.OpenDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.PasteAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SaveAsDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SaveDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.StatisticalInfoAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.ToggleAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.UniqueAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.SortAction;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.*;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static int length;
	public static int ln;
	public static int col;
	public static int sel;
	private static JTextField statusBarLength;
	private static JTextField statusBarOther;
	private static JTextField dateTime;
	public static ToggleAction upper;
	public static ToggleAction lower;
	public static ToggleAction invert;
	public static SortAction ascending;
	public static SortAction descending;
	public static UniqueAction unique;
	public static Action cut;
	public static Action copy;
	public static Action paste;
	
	private Sat sat;
	private MultipleDocumentModel model;
	private Action close;
	private Action create;
	private Action open;
	private Action saveAs;
	private Action save;
	private Action statisticalInfo;
	private Action exit;
	private FormLocalizationProvider flp;
	
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(800, 800);
		setTitle("JNotepad++");
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		
		model = new DefaultMultipleDocumentModel(this);
		initGUI();
		
		WindowListener wl = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (DefaultMultipleDocumentModel.exiting((DefaultMultipleDocumentModel) model)) 
					dispose(); 
				
				return;
			}
			@Override
			public void windowClosed(WindowEvent e) {
				sat.zaustavi();
			}
		};
		this.addWindowListener(wl);
	}
	
	public Action getSaveAs() {
		return saveAs;
	}
		
	public Action getClose() {
		return close;
	}
	
	public Action getSave() {
		return save;
	} 
	
	public void initialStatusValues(JTextArea editArea) {
		try {
			length = editArea.getText().length();
            int caretpos = editArea.getCaretPosition();
            ln = editArea.getLineOfOffset(caretpos);
            col = caretpos - editArea.getLineStartOffset(ln);
            sel = Math.abs(editArea.getCaret().getDot()-editArea.getCaret().getMark());
            ln += 1;
        }
        catch(Exception ex) { }
		updateStatus();
	}
	
	public static void updateStatus() {
		statusBarLength.setText("length:" + length);
		statusBarOther.setText("Ln:" + ln + "  Col:" + col + "  Sel:" + sel);
	}

	public void noCurrDocument() {
		int rezultat = JOptionPane.showConfirmDialog(this, "Nijedan dokument nije otvoren. Zelite li kreirati novi dokument?", "Upozorenje!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		switch(rezultat) {
		case JOptionPane.YES_OPTION:
			create.actionPerformed(null);
			return;
		case JOptionPane.NO_OPTION:
			return;
		}
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		JPanel p = new JPanel(new BorderLayout());
		JPanel status = new JPanel(new BorderLayout());
		p.add((DefaultMultipleDocumentModel) model, BorderLayout.CENTER);
		statusBarLength = new JTextField();
		statusBarLength.setPreferredSize(new Dimension(this.getWidth() / 3, statusBarLength.getHeight()));
		System.out.println(this.getWidth());
		statusBarOther = new JTextField();
		dateTime = new JTextField();
		dateTime.setPreferredSize(new Dimension(this.getWidth() / 3, dateTime.getHeight()));
		sat = new Sat();
		status.add(statusBarLength, BorderLayout.WEST);
		status.add(statusBarOther, BorderLayout.CENTER);
		status.add(dateTime, BorderLayout.EAST);
		
		p.add(status, BorderLayout.SOUTH);
		add(p, BorderLayout.CENTER);
		
		close = new CloseDocumentAction(this, "close", flp);
		create = new CreateDocumentAction(this, "create", flp);
		open = new OpenDocumentAction(this, "open", flp);
		saveAs = new SaveAsDocumentAction(this, "saveAs", flp);
		save = new SaveDocumentAction(this, "save", flp);
		statisticalInfo = new StatisticalInfoAction(this, "statisticalInfo", flp);
		exit = new ExitAction(this, "exit", flp);
		
		cut = new CutAction("cut", flp);
		copy = new CopyAction("copy", flp);
		paste = new PasteAction("paste", flp);
		
		upper = new ToggleAction("toUppercase", flp, this);
		lower = new ToggleAction("toLowercase", flp, this);
		invert = new ToggleAction("invertCase", flp, this);
		
		ascending = new SortAction("ascending", flp, this);
		descending = new SortAction("descending", flp, this);
		
		unique = new UniqueAction("unique", flp, this);
		
		createMenu();
		createToolBar();

	}
	
	static class Sat {
		
		volatile String vrijeme;
		volatile boolean stopRequested;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
		
		public Sat() {
			updateTime();
			
			Thread t = new Thread(()->{
				while(true) {
					try {
						Thread.sleep(500);
					} catch(Exception ex) {}
					if(stopRequested) break;
					SwingUtilities.invokeLater(()->{
						updateTime();
					});
				}
			});
			t.setDaemon(true);
			t.start();
		}
		
		private void zaustavi() {
			stopRequested = true;
		}
		
		private void updateTime() {
			dateTime.setText(formatter.format(LocalDateTime.now()));
		}
	}
	
	public DefaultMultipleDocumentModel getModel() {
		return (DefaultMultipleDocumentModel) model;
	}
	
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu(new LocalizableAction("file", flp) {
			private static final long serialVersionUID = 1L;
		});
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(open));
		fileMenu.add(new JMenuItem(save));
		fileMenu.add(new JMenuItem(saveAs));
		fileMenu.add(new JMenuItem(create));
		fileMenu.add(new JMenuItem(close));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exit));
		
		JMenu editMenu = new JMenu(new LocalizableAction("edit", flp) {
			private static final long serialVersionUID = 1L;
		});
		menuBar.add(editMenu);
		
		editMenu.add(new JMenuItem(cut));
		editMenu.add(new JMenuItem(copy));
		editMenu.add(new JMenuItem(paste));
		editMenu.add(new JMenuItem(statisticalInfo));
		
		JMenuItem hrComponentsItem = new JMenuItem("hr");
		hrComponentsItem.addActionListener(e -> {
			LocalizationProvider.getInstance().setLanguage("hr");
		});
		JMenuItem enComponentsItem = new JMenuItem("en");
		enComponentsItem.addActionListener(e -> {
			LocalizationProvider.getInstance().setLanguage("en");
		});
		JMenuItem deComponentsItem = new JMenuItem("de");
		deComponentsItem.addActionListener(e -> {
			LocalizationProvider.getInstance().setLanguage("de");
		});
		
		JMenu languageMenu = new JMenu(new LocalizableAction("language", flp) {
			private static final long serialVersionUID = 1L;
		});
		languageMenu.add(hrComponentsItem);
		languageMenu.add(enComponentsItem);
		languageMenu.add(deComponentsItem);
		menuBar.add(languageMenu);
		
		JMenu toolsMenu = new JMenu(new LocalizableAction("tools", flp) {
			private static final long serialVersionUID = 1L;
		});
		menuBar.add(toolsMenu);
		
		JMenu changeCaseMenu = new JMenu(new LocalizableAction("changeCase", flp) {
			private static final long serialVersionUID = 1L;
		});
		toolsMenu.add(changeCaseMenu);
		
		changeCaseMenu.add(new JMenuItem(upper));
		changeCaseMenu.add(new JMenuItem(lower));
		changeCaseMenu.add(new JMenuItem(invert));
		
		JMenu sortMenu = new JMenu(new LocalizableAction("sort", flp) {
			private static final long serialVersionUID = 1L;
		});
		toolsMenu.add(sortMenu);
		
		sortMenu.add(new JMenuItem(ascending));
		sortMenu.add(new JMenuItem(descending));
		
		toolsMenu.add(new JMenuItem(unique));
		
		this.setJMenuBar(menuBar);
	}
	
	private void createToolBar() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);
		
		toolBar.add(new JButton(open));
		toolBar.add(new JButton(save));
		toolBar.add(new JButton(saveAs));
		toolBar.add(new JButton(create));
		toolBar.add(new JButton(close));
		toolBar.addSeparator();
		toolBar.add(new JButton(cut));
		toolBar.add(new JButton(copy));
		toolBar.add(new JButton(paste));
		toolBar.add(new JButton(statisticalInfo));
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	public static boolean isSelected(JTextArea editor) {
		if (editor == null) return false;
		int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
		return len != 0;
	}
	
	public class LocalComparator implements Comparator<String> {
		private boolean reversed;
		public LocalComparator(boolean reversed) {
			this.reversed = reversed;
		}

		@Override
		public int compare(String o1, String o2) {
			Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
			Collator collator = Collator.getInstance(locale);
			if (reversed) return collator.compare(o2, o1);
			return collator.compare(o1, o2);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}

}
