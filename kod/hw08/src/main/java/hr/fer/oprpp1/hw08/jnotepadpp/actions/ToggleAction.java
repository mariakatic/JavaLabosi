package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;

public class ToggleAction extends LocalizableAction {
	
	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel model;
	private String key;
	
	public ToggleAction(String key, ILocalizationProvider provider, JNotepadPP frame) {
		super(key, provider);
		model = frame.getModel();
		this.key = key;
		setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		if (JNotepadPP.isSelected(editor)) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
			int offset = 0;
			offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
			try {
				String text = doc.getText(offset, len);
				text = change(text, key);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch(BadLocationException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private String change(String text, String method) {
		if (method.equals("invertCase")) {
			char[] znakovi = text.toCharArray();
			for(int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				if(Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if(Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
			return new String(znakovi);
		} else if (method.equals("toUppercase")) {
			return text.toUpperCase();
		} else {
			return text.toLowerCase();
		}
	}
	
	

}
