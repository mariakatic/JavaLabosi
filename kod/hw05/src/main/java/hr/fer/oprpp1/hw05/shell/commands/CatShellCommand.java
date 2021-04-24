package hr.fer.oprpp1.hw05.shell.commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
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

public class CatShellCommand implements ShellCommand {
	 
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = new String[2];
		String s = Parser.skipBlanks(arguments);
		args[0] = Parser.getUrl(s);
		String str = s.substring(args[0].length());
		String str2 = Parser.skipBlanks(str);
		args[1] = Parser.getWord(str2);
		
		if(Parser.skipBlanks(str2.substring(args[1].length())).length() != 0) {
			env.writeln("Ne smiju biti zadana više od dva argumenta.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		Path p = Paths.get(args[0]);
		if (Files.notExists(p)) {
			env.writeln("Datoteka mora postojati.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		if (!Files.isRegularFile(p)) {
			env.writeln("Zadana staza mora predstavljati datoteku.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		Charset ch = null;
		if (args[1] == "") {
			ch = Charset.defaultCharset();
		} else {
			ch = Charset.availableCharsets().get(args[1]);
		}
		
		try (InputStream is = new FileInputStream(p.toFile())) {
			
			String text = "";
			byte[] buffer = new byte[4096];
			int read;
			while ((read = is.read(buffer)) >= 0) {
				byte[] helpArr = new byte[read];
				System.arraycopy(buffer, 0, helpArr, 0, read);
				text += new String(helpArr, ch);
			}
			
			env.writeln(text);
			
		} catch (IOException ex) {
			env.writeln("Neuspješno čitanje iz datoteke.");
		}
		
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		
		lista.add("Naredba prima jedan ili dva argumenta.");
		lista.add("Prvi argument je staza do neke datoteke");
		lista.add("Drugi argument je ime charseta koji se koristi za interpretaciju znakova iz bajtova.");
		lista.add("Ako drugi argument nije naveden, koristi se defaultni charset.");
		lista.add("Ova naredba otvara dani dokument i ispisuje njegov sadržaj na konzolu.");
		
		lista = Collections.unmodifiableList(lista);
		
		return lista;
	}

}
