package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.listener.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

public class DefaultSingleDocumentModel extends JPanel implements SingleDocumentModel {
	
	private static final long serialVersionUID = 1L;
	
	private Path path;
	private JTextArea textComponent;
	private boolean modified;
	private List<SingleDocumentListener> listeners;
	
	public DefaultSingleDocumentModel(Path path, String textContent) {
		this.path = path;
		textComponent = new JTextArea(textContent);
		modified = false;
		listeners = new ArrayList<>();
		
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(textComponent), BorderLayout.CENTER);
		
		textComponent.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
		
		textComponent.addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				JTextArea editArea = (JTextArea)e.getSource();
				try {
					JNotepadPP.length = editArea.getText().length();
                    int caretpos = editArea.getCaretPosition();
                    JNotepadPP.ln = editArea.getLineOfOffset(caretpos);
                    JNotepadPP.col = caretpos - editArea.getLineStartOffset(JNotepadPP.ln);
                    JNotepadPP.sel = Math.abs(editArea.getCaret().getDot()-editArea.getCaret().getMark());
                    JNotepadPP.ln += 1;
                    
                    JNotepadPP.upper.setEnabled(JNotepadPP.isSelected(editArea));
                    JNotepadPP.lower.setEnabled(JNotepadPP.isSelected(editArea));
                    JNotepadPP.invert.setEnabled(JNotepadPP.isSelected(editArea));
                    
                    JNotepadPP.ascending.setEnabled(JNotepadPP.isSelected(editArea));
                    JNotepadPP.descending.setEnabled(JNotepadPP.isSelected(editArea));
                    
                    JNotepadPP.unique.setEnabled(JNotepadPP.isSelected(editArea));
                    
                    JNotepadPP.cut.setEnabled(JNotepadPP.isSelected(editArea));
                    JNotepadPP.copy.setEnabled(JNotepadPP.isSelected(editArea));
                    JNotepadPP.paste.setEnabled(JNotepadPP.isSelected(editArea));
                }
                catch(Exception ex) { }
				JNotepadPP.updateStatus();
			}
		});
	}

	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		if (path == null)
			throw new NullPointerException("Path must not be null.");
		
		this.path = path;
		
		for (SingleDocumentListener listener : listeners) {
			listener.documentFilePathUpdated(this);
		}
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		
		if (modified) {
			for (SingleDocumentListener listener : listeners) {
				listener.documentModifyStatusUpdated(this);
			}
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultSingleDocumentModel other = (DefaultSingleDocumentModel) obj;
		if (path == null || other == null) {
			return false;
		} else if (other.path == null || !path.toString().equals(other.path.toString()))
			return false;
		return true;
	}
	
	@Override
	public String getName() {
		return path == null ? "(unnamed)" : path.toString();
	}
	
}
