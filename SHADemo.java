// Java program to calculate SHA hash value

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class SHADemo {
	public static byte[] shaHash(byte[] input, String algo)
	{
		try {
			MessageDigest md = MessageDigest.getInstance(algo);

			byte[] messageDigest = md.digest(input);

			return messageDigest;
		}

		catch (NoSuchAlgorithmException e) {
			System.out.printf("Algorithm \"%s\" not regonized.", algo);
			return null;
		}
	}

	public static String bytesToString(byte[] bytes) {
		// // Convert byte array into signum representation
		// BigInteger no = new BigInteger(1, bytes);

		// // Convert message digest into hex value
		// String hashtext = no.toString(16);

		// // Add preceding 0s to make it 32 bit
		// while (hashtext.length() < 32) {
		// 	hashtext = "0" + hashtext;
		// }

		// // return the HashText
		// return hashtext;

		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02X ", b));
		}
		return sb.toString();

	}
	public static boolean VerifyInput(String input, String[] validStrings) {
		List validList = Arrays.asList(validStrings);
		if(validList.contains(input)) {
			return true;
		}
		return false;
	}

	public static byte[] ReadFile(String filename) {
		try {
			Path path = Paths.get(filename);
			if (Files.exists(path)) {
				byte[] data = Files.readAllBytes(path);
				return data;
			}
			else {
				throw new FileNotFoundException();
			}
		}
		catch (FileNotFoundException e)  {
			return null;
		}
		catch (IOException e) {
			return null;
		}
	}
	public static boolean WriteFile(String filename, byte[] bytes) {
		try {
			Path path = Paths.get(filename);
			if (Files.exists(path)) {
				Files.write(path, bytes);
				return true;
			}
			else {
				throw new FileNotFoundException();
			}
		}
		catch (FileNotFoundException e)  {
			return false;
		}
		catch (IOException e) {
			return false;
		}
	}

	// Driver code
	public static void main(String args[]) throws NoSuchAlgorithmException
	{
		if(args.length < 3) {
			System.exit(0);
		}
		String inputFileName = args[0];
		String outputFileName = args[1];
		String algorithmName = args[2];
		byte[] inputString = ReadFile(inputFileName);
		if(inputString == null) {
			System.exit(0);
		}
		byte[] output = shaHash(inputString, algorithmName);
		if(output == null) {
			System.exit(0);
		}
		System.out.printf("Hash using %s: %s", algorithmName, bytesToString(output));
		boolean success = WriteFile(outputFileName, output);
		if(success == false) {
			System.exit(0);
		}
	}
}
