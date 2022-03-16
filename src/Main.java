import data.DataHandler;
import data.SlangHashMap;
import screen.MainUI;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        SlangHashMap dict=DataHandler.loadData(SlangHashMap._DATA_PATH);

        MainUI mainUI=new MainUI(dict);
        mainUI.showUI();

    }
}
