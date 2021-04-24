package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;

public class OpenDocumentAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;
	
	private JNotepadPP frame;
	private DefaultMultipleDocumentModel model;
	
	public OpenDocumentAction(JNotepadPP frame, String key, ILocalizationProvider provider) {
		super(key, provider);
		this.frame = frame;
		model = frame.getModel();
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		this.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		if(fc.showOpenDialog(frame)!=JFileChooser.APPROVE_OPTION) {
			return;
		}
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		if(!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(
					frame, 
					"Datoteka "+fileName.getAbsolutePath()+" ne postoji!", 
					"Pogreska", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			model.loadDocument(filePath);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(
					frame, 
					"Pogreska prilikom citanja datoteke "+fileName.getAbsolutePath()+".", 
					"Pogreska", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

}
