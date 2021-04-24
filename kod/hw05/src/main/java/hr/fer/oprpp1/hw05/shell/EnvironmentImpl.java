package hr.fer.oprpp1.hw05.shell;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedMap;

public class EnvironmentImpl implements Environment {
	
	private Character multilineSymbol;
	private Character promptSymbol;
	private Character morelinesSymbol;
	private SortedMap<String, ShellCommand> commands;
	private Scanner sc;
	
	public EnvironmentImpl(Scanner sc) {
		this.sc = sc;
		multilineSymbol = '|';
		promptSymbol = '>';
		morelinesSymbol = '\\';
		commands = new SortedMapImpl<>();
	}

	@Override
	public String readLine() throws ShellIOException {
		String line = "";
		try {
			while(true) {
				if (sc.hasNextLine()) {
					line += sc.nextLine();
					if (!line.endsWith(String.valueOf(getMorelinesSymbol()))) {
						break;
					} else {
						line = line.substring(0, line.length()-1);
						write(String.valueOf(getMultilineSymbol()) + " ");
					}
				}
			}
	
		} catch(NoSuchElementException ex) {
			throw new ShellIOException();
		}
		return line;
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commands;
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		morelinesSymbol = symbol;
	}

}
