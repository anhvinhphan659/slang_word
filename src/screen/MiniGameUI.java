package screen;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class MiniGameUI extends JPanel
{
    private static final int _NUMBER_OF_ANSWER=4;
    private static final int _NUMBER_OF_QUIZ=10;
    private int rightAnswerQuiz=0;
    private int currentAnswerQuiz=0;
    private MainUI mainUI;

    private JPanel leftPanel;
    private JPanel centerPanel;

    private JLabel scoreLabel;

    JButton startButton;
    JButton refreshButton;

    JComboBox  gameModeCB;
    private DefaultComboBoxModel<String> gameModeCBModel;

    private ArrayList<String> wordList;
    private ArrayList<ArrayList<String>> meanList;

    public MiniGameUI(MainUI mainUI)
    {
        this.mainUI=mainUI;
        HashMap dictionary=mainUI.getDictionary().getData();
        TreeMap<String,ArrayList<String>> map=new TreeMap(dictionary);
        wordList=new ArrayList<>(map.keySet());
        meanList=new ArrayList<>(map.values());
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
        gameModeCBModel =new DefaultComboBoxModel<>();

        JPanel gameOutlinePanel=new JPanel();
        JPanel gamePanel=new JPanel();
        JPanel buttonPanel=new JPanel();
        JPanel buttonTopPanel=new JPanel(new FlowLayout());

        gamePanel.setLayout(new BoxLayout(gamePanel,BoxLayout.Y_AXIS));
        gamePanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        JLabel scroreText= new JLabel("Score");
        scroreText.setFont(new Font("Helvetica Neue",Font.PLAIN,30));
        scroreText.setForeground(Color.red);
        scroreText.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel=new JLabel("10/10");
        scoreLabel.setFont(new Font("Helvetica Neue",Font.PLAIN,25));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gamePanel.add(scroreText);
        gamePanel.add(scoreLabel);

        gameOutlinePanel.setLayout(new BorderLayout());
        gameOutlinePanel.add(gamePanel,BorderLayout.CENTER);

        gameOutlinePanel.setPreferredSize(new Dimension(200,250));
        gameOutlinePanel.setBorder(new EmptyBorder(30,20,30,20));


        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));

        gameModeCBModel.addElement("Word");
        gameModeCBModel.addElement("Mean");

        buttonTopPanel.add(new JLabel("Mode: "));

        gameModeCB = new JComboBox<>(gameModeCBModel);

        buttonTopPanel.add(gameModeCB);
        buttonTopPanel.setPreferredSize(new Dimension(200,50));

        startButton=new JButton(" START ");

        refreshButton = new JButton("Refresh");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(startButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(buttonTopPanel);

        gamePanel.setPreferredSize(new Dimension(250,200));



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

                loadStartedScreen();
            }
        });

        buttonPanel.setBackground(Color.white);
        buttonTopPanel.setBackground(Color.white);

        gameOutlinePanel.setBackground(Color.WHITE);

        leftPanel.add(gameOutlinePanel,BorderLayout.NORTH);
        leftPanel.add(buttonPanel,BorderLayout.CENTER);
        leftPanel.add(backButton,BorderLayout.SOUTH);

    }
    public void setupCenterPanel()
    {
        loadStartedScreen();
    }

    public void startGame()
    {

        centerPanel.removeAll();
        centerPanel.revalidate();
        centerPanel.repaint();
        startButton.setEnabled(false);

        //get mode
        int mode=gameModeCB.getSelectedIndex();

        //generate list question and answer



        //load game here
//        String[] anwers={"Ans1","Ans2","Ans3","Ans4"};
        loadGame(mode);
//        loadGame(quiz.question,quiz.answers.toArray(new String[0]),quiz.rightAns);

    }

    public void loadGame(int mode)
    {
        centerPanel.removeAll();
        centerPanel.revalidate();

        centerPanel.setLayout(new BorderLayout());


        GameQuiz quiz;
        quiz=generateQuiz(mode);
        String question= quiz.question;
        String[]answers=quiz.answers.toArray(new String[0]);
        int rightAnwser=quiz.rightAns;
        JPanel topGamePanel=new JPanel(new BorderLayout());
        JPanel questionPanel=new JPanel(new BorderLayout());
        JPanel centerGamePanel=new JPanel(new GridLayout(2,2,25,25));
        JPanel emptyPanel=new JPanel();

        topGamePanel.setLayout(new BoxLayout(topGamePanel,BoxLayout.Y_AXIS));
        topGamePanel.setPreferredSize(new Dimension(600,100));
        //handle question string
        if(mode==0)
        {
            question=String.format("Question %d. What is mean of \"%s\"?",currentAnswerQuiz+1,question);
        }
        else
        {
            question=String.format("Question %d. What is Slang word of \"%s\"?",currentAnswerQuiz+1,question);
        }

        JLabel questionLabel=new JLabel(question);
        questionLabel.setFont(new Font("Arial",Font.BOLD,16));
//        questionLabel.setForeground(Color.white);

        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setVerticalAlignment(SwingConstants.CENTER);

        questionPanel.add(questionLabel,BorderLayout.CENTER);
        questionPanel.setBackground(Color.white);
        questionPanel.setBorder(new BevelBorder(BevelBorder.RAISED));

        topGamePanel.add(questionPanel,BorderLayout.CENTER);
        topGamePanel.setBackground(Color.BLUE);
        topGamePanel.setBorder(new EmptyBorder(30,30,30,30));
        SlangAnswerButton.setRightAnswerID(rightAnwser);

        emptyPanel.setPreferredSize(new Dimension(600,200));

        for (int i=0;i<answers.length;i++)
        {
            SlangAnswerButton answerButton=new SlangAnswerButton(answers[i],i);

            answerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(answerButton.getAnswerID()==rightAnwser)
                    {
                        System.out.println("Right answer");
                        rightAnswerQuiz++;
                    }
                    currentAnswerQuiz++;
                    updateScore();
                    if(currentAnswerQuiz<_NUMBER_OF_QUIZ)
                    {
                        loadGame(mode);
                        System.out.println("Current: "+String.valueOf(currentAnswerQuiz));
                        System.out.println("Right: "+String.valueOf(rightAnswerQuiz));
                    }
                    else
                    {
                        //load done
                        JOptionPane.showMessageDialog(null,"Your game is finished with score: "+String.valueOf(rightAnswerQuiz));
                        loadStartedScreen();
                        startButton.setEnabled(true);
                    }
                }
            });
            centerGamePanel.add(answerButton);
        }

        centerGamePanel.setBorder(new EmptyBorder(30,30,30,30));

        topGamePanel.setBackground(Color.DARK_GRAY);
        centerGamePanel.setBackground(Color.DARK_GRAY);
        emptyPanel.setBackground(Color.DARK_GRAY);

        centerPanel.add(topGamePanel,BorderLayout.NORTH);
        centerPanel.add(centerGamePanel,BorderLayout.CENTER);
        centerPanel.add(emptyPanel,BorderLayout.SOUTH);

        centerPanel.setVisible(true);

    }

    public void updateScore()
    {
        scoreLabel.setText(String.format("%d/%d",rightAnswerQuiz,_NUMBER_OF_QUIZ));
    }

    public void loadStartedScreen()
    {
//        centerPanel.setVisible(false);
        currentAnswerQuiz=rightAnswerQuiz=0;
        centerPanel.removeAll();
        centerPanel.revalidate();
        centerPanel.repaint();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.DARK_GRAY);
        JLabel startLabel=new JLabel("PRESS START TO PLAY GAME");
        startLabel.setForeground(Color.white);
        startLabel.setFont(new Font("Arial",Font.BOLD,25));
        startLabel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(startLabel,BorderLayout.CENTER);
        updateScore();

    }

    class GameQuiz{
        public String question;
        public ArrayList<String> answers;
        public int rightAns;
        public GameQuiz()
        {
            question="";
            answers=new ArrayList<>();
            rightAns=-1;
        }

        @Override
        public String toString() {
            return "GameQuiz{" +
                    "question='" + question + '\'' +
                    ", answers=" + answers +
                    ", rightAns=" + rightAns +
                    '}';
        }
    }

    public GameQuiz generateQuiz(int mode)
    {
        GameQuiz quiz=new GameQuiz();
        Random random=new Random();
        String rightAnswer="";
        //get one question and answer
        int n=wordList.size();
        int pos=random.nextInt(n);
        if(mode==0) {
            quiz.question = wordList.get(pos);
            for (int i = 0; i < _NUMBER_OF_ANSWER - 1; i++) {
                quiz.answers.add(meanList.get(random.nextInt(n)).get(0));
            }
            rightAnswer=meanList.get(pos).get(0);
            quiz.answers.add(rightAnswer);
            Collections.shuffle(quiz.answers);
        }
        else {
            quiz.question = meanList.get(pos).get(0);
            for (int i = 0; i < _NUMBER_OF_ANSWER - 1; i++) {
                quiz.answers.add(wordList.get(random.nextInt(n)));
            }
            rightAnswer=wordList.get(pos);
            quiz.answers.add(rightAnswer);
            Collections.shuffle(quiz.answers);
        }

        //set right anwser
        for(int i=0;i<_NUMBER_OF_ANSWER;i++)
        {
            if(quiz.answers.get(i).equals(rightAnswer)) {
                quiz.rightAns = i;
                break;
            }
        }

        return quiz;
    }


}

class SlangAnswerButton extends JButton
{
    private static int rightAnswerID=-1;
    private String answer;
    private int answerID;
    public SlangAnswerButton(String answer,int answerID)
    {
        super(answer);
        this.answer=answer;
        this.answerID=answerID;
    }

    public static void setRightAnswerID(int rightID)
    {
        rightID=rightID;
    }

    public int getAnswerID()
    {
        return answerID;
    }

    public static int getRightAnswerID()
    {
        return rightAnswerID;
    }


}
