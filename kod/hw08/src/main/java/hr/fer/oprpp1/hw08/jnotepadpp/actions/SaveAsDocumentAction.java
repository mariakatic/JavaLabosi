package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Path;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;

public class SaveAsDocumentAction extends LocalizableAction {
	
	private static final long serialVersionUID = 1L;
	
	private JNotepadPP frame;
	private DefaultMultipleDocumentModel model;
	
	public SaveAsDocumentAction(JNotepadPP frame, String key, ILocalizationProvider provider) {
		super(key, provider);
		this.frame = frame;
		model = frame.getModel();
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (model.getCurrentDocument() == null) {
			frame.noCurrDocument();
			return;
		}
		
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");
		if(jfc.showSaveDialog(frame)!=JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(
					frame, 
					"Nista nije snimljeno.", 
					"Upozorenje", 
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		Path path = jfc.getSelectedFile().toPath();
		try {
			model.saveDocument(model.getCurrentDocument(), path);
			model.getCurrentDocument().setFilePath(path);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(
					frame, 
					"Pogreska prilikom zapisivanja datoteke "+path.toFile().getAbsolutePath()+".\nPaznja: nije jasno u kojem je stanju datoteka na disku!", 
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
