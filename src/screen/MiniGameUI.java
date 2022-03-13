package screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MiniGameUI extends JPanel
{
    private MainUI mainUI;

    private JPanel leftPanel;
    private JPanel centerPanel;

    private JLabel scoreLabel;

    JButton startButton;

    private DefaultComboBoxModel<String> gameModeCB;

    public MiniGameUI(MainUI mainUI)
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

        leftPanel.setPreferredSize(new Dimension(250,MainUI._WINDOW_HEIGHT));

        setupLeftPanel();
        setupCenterPanel();

        add(leftPanel,BorderLayout.WEST);
        add(centerPanel,BorderLayout.CENTER);
    }

    public void setupLeftPanel()
    {
        leftPanel.setLayout(new BorderLayout());
        gameModeCB=new DefaultComboBoxModel<>();

        JPanel gamePanel=new JPanel();
        JPanel buttonPanel=new JPanel();
        JPanel buttonTopPanel=new JPanel(new FlowLayout());

        gamePanel.setLayout(new BoxLayout(gamePanel,BoxLayout.Y_AXIS));
        JLabel scroreText= new JLabel("Score");
        scroreText.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel=new JLabel("10/10");
        gamePanel.add(scroreText);
        gamePanel.add(scoreLabel);


        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));

        gameModeCB.addElement("Word");
        gameModeCB.addElement("Mean");

        buttonTopPanel.add(new JLabel("Mode: "));
        buttonTopPanel.add(new JComboBox<>(gameModeCB));
        buttonTopPanel.setPreferredSize(new Dimension(200,50));

        startButton=new JButton("START");
        JButton refreshButton=new JButton("Refresh");

        buttonPanel.add(startButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(buttonTopPanel);

        gamePanel.setPreferredSize(new Dimension(250,200));

        buttonPanel.setBackground(Color.orange);

        JButton backButton=new JButton("Back to Main");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainUI.backToMain();
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(true);
            }
        });

        leftPanel.add(gamePanel,BorderLayout.NORTH);
        leftPanel.add(buttonPanel,BorderLayout.CENTER);
        leftPanel.add(backButton,BorderLayout.SOUTH);

    }
    public void setupCenterPanel()
    {
        centerPanel.add(new JLabel("Hello"));
    }

    public void startGame()
    {
        centerPanel.removeAll();
        centerPanel.revalidate();
        startButton.setEnabled(false);
        //load game here
    }

    public void loadGame(String question, String[]answers,int rightAnwser)
    {

    }


}
