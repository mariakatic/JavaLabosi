package hr.fer.oprpp1.hw04.db;

import java.util.LinkedList;
import java.util.List;
import java.util.OptionalInt;

public class RecordFormatter {
	
	public static List<String> format(List<StudentRecord> records) {
		List<String> list = new LinkedList<>();
		
		if (records.size() == 0)
			return list;
		
		OptionalInt maxLast = records.stream().map(r -> r.getLastName()).mapToInt(String::length).max();
		int longestLastName = maxLast.getAsInt();
		OptionalInt maxFirst = records.stream().map(r -> r.getFirstName()).mapToInt(String::length).max();
		int longestFirstName = maxFirst.getAsInt();
		
		for (StudentRecord r : records) {
			StringBuilder sb = new StringBuilder();
			sb.append("| ")
				.append(r.getJmbag())
				.append(" | ")
				.append(r.getLastName());
			for (int i = r.getLastName().length(); i < longestLastName; i++) 
				sb.append(" ");
				
			sb.append(" | ")
				.append(r.getFirstName());
			for (int i = r.getFirstName().length(); i < longestFirstName; i++) 
				sb.append(" ");
				
			sb.append(" | ")
				.append(r.getFinalGrade())
				.append(" |");
			list.add(sb.toString());
			
		}
		
		StringBuilder line = new StringBuilder();
		line.append("+============+");
		for (int i = 0; i < longestLastName + 2; i++) 
			line.append("=");
		line.append("+");
		for (int i = 0; i < longestFirstName + 2; i++) 
			line.append("=");
		line.append("+===+");
		
		list.add(0, line.toString());
		list.add(line.toString());
		list.add("Records selected: " + records.size());
		
		return list;
	}

}
