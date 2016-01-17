/**
 * Write a description of class shazam here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.TimedEvent;
import com.echonest.api.v4.Track;
import com.echonest.api.v4.TrackAnalysis;
import java.io.File;
import java.io.IOException;
import java.io.*;
public class shazam
{
    public static void main(String[] args) throws Exception 
    {
        EchoNestAPI en = new EchoNestAPI("BY1FSN7E8CONA8G7T");
        String outFile1= new String("beats1.txt");
        String outFile2= new String("beats2.txt");
        String path = "C:/Users/mrting/Music/Downloaded by MediaHuman/youAndITonight.mp3";
        String path2= "C:/Users/mrting/Music/Downloaded by MediaHuman/brownEyedGirl.mp3";
        FileWriter file3 = new FileWriter("test.json");
        if (args.length > 2) 
        {
            path = args[1];
            path2= args[2];
        }

        File file = new File(path);
        File file2= new File(path2);
        if (!file.exists() || !file2.exists()) 
        {
            System.err.println("Can't find " + path);
        } 
        else 
        {
            try 
            {
                Track track = en.uploadTrack(file);
                Track track2= en.uploadTrack(file2);
                track.waitForAnalysis(30000);
                track2.waitForAnalysis(30000);
                if (track.getStatus() == Track.AnalysisStatus.COMPLETE && track2.getStatus()==Track.AnalysisStatus.COMPLETE) 
                {
                    double tempo= track.getTempo(); 
                    JSONObject musicQualities= new JSONObject();
                    musicQualities.put("tempo", tempo);
                    musicQualities.put("danceability", track.getSpeechiness());
                    musicQualities.put("speechiness",track.getSpeechiness());
                    musicQualities.put("liveness",track.getLiveness());
                    musicQualities.put("energy",track.getEnergy());
                    musicQualities.put("loudness",track.getLoudness());
                    musicQualities.put("timeSignature",track.getTimeSignature());
                    musicQualities.put("key",track.getKey());
                    musicQualities.put("mode",track.getMode());
                    file3.write(musicQualities.toJSONString());
                    /*System.out.println("Track 1");
                    System.out.println("Tempo: " + tempo);
                    System.out.println("Danceability: " + track.getDanceability());
                    System.out.println("Speechiness: " + track.getSpeechiness());
                    System.out.println("Liveness: " + track.getLiveness());
                    System.out.println("Energy: " + track.getEnergy());
                    System.out.println("Loudness: " + track.getLoudness());
                    System.out.println("Time Signature:" + track.getTimeSignature());
                    System.out.println("Key:" + track.getKey());
                    System.out.println("Mode:" + track.getMode());
                    System.out.println();
                    System.out.println("Track 2");*/
                    double tempo2= track2.getTempo();
                    JSONObject musicQualities2= new JSONObject();
                    musicQualities2.put("tempo", tempo2);
                    musicQualities2.put("danceability",track2.getDanceability());
                    musicQualities2.put("speechiness",track2.getSpeechiness());
                    musicQualities2.put("liveness",track2.getLiveness());
                    musicQualities2.put("energy",track2.getEnergy());
                    musicQualities2.put("loudness",track2.getLoudness());
                    musicQualities2.put("timeSignature",track2.getTimeSignature());
                    musicQualities2.put("key",track2.getKey());
                    musicQualities2.put("mode",track2.getMode());
                    file3.write(musicQualities2.toJSONString());
                    /*System.out.println("Tempo: " + tempo2);
                    System.out.println("Danceability: " + track2.getDanceability());
                    System.out.println("Speechiness: " + track2.getSpeechiness());
                    System.out.println("Liveness: " + track2.getLiveness());
                    System.out.println("Energy: " + track2.getEnergy());
                    System.out.println("Loudness: " + track2.getLoudness());
                    System.out.println("Time Signature:" + track2.getTimeSignature());
                    System.out.println("Key:" + track2.getKey());
                    System.out.println("Mode:" + track2.getMode());*/
                    //System.out.println("Beat start times:");
                    double consistency= 100-(((tempo2-tempo)/(tempo+tempo2))*100);
                    JSONObject consistencyObj= new JSONObject();
                    consistencyObj.put("Tempo Consistency(% tempo similarity)", consistency);
                    file3.write(consistencyObj.toJSONString());
                    //System.out.println("Tempo Consistency:" + consistency + "% tempo similarity");
                    TrackAnalysis analysis = track.getAnalysis();
                    TrackAnalysis analysis2= track2.getAnalysis();
                    
                    FileWriter outBeat1;
                    outBeat1= new FileWriter(outFile1, false);
                    FileWriter outBeat2;
                    outBeat2= new FileWriter(outFile2, false);
                    for(TimedEvent beat : analysis.getBeats())
                        outBeat1.write(Double.toString(beat.getStart())+ "\n");
                    
                    for(TimedEvent beat2 : analysis2.getBeats())
                        outBeat2.write(Double.toString(beat2.getStart())+ "\n");
                    outBeat1.close(); 
                    outBeat2.close();
                    String inFile1= new String("beats1.txt");
                    String inFile2= new String("beats2.txt");
                    BufferedReader beatsFile1= new BufferedReader(new FileReader(inFile1));
                    BufferedReader beatsFile2= new BufferedReader(new FileReader(inFile2));
                    String beats1="0", beats2="0";
                    int counter=0, secondaryCounter=0;
                    beats1=beatsFile1.readLine();
                    beats2=beatsFile2.readLine();
                    double DBeats1, DBeats2;
                    while(beats1!= null && beats2!=null)
                    {
                        DBeats1= Double.parseDouble(beats1);
                        DBeats2= Double.parseDouble(beats2);
                        if(Math.abs(DBeats1-DBeats2)< 0.1)
                            counter++;
                        secondaryCounter++;
                        beats1=beatsFile1.readLine();
                        beats2=beatsFile2.readLine();
                    }
                    //System.out.println(counter/secondaryCounter*100);
                    JSONObject beatAccuracy= new JSONObject();
                    beatAccuracy.put("beat Consistency", counter/secondaryCounter*100);
                    file3.write(beatAccuracy.toJSONString());
                } 
                else 
                {
                    System.err.println("Trouble analysing track " + track.getStatus());
                }
            } 
            catch (IOException e) 
            {
                System.err.println("Trouble uploading file");
            }
            file3.close();
        }
        /*JSONObject tempo= new JSONObject();
        JSONObject danceability= new JSONObject();
        JSONObject speechiness= new JSONObject();
        JSONObject liveness= new JSONObject(); 
        JSONObject energy= new JSONObject();
        JSONObject loudness= new JSONObject(); 
        JSONObject timeSignature= new JSONObject();
        JSONObject key= new JSONObject();
        JSONObject mode= new JSONObject();*/
    }
 }

//mode
//time signature 
//key 