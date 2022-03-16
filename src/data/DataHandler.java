package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataHandler {
    public static SlangHashMap loadData(String dataPath)
    {
        BufferedReader br;
        SlangHashMap slangDict=new SlangHashMap();
        try {

            br=new BufferedReader(new FileReader(dataPath));
            String line=br.readLine();
            while (line!=null)
            {
                //process line
                String[] word=line.split("`");
                String key=word[0];
                String meaning="";
                if(word.length>1)
                {
                    meaning=word[1];
                }
                //split means in meaning;
                String[] means=meaning.split("\\|");

                //add key and mean to list
                for(int i=0;i< means.length;i++)
                {
                    slangDict.putValue(key,means[i].trim());
                }

                //read next line
                line=br.readLine();

            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return slangDict;
    }
}
