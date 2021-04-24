package hr.fer.oprpp1.hw05.shell;

import java.util.*;

import hr.fer.oprpp1.hw05.shell.commands.parser.Parser;

public class MyShell {
	
	public static void main(String[] args) {
		
		try {
			Scanner sc = new Scanner(System.in);
			Environment env = new EnvironmentImpl(sc);
			env.writeln("Welcome to MyShell v 1.0");
			env.write(env.getPromptSymbol() + " ");
			ShellStatus status = ShellStatus.CONTINUE;
			
			do {
				String s = env.readLine();
				String commandName = Parser.getWord(s);
				ShellCommand command = env.commands().get(commandName);
				if (command == null)
					throw new ShellIOException("Neispravno zadano ime naredbe.");
				String arguments = s.substring(commandName.length());
				status = command.executeCommand(env, arguments);
			
			} while (status != ShellStatus.TERMINATE);
			
			sc.close();
			
		} catch (ShellIOException ex) {
			System.out.println("Shell terminated");
		}
		
	}

}
