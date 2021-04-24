package hr.fer.oprpp1.hw04.db;

import java.util.*;

public class StudentDatabase {
	
	private List<StudentRecord> students;
	private Map<String, Integer> jmbagMap;
	private int idx;
	
	public StudentDatabase(List<String> rows) {
		students = new LinkedList<>();
		jmbagMap = new HashMap<>();
		parse(rows);
	}
	
	public String extractToken(char[] row) {
		StringBuilder sb = new StringBuilder();
		while (idx < row.length && row[idx] != ' ' && row[idx] != '\t') {
			sb.append(row[idx]);
			idx++;
		}
		return sb.toString();
	}
	
	public void parse(List<String> rows) {
		for (int i = 0; i < rows.size(); i++) {
			idx = 0;
			char[] row = rows.get(i).toCharArray();
			
			String jmbag = extractToken(row);
			
			if (jmbagMap.containsKey(jmbag)) 
				throw new IllegalArgumentException("Jmbag mora biti jedinstven.");
			
			if (jmbag.length() != 10)
				throw new IllegalArgumentException("Jmbag mora sadrzavati 10 znamenki");
			
			while (idx < row.length && (row[idx] == ' ' || row[idx] == '\t')) 
				idx++;
			
			String lastName = extractToken(row);
			
			while (idx < row.length && (row[idx] == ' ' || row[idx] == '\t')) 
				idx++;
			
			String firstName = extractToken(row);
			
			while (idx < row.length && (row[idx] == ' ' || row[idx] == '\t')) 
				idx++;
			
			Integer finalGrade;
			if (Character.isDigit(row[idx])) {
				String finalG = String.valueOf(row[idx]);
				idx++;
				
				try {
					finalGrade = Integer.parseInt(finalG);
					if (finalGrade < 1 || finalGrade > 5) 
						throw new NumberFormatException();
				} catch(NumberFormatException ex) {
					throw new IllegalArgumentException("Zavrsna ocjena mora biti izmedu 1 i 5.");
				}
			} else {
				lastName += " " + firstName;
				firstName = extractToken(row);
				
				while (idx < row.length && (row[idx] == ' ' || row[idx] == '\t')) 
					idx++;
				
				String finalG = String.valueOf(row[idx]);
				idx++;
				
				try {
					finalGrade = Integer.parseInt(finalG);
					if (finalGrade < 1 || finalGrade > 5) 
						throw new NumberFormatException();
				} catch(NumberFormatException ex) {
					throw new IllegalArgumentException("Zavrsna ocjena mora biti izmedu 1 i 5.");
				}
			}
			
			
			StudentRecord record = new StudentRecord(jmbag, lastName, firstName, finalGrade);
			students.add(record);
			jmbagMap.put(jmbag, i);
		}
	}
	
	public StudentRecord forJMBAG(String jmbag) {
		int idx = jmbagMap.get(jmbag);
		return students.get(idx);
	}
	
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> res = new LinkedList<>();
		students.forEach(s -> {
			if (filter.accepts(s)) 
				res.add(s);
		});
		return res;
	}
	

}
