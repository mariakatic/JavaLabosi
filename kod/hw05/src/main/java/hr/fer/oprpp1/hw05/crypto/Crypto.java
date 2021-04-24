package hr.fer.oprpp1.hw05.crypto;

import java.util.Arrays;
import java.util.Scanner;

public class Crypto {
	
	public static void main(String[] args)  {
		
		if (args.length == 2 && args[0].equals("checksha")) {
			char[] expected = new char[64];
			char[] computed = new char[64];
			
			computed = CheckSha.checksha(args[1]);
			
			System.out.println("Please provide expected sha-256 digest for " + args[1] + ":");
			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();
			sc.close();
			
			if (input.length() != 64 || !input.matches("-?[0-9a-fA-F]+")) {
				System.out.println("Zaštitna suma mora se sastojati od 64 heksadecimalne znamenke.");
				return;
			}
			
			expected = input.toCharArray();
			
			if (Arrays.equals(expected, computed)) {
				System.out.println("Digesting completed. Digest of " + args[1] + " matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of " + args[1] + " does not match the expected digest. Digest was: " + new String(computed));
			}
			
			
		} else if (args.length == 3 && (args[0].equals("encrypt") || args[0].equals("decrypt"))) {
			char[] password = new char[32];
			char[] initVector = new char[32];
			
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			Scanner sc = new Scanner(System.in);
			String input = sc.nextLine();
			
			if (input.length() != 32 || !input.matches("-?[0-9a-fA-F]+")) {
				System.out.println("Zaštitna šifra mora se sastojati od 64 heksadecimalne znamenke.");
				sc.close();
				return;
			} 
			
			password = input.toCharArray();
			
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			input = sc.nextLine();
			sc.close();
			
			if (input.length() != 32 || !input.matches("-?[0-9a-fA-F]+")) {
				System.out.println("Vektor inicijalziacije mora se sastojati od 64 heksadecimalne znamenke.");
				return;
			} 
			
			initVector = input.toCharArray();
			
			if (args[0].equals("encrypt")) {
				MyCipher.crypt(1, args[1], args[2], password, initVector);
				System.out.println("Encryption completed. Generated file " + args[1] + " based on file " + args[2] + ".");
			} else {
				MyCipher.crypt(2, args[1], args[2], password, initVector);
				System.out.println("Decryption completed. Generated file " + args[1] + " based on file " + args[2] + ".");
			}
			
		} else {
			System.out.println("Neispravan unos argumenata.");
		}
		
		
	}

}
