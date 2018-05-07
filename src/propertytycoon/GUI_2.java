package propertytycoon;


import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GrayFilter;
import javax.swing.JComboBox;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import jxl.read.biff.BiffException;

public class GUI_2 extends javax.swing.JFrame implements ActionListener,WindowListener {

    public GUI_2(GameController gameController) throws IOException, BiffException {
        UI(gameController);
    }
    
    PropertyGroupColour[] propertyColours = {PropertyGroupColour.Blue, PropertyGroupColour.Brown, PropertyGroupColour.DarkBlue, PropertyGroupColour.Green,
                        PropertyGroupColour.Orange, PropertyGroupColour.Purple, PropertyGroupColour.Red, PropertyGroupColour.White, PropertyGroupColour.Yellow};
    PropertyType[] types = {PropertyType.Standard, PropertyType.Station, PropertyType.Utility};
    
    private int logCounter;
    
    private JPanel chechBoxPanel;
    private Checkbox abridgedCheckBox;
    private Checkbox tradeCheckBox;
    
    private JPanel auctionPanel;
    private JComboBox auctionPlayerBox;
    private JTextField auctionAmaountField;
    
    private JPanel tradePanel;
    private JTextField tradePropertyField;
    private JComboBox tradePlayerBox;
    
    private JTextArea realAuto;

    private UserChoiceRequest currentRequest;
    private JTextArea pastActions;
    private JScrollPane pastActionsScrollPane;
    
    private Map<String, JLabel> tokenlabels;
    
    private GameController gameController;
    private GameModel gameModel;
    // dynamic tables
    private JTable boardInfo;
    private JTable playerStatus;
    // options panel
    private JButton selectOptionButton;
    private JComboBox<String> optionsBox;
    DefaultComboBoxModel<String> comboBoxModel;
            
    // game start panel
    private JButton startGameButton;
    private JButton quitGameButton;
    private JButton saveGameButton;
    private JButton loadGameButton;
    JSpinner numOfRealPlayersBox;
    JSpinner numOfAutoPlayersBox;
    
    private JTextArea gameLog;
    private JScrollPane gameLogScrollPane;
    
    private ArrayList<PlayerOption> options;
    private String[] optionTitles;
    
    private ArrayList<Space> spaces;
    private ArrayList<Player> players;
    private ArrayList<Property> properties;
    
    private JLabel currentPlayerLabel;
    
    private JLabel label;
    private JButton button;

    private JButton rollButton, buybutton, endbutton;

    private JFrame infoFrame, mainFrame;
    
    private JPanel mainPanel;

    private JPanel boardpanel, bpanel1, bpanel2, bpanel3, bpanel4;

    private JPanel opanel1, opanelin, inneropanel1, inneropanel2, opanel3, opanel4;

    private JLabel announcementlabel;

    private JPanel middlepanel;

    private JLabel[] labelTiles;

    private Integer turn;

    private boolean rolled = false, showed = false;

    private JButton[][] playerbuttons;

    private JFrame cardFrame;

    private int playernumber;

    String[] colours = {"Red", "Brown", "Purple", "Utilities", "Station", "Green", "Deep blue", "Blue", "Orange", "Yellow"};
    public static final String[] numbers = {"2", "3", "4", "5", "6"};

    public void UI(GameController gameController) throws IOException, BiffException {

        /////////////////////////////////////////////////////////////////////////
        this.gameController = gameController;
        this.gameModel = gameController.getGameModel();
        
        logCounter = 0;
        
        spaces = gameModel.getSpaces();
        players = gameModel.getPlayers();
        properties = gameModel.getProperties();
        
        chechBoxPanel = new JPanel(new GridLayout(2,1));
        abridgedCheckBox = new Checkbox("Abridged Game?");
        tradeCheckBox = new Checkbox("Allow Trading?");
        chechBoxPanel.add(abridgedCheckBox);
        chechBoxPanel.add(tradeCheckBox);
        
        auctionPanel = new JPanel(new GridLayout(2,2));
        String[] tokens = new String[players.size()];
        for (int pl = 0; pl < players.size(); pl++)
        {
            tokens[pl] = players.get(pl).getToken().name();
        }
        auctionPlayerBox = new JComboBox(tokens);
        auctionAmaountField = new JTextField();
        auctionPanel.add(new JLabel("Select player:"));
        auctionPanel.add(new JLabel("Type bid amount:"));
        auctionPanel.add(auctionPlayerBox);
        auctionPanel.add(auctionAmaountField);
        
        tradePanel = new JPanel(new GridLayout(2,2));
        tradePropertyField = new JTextField();
        tradePlayerBox = new JComboBox(tokens);
        tradePanel.add(new JLabel("Type name of property to be traded:"));
        tradePanel.add(new JLabel("Select player to trade with:"));
      
        realAuto = new JTextArea();
        realAuto.setEditable(false);
        
        currentPlayerLabel = new JLabel("");
        
        pastActionsScrollPane = new JScrollPane();
        
        
        pastActions = new JTextArea("User Message: ");
        pastActions.setOpaque(true);
        pastActions.setLineWrap(true);
        pastActions.setEditable(false);
        
        // Initialize options panel
        selectOptionButton = new JButton("Go!");
        
        // Initialize game start panel
        startGameButton = new JButton("Start");
        quitGameButton = new JButton("Quit");
        saveGameButton = new JButton("Save Game");
        loadGameButton = new JButton("Load Game");
        SpinnerModel numRealPlayersModel = new SpinnerNumberModel(1,1,6,1);   
        SpinnerModel numAutoPlayersModel = new SpinnerNumberModel(1,1,6,1);   
        numOfRealPlayersBox = new JSpinner(numRealPlayersModel);
        numOfAutoPlayersBox = new JSpinner(numAutoPlayersModel);
        optionTitles = new String[0];
        gameLog = new JTextArea();
        gameLog.setLineWrap(true);
        gameLog.setEditable(false);
        
        startGameButton.setSize(90, 60);
        quitGameButton.setSize(90, 60);
        saveGameButton.setSize(90, 60);
        loadGameButton.setSize(90, 60); 
        numOfRealPlayersBox.setSize(170, 30);
        numOfAutoPlayersBox.setSize(170, 30);
        gameLog.setSize(420,500);
        
        startGameButton.addActionListener(this);
        quitGameButton.addActionListener(this);
        saveGameButton.addActionListener(this);
        loadGameButton.addActionListener(this);
        selectOptionButton.addActionListener(this);
        
        comboBoxModel = new DefaultComboBoxModel<>(optionTitles);
        optionsBox = new JComboBox(comboBoxModel);
        if (optionTitles.length > 0)
        {
            optionsBox.setSelectedIndex(0);
        }
        optionsBox.setSize(170, 30);
//        _optionsBox.setLocation(5, 740);
        /////////////////////////////////////////////////////////////////////////  
        
        JFrame frame = new JFrame("Input Dialog");
        //frame.setSize(300, 300);
//        String realPlayerNum = (String) JOptionPane.showInputDialog(frame,
//                "How many real players?",
//                "Player number",
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                numbers,
//                numbers[0]);
//        
//        String autoPlayerNum = (String) JOptionPane.showInputDialog(frame,
//                "How many auto players?",
//                "Player number",
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                numbers,
//                numbers[0]);
//        
//
//        System.out.println("Number of real players selected is : " + realPlayerNum);
//        System.out.println("Number of auto players selected is : " + autoPlayerNum);

//        playernumber = Integer.parseInt(realPlayerNum); // keep for startGame

        tokenlabels = new HashMap<>();

        turn = 0;
//        PlayerTokenType[] chars = new PlayerTokenType[playernumber];
//
//        LinkedList characs = new LinkedList();
//        characs.add(PlayerTokenType.Boot);
//        characs.add(PlayerTokenType.Cat);
//        characs.add(PlayerTokenType.Goblet);
//        characs.add(PlayerTokenType.Hatstand);
//        characs.add(PlayerTokenType.Smartphone);
//        characs.add(PlayerTokenType.Spoon);
//
//        for (int y = 0; y < playernumber; y++) {
//            System.out.println(characs.peek());
//            chars[y] = (PlayerTokenType) characs.poll();
//        }
//
//        players = new Player[playernumber];
//        for (int y = 0; y < playernumber; y++) {
//            players[y] = new Player(chars[y]);
//        }
//
//        game = new Game(chars);
         
        ///////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////
        
        labelTiles = new JLabel[40];

        String path = System.getProperty("user.dir") + "\\src\\resources\\";
        System.out.println(path);

        spaces = gameModel.getSpaces();
        
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setSize(1031, 980);

        mainFrame = new JFrame("Property Tycoon");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //mainFrame.setSize(1650, 1650);  //1650 1650
        mainFrame.setSize(1500, 1020); // 1031
//        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setLayout(null);
        mainFrame.setResizable(false);
        
        opanel1 = new JPanel();

        opanel3 = new JPanel();

        opanel4 = new JPanel();

        //opanel3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opanel4.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        opanel1.setSize(625, 62);

        opanel3.setSize(125, 2000);

        opanel4.setSize(625, 80);

        opanel1.setLayout(new GridLayout(1, 0));

        opanel3.setLayout(new GridLayout(0, 1));
        opanel4.setLayout(new GridLayout(1, 0));

        boardpanel = new JPanel(new BorderLayout());
//        boardpanel.setSize(562, 562);
        boardpanel.setSize(930, 810);
        boardpanel.setForeground(Color.red);

        mainPanel.setLayout(new BorderLayout());

        bpanel1 = new JPanel();
        bpanel2 = new JPanel();
        bpanel3 = new JPanel();
        bpanel4 = new JPanel();

        bpanel1.setSize(562, 62);
        bpanel2.setSize(62, 375);
        bpanel3.setSize(62, 375);
        bpanel4.setSize(562, 62);

        bpanel1.setLayout(new GridLayout(1, 0));
        bpanel2.setLayout(new GridLayout(0, 1));
        bpanel3.setLayout(new GridLayout(0, 1));
        bpanel4.setLayout(new GridLayout(1, 0));

        boardpanel.add(bpanel1, BorderLayout.PAGE_START);
        boardpanel.add(bpanel2, BorderLayout.LINE_START);
        boardpanel.add(bpanel3, BorderLayout.LINE_END);
        boardpanel.add(bpanel4, BorderLayout.PAGE_END);

        middlepanel = new JPanel();
        middlepanel.setLayout(new BorderLayout(1, 0));

        announcementlabel = new JLabel("GAME STARTED", SwingConstants.CENTER);
        announcementlabel.setFont(new Font("Arial", Font.PLAIN, 15));

        BufferedImage Pic;
        Image img;

        Pic = ImageIO.read(new File(path + "background.png"));
//        Pic = ImageIO.read(new File(path));
        img = Pic.getScaledInstance(865, 814,
                Image.SCALE_SMOOTH);
        label = new JLabel(new ImageIcon(img));

        middlepanel.add(label);

        boardpanel.add(middlepanel, BorderLayout.CENTER);

        mainPanel.add(opanel1, BorderLayout.PAGE_START);

        mainPanel.add(opanel3, BorderLayout.LINE_END);
        mainPanel.add(opanel4, BorderLayout.PAGE_END);

        //top panel tokens
        BufferedImage wPic;
        Image dimg;
        ArrayList<String> paths = new ArrayList<>();
        paths.add("Boot");
        paths.add("Cat");
        paths.add("Goblet");
        paths.add("Hatstand");
        paths.add("Smartphone");
        paths.add("Spoon");

        playerbuttons = new JButton[6][10];
        for (int i = 0; i < 6; i++) {
            boolean passed = false;
            if(i >= players.size()){
                passed = true;
            }
            opanelin = new JPanel(new GridLayout(0, 2));
            opanel1.add(opanelin);

            inneropanel1 = new JPanel();
            inneropanel2 = new JPanel(new GridLayout(5, 2));
            opanelin.add(inneropanel1);
            opanelin.add(inneropanel2);

            JButton brown = new JButton("");
            brown.setBackground(new Color(139, 69, 19));
            if(passed){
                brown.setBackground(Color.LIGHT_GRAY);
            }
            brown.addActionListener(this);
            brown.setPreferredSize(new Dimension(45,15));
            brown.setActionCommand(Integer.toString(i) + "brown");
            brown.setFont(new Font("Arial", Font.PLAIN, 9));

            JButton purple = new JButton("");
            purple.setBackground(Color.magenta);
            if(passed){
                purple.setBackground(Color.LIGHT_GRAY);
            }
            purple.addActionListener(this);
            purple.setPreferredSize(new Dimension(45,15));
            purple.setActionCommand(Integer.toString(i) + "purple");

            JButton red = new JButton("");
            red.setBackground(Color.red);
            if(passed){
                red.setBackground(Color.LIGHT_GRAY);
            }
            red.addActionListener(this);
            red.setPreferredSize(new Dimension(45,15));
            red.setActionCommand(Integer.toString(i) + "red");

            JButton green = new JButton("");
            green.setBackground(Color.green);
            if(passed){
                green.setBackground(Color.LIGHT_GRAY);
            }
            green.addActionListener(this);
            green.setPreferredSize(new Dimension(45,15));
            green.setActionCommand(Integer.toString(i) + "green");

            JButton blue = new JButton("");
            blue.setBackground(Color.cyan);
            if(passed){
                blue.setBackground(Color.LIGHT_GRAY);
            }
            blue.addActionListener(this);
            blue.setPreferredSize(new Dimension(45,15));
            blue.setActionCommand(Integer.toString(i) + "blue");

            JButton orange = new JButton("");
            orange.setBackground(Color.orange);
            if(passed){
                orange.setBackground(Color.LIGHT_GRAY);
            }
            orange.addActionListener(this);
            orange.setPreferredSize(new Dimension(45,15));
            orange.setActionCommand(Integer.toString(i) + "orange");

            JButton yellow = new JButton("");
            yellow.setBackground(Color.yellow);
            if(passed){
                yellow.setBackground(Color.LIGHT_GRAY);
            }
            yellow.addActionListener(this);
            yellow.setPreferredSize(new Dimension(45,15));
            yellow.setActionCommand(Integer.toString(i) + "yellow");

            JButton darkblue = new JButton("");
            darkblue.setBackground(new Color(30, 144, 255));
            if(passed){
                darkblue.setBackground(Color.LIGHT_GRAY);
            }
            darkblue.addActionListener(this);
            darkblue.setPreferredSize(new Dimension(45,15));
            darkblue.setActionCommand(Integer.toString(i) + "darkblue");

            JButton utilities = new JButton("");
            utilities.setBackground(Color.PINK);
            if(passed){
                utilities.setBackground(Color.LIGHT_GRAY);
            }
            utilities.addActionListener(this);
            utilities.setPreferredSize(new Dimension(45,15));
            utilities.setActionCommand(Integer.toString(i) + "utilities");

            JButton stations = new JButton("");
            stations.setBackground(Color.WHITE);
            if(passed){
                stations.setBackground(Color.LIGHT_GRAY);
            }
            stations.addActionListener(this);
            stations.setPreferredSize(new Dimension(45,15));
            stations.setActionCommand(Integer.toString(i) + "stations");

            playerbuttons[i][0] = red; //red
            playerbuttons[i][1] = brown; //brown
            playerbuttons[i][2] = purple; //purple
            playerbuttons[i][3] = utilities; //utilities
            playerbuttons[i][4] = stations;  //stations
            playerbuttons[i][5] = green; //green
            playerbuttons[i][6] = darkblue; //deep blue
            playerbuttons[i][7] = blue; //blue, orange, yellow
            playerbuttons[i][8] = orange;
            playerbuttons[i][9] = yellow;

            inneropanel2.add(brown);
            inneropanel2.add(purple);
            inneropanel2.add(red);
            inneropanel2.add(green);
            inneropanel2.add(blue);
            inneropanel2.add(orange);
            inneropanel2.add(yellow);
            inneropanel2.add(darkblue);
            inneropanel2.add(utilities);
            inneropanel2.add(stations);

            button = new JButton();
            button.setSize(75, 87);
            System.out.println(path + paths.get(i) + ".png");
            wPic = ImageIO.read(new File(path + paths.get(i) + ".png"));
            dimg = wPic.getScaledInstance(button.getWidth(), button.getHeight(),
                    Image.SCALE_SMOOTH);
            if(passed){
                dimg = GrayFilter.createDisabledImage(dimg);
            }
            button = new JButton(new ImageIcon(dimg));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            button.addActionListener(this);
            button.setActionCommand(paths.get(i));
            inneropanel1.add(button);
        }
        
        GridBagConstraints c = new GridBagConstraints();

        //RIGHT BUTTONS       
        for (int i = 0; i < 6; i++) {
            boolean passed = false;
            if(i >= players.size()){
                passed = true;
            }
            label = new JLabel();
            label.setSize(125, 125);
            wPic = ImageIO.read(new File(path + paths.get(i) + ".png"));
            dimg = wPic.getScaledInstance(62, 62,
                    Image.SCALE_SMOOTH);
            if(passed){
                dimg = GrayFilter.createDisabledImage(dimg);
            }
            label = new JLabel(new ImageIcon(dimg));
            label.setForeground(Color.green);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
//            label.setLocation(i*100, 5);
            opanel3.add(label);
   
            tokenlabels.put(paths.get(i), label);
        }
        

        //Bottom buttons
        rollButton = new JButton(" ROLL ");
        rollButton.addActionListener(this);
        rollButton.setPreferredSize(new Dimension(62, 62));
        rollButton.setFont(new Font("Arial", Font.BOLD, 10));
        rollButton.setActionCommand("rolled"); // turn to roll
//        opanel4.add(rollButton);
        opanel4.add(startGameButton);

        buybutton = new JButton(" BUY ");
        buybutton.setPreferredSize(new Dimension(62, 62));
        buybutton.setForeground(Color.black);
        buybutton.addActionListener(this);
        buybutton.setFont(new Font("Arial", Font.BOLD, 15));
        buybutton.setActionCommand("buy");
        buybutton.setEnabled(false);
//        opanel4.add(buybutton);
        opanel4.add(quitGameButton);

        button = new JButton(" PLACEHOLDER  ");
        button.setPreferredSize(new Dimension(62, 62));
        button.setForeground(Color.PINK);
        button.setFont(new Font("Arial", Font.BOLD, 10));
//        opanel4.add(button);
        opanel4.add(saveGameButton);

        endbutton = new JButton(" END TURN ");
        endbutton.setPreferredSize(new Dimension(62, 62));
        endbutton.setForeground(Color.BLACK);
        endbutton.addActionListener(this);
        endbutton.setActionCommand("endturn");
        endbutton.setFont(new Font("Arial", Font.BOLD, 10));
        endbutton.setEnabled(false);
//        opanel4.add(endbutton);
        opanel4.add(loadGameButton);
        
        endbutton = new JButton(" END TURN ");
        endbutton.setPreferredSize(new Dimension(62, 62));
        endbutton.setForeground(Color.BLACK);
        endbutton.addActionListener(this);
        endbutton.setActionCommand("endturn");
        endbutton.setFont(new Font("Arial", Font.BOLD, 10));
        endbutton.setEnabled(false);
//        opanel4.add(endbutton);
        opanel4.add(selectOptionButton);
        
        
//        opanel4.add(startGameButton);
//        opanel4.add(quitGameButton);
//        opanel4.add(saveGameButton);
//        opanel4.add(loadGameButton);
        

        //tiles top
        for (int i = 0; i < 11; i++) {
            String file_path = path + spaces.get(i).getTitle() + ".png";
            if (file_path.contains("Jail/Just visiting")){file_path = path + "Jail.png";}
            System.out.println(file_path); //**********************************
            label = new JLabel();
            label.setSize(85, 61);
            wPic = ImageIO.read(new File(file_path));
            dimg = wPic.getScaledInstance(label.getWidth(), label.getHeight(),
                    Image.SCALE_SMOOTH);
            label = new JLabel(new ImageIcon(dimg));
            label.setForeground(Color.BLACK);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            bpanel1.add(label);
            labelTiles[i] = label;
        }

        //tiles left
        for (int i = 8; i >= 0; i--) {
            System.out.println(path + spaces.get(i + 31));
            label = new JLabel();
            label.setSize(61, 85);
            wPic = ImageIO.read(new File(path + spaces.get(31 + i).getTitle() + ".png"));
            if(i == 5){
                wPic = ImageIO.read(new File(path + "Opportunity knocks left.png"));
            }
            if(i == 2){
                wPic = ImageIO.read(new File(path + "Pot Luck left.png"));
            }
            dimg = wPic.getScaledInstance(label.getWidth(), label.getHeight(),
                    Image.SCALE_SMOOTH);
            label = new JLabel(new ImageIcon(dimg));
            label.setForeground(Color.green);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            bpanel2.add(label);
            labelTiles[31 + i] = label;

        }
        //tiles right
        for (int i = 0; i < 9; i++) {
            System.out.println(path + spaces.get(i + 11));
            label = new JLabel();
            label.setSize(61,85 );
            wPic = ImageIO.read(new File(path + spaces.get(11 + i).getTitle() + ".png"));
            if(i == 6){
            wPic = ImageIO.read(new File(path + "Pot Luck Right.png"));
            }
            dimg = wPic.getScaledInstance(label.getWidth(), label.getHeight(),
                    Image.SCALE_SMOOTH);
            label = new JLabel(new ImageIcon(dimg));
            label.setForeground(Color.green);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            bpanel3.add(label);
            labelTiles[11 + i] = label;

        }
        //tiles bottom
        for (int i = 10; i >= 0; i--) {
            System.out.println(path + spaces.get(i + 20));
            label = new JLabel();
            label.setSize(85, 61);
            wPic = ImageIO.read(new File(path + spaces.get(20 + i).getTitle() + ".png"));
            dimg = wPic.getScaledInstance(label.getWidth(), label.getHeight(),
                    Image.SCALE_SMOOTH);
            label = new JLabel(new ImageIcon(dimg));
            label.setForeground(Color.green);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            bpanel4.add(label);
            labelTiles[20 + i] = label;

        }

        mainPanel.add(boardpanel, BorderLayout.CENTER);
        boardpanel.setLocation(20, 110);
        mainFrame.add(boardpanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Should probably have a start game button for this stuff
//        try
//        {
//            gameController.startGame(Integer.parseInt(realPlayerNum), Integer.parseInt(autoPlayerNum));
//        }
//        catch (Exception ex)
//        {
//            System.out.println("Check startup dialogue return types!");
//        }
//        setOptionsFromRequest(gameController.processUserResponse(new UserChoiceResponse(null)));
//        UpdateUI();

        infoFrame = new JFrame("Info Pane");
        infoFrame.setAlwaysOnTop(true);
        infoFrame.setLocationRelativeTo(mainPanel);
        infoFrame.setSize(375, 625);
        
        mainPanel.setLocation(0, 0);
        mainFrame.add(mainPanel);
        
        /////////////////////////////////////////////////////////////////////////
        JLabel optionsBoxLabel  = new JLabel("Current Player Options:");
        optionsBoxLabel.setSize(170, 20);
        optionsBoxLabel.setLocation(1050,540);
        pastActions.setSize(220, 125);
        pastActions.setLocation(1230,570); // 1230
//        pastActionsScrollPane.setLocation(1230,570);
//        pastActionsScrollPane.setSize(220, 125);
//        pastActionsScrollPane.add(pastActions);
        optionsBox.setLocation(1050,570);
        
        currentPlayerLabel.setSize(170, 20);
        currentPlayerLabel.setLocation(1230,540);
        
        JLabel logLabel  = new JLabel("Game Log:");
        logLabel.setSize(170, 20);
        logLabel.setLocation(1050,10);
        gameLog.setLocation(1050,30);
//        gameLogScrollPane = new JScrollPane(gameLog);
//        gameLogScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        gameLogScrollPane.setSize(420,500);
//        gameLogScrollPane.setLocation(1050,30);
        
        mainFrame.add(gameLog);
        mainFrame.add(optionsBoxLabel);
        mainFrame.add(pastActions);
        mainFrame.add(optionsBox);
        mainFrame.add(logLabel);

        JLabel numRealPlayersLabel  = new JLabel("Select number of real players:");
        numRealPlayersLabel.setSize(170, 20);
        numRealPlayersLabel.setLocation(1050,700);
        numOfRealPlayersBox.setLocation(1050,720);
        
        JLabel numRealAutoLabel  = new JLabel("Select number of auto players:");
        numRealAutoLabel.setSize(170, 20);
        numRealAutoLabel.setLocation(1250,700);
        numOfAutoPlayersBox.setLocation(1250,720);
        
        realAuto.setSize(300, 160);
        realAuto.setLocation(1050, 780);
        
        JLabel gameSettingsLabel = new JLabel("Game Settings:");
        gameSettingsLabel.setSize(170, 20);
        gameSettingsLabel.setLocation(1370,760);
        chechBoxPanel.setSize(110,160);
        chechBoxPanel.setLocation(1360,780);
        
        mainFrame.add(gameSettingsLabel);
        mainFrame.add(chechBoxPanel);
        mainFrame.add(numRealPlayersLabel);
        mainFrame.add(numOfRealPlayersBox);
        mainFrame.add(numRealAutoLabel);
        mainFrame.add(numOfAutoPlayersBox);
        mainFrame.add(realAuto);
        mainFrame.add(currentPlayerLabel);
    }

//    public static void main(String args[]) throws IOException, BiffException {
//        GUI gui = new GUI();
//
//    }
    public void setOptionsFromRequest(UserChoiceRequest request)
    {
        options = request.getOptions();
    }
    
    public void getOptionTitles(UserChoiceRequest request)
    {
        options = request.getOptions();
        comboBoxModel.removeAllElements();
        for (int i = 0; i < options.size(); i++)
        {
            comboBoxModel.addElement(options.get(i).getTitle());
        }
        if (comboBoxModel.getSize() > 0)
        {
            comboBoxModel.setSelectedItem(options.get(0).getTitle());
        }
    }
    
    public void updateLog()
    {
        logCounter++;
        Player currentPlayer = gameModel.getCurrentPlayer();
        ArrayList<String> log = currentPlayer.getInstructionLog();
        String playerHistory = currentPlayer.getToken().name() + " - " + "*************************************\n";
        for (String str : log)
        {
            playerHistory += str + "\n";
        }
        gameLog.setText(playerHistory);
    }
    
    @Override
    public void windowClosing(WindowEvent e)
    {
        dispose();
        System.exit(0);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == selectOptionButton)    
        {
            int selectedIndex = optionsBox.getSelectedIndex();
            PlayerOption option = options.get(selectedIndex);
            if (option.toString().equals(PlayerOptionType.TradeProperties))
            {
                JOptionPane.showConfirmDialog(mainFrame, tradePanel, 
               "Please Enter Trade Info", JOptionPane.OK_CANCEL_OPTION);
                option.setAmount1(Integer.parseInt(tradePropertyField.getText()));
                option.setAmount2(tradePlayerBox.getSelectedIndex());
            }
            if (option.toString().equals(PlayerOptionType.AuctionProperty))
            {
                JOptionPane.showConfirmDialog(mainFrame, auctionPanel, 
               "Please Enter Auction Info", JOptionPane.OK_CANCEL_OPTION);
                option.setAmount1(Integer.parseInt(auctionAmaountField.getText()));
                option.setAmount2(auctionPlayerBox.getSelectedIndex());
            }
            
            if (option.getOptionType().name().equals("EndOfTurn")) {updateLog();}
            
            UserChoiceRequest request = gameController.processUserResponse(new UserChoiceResponse(option));
            currentRequest = request;
            String message = "User Message: " + "\n" + request.getMessage();
            pastActions.setText(message); //+ currentRequest.getMessage()
            UpdateUI();
            getOptionTitles(request);
        }
        else if (e.getSource() == startGameButton)
        {
            try
            {
                if (abridgedCheckBox.getState()) {gameModel.setIsAbridgedGame(true);}
                if (tradeCheckBox.getState()) {gameModel.setIsTradingAllowed(true);}
                gameController.startGame((int)numOfRealPlayersBox.getValue(), (int)numOfAutoPlayersBox.getValue());
                String realAutoInfo = "";
                ArrayList<Player> activePlayers = new ArrayList<Player>();
                for (Player player : players)
                {
                    if (player.getIsActive()) {activePlayers.add(player);}
                }
                for (Player player : activePlayers) {realAutoInfo += player.getToken().name() + ": " + ((player.getIsAuto()) ? "Auto" : "Real") + "\n";}
                realAuto.setText(realAutoInfo);
                UpdateUI();
                getOptionTitles(gameController.processUserResponse(new UserChoiceResponse(null)));
            } 
            catch (Exception ex)
            {
            }
        }
        
        else if (e.getSource() == quitGameButton)
        {
            System.exit(0);
        }
        else if (e.getSource() == saveGameButton)
        {
            GameController.SaveGame(gameModel, "SavedGame.xml");
        }
        else if (e.getSource() == loadGameButton)
        {
            gameModel = GameController.LoadGame("SavedGame.xml");
        }
        else
        {
            for (int pl = 0; pl < players.size(); pl++)
            {
                for (int col = 0; col < 10; col++)
                {
                   if (playerbuttons[pl][col].getActionCommand().equals(e.getActionCommand()))
                   {
                       String colour = e.getActionCommand().substring(1);
                       PropertyGroupColour selectedColour = null;
                       for (int pgc = 0; pgc < propertyColours.length; pgc++)
                       {
                           if (propertyColours[pgc].name().toLowerCase().equals(colour)){selectedColour = propertyColours[pgc];}
                       }
                       ArrayList<Property> colourProperties = gameModel.getPlayerGroupProperties(selectedColour, pl);
                       if (colourProperties.isEmpty())
                       {
                           JOptionPane.showMessageDialog(mainFrame,
                            "There are no properties owned of this colour",
                            "A plain message",
                            JOptionPane.PLAIN_MESSAGE);
                           return;
                       }
                       String propertyGroupInfo = "";
                       for (Property prop : colourProperties)
                       {
                           propertyGroupInfo += prop.getPropertyName() + "\n\n" + "Number of Houses: " + prop.getNumberOfHouses() + "\n"
                            + "Has a Hotel?: " + ((prop.hasHotel()) ? "Yes" : "No") + "\n"
                            + "Is priperty Mortgaged?: " + ((prop.getIsMortgaged()) ? "Yes" : "No");
                       }
                        JOptionPane.showMessageDialog(mainFrame,
                        propertyGroupInfo, 
                        "Property Group Info",
                        JOptionPane.PLAIN_MESSAGE);
                   }
                }
            }
        }
        try
        {
            
        }
        catch (Exception ex)
        {
            
        }
//        try {
//            System.out.println(e.getActionCommand());
//            int playercalled = Integer.parseInt(e.getActionCommand().substring(0, 1));
//            System.out.println(playercalled);
//            String colour = e.getActionCommand().substring(1);
//            //{"Red", "Brown", "Purple", "Utilities", "Station", "Green", "Deep blue", "Blue", "Orange", "Yellow"};
//            switch (colour) {
//                case "red":
//                    createCardPanel(players.get(playercalled), "Red");
//                    break;
//                case "brown":
//                    createCardPanel(players.get(playercalled), "Brown");
//                    break;
//                case "yellow":
//                    createCardPanel(players.get(playercalled), "Yellow");
//                    break;
//                case "blue":
//                    createCardPanel(players.get(playercalled), "Blue");
//                    break;
//                case "darkblue":
//                    createCardPanel(players.get(playercalled), "Deep blue");
//                    break;
//                case "utilities":
//                    createCardPanel(players.get(playercalled), "Utility");
//                    break;
//                case "stations":
//                    createCardPanel(players.get(playercalled), "Station");
//                    break;
//                case "orange":
//                    createCardPanel(players.get(playercalled), "Orange");
//                    break;
//                case "green":
//                    createCardPanel(players.get(playercalled), "Green");
//                    break;
//                case "purple":
//                    createCardPanel(players.get(playercalled), "Purple");
//                    break;
//            }
//        } catch (Exception ex) {
//
//        }
    }
    
    public void updatePlayerbuttons()
    {
        
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        String selectedOption = "";
////        if ("rolled".equals(e.getActionCommand())) {
////            if (!rolled) {
////                System.out.println(players[turn].characters_Player().toString() + " - Rolled -");
////                game.player_turn(players[turn]);
////                rolled = true;
////                checkSpace();
////                UpdateUI();
////            }
////        }   
//            selectedOption = "ThrowDice";
//            UpdateUI();
//        
//        if ("buy".equals(e.getActionCommand())) {
////            for (Properties prop : game.properties) {
////                if (prop.returnPos() == players[turn].Player_position() + 1) {
////                    players[turn].property_buy(prop);
////                    System.out.println(players[turn].characters_Player() + " bought " + prop.space_name());
////                    Announce(players[turn].characters_Player() + " bought " + prop.space_name());
////                    buybutton.setEnabled(false);
////                }
////            }
//            int currentPlayerIndex = gameModel.getCurrentPlayerIndex();
//            for (Space space : spaces)
//            {
//                if (space instanceof PropertySpace)
//                {
//                    if (((PropertySpace)space).getProperty().getOwnerIndex() == currentPlayerIndex)
//                    {
//                        Announce(players.get(currentPlayerIndex).getToken().name() + " bought " + space.getTitle());
//                    }
//                }
//            }
//            
//            buybutton.setEnabled(false);
//            selectedOption = "BuyProperty";
//            UpdateUI();
//        }
//
//        if ("endturn".equals(e.getActionCommand())) {
//            selectedOption = "EndOfTurn";
//        }
//        try {
//
//            if (e.getActionCommand().substring(0, 8).equalsIgnoreCase("buyhouse")) {
////                System.out.println(e.getActionCommand().substring(8));
////                for (Properties prop : game.properties) {
////
////                    if (prop.space_name().equalsIgnoreCase(e.getActionCommand().substring(8))) {
////                        System.out.println("Attempting to buy house for : " + prop.space_name());
////                        prop.buy_house(players[turn]);
////                        UpdateUI();
////                        cardFrame.setVisible(false);
////                        cardFrame.dispose();
////                    }
////                }
//                selectedOption = "ImproveProperty";
//                UpdateUI();
//                cardFrame.setVisible(false);
//                cardFrame.dispose();
//            }
//            if (e.getActionCommand().substring(0, 8).equalsIgnoreCase("buyhotel")) {
////                System.out.println(e.getActionCommand().substring(8));
////                for (Properties prop : game.properties) {
////
////                    if (prop.space_name().equalsIgnoreCase(e.getActionCommand().substring(8))) {
////                        System.out.println("Attempting to buy hotel for : " + prop.space_name());
////                        prop.buy_hotel(players[turn]);
////                        UpdateUI();
////                        cardFrame.setVisible(false);
////                        cardFrame.dispose();
////                    }
////                }
//                selectedOption = "ImproveProperty";
//                UpdateUI();
//                cardFrame.setVisible(false);
//                cardFrame.dispose();
//            }
//            if (e.getActionCommand().substring(0, 4).equalsIgnoreCase("sell")) {
////                System.out.println("Selling a property");
////                for (Properties prop : game.properties) {
////
////                    if (prop.space_name().equalsIgnoreCase(e.getActionCommand().substring(4))) {
////                        System.out.println("Attempting to sell hotel/house or property : " + prop.space_name());
////                        System.out.println(prop.property_sell(players[turn]));
////                        UpdateUI();
////                        cardFrame.setVisible(false);
////                        cardFrame.dispose();
////                    }
////                }
//                selectedOption = "SellProperty";
//                UpdateUI();
//                cardFrame.setVisible(false);
//                cardFrame.dispose();
//            }
//        } catch (Exception ex) {
//
//        }
//
//        try {
//            System.out.println(e.getActionCommand());
//            int playercalled = Integer.parseInt(e.getActionCommand().substring(0, 1));
//            System.out.println(playercalled);
//            String colour = e.getActionCommand().substring(1);
//            //{"Red", "Brown", "Purple", "Utilities", "Station", "Green", "Deep blue", "Blue", "Orange", "Yellow"};
//            switch (colour) {
//                case "red":
//                    createCardPanel(players.get(playercalled), "Red");
//                    break;
//                case "brown":
//                    createCardPanel(players.get(playercalled), "Brown");
//                    break;
//                case "yellow":
//                    createCardPanel(players.get(playercalled), "Yellow");
//                    break;
//                case "blue":
//                    createCardPanel(players.get(playercalled), "Blue");
//                    break;
//                case "darkblue":
//                    createCardPanel(players.get(playercalled), "Deep blue");
//                    break;
//                case "utilities":
//                    createCardPanel(players.get(playercalled), "Utility");
//                    break;
//                case "stations":
//                    createCardPanel(players.get(playercalled), "Station");
//                    break;
//                case "orange":
//                    createCardPanel(players.get(playercalled), "Orange");
//                    break;
//                case "green":
//                    createCardPanel(players.get(playercalled), "Green");
//                    break;
//                case "purple":
//                    createCardPanel(players.get(playercalled), "Purple");
//                    break;
//            }
//        } catch (Exception ex) {
//
//        }
//        
//        PlayerOption toProcess = null;
//        for (PlayerOption option : options)
//        {
//            if (option.getOptionType().name().equals(selectedOption)){toProcess = option;}
//        }
//        UserChoiceRequest request = gameController.processUserResponse(new UserChoiceResponse(toProcess));
//        UpdateUI();
//    }

//    private void nextPlayer() {
//
//        rolled = false;
//
//        if (turn >= players.size() - 1) {
//            turn = 0;
//        } else {
//            turn++;
//        }
//        if (players[turn].jailed) {
//            System.out.println(players[turn].player_characters.toString() + " jailed, turn skipped");
//            players[turn].Player_still_in_jail();
//            nextPlayer();
//
//        } else {
//            UpdateUI();
//        }
//
//    }

    public void UpdateUI() {

//        gameLog.setText(currentRequest.getMessage() + "\n");
        String currentPlayerInfo = "Current Player: " + gameModel.getCurrentPlayer().getToken().name();
        currentPlayerLabel.setText(currentPlayerInfo);
        
        for (JLabel lab : labelTiles) {
            lab.setText("");
        }
        
        int i = 0;
        for (Player player : players) {
            for (int col = 0; col < 10; col++) 
            {
                int count = 0;
                for (Property property : gameModel.getPlayerProperties(i))
                {
                    if (property.getPropertyGroup().name().toLowerCase().equals(playerbuttons[i][col].getActionCommand().substring(1))){count++;}    
                }   
                Integer s = count;
                playerbuttons[i][col].setText(s.toString());
            }

            labelTiles[player.getCurrentSpaceIndex()].setText(labelTiles[player.getCurrentSpaceIndex()].getText() + player.getToken().name().toUpperCase().substring(0, 3) + " - ");
            labelTiles[player.getCurrentSpaceIndex()].setFont(new Font("Arial", Font.BOLD, 10));
            labelTiles[player.getCurrentSpaceIndex()].setHorizontalTextPosition(JLabel.CENTER);
            labelTiles[player.getCurrentSpaceIndex()].setForeground(Color.pink);

            JLabel curLabel = tokenlabels.get(player.getToken().name());
            curLabel.setText("£ " + player.getBalance() + " ");
            curLabel.setFont(new Font("Arial", Font.BOLD, 10));
            curLabel.setForeground(Color.black);
            curLabel.setHorizontalTextPosition(JLabel.CENTER);
            curLabel.setVerticalTextPosition(JLabel.BOTTOM);
            i++;

        }

        UpdateRollButton();
    }

    private void UpdateRollButton() {
        if (!rolled) {
            rollButton.setText(players.get(gameModel.getCurrentPlayerIndex()).getToken().name().toUpperCase() + "'S TURN , CLICK TO ROLL");
            endbutton.setEnabled(false);
            showed = false;
        } else {
            if (!showed) {
                Announce(players.get(gameModel.getCurrentPlayerIndex()).getToken().name() + " landed on " + spaces.get(players.get(gameModel.getCurrentPlayerIndex()).getCurrentSpaceIndex()).getTitle());
                showed = true;
            }
            rollButton.setText(players.get(gameModel.getCurrentPlayerIndex()).getToken().name().toUpperCase() + " HAS ROLLED. PRESS END TURN");
            endbutton.setEnabled(true);
        }
    }

    private void Announce(String string) {
        announcementlabel.setText(string.toUpperCase());
    }

//    public void checkSpace() {
////        game.check_player_location(players[turn]);
////        System.out.println(game.space.get(players[turn].Player_position()).space_name());
//
//        int index = properties.indexOf(players.get(gameModel.getCurrentPlayerIndex()).getCurrentSpaceIndex());
//
//        if (index != -1 && players.get(properties.get(index).getOwnerIndex()) == null && players[turn].checkPassedGo()) {
//            buybutton.setEnabled(true);
//        } else {
//            buybutton.setEnabled(false);
//        }
//    }

    public void createCardPanel(Player player, String colour) {
        BufferedImage wPic;

        Image dimg;
        ArrayList<String> paths = new ArrayList<>();
        paths.add("boot");
        paths.add("cat");
        paths.add("goblet");
        paths.add("hatstand");
        paths.add("smartphone");
        paths.add("spoon");
//        String path = getClass().getResource("").toString().substring(5)  + "resources/";
        String path = "D:\\University\\Software Engineering\\Couresework\\Group Project\\software-engineering\\src\\propertytycoon\\resources\\";

        //{"Red", "Brown", "Purple", "Utilities", "Station", "Green", "Deep blue", "Blue", "Orange", "Yellow"};
        ArrayList<Property> propertiesForColour = new ArrayList<Property>();
        for (Property property : properties)
        {
            String col = property.getPropertyGroup().name();
            String type = property.getPropertyType().name();
            if (property.getPropertyGroup().name().equals(colour) || property.getPropertyType().name().equals(colour)){propertiesForColour.add(property);}
        }

        String tmp = "Player " + player.getToken().name() + " Colour " + colour;
        cardFrame = new JFrame(tmp);
        cardFrame.setSize(500, 500);
        cardFrame.setVisible(true);
        JPanel container = new JPanel(new GridLayout(propertiesForColour.size(), 5));
        cardFrame.add(container);   
        
        for (int i = 0; i < propertiesForColour.size(); i++) {
            Space propSpace = null;
            for (Space space : spaces)
            {
                if (space instanceof PropertySpace)
                {
                    String name1 = ((PropertySpace)space).getProperty().getPropertyName();
                    String name2 = propertiesForColour.get(i).getPropertyName();
                    if (((PropertySpace)space).getProperty().getPropertyName().equals(propertiesForColour.get(i).getPropertyName())){propSpace = space;}
                }
            }
            
            button = new JButton(propSpace.getTitle().toUpperCase());
            label = new JLabel();
            label.setSize(125, 125);
            try {
                int index = spaces.indexOf(propertiesForColour.get(i));
                System.out.println(path + spaces.get(index).getTitle() + ".png");
                wPic = ImageIO.read(new File(path + spaces.get(index).getTitle() + ".png"));
                dimg = wPic.getScaledInstance(125, 125,
                        Image.SCALE_SMOOTH);

                if (propertiesForColour.get(i).getOwnerIndex() != players.indexOf(player)) {
                    dimg = GrayFilter.createDisabledImage(dimg);
                }

                label = new JLabel(new ImageIcon(dimg));
                label.setForeground(Color.green);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                container.add(label);
            } catch (Exception ex) {
                Logger.getLogger(GUI_2.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            boolean hasSet = true;
            int count = 0;
            for (PlayerOption plOption : options)
            {
                if (plOption.getOptionType().name().equals("ImproveProperty")){break;} else {count++;}
            }
            if (count == options.size()) {hasSet = false;}
            
            if (propertiesForColour.get(i).getOwnerIndex() == players.indexOf(player) && propertiesForColour.get(i).getOwnerIndex() == gameModel.getCurrentPlayerIndex()) {
                if (hasSet) {
                    if (propertiesForColour.get(i).getNumberOfHouses() > 3) {
                        if (propertiesForColour.get(i).getNumberOfHouses() != 5) {
                            button = new JButton("(" + (propertiesForColour.get(i).getNumberOfHouses() - 4) + ")" + " +HOTEL ");
                            button.addActionListener(this);
                            button.setActionCommand("buyhotel" + propSpace.getTitle());
                            container.add(button);
                            /////////////////////////////////////////
                            button = new JButton(" - HOUSE ");
                            button.addActionListener(this);
                            button.setActionCommand("sell" + propSpace.getTitle());
                            container.add(button);
                        } else {
                            button = new JButton("Max");
                            container.add(button);
                            ////////////////////////////////////
                            button = new JButton(" - HOTEL ");
                            button.addActionListener(this);
                            button.setActionCommand("sell" + propSpace.getTitle());
                            container.add(button);
                        }
                    } else {

                        button = new JButton("(" + propertiesForColour.get(i).getNumberOfHouses() + ")" + " +HOUSE ");
                        button.addActionListener(this);
                        button.setActionCommand("buyhouse" + propSpace.getTitle());
                        container.add(button);
                        ///////////////////////////////
                        if(propertiesForColour.get(i).getNumberOfHouses() != 0){
                            button = new JButton(" - HOUSE ");
                            button.addActionListener(this);
                            button.setActionCommand("sell" + propSpace.getTitle());
                            container.add(button);
                        }else{
                            button = new JButton(" SELL ");
                            button.addActionListener(this);
                            button.setActionCommand("sell" + propSpace.getTitle());
                            container.add(button);
                        }
                    }
                }else{
                    button = new JButton(" - ");
                    container.add(button);
                
                    button = new JButton(" SELL ");
                    button.addActionListener(this);
                    button.setActionCommand("sell" + propSpace.getTitle());
                    container.add(button);
                }
                button = new JButton(" MORTAGE ");
                button.addActionListener(this);
                button.setActionCommand("mortgage" + propSpace.getTitle());
                container.add(button);

            } else {
                button = new JButton(" - ");
                container.add(button);

                button = new JButton(" - ");
                container.add(button);

                button = new JButton(" - ");
                container.add(button);
            }

            button = new JButton(" TRADE ");
            container.add(button);

        }

    }
    public void windowOpened(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
}
