package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
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

public class TreeShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String s = Parser.skipBlanks(arguments);
		String strPath = Parser.getUrl(s);
		
		String s2 = "";
		if (s.startsWith("\"")) {
			s2 = s.substring(strPath.length() + 2);
		} else {
			s2 = s.substring(strPath.length());
		}
		
		if (Parser.skipBlanks(s2).length() != 0) {
			env.writeln("Mora biti zadan točno jedan argument.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		Path path = Paths.get(strPath);
		
		if (Files.notExists(path)) {
			env.writeln("Direktorij mora postojati.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		if (!Files.isDirectory(path)) {
			env.writeln("Zadana staza mora predstavljati direktorij.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		env.write(printTree(path.toFile()));
		
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		
		lista.add("Naredba prima jedan argument - direktorij.");
		lista.add("Naredba ispisuje stablo direktorija.");
		
		lista = Collections.unmodifiableList(lista);
		
		return lista;
	}
	
	public static String printTree(File file) {
		StringBuilder sb = new StringBuilder();
		printTree(file, 0, sb);
		return sb.toString();
	}
	
	public static void printTree(File file, int blanks, StringBuilder sb) {
		for (int i = 0; i < blanks; i++) {
			sb.append(" ");
		}
		sb.append(file.getName()).append("\n");
		for (File f : file.listFiles()) {
			if (f.isDirectory()) 
				printTree(f, blanks + 2, sb);
			else {
				for (int i = 0; i < blanks; i++) {
					sb.append(" ");
				}
				sb.append("-").append(f.getName()).append("\n");
			}
		}
	}

}
