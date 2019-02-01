package res;

import java.io.FileReader;
import java.io.IOException;

/**
 * This class is reads a Keycard text file.
 * @author Rosy
 * 
 *
 */
public class KeyCardReader {
	
	private String typesFileName;
	private String wordsFileName;
	private int[] cardTypes = new int[25];
	
	public KeyCardReader(String typesFileName, String wordsFileName) {
		this.typesFileName = typesFileName;
		this.wordsFileName = wordsFileName;
	}
	
	/*
	 * Read the Keycard type.
	 * Keycard.txt CANNOT have spaces or line skips.
	 */
	public int[] readKeycardTypes() throws IOException {
		
		FileReader typeReader = new FileReader(typesFileName);
		//FileReader wordReader = new FileReader(wordsFileName);

		/*
		 * Read the types
		 */
		int count = 0;
		int i;
		while ((i=typeReader.read()) != -1) {	
			cardTypes[count] = Character.getNumericValue((char)i);	
			count++;
		}
		
		typeReader.close();
		
		return cardTypes;
	}
	
	
	
	/*
	 * Prints the Keycard type content on the console
	 */
	public void printKeyCard() throws IOException {
		
		FileReader typeReader = new FileReader(typesFileName);
		
		int i; 
		while ((i=typeReader.read()) != -1) 
			System.out.println((char)i);
		
		typeReader.close();
		
	} 
	
}
