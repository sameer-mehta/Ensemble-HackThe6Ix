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
		Document doc = null;
		JSONParser parser = new JSONParser();
		try {
			
			JSONobj = (JSONObject) parser.parse(new FileReader("userdata.json"));
			long number_of_users = (Long) JSONobj.get("users");
			System.out.println(number_of_users);

			// loop through urls
			JSONArray sounds = (JSONArray) JSONobj.get("soundcloudurls");
			//@SuppressWarnings("unchecked") //Using legacy API
			Iterator<?> iterator = sounds.iterator();
			while (iterator.hasNext()) {
				
				String url = (String) iterator.next();
					for(int i = 0; i< number_of_users; i++)
					{
						try {
							doc = Jsoup.connect(url).get();
						} 
						catch (IOException ioe)
						{
							System.out.println("Can't access url");
							ioe.printStackTrace();
						}
						//System.out.println("b4 section after load");
						Elements section = doc.getElementsByClass("comments");
						
						//System.out.println("after section");
						//System.out.println(section.text());
						Elements paragraphs = section.select("p");
						//System.out.println(paragraphs.text());
						
						try {
				            FileWriter writer = new FileWriter("Scrape_Output.txt", false);
				            writer.write(paragraphs.text());
				            writer.close();
				        } 
						catch (IOException e)
						{
							System.out.println("Can't write comments to file");
				            e.printStackTrace();
				        }
						
					}
					
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
