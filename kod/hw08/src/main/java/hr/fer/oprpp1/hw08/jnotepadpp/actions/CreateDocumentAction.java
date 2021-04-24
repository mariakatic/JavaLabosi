package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;

public class CreateDocumentAction extends LocalizableAction {
	
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private JNotepadPP frame;
	private DefaultMultipleDocumentModel model;
	
	public CreateDocumentAction(JNotepadPP frame, String key, ILocalizationProvider provider) {
		super(key, provider);
		this.frame = frame;
		model = frame.getModel();
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.createNewDocument();
	}
	
	

}
