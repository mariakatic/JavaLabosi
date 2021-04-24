package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.SimpleHashtable;
import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

public class Demo {
	
	public static void main(String[] args) {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		//examMarks.remove("Ivana");
		
		Integer kristinaGrade = examMarks.get("Kristina");
		System.out.println("Kristina's exam grade is: " + kristinaGrade); 
		System.out.println("Number of stored pairs: " + examMarks.size()); 
		System.out.println(examMarks.containsKey("Ante"));
		System.out.println(examMarks);
		TableEntry<String, Integer>[] arr = examMarks.toArray();
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}

}
