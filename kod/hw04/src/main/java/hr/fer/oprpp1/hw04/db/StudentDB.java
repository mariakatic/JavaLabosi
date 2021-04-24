package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class StudentDB {
	
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("C:/Users/Maria/eclipse-workspace/database.txt"), StandardCharsets.UTF_8);
		StudentDatabase db = new StudentDatabase(lines);
		
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			
			String s = sc.next();
			
			if (s.equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			
			if (s.equals("query")) {
				String query = sc.nextLine();
				QueryParser parser = new QueryParser(query);
				List<StudentRecord> list = new LinkedList<>();
				if (parser.isDirectQuery()) {
					StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
					System.out.println("Using index for record retrieval.");
					list.add(r);
				} else {
					for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
						list.add(r);
					}
				}
				if (list.isEmpty())
					System.out.println("Records selected: 0");
				List<String> output = RecordFormatter.format(list);
				output.forEach(System.out::println);
			} else {
				System.out.println("Neispravna naredba.");
				break;
			}
		}
		sc.close();
	}

}
