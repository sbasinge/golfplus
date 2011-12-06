package com.basinc.golfminus.util;

import java.io.ByteArrayOutputStream;
import java.security.Provider;
import java.security.Security;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

/**
 * Utility class for encrypting and decrypting data using currently the AES
 * routines.
 * 
 */
public class EncryptionUtils {

	private static Logger log = Logger.getLogger(EncryptionUtils.class);
	private static final String ALGORITHM = "AES";
	private static final String AES_KEY = "138-250-228-149-209-13-189-103-145-215-42-99-30-105-7-59";

	public static String encrypt(String source) {
		// Check for null
		if (source == null || "".equalsIgnoreCase(source.trim())) {
			return "";
		}

		if (isEncrypted(source)) {
			return source;
		}

		try {
			// Get our secret key
			// Key key = getKey();
			// KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
			// SecretKey key = keyGen.generateKey();
			SecretKey key = new SecretKeySpec(getBytes(AES_KEY), ALGORITHM);

			// Create the cipher
			Cipher cipher = Cipher.getInstance(ALGORITHM);

			// Initialize the cipher for encryption
			cipher.init(Cipher.ENCRYPT_MODE, key);

			// Our cleartext as bytes
			byte[] cleartext = source.getBytes();

			// Encrypt the cleartext
			byte[] ciphertext = cipher.doFinal(cleartext);

			// Return a String representation of the cipher text
			return getString(ciphertext);
		} catch (Exception e) {
			log.error("Error encrypting data.", e);
			return null;
		}
	}

	public static String generateKey() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
			SecretKey key = keyGen.generateKey();
			byte[] bytes = key.getEncoded();
			return getString(bytes);
		} catch (Exception e) {
			log.error("Error generating encryption key.", e);
			return null;
		}
	}

	public static String decrypt(String source) {
		try {
			if (source == null || "".equalsIgnoreCase(source.trim())) {
				return "";
			}

			// If the source is not encrypted, simply return it.
			if (!isEncrypted(source)) {
				return source;
			}

			// Get our secret key
			// Key key = getKey();
			// KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
			// SecretKey key = keyGen.generateKey();
			SecretKey key = new SecretKeySpec(getBytes(AES_KEY), ALGORITHM);

			// Create the cipher
			Cipher cipher = Cipher.getInstance(ALGORITHM);

			// Encrypt the cleartext
			byte[] ciphertext = getBytes(source);

			// Initialize the same cipher for decryption
			cipher.init(Cipher.DECRYPT_MODE, key);

			// Decrypt the ciphertext
			byte[] cleartext = cipher.doFinal(ciphertext);

			// Return the clear text
			return new String(cleartext);
		} catch (Exception e) {
			log.error("Error decrypting data.", e);
			return null;
		}
	}

	// private static Key getKey()
	// {
	// try
	// {
	// byte[] bytes = getBytes(KEY_STRING);
	// DESKeySpec pass = new DESKeySpec(bytes);
	// SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
	// SecretKey s = skf.generateSecret(pass);
	// return s;
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// return null;
	// }

	/**
	 * Returns true if the specified text is encrypted, false otherwise
	 * 
	 * @param text
	 * @return boolean
	 */
	public static boolean isEncrypted(String text) {
		// Check for null
		if (text == null) {
			return false;
		}

		// If the string does not have any separators then it is not encrypted
		if (text.indexOf('-') == -1) {
			return false;
		}

		StringTokenizer st = new StringTokenizer(text, "-", false);
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (token.length() > 3) {
				return false;
			}
			for (int i = 0; i < token.length(); i++) {
				if (!Character.isDigit(token.charAt(i))) {
					return false;
				}
			}
		}
		return true;
	}

	private static String getString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			sb.append((int) (0x00FF & b));
			if (i + 1 < bytes.length) {
				sb.append("-");
			}
		}
		return sb.toString();
	}

	private static byte[] getBytes(String str) {
		if (str == null) {
			return null;
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		StringTokenizer st = new StringTokenizer(str, "-", false);
		while (st.hasMoreTokens()) {
			int i = Integer.parseInt(st.nextToken());
			bos.write((byte) i);
		}
		return bos.toByteArray();
	}

	// private static String generateBlowfishKey() {
	// try {
	// KeyGenerator kgen = KeyGenerator.getInstance("Blowfish");
	// SecretKey skey = kgen.generateKey();
	// byte[] raw = skey.getEncoded();
	// return getString(raw);
	// } catch (Exception e) {
	// log.error("Error generating encryption key.", e);
	// return null;
	// }
	// }

	public static void main(String[] args) {
		showProviders();
		if (args.length < 1) {
			System.out.println("Usage: EncryptionUtils <command> < args > ");
			System.out.println("\t<command>: encrypt, decrypt, generate - key");
			System.exit(0);

		}
		String command = args[0];
		if (command.equalsIgnoreCase("generate-key")) {
			System.out.println("New key: " + generateKey());
		} else if (command.equalsIgnoreCase("encrypt")) {
			String plaintext = args[1];
			String cipherText = encrypt(plaintext);
			System.out.println(plaintext + " = " + cipherText);
			System.out.println(cipherText + " = " + decrypt(cipherText));
		} else if (command.equalsIgnoreCase("decrypt")) {
			String ciphertext = args[1];
			System.out.println(ciphertext + " = " + decrypt(ciphertext));
		}
	}

	public static void showProviders() {
		try {
			Provider[] providers = Security.getProviders();
			for (int i = 0; i < providers.length; i++) {
				System.out.println("Provider: " + providers[i].getName() + ", " + providers[i].getInfo());
				for (Object o : providers[i].keySet()) {
					String key = (String) o;
					String value = (String) providers[i].get(key);
					System.out.println("\t" + key + " = " + value);
				}
			}
		} catch (Exception e) {
			log.error("Error listing security providers.", e);
		}
	}
}
