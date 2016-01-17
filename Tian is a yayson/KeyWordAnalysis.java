
/**
 * Write a description of class KeyWordAnalysis here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.*;
import io.indico.*;
import io.indico.api.*;
import io.indico.api.results.*;
public class KeyWordAnalysis
{
    public static void main(String []args) throws Exception
    {
        String keywords[]= new String[100];
        String comments;
        String resultString;
        String inFileName= new String("comments.txt");
        String outFileName= new String("keywords.txt");
        int i;
        double repetition=0;
        FileWriter fpOut; 
        fpOut= new FileWriter(outFileName);
        BufferedReader fpIn= new BufferedReader(new FileReader(inFileName));
        
        Indico indico= new Indico("a580ae84a4593a8ef4943c1c9146bbbf");
        while((comments= fpIn.readLine())!=null)
        {
            IndicoResult single = indico.keywords.predict(comments);
            Map result = single.getKeywords();
            System.out.println(result);
        }
        /*resultString= Map.toString(result);
        System.out.println(resultString);
        fpOut.write(resultString);*/
    }
}