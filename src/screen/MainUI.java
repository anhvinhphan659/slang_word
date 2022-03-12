package screen;

import javax.swing.*;
import java.awt.*;

public class MainUI {
    static final int _WINDOW_WIDTH=800;
    static final int _WINDOW_HEIGHT=600;
    static final int _TOP_HEIGHT=200;

    private JFrame mainFrame;

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;

    private JButton dictionaryButton;
    private JButton mini_gameButton;
    private JButton exitButton;
    public MainUI()
    {
        setUpUI();
    }

    private void initializeUI()
    {
        mainFrame=new JFrame("Slang Word Dictionary");

        mainPanel=new JPanel(new BorderLayout());
        topPanel=new JPanel();
        bottomPanel=new JPanel(new GridBagLayout());

        //set up default parameters
        JFrame.setDefaultLookAndFeelDecorated(true);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setSize(_WINDOW_WIDTH,_WINDOW_HEIGHT);

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
        mainPanel.add(bottomPanel,BorderLayout.CENTER);

        //set up top panel
        Image slang_image=new ImageIcon("resources/asset/slang_word_main.png")
                .getImage().getScaledInstance(_WINDOW_WIDTH,_TOP_HEIGHT,Image.SCALE_DEFAULT);

        topPanel.add(new JLabel(new ImageIcon(slang_image)));

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


    }

    public void showUI()
    {
        mainFrame.setVisible(true);
    }
}
