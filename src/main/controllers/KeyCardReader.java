package main.controllers;

import main.models.business.Word;
import main.models.business.WordAssociation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

/**
 * This class is reads a Keycard text file and a Word text file.
 * The description for the Keycards is defined in the text file called "KEYCARDS_README.TXT"
 * 
 * @author Rosy
 * Last update: 5 Feb 2019
 */
public class KeyCardReader {
	
	private String typesFileName; //types files
	private String wordsFileName; //words files
	
	
	private int[] cardTypes = new int[25]; //25 cards
	private Word[] allWords = new Word[392];
	private Word[] cardWords = new Word[25];

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

    /**
     * Read the Keycard words.
     * @return cardWords Array of Word objects
     * @throws IOException
     */
	public Word[] readKeycardWords() throws IOException {
		
		FileReader wordReader = new FileReader(wordsFileName);
		BufferedReader bufferedReader = new BufferedReader(wordReader);
		String[] word_line = new String[allWords.length];
        HashSet<WordAssociation> ass_words = new HashSet<WordAssociation>();

		//Read each line of the textfile
		String line;
		int count = 0;
		while ((line = bufferedReader.readLine()) != null) {
			System.out.println(line);
			word_line[count++] = line;
		}
		count = 0;
		// parse file line by line
        // create Word object
        for (String current_line :
                word_line) {
            String[] parts = current_line.split("(-|,)");

            // Create word association object
            for (int i = 1; i <= 5; i++){
                String[] associated_word_part = parts[i].split(" ");
                ass_words.add(new WordAssociation(associated_word_part[0],Integer.parseInt(associated_word_part[1])));
            }

            // Store into master words container
            allWords[count++] = new Word(parts[0],ass_words);
        }

		/*
		 * 25 times, search for a random word in the list from the master words container.
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
