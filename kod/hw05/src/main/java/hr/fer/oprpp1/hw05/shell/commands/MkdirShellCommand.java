package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.commands.parser.Parser;

public class MkdirShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String s = Parser.skipBlanks(arguments);
		String pathS = Parser.getUrl(s);
		
		String s2 = "";
		if (s.startsWith("\"")) {
			s2 = s.substring(pathS.length() + 2);
		} else {
			s2 = s.substring(pathS.length());
		}
		
		if (Parser.skipBlanks(s2).length() != 0) {
			env.writeln("Mora biti zadan točno jedan argument.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		Path dir = Paths.get(pathS);
		
		if (Files.exists(dir)) {
			env.writeln("Datoteka/direktorij već postoji.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		if(!dir.toFile().mkdir()) {
			env.writeln("Neuspješno stvaranje direktorija.");
		}
		
		env.writeln("Direktorij uspješno stvoren.");
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		
		lista.add("Naredba prima jedan argument - direktorij.");
		lista.add("Naredba stvara navedeni direktorij.");
		
		lista = Collections.unmodifiableList(lista);
		
		return lista;
	}

}
