package hr.fer.oprpp1.hw05.shell.commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class HexdumpShellCommand implements ShellCommand {

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
		
		Path path = Paths.get(pathS);
		
		if (Files.notExists(path)) {
			env.writeln("Direktorij mora postojati.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		if (!Files.isRegularFile(path)) {
			env.writeln("Zadana staza mora predstavljati datoteku.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		
		
		try (InputStream is = new FileInputStream(path.toFile())) {
			int brojac = 0;
			boolean flag = true;
			String line = "";
			
			while (flag) {
				String hex = "";
				String chars = "";
				int sign;
				for (int i = 0; i < 16; i++) {
					sign = is.read();
					
					if (sign == -1) {
						for (int j = i; j < 16; j++) {
							if (j == 7) hex += "   |";
							else if (j == 15) hex += "   | ";
							else hex += "   ";
						}
						flag = false;
						break;
				    }
					
					String sHex = Integer.toHexString(sign).toUpperCase();
					if(sHex.length() == 1) sHex = "0" + sHex;
					
					hex += String.format("%2s", sHex);
					if (i == 7) hex += "|";
					else if (i == 15) hex += " | ";
					else hex += " ";
					
					if (sign < 32 || sign > 127) {
						sign = 46;
					}
					chars += String.valueOf((char)sign);
				}
				line += String.format("%08d", brojac*10) + ": " + hex + chars + "\n";
				brojac++;
			}
			
			env.write(line);
		
		
		} catch (IOException ex) {
			env.writeln("Greška...");
		}
		
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		
		lista.add("Naredba prima točno jedan argument koji predstavlja ime datoteke.");
		lista.add("Naredba ispisuje datoteku u heksadecimalnom obliku i u izvornom obliku.");
		
		lista = Collections.unmodifiableList(lista);
		
		return lista;
	}

}
