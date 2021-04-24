package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;

public class CloseDocumentAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;
	
	private JNotepadPP frame;
	private DefaultMultipleDocumentModel model;

	public CloseDocumentAction(JNotepadPP frame, String key, ILocalizationProvider provider) {
		super(key, provider);
		this.frame = frame;
		model = frame.getModel();
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (model.getCurrentDocument() == null) {
			frame.noCurrDocument();
			return;
		}
		
		model.closeDocument(model.getCurrentDocument());
	}
	
	

}
