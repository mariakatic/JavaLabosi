package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.commands.parser.Parser;

public class CharsetsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(Parser.skipBlanks(arguments).length() != 0) {
			env.writeln("Naredba ne prima niti jedan argument.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		SortedMap<String,Charset> mapa = Charset.availableCharsets();
		int granica = mapa.keySet().size();
		int idx = 0;
		for(String key : mapa.keySet()) {
			env.writeln(key);
			idx++;
			if (idx == granica) 
				env.write(env.getPromptSymbol() + " ");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		
		lista.add("Naredba ne prima niti jedan argument.");
		lista.add("Naredba ispisuje listu imena podržanih charset-ova za vašu Java platformu.");

		lista = Collections.unmodifiableList(lista);
		
		return lista;
	}
	


}
