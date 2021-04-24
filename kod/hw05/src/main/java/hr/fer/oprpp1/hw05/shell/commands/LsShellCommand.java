package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.commands.parser.Parser;

public class LsShellCommand implements ShellCommand {

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
		
		Path p = Paths.get(strPath);
		if (Files.notExists(p)) {
			env.writeln("Direktorij mora postojati.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		if (!Files.isDirectory(p)) {
			env.writeln("Zadan staza mora predstavljati direktorij.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		try {
			for (File f : p.toFile().listFiles()) {
				String first = "";
				first += f.isDirectory() ? "d" : "-";
				first += Files.isReadable(f.toPath()) ? "r" : "-";
				first += Files.isWritable(f.toPath()) ? "w" : "-";
				first += Files.isExecutable(f.toPath()) ? "x" : "-";
				
				long second = f.length();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Path path = f.toPath();
				BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String third = sdf.format(new Date(fileTime.toMillis()));
				
				String fourth = f.getName();
				String ispis = String.format("%s %10d %s %s", first, second, third, fourth);
				env.writeln(ispis);
			} 
		} catch (IOException ex) {
			env.writeln("Greška...");
		}
		
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		
		lista.add("Naredba prima jedan argument - direktorij.");
		lista.add("Naredba ispisuje popis svega što se nalazi u direktoriju.");
		lista.add("Prvi stupac govori redom je li zadani objekt direktorij, čitljiv, može li se pisati u njega i može li ga se izvršiti.");
		lista.add("U drugom stupcu navedena je veličina objekta, u trećem datum stvaranja te na kraju ime.");
		
		lista = Collections.unmodifiableList(lista);
		
		return lista;
	}

}
