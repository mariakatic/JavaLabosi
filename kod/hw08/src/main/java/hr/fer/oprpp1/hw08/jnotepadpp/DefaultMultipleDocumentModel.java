package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Component;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.oprpp1.hw08.jnotepadpp.listener.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.listener.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;
	private JNotepadPP frame;
	private List<SingleDocumentModel> documents;
	private SingleDocumentModel currentDocument;
	private List<MultipleDocumentListener> listeners;
	private SingleDocumentListener docListener;
	private ImageIcon redIcon = createImage("red");
	private ImageIcon greenIcon = createImage("green");
	
	public DefaultMultipleDocumentModel(JNotepadPP frame) {
		documents = new ArrayList<>();
		listeners = new ArrayList<>();
		this.frame = frame;
		
		docListener = new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				int idx = documents.indexOf(model);
				JTabbedPane tabbedPane = (JTabbedPane) SwingUtilities.getAncestorOfClass(JTabbedPane.class, (Component) model);
				if (model.isModified()) {
					tabbedPane.setIconAt(idx, redIcon);
				} else {
					tabbedPane.setIconAt(idx, greenIcon);
				}
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				JTabbedPane tabbedPane = (JTabbedPane) SwingUtilities.getAncestorOfClass(JTabbedPane.class, (Component) model);
				if (model.getFilePath() != null) {
					tabbedPane.setTitleAt(documents.indexOf(model), model.getFilePath().toString());
				} else {
					tabbedPane.setTitleAt(documents.indexOf(model), "(unnamed)");
				}
			}
		};
		
		addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(DefaultMultipleDocumentModel.this.getSelectedIndex() == -1)
					frame.setTitle("JNotepad++");
				else {
					currentDocument = (SingleDocumentModel) DefaultMultipleDocumentModel.this.getSelectedComponent();
					String path = DefaultMultipleDocumentModel.this.getSelectedComponent().getName();
					frame.setTitle(path + " - JNotepad++");
				}
				frame.initialStatusValues(currentDocument.getTextComponent());
			}
		});
		
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel doc = new DefaultSingleDocumentModel(null, "");
		doc.addSingleDocumentListener(docListener);
		documents.add(doc);
		this.addTab("(unnamed)", (Component) doc);
		this.setIconAt(documents.indexOf(doc), greenIcon);
		
		currentDocument = doc;
		this.setSelectedComponent((Component) currentDocument);
		for (MultipleDocumentListener listener : listeners) {
			listener.documentAdded(doc);
		}
		
		return doc;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) throws IOException {
		
		for (SingleDocumentModel doc : documents) {
			if (doc.getFilePath() != null && doc.getFilePath().equals(path)) {
				currentDocument = doc;
				this.setSelectedComponent((Component) currentDocument);
				return doc;
			}
		}
		
		byte[] okteti = Files.readAllBytes(path);
		String tekst = new String(okteti, StandardCharsets.UTF_8);
		SingleDocumentModel doc = new DefaultSingleDocumentModel(path, tekst);
		documents.add(doc);
		doc.addSingleDocumentListener(docListener);
		this.addTab(path.getFileName().toString(), (Component) doc);
		this.setIconAt(documents.indexOf(doc), greenIcon);
		this.setSelectedComponent((Component) doc);
		
		currentDocument = doc;
		
		for (MultipleDocumentListener listener : listeners) {
			System.out.println(listeners.size());
			listener.documentAdded(currentDocument);
		}
		
		return currentDocument;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) throws IOException {
		byte[] podatci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		Files.write(newPath, podatci);
		this.setIconAt(documents.indexOf(currentDocument), greenIcon);
		
		for (MultipleDocumentListener listener : listeners) {
			listener.currentDocumentChanged(currentDocument, model);
		}
		
		currentDocument = model;
		currentDocument.addSingleDocumentListener(docListener);
		currentDocument.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int idx = documents.indexOf(model);
		documents.remove(model);
		this.remove((Component) model);
		
		if (documents.size() == 0)
			currentDocument = null;
		else 
			currentDocument = documents.size() == idx ? documents.get(idx - 1) : documents.get(idx);
		
		for (MultipleDocumentListener listener : listeners) {
			listener.documentRemoved(model);
		}
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}
	
	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}
	
	private ImageIcon createImage(String s) {
		String modified = s.equals("red") ? "Modified" : "Unmodified";
		byte[] bytes;
		try (InputStream is = this.getClass().getResourceAsStream("icons/file" + modified + ".png")) {
			bytes = is.readAllBytes();
			ImageIcon icon = new ImageIcon(bytes);
			return new ImageIcon(icon.getImage().getScaledInstance(13, 13, java.awt.Image.SCALE_SMOOTH));
		} catch (IOException e) {
		}
		return null;
	}
	
	public static boolean exiting(DefaultMultipleDocumentModel model) {
		List<SingleDocumentModel> unsavedDocuments = findUnsaved(model);
		for (SingleDocumentModel doc : unsavedDocuments) {
			model.currentDocument = doc;
			int rezultat = JOptionPane.showConfirmDialog(model.frame, "Postoji nesnimljeni sadrzaj. Zelite li ga snimiti?", "Upozorenje!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			switch(rezultat) {
			case JOptionPane.CANCEL_OPTION:
				return false;
			case JOptionPane.YES_OPTION:
				model.frame.getSave().actionPerformed(null);
				model.frame.getClose().actionPerformed(null);
				continue;
			case JOptionPane.NO_OPTION:
				model.frame.getClose().actionPerformed(null);
				continue;
			}
		}
		return true;
		
	}
	
	public static List<SingleDocumentModel> findUnsaved(DefaultMultipleDocumentModel model) {
		List<SingleDocumentModel> unsavedDocuments = new ArrayList<>();
		for (SingleDocumentModel doc : model) {
			if (doc.isModified())
				unsavedDocuments.add(doc);
		}
		return unsavedDocuments;
	}

}
