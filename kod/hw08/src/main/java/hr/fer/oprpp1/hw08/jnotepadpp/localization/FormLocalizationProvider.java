package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class FormLocalizationProvider extends LocalizationProviderBridge {

	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		this.connect();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		this.disconnect();
	}

}
