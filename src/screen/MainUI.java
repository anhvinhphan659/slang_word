package screen;

import data.DataHandler;
import data.SlangHashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

public class MainUI {

    public static final int _WINDOW_WIDTH=800;
    public static final int _WINDOW_HEIGHT=600;
    public static final int _TOP_HEIGHT=200;

    private JFrame mainFrame;

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;

    private JButton dictionaryButton;
    private JButton mini_gameButton;
    private JButton exitButton;
    private JButton randomSlangButton;

    private JLabel randomSlangLabel;

    private SlangHashMap dictionary;
    public MainUI(SlangHashMap dictionary)
    {
        mainFrame=new JFrame("Slang Word Dictionary");
        this.dictionary=dictionary;
        setUpUI();
    }

    public SlangHashMap getDictionary() {
        return dictionary;
    }

    private void initializeUI()
    {
        mainPanel=new JPanel(new BorderLayout());
        topPanel=new JPanel();
        centerPanel=new JPanel();
        bottomPanel=new JPanel(new GridBagLayout());
        bottomPanel.setBorder(new EmptyBorder(5,0,50,0));

        //set up default parameters
        JFrame.setDefaultLookAndFeelDecorated(true);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setSize(_WINDOW_WIDTH,_WINDOW_HEIGHT);
        mainFrame.setMinimumSize(new Dimension(950,700));

        topPanel.setSize(_WINDOW_WIDTH,_TOP_HEIGHT);

        bottomPanel.setBackground(Color.white);

        dictionaryButton=createButton("Dictionary");
        mini_gameButton=createButton("Mini Game");
        exitButton=createButton("Exit");

    }

    public JButton createButton(String name)
    {
        JButton button=new JButton(name);
        button.setPreferredSize(new Dimension(150,40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    public void setUpUI()
    {
        initializeUI();

        //add component to Frame
        mainFrame.add(mainPanel);
        mainPanel.add(new JLabel("SLANG WORD"));

        mainPanel.add(topPanel,BorderLayout.NORTH);
        mainPanel.add(centerPanel,BorderLayout.CENTER);
        mainPanel.add(bottomPanel,BorderLayout.SOUTH);

        //set up top panel
        Image slang_image=new ImageIcon("resources/asset/slang_word_main.png")
                .getImage().getScaledInstance(_WINDOW_WIDTH,_TOP_HEIGHT,Image.SCALE_DEFAULT);

        topPanel.add(new JLabel(new ImageIcon(slang_image)));
        //set up center panel
        centerPanel.setPreferredSize(new Dimension(_WINDOW_WIDTH,40));
        centerPanel.setBackground(Color.white);
        centerPanel.setLayout(new FlowLayout());
        randomSlangButton=new JButton("Random Slang Word");
        randomSlangLabel=new JLabel();
        showRandomSlang();


        //set up bottom panel
        GridBagConstraints g=new GridBagConstraints();
        g.fill=GridBagConstraints.VERTICAL;
        g.insets=new Insets(5,0,5,0);
        g.gridx=1;
        g.gridy=0;

        bottomPanel.add(dictionaryButton,g);
        g.gridy++;
        bottomPanel.add(mini_gameButton,g);
        g.gridy++;
        bottomPanel.add(exitButton,g);

        //set up action for button
        dictionaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadScreen(new dictionaryUI(MainUI.this));
            }
        });

        mini_gameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadScreen(new MiniGameUI(MainUI.this));
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        randomSlangButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRandomSlang();
            }
        });
    }

    public void showUI()
    {
        mainFrame.setVisible(true);
    }

    public void loadScreen(JPanel panel)
    {
        mainFrame.getContentPane().removeAll();
        mainPanel=panel;
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    public void backToMain()
    {
        mainFrame.getContentPane().removeAll();
        initializeUI();
        setUpUI();
        mainFrame.setVisible(true);
    }

    public void addNewPaired(String word, String mean)
    {
        dictionary.putValue(word,mean);
    }

    public void removeWord(String word)
    {
        dictionary.removeWord(word);
    }

    public void saveDictionary()
    {
        System.out.println("Dictionary is saved");
        dictionary.saveDictionary();
    }
    public void resetDictionary()
    {
        dictionary= DataHandler.loadData(SlangHashMap._BACKUP_DATA_PATH);
    }
    public void showRandomSlang()
    {
        centerPanel.removeAll();
        centerPanel.revalidate();
        centerPanel.repaint();

        Random random=new Random();
        ArrayList<String> words=new ArrayList<>(dictionary.getData().keySet());
        int pos=random.nextInt(words.size());
        String randomSlang=words.get(pos);
        randomSlang+=" means "+SlangHashMap.meansToString(dictionary.getData().get(randomSlang)," or ");
        randomSlangLabel.setText(randomSlang);
        //add label to center
        centerPanel.add(randomSlangButton);
        centerPanel.add(randomSlangLabel);
    }

}
