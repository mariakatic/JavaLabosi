package hr.fer.oprpp1.hw05.shell.commands;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

public class CopyShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = new String[2];
		String s1 = Parser.skipBlanks(arguments);
		args[0] = Parser.getUrl(s1);
		
		String s2 = "";
		if (s1.startsWith("\"")) {
			s2 = Parser.skipBlanks(s1.substring(args[0].length()+2));
		} else {
			s2 = Parser.skipBlanks(s1.substring(args[0].length()));
		}
		
		args[1] = Parser.getUrl(s2);
		
		String s3 = "";
		if (s2.startsWith("\"")) {
			s3 = s2.substring(args[1].length() + 2);
		} else {
			s3 = s2.substring(args[1].length());
		}
		
		if(Parser.skipBlanks(s3).length() != 0) {
			env.writeln("Moraju biti zadana točno dva argumenta.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		Path p1 = Paths.get(args[0]);
		Path p2 = Paths.get(args[1]);
		
		if (Files.notExists(p1)) {
			env.writeln("Prva datoteka mora postojati.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		if (!Files.isRegularFile(p1)) {
			env.writeln("Prva staza mora biti datoteka");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		if (Files.exists(p2)) {
			if (Files.isRegularFile(p2)) {
				env.writeln("Druga datoteka već postoji. Je li dopušteno pisanje preko tog sadržaja? (D/N)");
				env.write(env.getPromptSymbol() + " ");
				String s = env.readLine();
				if (s.equals("N")) {
					env.write(env.getPromptSymbol() + " ");
					return ShellStatus.CONTINUE;
				} else if (!s.equals("D")) {
					env.writeln("Neispravan odgovor.");
					env.write(env.getPromptSymbol() + " ");
					return ShellStatus.CONTINUE;
				}
			} else {
				p2 = Paths.get(args[1]  + "\\" + p1.getFileName());
				try {
					System.out.println(p2);
					p2.toFile().createNewFile();
				} catch (IOException e) {
					env.writeln("Neuspješno stvaranje druge datoteke.");
					env.write(env.getPromptSymbol() + " ");
					return ShellStatus.CONTINUE;
				}
			} 
		} else {
			try {
				p2.toFile().createNewFile();
			} catch (IOException e) {
				env.writeln("Neuspješno stvaranje druge datoteke.");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}
		}
		
		InputStream is = null;
		OutputStream os = null;
		
		try {
			is = new FileInputStream(p1.toFile());
			os = new FileOutputStream(p2.toFile());
			byte[] buffer = new byte[4096];
			int read;
			while((read = is.read(buffer)) >= 0) {
				os.write(buffer, 0, read);
			}
			
		} catch (IOException e) {
			env.writeln("Kopiranje nije uspjelo.");
		} finally {
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				env.writeln("Kopiranje nije uspjelo.");
			}
		}
		
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		
		lista.add("Naredba prima dva argumenta");
		lista.add("Prvi argument je izvorna datoteka, a drugi odredišna datoteka.");
		lista.add("Ako odredišna datoteka postoji na disku, naredba pita za dopuštenje može li pisati preko sadržaja koji tu postoji.");
		lista.add("Prvi argument ne smije predstavljati direktorij, a ako je drugi argument direktorij onda se stvara nova datoteka u tom direktoriju.");
		lista.add("Naredba kopira sadržaj izvorne datoteke u odredišnu datoteku.");
		
		lista = Collections.unmodifiableList(lista);
		
		return lista;
	}

}
