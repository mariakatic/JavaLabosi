package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import javax.swing.JLabel;

public class LJLabel extends JLabel {

	private static final long serialVersionUID = 1L;
	
	private String key;
	private ILocalizationProvider provider;
	private ILocalizationListener listener = () -> {
		this.setText(provider.getString(key));
	};
	
	public LJLabel(String key, ILocalizationProvider provider) {
		this.provider = provider;
		this.key = key;
		this.setText(provider.getString(key));
		provider.addLocalizationListener(listener);
	}

	public void updateLabel() {
		
	}

}
