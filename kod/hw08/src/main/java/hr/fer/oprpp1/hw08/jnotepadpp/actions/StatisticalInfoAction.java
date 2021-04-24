package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;

public class StatisticalInfoAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;
	private JNotepadPP frame;
	private DefaultMultipleDocumentModel model;
	
	public StatisticalInfoAction(JNotepadPP frame, String key, ILocalizationProvider provider) {
		super(key, provider);
		this.frame = frame;
		model = frame.getModel();
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (model.getCurrentDocument() == null) {
			frame.noCurrDocument();
			return;
		}
		
		int numLines = 0;
		int chars = 0;
		int nonBlankChars = 0;
		
		String text = frame.getModel().getCurrentDocument().getTextComponent().getText();
		numLines = text.split("\\r?\\n").length;
		chars = text.length();
		nonBlankChars = text.replaceAll("\\r?\\n?\\t? ", "").length();

		JOptionPane.showMessageDialog(
				frame, 
				"Your document has " + chars + " characters, " + nonBlankChars + " non-blank characters and " + numLines + " lines.", 
				"Statistika dokumenta", 
				JOptionPane.INFORMATION_MESSAGE);

	}
	
	

}
