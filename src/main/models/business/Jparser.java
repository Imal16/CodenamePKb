package main.models.business;

import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;

/**
 * The parser parses a Json file containing the dictionary of codenames(keys) and its relation(dictionary array)
 * Once parser it will have 2 main collections to hold the data obtained from the parsing:
 * the first is a hash map with the key words and their relations
 * the second is a list of 25 randomly generated keycard words(codenames) from the hashmap.
 *  
 * 
 * @author Ihsaan Malek
 * @version 03/07/2019
 */
public class Jparser {

	private static  Properties properties = null;
	
	private  JsonObject jsonObject = null;
	
	static {properties = new Properties();}
	
	private  HashMap<String,ArrayList<String>> jsonfilestorage;
	private  List<String> listofkeys;
	private  List<String> listofkeycards;
	
	private  int keycards= 25;		//Number of words in play
	
	public Jparser() {
		this.jsonfilestorage = new HashMap<String,ArrayList<String>>();
		this.listofkeys =new ArrayList<String>();
		this.listofkeycards =new ArrayList<String>();
	}

	//read file, creates JSON object
	public JsonObject readfile() {
		try {
			JsonParser jsonParser = new JsonParser();
			File file = new File("resources/keycards/testswords.json");
			
			Object object =jsonParser.parse(new FileReader(file));
			
			this.jsonObject=(JsonObject) object;
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		return this.jsonObject;
	}

	//converts json object into hashmap
	public HashMap<String,ArrayList<String>> parseJson(JsonObject jsonObject) throws ParseException{
		Map<String, Object> attributes = new HashMap<String, Object>();
		Set<Entry<String, JsonElement>> entrySet=jsonObject.entrySet();
		for(Map.Entry<String,JsonElement> entry: entrySet) {
			attributes.put(entry.getKey(), jsonObject.get(entry.getKey()));
			this.jsonfilestorage.put(entry.getKey(), new ArrayList<String>());
			for (int i=0; i< jsonObject.get(entry.getKey()).getAsJsonArray().size(); i++) {
				this.jsonfilestorage.get(entry.getKey()).add(jsonObject.get(entry.getKey()).getAsJsonArray().get(i).toString().replace("\"",""));
			}
		}
		return this.jsonfilestorage;
	}

	//gets 25 random words from the hashmap
	public List<String> generaterandomkeycards(HashMap<String,ArrayList<String>> jsonfilestorage){
		this.listofkeys.addAll(jsonfilestorage.keySet());
		Collections.shuffle(this.listofkeys);
		this.listofkeycards=this.listofkeys.subList(0, keycards);
		return this.listofkeycards;
		
	}
	public HashMap<String, ArrayList<String>> getHashmap() {
		return this.jsonfilestorage;
	}
	public List<String> getkeycardlist() {
		return this.listofkeycards;
	}
	

}
