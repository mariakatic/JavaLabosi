package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.awt.event.WindowAdapter;
import java.util.*;

public abstract class AbstractLocalizationProvider extends WindowAdapter implements ILocalizationProvider {
	
	private List<ILocalizationListener> listeners;
	
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}
	
	public void fire() {
		for(ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}
}
