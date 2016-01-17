import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//Imports

public class Scraper {
	
		
	//Default Constructor
	public Scraper() 
	{
		JSONObject JSONobj; //JSON object, used to read user data from json files 
		FileWriter writer = null; //Used to write scraped comments
		Document doc = null; //Document used to fetch html
		JSONParser parser = new JSONParser(); //parser for json
		//Get user data(sound cloud urls from json)
		try {
			
			JSONobj = (JSONObject) parser.parse(new FileReader("db.json"));
			
			
			// loop through file and get urls
			JSONArray sounds = (JSONArray) JSONobj.get("soundcloudurls");
			
		    //Used to iterate through urls
			Iterator<?> iterator = sounds.iterator();
			while (iterator.hasNext()) {
				
				String url = (String) iterator.next();
						
						//Connect to website using Jsoup API
						try {
							doc = Jsoup.connect(url).get();
						} 
						catch (IOException ioe)
						{
							System.out.println("Can't access url");
							ioe.printStackTrace();
						}
						
						//Get comments class
						Elements section = doc.getElementsByClass("comments");
						
						//Get all paragraph elements under section class comments
						Elements paragraphs = section.select("p");
						
						//Try to write/ append paragraph text data to output file 
						try {
				            writer = new FileWriter("Scrape_Output.txt", true);
				            writer.write("\n");
				            writer.write(paragraphs.text());
				            writer.write("\n");
				           
				        } 
						catch (IOException e)
						{
							System.out.println("Can't write comments to file");
				            e.printStackTrace();
				        }
						
						writer.close();
						
					}
						
		}
		//Final catch for exceptions
		catch(IOException | ParseException exp)
		{
			exp.printStackTrace();
		}
		
		
		 
					
					
							
	}
		
	public static void main(String args[])
	{
		 new Scraper();
		 
		
	}
}
