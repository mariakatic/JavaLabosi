package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;

public class PasteAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private Action action;

	public PasteAction(String key, ILocalizationProvider provider) {
		super(key, provider);
		action = new DefaultEditorKit.PasteAction();
		action.putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		action.actionPerformed(e);
	}

}
