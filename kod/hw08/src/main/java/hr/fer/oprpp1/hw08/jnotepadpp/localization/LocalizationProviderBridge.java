package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	
	private ILocalizationProvider provider;
	private List<ILocalizationListener> listeners;
	private ILocalizationListener listener = new ILocalizationListener() {
		
		@Override
		public void localizationChanged() {
			for(ILocalizationListener l : listeners) {
				l.localizationChanged();
			}
		}
	};
	private boolean connected;
	
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
		this.listeners = new ArrayList<>();
		connected = false;
	}
	
	public void disconnect() {
		if (connected) {
			provider.removeLocalizationListener(listener);
			connected = false;
		} else {
			throw new RuntimeException();
		}
	}
	
	public void connect() {
		if (!connected) {
			provider.addLocalizationListener(listener);
			connected = true;
		} else {
			throw new RuntimeException();
		}
	}

	@Override
	public String getString(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.localization.prijevodi", Locale.forLanguageTag(getCurrentLanguage()));
		return bundle.getString(key);
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}

	@Override
	public String getCurrentLanguage() {
		return provider.getCurrentLanguage();
	}

}
