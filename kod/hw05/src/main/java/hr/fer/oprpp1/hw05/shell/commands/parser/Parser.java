package hr.fer.oprpp1.hw05.shell.commands.parser;

public class Parser {
	
	public static String skipBlanks(String input) {
		int idx = 0;
		while (idx < input.length()) {
			if (input.charAt(idx) != ' ') 
				return input.substring(idx, input.length());
			idx++;
		}
		return "";
	}
	
	public static String getWord(String input) {
		int idx = 0;
		StringBuilder sb = new StringBuilder();
		while (idx < input.length() && input.charAt(idx) != ' ') {
			sb.append(input.charAt(idx));
			idx++;
		}
		return sb.toString();
	}
	
	public static Character getChar(String input) {
		if (input.length() != 0)
			return input.charAt(0);
		
		return null;
	}
	
	public static String getUrl(String input) {
		if (input.length() != 0) {
			if (input.startsWith("\"")) {
				StringBuilder sb = new StringBuilder();
				boolean flag = false;
				int idx = 1;
				while(idx < input.length()) {
					if (input.charAt(idx) == '"') {
						flag = true;
						break;
					}
					sb.append(input.charAt(idx));
					idx++;
				} 
				if (flag) {
					return sb.toString();
				} 
			} else {
				return getWord(input);
			}
		}
		return "";
	}
	

}
