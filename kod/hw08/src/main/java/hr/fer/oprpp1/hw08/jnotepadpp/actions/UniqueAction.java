package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import hr.fer.oprpp1.hw08.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.JNotepadPP;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;

public class UniqueAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;
	
	private DefaultMultipleDocumentModel model;

	public UniqueAction(String key, ILocalizationProvider provider, JNotepadPP frame) {
		super(key, provider);
		model = frame.getModel();
		setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextComponent c = model.getCurrentDocument().getTextComponent();
		Document doc = c.getDocument();
		Element root = doc.getDefaultRootElement();
		int firstLine = root.getElementIndex(c.getCaret().getMark());
		int lastLine = root.getElementIndex(c.getCaret().getDot());
		
		if (firstLine > lastLine) {
			int help = firstLine;
			firstLine = lastLine;
			lastLine = help;
		}

		String[] allLines = c.getText().split("\n");
		List<String> linesList = Arrays.asList(Arrays.copyOfRange(allLines, firstLine, lastLine))
				.stream()
		        .distinct()
		        .collect(Collectors.toList());

		String[] lines = new String[allLines.length - (lastLine - firstLine + 1) + linesList.size()];

		for (int i = 0; i < firstLine; i++) {
			lines[i] = allLines[i];
		}
		
		for (int i = firstLine; i < linesList.size(); i++) {
			lines[i] = linesList.get(i - firstLine);
		}
		
		for (int i = linesList.size(); i < lines.length; i++) {
			lines[i] = allLines[i + lastLine];
		}
		
		String rez = new String();
		for (String s : lines) {
			rez += s + "\n"; 
		}
		model.getCurrentDocument().getTextComponent().setText(rez);
	}

}
