package data;

import java.util.*;

public class SlangHashMap {
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


}
