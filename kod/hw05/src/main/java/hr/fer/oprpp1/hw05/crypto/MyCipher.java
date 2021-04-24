package hr.fer.oprpp1.hw05.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MyCipher {
	
	public static void crypt(int mode, String originFile, String resultFile, char[] pwd, char[] vec) {
		
		Path originP = Paths.get(originFile);
		Path resultP = Paths.get(resultFile);
		if (!Files.exists(originP) || !Files.isReadable(originP)) {
			throw new IllegalArgumentException(originFile + " mora postojati i mora biti omogućeno čitanje iz nje.");
		}
		
		InputStream is = null;
		OutputStream os = null;
		
		try {
			if (!Files.exists(resultP)) {
				resultP.toFile().createNewFile();
			}
			
			is = Files.newInputStream(originP);
			os = Files.newOutputStream(resultP);
			
			String keyText = new String(pwd);
			String ivText = new String(vec);
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(mode, keySpec, paramSpec);
			
			byte[] buffer = new byte[4096];
			int read;
			while ((read = is.read(buffer)) >= 0) {
				os.write(cipher.update(buffer, 0, read));
			}
			os.write(cipher.doFinal());
			
		} catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IOException | IllegalBlockSizeException | BadPaddingException ex) {
			throw new RuntimeException("Greska prilikom kripitranja/dekriptiranja datoteke.");
		} finally {
			try {
				if (is != null) is.close();
				if (os != null) os.close();
			} catch(IOException e) {}
		}
	}

}
