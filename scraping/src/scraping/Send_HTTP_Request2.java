package scraping;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.Scanner;
import org.json.JSONObject;
public class Send_HTTP_Request2 {
	
	static int count =0;
	public static void main(String[] args) throws FileNotFoundException{
		LinkedList<String> words = new LinkedList<String>();
		File file = new File("words.txt");
Scanner sc = new Scanner(file);
		PrintWriter pw = new PrintWriter(new File("Dictionary.txt"));
		//Read each character of the textfile
		int count = 0;
		
		while (sc.hasNext()) {	
			String line;
			line = sc.next();
			
			words.add(line);
			//System.out.println();
		}
		
		sc.close();
		String word = null;
     try {
    	while(words.size()!=0)
         Send_HTTP_Request2.call_me( pw, words.pop());
        } catch (Exception e) {
         e.printStackTrace();
       }
     }
	   
public static void call_me(PrintWriter pw, String word) throws Exception {

	
	
     String url = "https://api.wordassociations.net/associations/v1.0/json/search?apikey=09e1339d-8bc2-497c-9cfe-f975a0188dac&text="+word+"&lang=en";
     URL obj = new URL(url);
     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
     // optional default is GET
     con.setRequestMethod("GET");
     //add request header
     con.setRequestProperty("User-Agent", "Mozilla/5.0");
     int responseCode = con.getResponseCode();
     //System.out.println("\nSending 'GET' request to URL : " + url);
   //  System.out.println("Response Code : " + responseCode);
     BufferedReader in = new BufferedReader(
             new InputStreamReader(con.getInputStream()));
     String inputLine;
     StringBuffer response = new StringBuffer();
     while ((inputLine = in.readLine()) != null) {
     	response.append(inputLine);
     }
     in.close();
     int i=0;
     //print in String
     System.out.println("\n\n"+count++ + response.toString());
     pw.write(response.toString()+"\n\n");
  pw.close();
     //Read JSON response and print
     JSONObject myResponse = new JSONObject(response.toString());
     System.out.println("result after Reading JSON Response");
   //  System.out.println("text- "+myResponse.getString("text"));
   //  System.out.println("2"+ myResponse.toString());
   //  System.out.println("limit- "+(myResponse.names()));
    // System.out.println(myResponse.getJSONObject("request"));
    
     JSONObject myResponse2 = new JSONObject(myResponse.toString());
    //System.out.println("countryCode- "+myResponse2.toString());
   /*   System.out.println("countryName- "+myResponse.getString("countryName"));
     System.out.println("regionName- "+myResponse.getString("regionName"));
     System.out.println("cityName- "+myResponse.getString("cityName"));
     System.out.println("zipCode- "+myResponse.getString("zipCode"));
     System.out.println("latitude- "+myResponse.getString("latitude"));
     System.out.println("longitude- "+myResponse.getString("longitude"));
     System.out.println("timeZone- "+myResponse.getString("timeZone"));  
   */
     }
}
