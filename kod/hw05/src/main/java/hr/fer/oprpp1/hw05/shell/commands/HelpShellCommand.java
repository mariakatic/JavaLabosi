package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.commands.parser.Parser;

public class HelpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		SortedMap<String, ShellCommand> mapa = env.commands();
		if (Parser.skipBlanks(arguments).length() == 0) {
			for (String key : mapa.keySet()) {
				env.writeln(key);
			}
		} else {
			ShellCommand command = mapa.get(Parser.skipBlanks(arguments));
			if (command != null) {
				env.writeln(command.getCommandName());
				String write = "";
				for (String s : command.getCommandDescription())
					write += s + "\n";
				
				env.write(write);
			} else {
				env.writeln("Naredba neispravno zadana.");
				return ShellStatus.CONTINUE;
			}
		}
		
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		
		lista.add("Naredba može primiti najviše jedan argument.");
		lista.add("Ako se pozove bez argumenata, ispisuje popis svih naredbi u terminalu.");
		lista.add("Ako joj se kao argument preda naredba, ispisuje naziv i opis te naredbe.");
		
		lista = Collections.unmodifiableList(lista);
		
		return lista;
	}

}
