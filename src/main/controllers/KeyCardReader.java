package main.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * This class is reads a Keycard text file and a Word text file.
 * The description for the Keycards is defined in the text file called "KEYCARDS_README.TXT"
 * 
 * @author Rosy
 * Last update: 2 Feb 2019
 */
public class KeyCardReader {
	
	private String typesFileName; //types files
	private String wordsFileName; //words files
	
	private int[] cardTypes = new int[25]; //25 cards
	private String[] cardWords = new String[25]; //25 cards
	private String[] allWords = new String[100]; //100 codenames
	
	/*
	 * Empty constructor
	 */
	public KeyCardReader() {
		System.out.println("No text files given.");
	}
	
	/*
	 * Parameter constructor
	 */
	public KeyCardReader(String typesFileName, String wordsFileName) {
		this.typesFileName = typesFileName;
		this.wordsFileName = wordsFileName;
	}
	
	/*
	 * This method reads each Card's type.
	 */
	public int[] readKeycardTypes() throws IOException {
		
		FileReader typeReader = new FileReader(typesFileName);
		
		//Read each character of the textfile
		int count = 0;
		int i;
		while ((i=typeReader.read()) != -1) {	
			cardTypes[count++] = Character.getNumericValue((char)i);
		}
		
		typeReader.close();
		
		return cardTypes;
	}
	
	/*
	 * Read the Keycard words.
	 */
	public String[] readKeycardWords() throws IOException {
		
		FileReader wordReader = new FileReader(wordsFileName);
		BufferedReader bufferedReader = new BufferedReader(wordReader);
		
		//Read each line of the textfile
		String line;
		int count = 0;
		while ((line = bufferedReader.readLine()) != null) {
			allWords[count++] = line;
		}
		count = 0;
		
		/*
		 * 25 times, search for a random word in the list from the text file.
		 */
		for (int i = 0; i < cardWords.length; i++) {
			int index = new Random().nextInt(allWords.length-1);
			cardWords[i] = allWords[index];
		}
		
		wordReader.close();
		bufferedReader.close();
		
		return cardWords;
		
	}

	/*
	 * Prints the Keycard type content on the console.
	 */
	public void printKeyCard() throws IOException {
		
		FileReader typeReader = new FileReader(typesFileName);
		
		int i; 
		while ((i=typeReader.read()) != -1) 
			System.out.println((char)i);
		
		typeReader.close();
		
	} 
	
}
