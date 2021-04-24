package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private String key;
	private ILocalizationProvider provider;
	private ILocalizationListener listener = () -> {
		this.putValue(Action.NAME, provider.getString(this.key));
		List<String> noDescription = Arrays.asList("file", "edit", "language", "tools", "changeCase", "sort");
		if (!noDescription.contains(key))
			this.putValue(Action.SHORT_DESCRIPTION, provider.getString(key + "Description"));
	};
		
	public LocalizableAction(String key, ILocalizationProvider provider) {
		this.provider = provider;
		this.key = key;
		this.putValue(Action.NAME, provider.getString(key));
		List<String> noDescription = Arrays.asList("file", "edit", "language", "tools", "changeCase", "sort");
		if (!noDescription.contains(key))
			this.putValue(Action.SHORT_DESCRIPTION, provider.getString(key + "Description"));
		provider.addLocalizationListener(listener);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}
