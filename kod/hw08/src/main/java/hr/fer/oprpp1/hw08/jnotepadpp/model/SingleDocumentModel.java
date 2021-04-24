package hr.fer.oprpp1.hw08.jnotepadpp.model;

import java.nio.file.Path;
import javax.swing.JTextArea;
import hr.fer.oprpp1.hw08.jnotepadpp.listener.SingleDocumentListener;

public interface SingleDocumentModel {
	
	JTextArea getTextComponent();
	
	Path getFilePath();
	
	void setFilePath(Path path);
	
	boolean isModified();
	
	void setModified(boolean modified);
	
	void addSingleDocumentListener(SingleDocumentListener l);
	
	void removeSingleDocumentListener(SingleDocumentListener l);

}
