package screen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.TextAction;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public dictionaryUI(MainUI mainUI)
    {

        this.mainUI=mainUI;
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
        rightPanel.add(scrollPane,BorderLayout.CENTER);
    }

    public void setupLeftPanel()
    {
        dictionaryListModel=new DefaultListModel<>();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(Color.darkGray);

        JPanel topPanel=new JPanel(new FlowLayout());
        JPanel centerPanel=new JPanel();
        topPanel.add(new JLabel("Search: "));

        searchTextField=new JTextField();
        searchTextField.setPreferredSize(new Dimension(100,30));

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
            private void filter()
            {
                String text=searchTextField.getText();
                System.out.println(text);
            }
        });
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

        JScrollPane scrollPane=new JScrollPane(new JList<>(dictionaryListModel));

        scrollPane.setBorder(new EmptyBorder(10,10,10,10));

        JPanel buttonPanel=new JPanel(new GridLayout(2,2,20,20));
        buttonPanel.setBorder(new EmptyBorder(10,10,10,10));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));

        buttonPanel.add(new JButton("Button 1"));
        buttonPanel.add(new JButton("Button 1"));
        buttonPanel.add(new JButton("Button 1"));
        buttonPanel.add(new JButton("Button 1"));

        centerPanel.add(scrollPane);
        centerPanel.add(buttonPanel);


        leftPanel.add(topPanel, BorderLayout.NORTH);
        leftPanel.add(centerPanel,BorderLayout.CENTER);
        leftPanel.add(backButton, BorderLayout.SOUTH);



    }

    public void setupCenterPanel()
    {
        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));

        JPanel topPanel=new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Word Meaning"), BorderLayout.NORTH);
        topPanel.add(new JSeparator(),BorderLayout.CENTER);
        topPanel.setBackground(Color.GREEN);
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
        JPanel wordPanel=new JPanel(new BorderLayout());
        JPanel meanPanel=new JPanel(new BorderLayout());

        wordPanel.add(new JLabel("Word"), BorderLayout.LINE_START);
//        wordPanel.add(new JLabel("<html>Hello <br> tessts</html>"),BorderLayout.CENTER);
        wordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
        wordPanel.setBackground(Color.orange);

        meanPanel.setBackground(Color.DARK_GRAY);

        meanPanel.add(new JLabel("Mean:"),BorderLayout.NORTH);

        centerPanel.add(topPanel);
        centerPanel.add(wordPanel);
        centerPanel.add(meanPanel);

    }

}
