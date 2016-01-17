/**
 * Write a description of class SentimentAnalysis here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
import io.indico.*;
import io.indico.api.*;
import io.indico.api.results.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class SentimentAnalysis
{
    public static void main (String []args) throws Exception
    {
        String comments;
        String resultString;
        String inFileName= new String("comments.txt");
        String inFileName2= new String("comments2.txt");
        String inFileName3= new String("comments3.txt");
        String inFileName4= new String("comments4.txt");
        String outFileName= new String("percentage.txt");
        double result=0;
        int counter=0;
        //FileWriter fpOut;
        //fpOut= new FileWriter(outFileName);
        BufferedReader fpIn= new BufferedReader(new FileReader(inFileName));
        Indico indico = new Indico("a580ae84a4593a8ef4943c1c9146bbbf");
        FileWriter file = new FileWriter("test.json");
        for(int x=0; x<4; x++)
        {
            if(x==1)
                fpIn= new BufferedReader(new FileReader(inFileName2));
            if(x==2)
                fpIn= new BufferedReader(new FileReader(inFileName3));
            if(x==3)
                fpIn= new BufferedReader(new FileReader(inFileName4));
            while((comments= fpIn.readLine())!= null)
            {
                IndicoResult single = indico.sentiment.predict(comments);
                System.out.println(single.getSentiment());
                result+= single.getSentiment();
                counter++;
            }
            result/=counter;
            System.out.println(result);
            /*resultString= Double.toString(result*100);
            fpOut.write(resultString);
            fpOut.close();
            */
            JSONObject obj= new JSONObject();
            obj.put("score", result*100);
            try 
            {
                file.write(obj.toJSONString());
                file.flush();
                //file.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
            System.out.print(obj);
        }
        file.close();
    }
}
