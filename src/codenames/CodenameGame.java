package codenames;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

/**
 * @authors Rosy Teasdale, chavind
 * 
 * Project made by pk-b to simulate the game CODENAME
 * Array used to implement the board
 * Arraylist used to keep of the count to avoid duplicates
 * Class Card keeps the attributes.
 * 
 */
public class CodenameGame  {

	public static Scanner FileReader;//name of Scanner
	
	static boolean StatusWait = true;//finds status of whether any resources is loading or not
	
	static int count = 5;//Instances to record what random values are picked
	
	static ArrayList<Integer> CountTracker= new ArrayList<>();//Keep track of the random Counts
	
	static Card[][] cards;// Grid Array declaration
	
	static Random rand;//Random generator 
	
	static int RedPlayers=9, BluePlayers=8;//Players
	
	public	static boolean turn = true; //current player's turn

	
	
	public static void main(String[] args) {
		
		cards = new Card[5][5];// Initialize the array
		
		System.out.println("Welcome to Codename!");
		
		//Load dictionary
		loadingWordsDIR();
		
		//We could place a waiting bar or a waiting logo, until game start (depending on waiting time).
		while(StatusWait == true) {

			System.out.println("Initializing game");
		
			//Board Set up before games start
			boardSetup();
		
			 //Simple PrintOut
			display();
		}
		
		System.out.println("Game loaded.");
	
//		if(synonym("manchester"))
//			System.out.println("true");
//			System.out.println("false");
	}

	/**
	 * Method to load the dictionary words.
	 */
	public static void loadingWordsDIR() {
		FileReader reader;
		
		//System.out.println("Loading Directory of Words");
		
		try { //Using try catch just to catch file not found Ex
			reader = new FileReader("miniDictionary.txt");
			FileReader = new Scanner(reader);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		//System.out.println("Successfully Loaded Directory");
	}

	/**
	 * Method to reset the counter 
	 * @param n
	 */
	public static void resetCounter(int n) {
		
		rand=  new Random();
		count = rand.nextInt(n); 
		
		for(int i =0; i<100;i++) {
			
			if((CountTracker.contains(count))) {
				resetCounter(n);
			}
			else
				CountTracker.add(count);
			
			break;
		}
	}

	/**
	 * Method to setup the board cards.	
	 */
	public static void boardSetup() {
		
		/*
		 * Randomize words
		 */
		resetCounter(cards.length*cards[0].length);
		
		randomizeWords();
		System.out.println("Successfully Randomized word");
		
		randomizeColors();
		System.out.println("Successfully Randomized Colors");

		/*
		 * randomize assassin
		 */
		FileReader.close();

		StatusWait = false;
	}
	
	/**
	 * Method to randomize the words.
	 */
	public static void randomizeWords() {	
		/*
		 * Iteration to fill in the board
		 */
		System.out.println("Randomizing words and colors");
			
		String tempWord= null;// Temporary String to keep sc.next
		
		for(int i = 0; i < cards.length; i++) {
			
			for(int j = 0; j < cards[0].length; j++) {
				
				//Instantiating each element of array first	
				cards[i][j] = new Card();
				
				/**
				 * Count the number of words to skip
				 */
				while(true) {
					if (count <= 0)
						break;
					tempWord =FileReader.next();
					--count;
				}
				
				if(tempWord != null)
					cards[i][j].setWord(tempWord);
				
				cards[i][j].setWord(FileReader.next());
	
				resetCounter(36);
				
				loadingWordsDIR();

			}
		}
	}
	
	/**
	 * Checks if a word is a synonym.
	 * @param word
	 * @return true if the word is a synonym.
	 */
	public static  boolean synonym(String word) {
		loadingWordsDIR();
		
		System.out.println(FileReader);
		
		return true;
	}
	
	/**
	 * This is where the colors are distributed on the board and the assassin is determined
	 * First the assassin Hides, the we assign the Red Players and blue players in random spaces.
	 * If statement ascertains that no duplicates are met.
	 */
	public static void  randomizeColors() {
		
		cards[rand.nextInt(cards.length)][rand.nextInt(cards[0].length)].setColor("Black"); //Assassin
		
		for(int i = 0;i<RedPlayers;i++) { //Distributing Red Players
			int	rowRED = rand.nextInt((5));
			int columRED= rand.nextInt(5);
				
			while(cards[rowRED][columRED].getColor()!="Wild") {
				rowRED = rand.nextInt((5));
				columRED= rand.nextInt(5);
			}
			
			cards[rowRED][columRED].setColor("Red");
		} 
		//Anti-duplicate
					
					
		for(int i = 0;i < BluePlayers; i++) { //Distributing Blue Players
			int	rowBLUE = rand.nextInt(5);
			int columnBLUE= rand.nextInt(5);
			
			while(cards[rowBLUE][columnBLUE].getColor() != "Wild") { //Anti-duplicate
				rowBLUE = rand.nextInt(5);
				columnBLUE= rand.nextInt(5);	
			}
				
			cards[rowBLUE][columnBLUE].setColor("Blue");
				
		}
		
	}
	
	/**
	 * Display on the console.
	 */
	public static void display() {
		
		for(int i =0; i<cards.length; i++) {
			
			for(int j=0; j<cards[0].length;j++) {
				
				System.out.print(cards[i][j].getWord()+ " , " + cards[i][j].getColor());
				String tab = (((cards[i][j].getWord().length()+ cards[i][j].getColor().length()) <=10 ) ? "\t\t":"\t");
				System.out.print(tab);
				
			}			
			
			System.out.println();
		}
	}
	
	
	/**
	 * Count Validator to avoid duplicates
	 */


}

