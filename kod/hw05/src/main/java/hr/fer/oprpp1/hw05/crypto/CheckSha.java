package hr.fer.oprpp1.hw05.crypto;

import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CheckSha {
	
	public static char[] checksha(String path)  {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) { }
		
		Path p = Paths.get(path);
		if (!Files.exists(p) || !Files.isReadable(p)) {
			throw new IllegalArgumentException("Datoteka ne postoji ili iz nje nije moguće čitati sadržaj.");
		}
		
		try (InputStream is = Files.newInputStream(p)) {;
			
			byte[] buffer = new byte[4096];
			int read;
			while ((read = is.read(buffer)) >= 0) {
				sha.update(buffer, 0, read);
			}
			
		} catch (IOException e) {
			throw new RuntimeException();
		}
		
		return Util.bytetohex(sha.digest()).toCharArray();
	}

} 
