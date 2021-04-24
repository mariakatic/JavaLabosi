package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.commands.parser.Parser;

public class SymbolShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] args = new String[2];
		String s = Parser.skipBlanks(arguments);
		args[0] = Parser.getWord(s);
		String s2 = Parser.skipBlanks(s.substring(args[0].length(), s.length()));
		
		if (s2.length() > 1 || (s2.length() > 0 && Character.isAlphabetic(Parser.getChar(s2)))) {
			env.writeln("Neispravno zadana naredba.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		if (s2.length() > 0)
			args[1] = String.valueOf(Parser.getChar(s2));
		
		if (args[0].equals("PROMPT")) {
			if (args[1] == null) {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			} else {
				Character symb = env.getPromptSymbol();
				env.setPromptSymbol(args[1].charAt(0));
				env.writeln("Symbol for PROMPT changed from '" + symb + "' to '" + env.getPromptSymbol() + "'");
			}
			
			
		} else if (args[0].equals("MORELINES")) {
			if (args[1] == null) {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			} else {
				Character symb = env.getMorelinesSymbol();
				env.setMorelinesSymbol(args[1].charAt(0));
				env.writeln("Symbol for MORELINES changed from '" + symb + "' to '" + env.getMorelinesSymbol() + "'");
			}
			
			
		} else if (args[0].equals("MULTILINES")) {
			if (args[1] == null) {
				env.writeln("Symbol for MULTILINES is '" + env.getMultilineSymbol() + "'");
			} else {
				Character symb = env.getMultilineSymbol();
				env.setMultilineSymbol(args[1].charAt(0));
				env.writeln("Symbol for MULTILINES changed from '" + symb + "' to '" + env.getMultilineSymbol() + "'");
			}
			
			
		} else {
			env.writeln("Neispravno zadana naredba.");
		}
		
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		
		lista.add("Naredba prima jedan ili dva argumenta.");
		lista.add("Prvi argument moguu biti ključne riječi PROMPT, MORELINES, MULTILINE.");
		lista.add("Ako je zadan samo jedan argument, ispisuje simbol za taj argument.");
		lista.add("Ako su zadana dva argumenta, mijenja simbol prvog argumenta u simbol koji je zadan drugim argumentom.");
		
		lista = Collections.unmodifiableList(lista);
		
		return lista;
	}

}
