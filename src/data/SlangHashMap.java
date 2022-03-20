package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SlangHashMap {
    public static final String _BACKUP_DATA_PATH="resources/data/slang_backup.txt";
    public static final String _DATA_PATH="resources/data/slang.txt";

    private HashMap<String,ArrayList<String>> data;
    public SlangHashMap()
    {
        data=new HashMap<>();
    }

    public HashMap<String, ArrayList<String>> getData() {
        return data;
    }

    public void setData(HashMap<String, ArrayList<String>> data) {
        this.data = data;
    }

    public boolean putValue(String key, String value)
    {
        ArrayList<String> values=data.get(key);
        if(values==null)
        {
            values=new ArrayList<>();
        }
        if(!values.contains(value))
        {
            //add new value to list
            values.add(value);
            //update new value list to map
            data.put(key,values);
            return true;
        }
        return false;
    }

    public String toStringPaired(String key)
    {
        ArrayList<String> values=data.get(key);
        if(values==null)
            return "";
        return values.toString();
    }

    public void removeWord(String word)
    {
        data.remove(word);
    }

    static public String meansToString(ArrayList<String>means,String splitRegex)
    {
        String ret="";
        for(int i=0;i< means.size();i++) {
            ret+=means.get(i)+splitRegex;

        }
        return ret.substring(0,ret.length()-splitRegex.length());
    }

    public void saveDictionary()
    {
        try {
            BufferedWriter bw=new BufferedWriter(new FileWriter(_DATA_PATH));
            TreeMap<String,ArrayList<String>> dictMap=new TreeMap(data);
            TreeSet<String> wordSet=new TreeSet(dictMap.keySet());
            for(String word:wordSet)
            {
                String line=word+"`";
                ArrayList<String> means=dictMap.get(word);
                line+=meansToString(means,"| ")+"\n";

                bw.write(line);
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
