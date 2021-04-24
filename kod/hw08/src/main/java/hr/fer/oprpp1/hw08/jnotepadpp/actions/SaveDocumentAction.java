package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;

public class SaveDocumentAction extends LocalizableAction {
	
	private static final long serialVersionUID = 1L;
	
	private JNotepadPP frame;
	private DefaultMultipleDocumentModel model;
	private Action saveAsAction;
	
	public SaveDocumentAction(JNotepadPP frame, String key, ILocalizationProvider provider) {
		super(key, provider);
		this.frame = frame;
		model = frame.getModel();
		this.saveAsAction = frame.getSaveAs();
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (model.getCurrentDocument() == null) {
			frame.noCurrDocument();
			return;
		}
		
		if (model.getCurrentDocument().getFilePath() == null) {
			saveAsAction.actionPerformed(e);
			return;
		}
		
		try {
			model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(
					frame, 
					"Pogreska prilikom zapisivanja datoteke "+model.getCurrentDocument().getFilePath().toFile().getAbsolutePath()+".\nPaznja: nije jasno u kojem je stanju datoteka na disku!", 
					"Pogreska", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(
				frame, 
				"Datoteka je snimljena.", 
				"Informacija", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
}
