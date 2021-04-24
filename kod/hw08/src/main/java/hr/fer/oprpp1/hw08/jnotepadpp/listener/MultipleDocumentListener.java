package hr.fer.oprpp1.hw08.jnotepadpp.listener;

import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

public interface MultipleDocumentListener {
	
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	void documentAdded(SingleDocumentModel model);
			
	void documentRemoved(SingleDocumentModel model);

}
