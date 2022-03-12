import data.DataHandler;
import data.SlangHashMap;
import screen.MainUI;

import java.util.Arrays;

public class Main {
    static final String _BACKUP_DATA_PATH="resources/data/slang_backup.txt";
    static final String _DATA_PATH="resources/data/slang.txt";
    public static void main(String[] args) {
//        SlangHashMap dict=DataHandler.loadData("resources/data/slang.txt");
//        System.out.println( dict.getData().get(">.<"));

        MainUI mainUI=new MainUI();
        mainUI.showUI();

    }
}
