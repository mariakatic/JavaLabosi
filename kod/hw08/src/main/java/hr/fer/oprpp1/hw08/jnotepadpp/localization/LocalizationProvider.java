package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {

	private String language;
	private ResourceBundle bundle;
	
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	private LocalizationProvider() {
		language = "en";
	}
	
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
		fire();
	}

	@Override
	public String getString(String key) {
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.localization.prijevodi", Locale.forLanguageTag(language));
		return bundle.getString(key);
	}
	
	@Override
	public String getCurrentLanguage() {
		return language;
	}

}