import data.DataHandler;
import data.SlangHashMap;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        SlangHashMap dict=DataHandler.loadData("data/slang.txt");
        System.out.println( dict.getData().get(">.<"));
    }
}
