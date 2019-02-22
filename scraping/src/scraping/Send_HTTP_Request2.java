package scraping;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL; 
import org.json.JSONObject;
public class Send_HTTP_Request2 {
	public static void main(String[] args) {
     try {
    	 System.out.println(1);
         Send_HTTP_Request2.call_me();
        } catch (Exception e) {
         e.printStackTrace();
       }
     }
	   
public static void call_me() throws Exception {
     String url = "https://api.wordassociations.net/associations/v1.0/json/search?apikey=09e1339d-8bc2-497c-9cfe-f975a0188dac&text=man&lang=en";
     URL obj = new URL(url);
     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
     // optional default is GET
     con.setRequestMethod("GET");
     //add request header
     con.setRequestProperty("User-Agent", "Mozilla/5.0");
     int responseCode = con.getResponseCode();
     System.out.println("\nSending 'GET' request to URL : " + url);
    
     BufferedReader in = new BufferedReader(
             new InputStreamReader(con.getInputStream()));
     String inputLine;
     StringBuffer response = new StringBuffer();
     while ((inputLine = in.readLine()) != null) {
     	response.append(inputLine);
     }
     in.close();
     //print in String
     System.out.println("1"+response.toString());
     
  
     //Read JSON response and print
     JSONObject myResponse = new JSONObject(response.toString());
     System.out.println("result after Reading JSON Response");
   //  System.out.println("text- "+myResponse.getString("text"));
     System.out.println("2"+ myResponse.toString());
     System.out.println(" "+(myResponse.names()));
     System.out.println(myResponse.getJSONObject("request"));
     JSONObject myResponse2 = new JSONObject(myResponse.toString());
    System.out.println(" "+myResponse2.toString());
 
     }
}
