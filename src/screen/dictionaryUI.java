package screen;

import data.SlangHashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.TextAction;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class dictionaryUI extends JPanel
{
    private MainUI mainUI;

    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel rightPanel;

    private DefaultListModel<String> dictionaryListModel;
    private DefaultListModel<String> historyListModel;

    private JTextField searchTextField;
    private DefaultComboBoxModel<String> searchOptionModel;
    private JComboBox<String> searchOptionCB;
    private JList<String> dictionaryList;
    private JScrollPane scrollPane;

    private JLabel wordLabel;
    private JLabel meanLabel;

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton resetButton;

    TreeMap<String,ArrayList<String>> map ;

    TreeSet<String> sortedWordSet;

    public dictionaryUI(MainUI mainUI)
    {

        this.mainUI=mainUI;
        map =new TreeMap(mainUI.getDictionary().getData());
        sortedWordSet=new TreeSet<>(map.keySet());
        initializeUI();
        setVisible(true);
    }

    public void initializeUI()
    {
        setLayout(new BorderLayout());
        leftPanel=new JPanel();
        centerPanel=new JPanel();
        rightPanel=new JPanel();

        leftPanel.setPreferredSize(new Dimension(250,MainUI._WINDOW_HEIGHT));
        rightPanel.setPreferredSize(new Dimension(200,MainUI._WINDOW_HEIGHT));

        leftPanel.setBackground(Color.BLUE);
        rightPanel.setBackground(Color.BLUE);

        setupLeftPanel();
        setupCenterPanel();
        setupRightPanel();


        add(leftPanel,BorderLayout.WEST);
        add(centerPanel,BorderLayout.CENTER);
        add(rightPanel,BorderLayout.EAST);

    }

    public void setupRightPanel()
    {
        historyListModel=new DefaultListModel<>();

        rightPanel.setLayout(new BorderLayout());
        JLabel label=new JLabel("Search history");
        label.setFont(new Font("Serif",Font.BOLD,20));
        rightPanel.add(label,BorderLayout.NORTH);

        JScrollPane scrollPane=new JScrollPane(new JList(historyListModel));
        scrollPane.setBorder(new EmptyBorder(10,10,10,10));
        scrollPane.setPreferredSize(new Dimension(600,300));

        JPanel emptyPanel=new JPanel(new GridLayout(1,1,10,10));

        JButton emptyButton=new JButton("Empty History");
        emptyPanel.setBorder(new EmptyBorder(10,20,10,20));
        emptyPanel.setPreferredSize(new Dimension(200,50));
        emptyPanel.add(emptyButton);

        rightPanel.add(scrollPane,BorderLayout.CENTER);
        rightPanel.add(emptyPanel,BorderLayout.SOUTH);

        //set up action
        emptyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyListModel.removeAllElements();
            }
        });
    }

    public void setupLeftPanel()
    {
        dictionaryListModel=new DefaultListModel<>();

        dictionaryListModel.addAll(sortedWordSet);
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(Color.darkGray);

        JPanel topPanel=new JPanel(new FlowLayout());
        JPanel centerPanel=new JPanel();
        topPanel.add(new JLabel("Search: "));

        searchTextField=new JTextField();
        searchTextField.setPreferredSize(new Dimension(100,30));


        searchOptionModel=new DefaultComboBoxModel<>();
        searchOptionCB=new JComboBox<>(searchOptionModel);
        searchOptionCB.addItem("By Word");
        searchOptionCB.addItem("By Mean");
        searchOptionCB.setSelectedIndex(0);

        topPanel.add(searchTextField);
        topPanel.add(searchOptionCB);

        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));

        JButton backButton=new JButton("Back to Main");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainUI.backToMain();
            }
        });

        dictionaryList=new JList<>(dictionaryListModel);

        scrollPane = new JScrollPane(dictionaryList);

        scrollPane.setBorder(new EmptyBorder(10,10,10,10));

        JPanel buttonPanel=new JPanel(new GridLayout(2,2,20,20));
        buttonPanel.setBorder(new EmptyBorder(10,10,10,10));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));

        addButton=new JButton("Add");
        updateButton=new JButton("Update");
        deleteButton=new JButton("Remove");
        resetButton=new JButton("Reset List");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(resetButton);

        centerPanel.add(scrollPane);
        centerPanel.add(buttonPanel);


        leftPanel.add(topPanel, BorderLayout.NORTH);
        leftPanel.add(centerPanel,BorderLayout.CENTER);
        leftPanel.add(backButton, BorderLayout.SOUTH);

        //set up action
        dictionaryList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()%2==0)
                {
                    showMean();
                }
            }
        });

        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }

        });

        searchOptionCB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                filter();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadWordCustomScreen("","");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=dictionaryList.getSelectedIndex();
                if(index>=0)
                {
                    String word=dictionaryListModel.get(index);
                    ArrayList<String> means=map.get(word);
                    loadWordCustomScreen(word, SlangHashMap.meansToString(means,"\n"));
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=dictionaryList.getSelectedIndex();
                if(index >= 0)
                {
                    String word=dictionaryListModel.get(index);
                    int result=JOptionPane.showConfirmDialog(null,"Do you want to delete \"" +
                           word+ "\"?","Delete word",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                    if(result==JOptionPane.YES_OPTION)
                    {
                        System.out.println(word+"is removed!");
                        //remove in list model and slang dict
                        mainUI.removeWord(word);
                        //update dictionary
                        updateDictionary();
                        //update file
                        mainUI.saveDictionary();

                    }
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainUI.resetDictionary();
                //update all
                updateDictionary();
                JOptionPane.showMessageDialog(null,"Dictionary is reseted!",
                        "Message",JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }

    private void filter()
    {
        String text=searchTextField.getText();
        dictionaryListModel.removeAllElements();
        int mode=searchOptionCB.getSelectedIndex();
        for(String s:sortedWordSet)
        {
            if(mode==0)
            {
                text=text.toUpperCase();
                if(s.contains(text)&&s.startsWith(text))
                {
                    dictionaryListModel.addElement(s);
                }
            }
            else
            {
                String mean=map.get(s).toString();
                if(mean.contains(text))
                {
                    dictionaryListModel.addElement(s);
                }
            }

        }
    }

    public void setupCenterPanel()
    {
        centerPanel.removeAll();
        centerPanel.revalidate();
        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));

        JPanel topPanel=new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Word Meaning"), BorderLayout.NORTH);
        topPanel.add(new JSeparator(),BorderLayout.CENTER);
        topPanel.setBackground(Color.GREEN);
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));

        wordLabel=new JLabel();
        meanLabel=new JLabel();

        JPanel wordPanel=new JPanel(new BorderLayout());
        JPanel meanPanel=new JPanel(new BorderLayout());

        wordPanel.add(new JLabel("Word"), BorderLayout.LINE_START);
        wordPanel.add(wordLabel,BorderLayout.CENTER);
        wordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
        wordPanel.setBackground(Color.orange);

        meanPanel.setBackground(Color.DARK_GRAY);

        meanPanel.add(new JLabel("Mean:"),BorderLayout.NORTH);
        meanPanel.add(meanLabel,BorderLayout.CENTER);

        centerPanel.add(topPanel);
        centerPanel.add(wordPanel);
        centerPanel.add(meanPanel);

    }

    public void loadWordCustomScreen(String word,String mean)
    {
        centerPanel.removeAll();
        centerPanel.revalidate();

        int mode=0;
        if(word.length()>0)
            mode=1;

        System.out.println(mode);

        JPanel custom;
        custom = new JPanel();
        custom.setPreferredSize(new Dimension(300,150));
        custom.setLayout(new BorderLayout());

        JPanel topCustomPanel=new JPanel();
        JPanel centerCustomPanel=new JPanel();
        JPanel bottomCustomPanel=new JPanel();

        topCustomPanel.setPreferredSize(new Dimension(300,50));
        topCustomPanel.setLayout(new BorderLayout());


        JTextField wordTF=new JTextField();
        JTextArea meanTA=new JTextArea();

        wordTF.setDisabledTextColor(Color.BLACK);
        wordTF.setText(word);
        meanTA.setText(mean);

        wordTF.setEnabled(true);

        //disable wordTF while update new word
        if(mode!=0)
        {
            wordTF.setEnabled(false);
        }

        JButton doneButton=new JButton("Done");


        JScrollPane meanScrollPane=new JScrollPane(meanTA);

        topCustomPanel.setBorder(new EmptyBorder(10,0,10,20));
        topCustomPanel.add(new JLabel("Word: "), BorderLayout.WEST);
        meanScrollPane.setMaximumSize(new Dimension(300,500));

        topCustomPanel.add(wordTF,BorderLayout.CENTER);
//        topCustomPanel.add(doneButton,BorderLayout.EAST);


        meanTA.setSize(new Dimension(200,100));
        centerCustomPanel.setLayout(new BorderLayout());
        centerCustomPanel.add(new JLabel("Mean:"),BorderLayout.WEST);
        centerCustomPanel.add(meanScrollPane,BorderLayout.CENTER);
        centerCustomPanel.add(new JLabel("Input multiple mean of word line by line"),BorderLayout.NORTH);
        centerCustomPanel.setBorder(new EmptyBorder(0,0,50,20));

        doneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomCustomPanel.setPreferredSize(new Dimension(300,200));
        bottomCustomPanel.setLayout(new BoxLayout(bottomCustomPanel,BoxLayout.Y_AXIS));
//        bottomCustomPanel.setBorder(new EmptyBorder(75,50,75,50));

        bottomCustomPanel.add(doneButton);
//        bottomCustomPanel.add(new JButton("Test"));

        int finalMode = mode;
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(wordTF.getText().length()==0)
                {
                    JOptionPane.showMessageDialog(null,"Word is empty",
                            "Message",JOptionPane.WARNING_MESSAGE);
                }else
                {
                    String addWord=wordTF.getText().strip();
                   //add new word
                    String[] means=meanTA.getText().split("\n");
                    for(int i=0;i<means.length;i++)
                    {
                        String addMean=means[i].strip();
                        if(addMean.length()!=0)
                        {
                            mainUI.addNewPaired(addWord,addMean);
                        }
                    }
                    if(finalMode == 0) {
                        JOptionPane.showMessageDialog(null, "Your new word is added successfully!",
                                "Message", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Your word is updated!",
                                "Message", JOptionPane.INFORMATION_MESSAGE);
                    }
                    //update all
                    updateDictionary();
                    //update file
                    mainUI.saveDictionary();
                    //set focus to new word
                    int focusIndex=-1;
                    for (String findWord:sortedWordSet)
                    {
                        focusIndex++;
                        if(findWord.compareTo(addWord)==0)
                        {
                            break;
                        }
                    }
                    System.out.println(focusIndex);
                    dictionaryList.setSelectedIndex(focusIndex);

                }
            }
        });
        custom.add(topCustomPanel,BorderLayout.NORTH);
        custom.add(centerCustomPanel,BorderLayout.CENTER);
        custom.add(bottomCustomPanel,BorderLayout.SOUTH);
        centerPanel.add(custom);
        centerPanel.setVisible(true);

    }

    public void showMean()
    {
        int index=dictionaryList.getSelectedIndex();
        if(index>=0) {

            String word=dictionaryListModel.get(index);
            setupCenterPanel();
            //display to UI
            wordLabel.setText(word);
            meanLabel.setText(map.get(word).toString());
            //add to history;
            historyListModel.addElement(word);
            System.out.println(word);
        }
    }

    public void updateDictionary()
    {
        //update map word
        map=new TreeMap<>(mainUI.getDictionary().getData());

        //update wordset
        sortedWordSet=new TreeSet<>(map.keySet());

        //update model
        dictionaryListModel.removeAllElements();
        dictionaryListModel.addAll(sortedWordSet);


    }



}

class CustomWordDialog
{

    public CustomWordDialog()
    {
        createCustomPanel();
    }

    public JPanel createCustomPanel()
    {
        JPanel custom;
        custom = new JPanel();
        custom.setPreferredSize(new Dimension(300,150));
        custom.setLayout(new BorderLayout());

        JPanel topPanel=new JPanel();
        JPanel centerPanel=new JPanel();
        JPanel bottomPanel=new JPanel();

        topPanel.setPreferredSize(new Dimension(300,50));
        topPanel.setLayout(new BorderLayout());


        JTextField wordTF=new JTextField();
        JTextArea meanTA=new JTextArea();
        wordTF.setSize(new Dimension(200,30));
        topPanel.setBorder(new EmptyBorder(10,0,10,0));
        topPanel.add(new JLabel("Word: "), BorderLayout.WEST);
        topPanel.add(wordTF,BorderLayout.CENTER);


        meanTA.setSize(new Dimension(200,100));
        centerPanel.setPreferredSize(new Dimension(300,100));
        centerPanel.add(new JLabel("Mean:"));


        bottomPanel.setLayout(new GridLayout(1,2,20,20));
        JButton doneButton=new JButton("Done");
        bottomPanel.add(doneButton);
        bottomPanel.add(new JButton("Test"));

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                custom.setVisible(true);
            }
        });
        custom.add(topPanel,BorderLayout.NORTH);
        custom.add(centerPanel,BorderLayout.CENTER);
//        custom.add(bottomPanel,BorderLayout.SOUTH);

        return custom;
    }

    public String getWord()
    {
        return "wordTF.getText()";
    }
}