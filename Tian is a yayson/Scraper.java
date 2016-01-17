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


public class Scraper {
	
		
	
	public Scraper() 
	{
		JSONObject JSONobj;
		FileWriter writer = null;
		Document doc = null;
		JSONParser parser = new JSONParser();
		try {
			
			JSONobj = (JSONObject) parser.parse(new FileReader("db.json"));
			
			
			// loop through urls
			JSONArray sounds = (JSONArray) JSONobj.get("soundcloudurls");
		
			Iterator<?> iterator = sounds.iterator();
			while (iterator.hasNext()) {
				
				String url = (String) iterator.next();
												
						try {
							doc = Jsoup.connect(url).get();
						} 
						catch (IOException ioe)
						{
							System.out.println("Can't access url");
							ioe.printStackTrace();
						}
						
						Elements section = doc.getElementsByClass("comments");
						
						Elements paragraphs = section.select("p");
						
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
