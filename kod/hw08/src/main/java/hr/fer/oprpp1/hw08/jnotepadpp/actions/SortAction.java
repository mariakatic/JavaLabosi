package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;

public class SortAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel model;
	private String key;
	private JNotepadPP frame;
	
	public SortAction(String key, ILocalizationProvider provider, JNotepadPP frame) {
		super(key, provider);
		this.frame = frame;
		model = frame.getModel();
		this.key = key;
		setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextComponent c = model.getCurrentDocument().getTextComponent();
		Document doc = c.getDocument();
		Element root = doc.getDefaultRootElement();
		int firstLine = root.getElementIndex(c.getCaret().getMark());
		int lastLine = root.getElementIndex(c.getCaret().getDot());
		
		if (firstLine > lastLine) {
			int help = firstLine;
			firstLine = lastLine;
			lastLine = help;
		}

		String[] allLines = c.getText().split("\n");
		String[] lines = Arrays.copyOfRange(allLines, firstLine, lastLine);
		
		if (key.equals("ascending"))
			Arrays.sort(lines, frame.new LocalComparator(false));
		else
			Arrays.sort(lines, frame.new LocalComparator(true));
		
		for (int i = firstLine; i < lastLine; i++) {
			allLines[i] = lines[i - firstLine];
		}
		
		String rez = new String();
		for (String s : allLines) {
			rez += s + "\n"; 
		}
		model.getCurrentDocument().getTextComponent().setText(rez);
	}
	
	

}
