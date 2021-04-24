package hr.fer.oprpp1.hw08.jnotepadpp.listener;

import hr.fer.oprpp1.hw08.jnotepadpp.model.SingleDocumentModel;

public interface SingleDocumentListener {
	
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	void documentFilePathUpdated(SingleDocumentModel model);

}
