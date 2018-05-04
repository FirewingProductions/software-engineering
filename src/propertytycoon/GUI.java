package propertytycoon;

/**
 *
 * @author Dion
 */
import java.awt.BorderLayout;
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

import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GrayFilter;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import jxl.format.Border;
import jxl.read.biff.BiffException;

public class GUI extends javax.swing.JFrame implements ActionListener {

    public GUI() throws IOException, BiffException {
        UI();
    }

    private boolean auctioning = false;
    private ArrayList<Space> tilesmap;
    private Map<String, JLabel> tokenlabels;

    private ArrayList<String> announcementstrings;
    
    private JLabel label;
    private JButton button;

    private JButton rollButton, buybutton, endbutton, auctionbutton;

    private JFrame infoFrame, mainFrame;

    private JPanel boardpanel, bpanel1, bpanel2, bpanel3, bpanel4;

    private JPanel opanel1, opanelin, inneropanel1, inneropanel2, opanel3, opanel4;

    private JLabel announcementlabel;

    private JPanel middlepanel;

    private Game game;

    private JLabel[] labelTiles;

    private Integer turn;

    private Player[] players;

    private Boolean rolled = false, showed = false;

    private JButton[][] playerbuttons;

    private JFrame cardFrame;

    private int playernumber;

    //bidding variables
    private int price;
    private int bidturn;
    private ArrayList<Integer> passed;

    String[] colours = {"Red", "Brown", "Purple", "Utilities", "Station", "Green", "Deep blue", "Blue", "Orange", "Yellow"};
    public static final String[] numbers = {"2", "3", "4", "5", "6"};

    public void UI() throws IOException, BiffException {
        announcementstrings = new ArrayList<>();
        JFrame frame = new JFrame("Input Dialog");
        //frame.setSize(300, 300);
        String pnum = (String) JOptionPane.showInputDialog(frame,
                "How many players?",
                "Player number",
                JOptionPane.QUESTION_MESSAGE,
                null,
                numbers,
                numbers[0]);

        System.out.println("Number of players selected is : " + pnum);
     
        
        playernumber = Integer.parseInt(pnum);

        tokenlabels = new HashMap<>();

        turn = 0;
        Player.character[] chars = new Player.character[playernumber];

        LinkedList characs = new LinkedList();
        characs.add(Player.character.boot);
        characs.add(Player.character.cat);
        characs.add(Player.character.goblet);
        characs.add(Player.character.hatstand);
        characs.add(Player.character.smartphone);
        characs.add(Player.character.spoon);

        for (int y = 0; y < playernumber; y++) {
            System.out.println(characs.peek());
            chars[y] = (Player.character) characs.poll();
        }

        players = new Player[playernumber];
        for (int y = 0; y < playernumber; y++) {
            players[y] = new Player(chars[y]);
        }

        game = new Game(chars);

        labelTiles = new JLabel[40];

        String path = getClass().getResource("").toString().substring(5) + "resources/";
        System.out.println(path);

        tilesmap = game.space;

        mainFrame = new JFrame("Property Tycoon");
        //mainFrame.setSize(1650, 1650);  //1650 1650
        mainFrame.setSize(1031, 1031);
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setResizable(false);

        opanel1 = new JPanel();

        opanel3 = new JPanel();
        opanel4 = new JPanel();

        opanel3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opanel4.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        opanel1.setSize(625, 62);

        opanel3.setSize(125, 1000);

        opanel4.setSize(625, 62);

        opanel1.setLayout(new GridLayout(1, 0));

        opanel3.setLayout(new GridLayout(0, 1));
        opanel4.setLayout(new GridLayout(1, 0));

        boardpanel = new JPanel(new BorderLayout());
        boardpanel.setSize(562, 562);
        boardpanel.setForeground(Color.red);

        mainFrame.setLayout(new BorderLayout());

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
        img = Pic.getScaledInstance(865, 814,
                Image.SCALE_SMOOTH);
        label = new JLabel(new ImageIcon(img));

        middlepanel.add(label);

        boardpanel.add(middlepanel, BorderLayout.CENTER);

        mainFrame.add(opanel1, BorderLayout.PAGE_START);

        mainFrame.add(opanel3, BorderLayout.LINE_END);
        mainFrame.add(opanel4, BorderLayout.PAGE_END);

        //top panel tokens
        BufferedImage wPic;
        Image dimg;
        ArrayList<String> paths = new ArrayList<>();
        paths.add("boot");
        paths.add("cat");
        paths.add("goblet");
        paths.add("hatstand");
        paths.add("smartphone");
        paths.add("spoon");

        playerbuttons = new JButton[6][10];
        for (int i = 0; i < 6; i++) {
            Boolean passed = false;
            if (i >= players.length) {
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
            if (passed) {
                brown.setBackground(Color.LIGHT_GRAY);
            }
            brown.addActionListener(this);
            brown.setPreferredSize(new Dimension(45, 15));
            brown.setActionCommand(Integer.toString(i) + "brown");
            // brown.setFont(new Font("Arial", Font.PLAIN, 9));

            JButton purple = new JButton("");
            purple.setBackground(Color.magenta);
            if (passed) {
                purple.setBackground(Color.LIGHT_GRAY);
            }
            purple.addActionListener(this);
            purple.setPreferredSize(new Dimension(45, 15));
            purple.setActionCommand(Integer.toString(i) + "purple");

            JButton red = new JButton("");
            red.setBackground(Color.red);
            if (passed) {
                red.setBackground(Color.LIGHT_GRAY);
            }
            red.addActionListener(this);
            red.setPreferredSize(new Dimension(45, 15));
            red.setActionCommand(Integer.toString(i) + "red");

            JButton green = new JButton("");
            green.setBackground(Color.green);
            if (passed) {
                green.setBackground(Color.LIGHT_GRAY);
            }
            green.addActionListener(this);
            green.setPreferredSize(new Dimension(45, 15));
            green.setActionCommand(Integer.toString(i) + "green");

            JButton blue = new JButton("");
            blue.setBackground(Color.cyan);
            if (passed) {
                blue.setBackground(Color.LIGHT_GRAY);
            }
            blue.addActionListener(this);
            blue.setPreferredSize(new Dimension(45, 15));
            blue.setActionCommand(Integer.toString(i) + "blue");

            JButton orange = new JButton("");
            orange.setBackground(Color.orange);
            if (passed) {
                orange.setBackground(Color.LIGHT_GRAY);
            }
            orange.addActionListener(this);
            orange.setPreferredSize(new Dimension(45, 15));
            orange.setActionCommand(Integer.toString(i) + "orange");

            JButton yellow = new JButton("");
            yellow.setBackground(Color.yellow);
            if (passed) {
                yellow.setBackground(Color.LIGHT_GRAY);
            }
            yellow.addActionListener(this);
            yellow.setPreferredSize(new Dimension(45, 15));
            yellow.setActionCommand(Integer.toString(i) + "yellow");

            JButton darkblue = new JButton("");
            darkblue.setBackground(new Color(30, 144, 255));
            if (passed) {
                darkblue.setBackground(Color.LIGHT_GRAY);
            }
            darkblue.addActionListener(this);
            darkblue.setPreferredSize(new Dimension(45, 15));
            darkblue.setActionCommand(Integer.toString(i) + "darkblue");

            JButton utilities = new JButton("");
            utilities.setBackground(Color.PINK);
            if (passed) {
                utilities.setBackground(Color.LIGHT_GRAY);
            }
            utilities.addActionListener(this);
            utilities.setPreferredSize(new Dimension(45, 15));
            utilities.setActionCommand(Integer.toString(i) + "utilities");

            JButton stations = new JButton("");
            stations.setBackground(Color.WHITE);
            if (passed) {
                stations.setBackground(Color.LIGHT_GRAY);
            }
            stations.addActionListener(this);
            stations.setPreferredSize(new Dimension(45, 15));
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
            if (passed) {
                dimg = GrayFilter.createDisabledImage(dimg);
            }
            button = new JButton(new ImageIcon(dimg));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            button.addActionListener(this);
            button.setActionCommand(paths.get(i));
            inneropanel1.add(button);

        }

        //RIGHT BUTTONS
        for (int i = 0; i < 6; i++) {
            Boolean passed = false;
            if (i >= players.length) {
                passed = true;
            }
            label = new JLabel();
            label.setSize(125, 125);
            wPic = ImageIO.read(new File(path + paths.get(i) + ".png"));
            dimg = wPic.getScaledInstance(62, 62,
                    Image.SCALE_SMOOTH);
            if (passed) {
                dimg = GrayFilter.createDisabledImage(dimg);
            }
            label = new JLabel(new ImageIcon(dimg));
            label.setForeground(Color.green);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            opanel3.add(label);
            tokenlabels.put(paths.get(i), label);
        }

        //Bottom buttons
        rollButton = new JButton(" ROLL ");
        rollButton.addActionListener(this);
        rollButton.setPreferredSize(new Dimension(62, 62));
        rollButton.setFont(new Font("Arial", Font.BOLD, 10));
        rollButton.setActionCommand("rolled");
        opanel4.add(rollButton);

        buybutton = new JButton(" BUY ");
        buybutton.setPreferredSize(new Dimension(62, 62));
        buybutton.setForeground(Color.black);
        buybutton.addActionListener(this);
        buybutton.setFont(new Font("Arial", Font.BOLD, 15));
        buybutton.setActionCommand("buy");
        buybutton.setEnabled(false);
        opanel4.add(buybutton);

        auctionbutton = new JButton(" AUCTION ");
        auctionbutton.setPreferredSize(new Dimension(62, 62));
        auctionbutton.setForeground(Color.PINK);
        auctionbutton.setFont(new Font("Arial", Font.BOLD, 15));
        auctionbutton.addActionListener(this);
        auctionbutton.setActionCommand("auction");
        auctionbutton.setEnabled(false);
        opanel4.add(auctionbutton);

        endbutton = new JButton(" END TURN ");
        endbutton.setPreferredSize(new Dimension(62, 62));
        endbutton.setForeground(Color.BLACK);
        endbutton.addActionListener(this);
        endbutton.setActionCommand("endturn");
        endbutton.setFont(new Font("Arial", Font.BOLD, 10));
        endbutton.setEnabled(false);
        opanel4.add(endbutton);

        //tiles top
        for (int i = 0; i < 11; i++) {
            System.out.println(path + tilesmap.get(i).space_name() + ".png");
            label = new JLabel();
            label.setSize(85, 61);
            wPic = ImageIO.read(new File(path + tilesmap.get(i).space_name() + ".png"));
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
            System.out.println(path + tilesmap.get(i + 31));
            label = new JLabel();
            label.setSize(61, 85);
            wPic = ImageIO.read(new File(path + tilesmap.get(31 + i).space_name() + ".png"));
            if (i == 5) {
                wPic = ImageIO.read(new File(path + "Opportunity knocks left.png"));
            }
            if (i == 2) {
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
            System.out.println(path + tilesmap.get(i + 11));
            label = new JLabel();
            label.setSize(61, 85);
            wPic = ImageIO.read(new File(path + tilesmap.get(11 + i).space_name() + ".png"));
            if (i == 6) {
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
            System.out.println(path + tilesmap.get(i + 20));
            label = new JLabel();
            label.setSize(85, 61);
            wPic = ImageIO.read(new File(path + tilesmap.get(20 + i).space_name() + ".png"));
            dimg = wPic.getScaledInstance(label.getWidth(), label.getHeight(),
                    Image.SCALE_SMOOTH);
            label = new JLabel(new ImageIcon(dimg));
            label.setForeground(Color.green);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            bpanel4.add(label);
            labelTiles[20 + i] = label;

        }

        mainFrame.add(boardpanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        UpdateUI();

        infoFrame = new JFrame("Info Pane");
        infoFrame.setAlwaysOnTop(true);
        infoFrame.setLocationRelativeTo(mainFrame);
        infoFrame.setSize(375, 625);
        infoFrame.setLayout(new BorderLayout(1, 0));
        label = new JLabel(" GAME INFO ");
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoFrame.add(label, BorderLayout.PAGE_START);
        announcementlabel = new JLabel("", SwingConstants.CENTER);
        
        infoFrame.add(announcementlabel, BorderLayout.CENTER);
        
        
        
        infoFrame.setVisible(true);
    }

    public static void main(String args[]) throws IOException, BiffException {
        GUI gui = new GUI();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if ("rolled".equals(e.getActionCommand())) {
            if (!rolled) {
                System.out.println(players[turn].characters_Player().toString() + " - Rolled -");
                Announce(players[turn].characters_Player().toString() + " - Rolled -");
                game.player_turn(players[turn]);
                rolled = true;
                checkSpace();
                UpdateUI();

            }

        }
        if ("buy".equals(e.getActionCommand())) {
            for (Properties prop : game.properties) {
                if (prop.returnPos() == players[turn].Player_position() + 1) {
                    Announce(players[turn].property_buy(prop));
                    //System.out.println(players[turn].characters_Player() + " bought " + prop.space_name());
                    //Announce(players[turn].characters_Player() + " bought " + prop.space_name());
                    buybutton.setEnabled(false);
                    auctionbutton.setEnabled(false);
                }
            }
            UpdateUI();
        }

        if ("endturn".equals(e.getActionCommand())) {
            nextPlayer();
        }

        if ("bid".equals(e.getActionCommand())) {

            auction(true);
        }

        if ("pass".equals(e.getActionCommand())) {
            passed.add(bidturn);

            auction(false);
        }

        if ("auction".equals(e.getActionCommand())) {
            startAuction();
        }

        try {

            if (e.getActionCommand().substring(0, 8).equalsIgnoreCase("buyhouse")) {
                //System.out.println(e.getActionCommand().substring(8));
                for (Properties prop : game.properties) {

                    if (prop.space_name().equalsIgnoreCase(e.getActionCommand().substring(8))) {
                        System.out.println("Attempting to buy house for : " + prop.space_name());
                        Announce("Attempting to buy house for : " + prop.space_name());
                        Announce(prop.buy_house(players[turn]));
                        UpdateUI();
                        cardFrame.setVisible(false);
                        cardFrame.dispose();
                    }
                }
            }
            if (e.getActionCommand().substring(0, 8).equalsIgnoreCase("buyhotel")) {
                // System.out.println(e.getActionCommand().substring(8));
                for (Properties prop : game.properties) {

                    if (prop.space_name().equalsIgnoreCase(e.getActionCommand().substring(8))) {
                        System.out.println("Attempting to buy hotel for : " + prop.space_name());
                        Announce("Attempting to buy hotel for : " + prop.space_name());
                        Announce(prop.buy_hotel(players[turn]));
                        UpdateUI();
                        cardFrame.setVisible(false);
                        cardFrame.dispose();
                    }
                }
            }
            if (e.getActionCommand().substring(0, 4).equalsIgnoreCase("sell")) {
                // System.out.println("Selling a property");
                for (Properties prop : game.properties) {

                    if (prop.space_name().equalsIgnoreCase(e.getActionCommand().substring(4))) {
                        System.out.println("Attempting to sell hotel/house or property : " + prop.space_name());
                        Announce("Attempting to sell hotel/house or property : " + prop.space_name());
                        System.out.println(prop.property_sell(players[turn]));
                        Announce(prop.property_sell(players[turn]));
                        UpdateUI();
                        cardFrame.setVisible(false);
                        cardFrame.dispose();
                    }
                }
            }
        } catch (Exception ex) {

        }

        try {
            //System.out.println(e.getActionCommand());
            int playercalled = Integer.parseInt(e.getActionCommand().substring(0, 1));
            System.out.println(playercalled);
            String colour = e.getActionCommand().substring(1);
            //{"Red", "Brown", "Purple", "Utilities", "Station", "Green", "Deep blue", "Blue", "Orange", "Yellow"};
            switch (colour) {
                case "red":
                    createCardPanel(players[playercalled], "Red");
                    break;
                case "brown":
                    createCardPanel(players[playercalled], "Brown");
                    break;
                case "yellow":
                    createCardPanel(players[playercalled], "Yellow");
                    break;
                case "blue":
                    createCardPanel(players[playercalled], "Blue");
                    break;
                case "darkblue":
                    createCardPanel(players[playercalled], "Deep blue");
                    break;
                case "utilities":
                    createCardPanel(players[playercalled], "Utilities");
                    break;
                case "stations":
                    createCardPanel(players[playercalled], "Station");
                    break;
                case "orange":
                    createCardPanel(players[playercalled], "Orange");
                    break;
                case "green":
                    createCardPanel(players[playercalled], "Green");
                    break;
                case "purple":
                    createCardPanel(players[playercalled], "Purple");
                    break;
            }
        } catch (Exception ex) {

        }

    }

    private void nextPlayer() {
        buybutton.setEnabled(false);
        auctionbutton.setEnabled(false);

        rolled = false;

        if (turn >= players.length - 1) {
            turn = 0;
        } else {
            turn++;
        }
        if (players[turn].jailed) {
            System.out.println(players[turn].player_characters.toString() + " jailed, turn skipped");
            players[turn].Player_still_in_jail();
            nextPlayer();

        } else {
            UpdateUI();
        }

    }

    public void UpdateUI() {

        for (JLabel lab : labelTiles) {
            lab.setText("");
        }
        int i = 0;
        for (Player p : players) {

            for (int y = 0; y < 10; y++) {
                Integer s = p.properties.get(colours[y]).size();
                playerbuttons[i][y].setText(s.toString());
            }
            System.out.println(p.characters_Player() + " at " + p.Player_position());
            labelTiles[p.Player_position()].setText(labelTiles[p.Player_position()].getText() + p.characters_Player().toUpperCase().substring(0, 3) + " - ");
            labelTiles[p.Player_position()].setFont(new Font("Arial", Font.BOLD, 10));
            labelTiles[p.Player_position()].setHorizontalTextPosition(JLabel.CENTER);
            labelTiles[p.Player_position()].setForeground(Color.black);

            JLabel curLabel = tokenlabels.get(p.player_characters.name());
            curLabel.setText("£ " + p.Player_balance() + " ");
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
            rollButton.setText(players[turn].characters_Player().toUpperCase() + "'S TURN , CLICK TO ROLL");
            endbutton.setEnabled(false);
            showed = false;
        } else {
            if (!showed) {
                //Announce(players[turn].characters_Player() + " landed on " + game.space.get(players[turn].Player_position()).space_name());
                showed = true;
            }
            rollButton.setText(players[turn].characters_Player().toUpperCase() + " HAS ROLLED. PRESS END TURN");
            endbutton.setEnabled(true);
        }
    }

    private void Announce(String string) {
        if(announcementstrings.size() < 30 && !"".equals(string)){
            
            announcementstrings.add(string);
        }else{
            if(!"".equals(string)){
                announcementstrings.remove(0);
                announcementstrings.add(string);
            }
        }
        String newstring ="";
        for(String s : announcementstrings){
         
           newstring = newstring.concat("<br/>" + s);
         
       
        }
      
        string = "<html>" + newstring + "</html>";
        announcementlabel.setText(string);
        
        System.out.println(string);
        
    }

    public void checkSpace() {
        Announce(players[turn].characters_Player() + " landed on " + game.space.get(players[turn].Player_position()).space_name());
        Announce(game.check_player_location(players[turn]));
        System.out.println(game.space.get(players[turn].Player_position()).space_name());

        int index = game.properties.indexOf(game.space.get(players[turn].Player_position()));

        if (index != -1 && game.properties.get(index).property_owener() == null && players[turn].checkPassedGo()) {
            buybutton.setEnabled(true);
            buybutton.setText("BUY : " + game.properties.get(index).getcost());
            auctionbutton.setEnabled(true);
        } else {
            buybutton.setEnabled(false);
            auctionbutton.setEnabled(false);
        }
    }

    public void createCardPanel(Player p, String colour) {
        BufferedImage wPic;

        Image dimg;
        ArrayList<String> paths = new ArrayList<>();
        paths.add("boot");
        paths.add("cat");
        paths.add("goblet");
        paths.add("hatstand");
        paths.add("smartphone");
        paths.add("spoon");
        String path = getClass().getResource("").toString().substring(5) + "resources/";

        ArrayList<Properties> props = game.properties_final.get(colour);

        String tmp = "Player " + p.characters_Player() + " Colour " + colour;
        cardFrame = new JFrame(tmp);
        cardFrame.setSize(600, 400);
        cardFrame.setVisible(true);
        JPanel container = new JPanel(new GridLayout(props.size(), 5));
        cardFrame.add(container);
        for (int i = 0; i < props.size(); i++) {
            button = new JButton(props.get(i).space_name().toUpperCase());
            label = new JLabel();
            label.setSize(125, 125);
            try {
                int index = tilesmap.indexOf(props.get(i));
                System.out.println(path + tilesmap.get(index).space_name() + ".png");
                wPic = ImageIO.read(new File(path + tilesmap.get(index).space_name() + ".png"));
                dimg = wPic.getScaledInstance(125, 125,
                        Image.SCALE_SMOOTH);

                if (props.get(i).property_owener() != p) {
                    dimg = GrayFilter.createDisabledImage(dimg);
                }

                label = new JLabel(new ImageIcon(dimg));
                label.setForeground(Color.green);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                container.add(label);
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (props.get(i).property_owener() == p) {
                if (props.get(i).property_owener() == players[turn]) {
                    if (players[turn].player_has_set(colour, game.properties_final.get(colour))) {
                        if (props.get(i).return_house_amount() > 3) {
                            if (props.get(i).return_house_amount() != 5) {
                                button = new JButton("(" + (props.get(i).return_house_amount() - 4) + ")" + " +HOTEL ");
                                button.addActionListener(this);
                                button.setActionCommand("buyhotel" + props.get(i).space_name());
                                container.add(button);
                                /////////////////////////////////////////
                                button = new JButton(" - HOUSE ");
                                button.addActionListener(this);
                                button.setActionCommand("sell" + props.get(i).space_name());
                                container.add(button);
                            } else {
                                button = new JButton("Max");
                                container.add(button);
                                ////////////////////////////////////
                                button = new JButton(" - HOTEL ");
                                button.addActionListener(this);
                                button.setActionCommand("sell" + props.get(i).space_name());
                                container.add(button);
                            }
                        } else {

                            button = new JButton("(" + props.get(i).return_house_amount() + ")" + " +HOUSE ");
                            button.addActionListener(this);
                            button.setActionCommand("buyhouse" + props.get(i).space_name());
                            container.add(button);
                            ///////////////////////////////
                            if (props.get(i).return_house_amount() != 0) {
                                button = new JButton(" - HOUSE ");
                                button.addActionListener(this);
                                button.setActionCommand("sell" + props.get(i).space_name());
                                container.add(button);
                            } else {
                                button = new JButton(" SELL ");
                                button.addActionListener(this);
                                button.setActionCommand("sell" + props.get(i).space_name());
                                container.add(button);
                            }
                        }
                    } else {
                        button = new JButton(" - ");
                        container.add(button);

                        button = new JButton(" SELL ");
                        button.addActionListener(this);
                        button.setActionCommand("sell" + props.get(i).space_name());
                        container.add(button);
                    }
                    button = new JButton(" MORTAGE ");
                    button.addActionListener(this);
                    button.setActionCommand("mortgage" + props.get(i).space_name());
                    container.add(button);
                } else {
                    button = new JButton("Not " + p.characters_Player() + "'s turn");
                    container.add(button);

                    button = new JButton("Not " + p.characters_Player() + "'s turn");
                    container.add(button);

                    button = new JButton("Not " + p.characters_Player() + "'s turn");
                    container.add(button);
                }
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

    private void startAuction() {
        bidturn = 0;
        price = 0;
        auctioning = true;
        passed = new ArrayList<>();
        for (Player p : players) {
            if (!p.checkPassedGo()) {
                passed.add(Arrays.asList(players).indexOf(p));
            }
        }
        auction(false);

    }

    private void auction(boolean b) {
        if (auctioning) {
            checkPlayers();
            if (b) {
                price += 10;
                //highestbidder = bidturn;
            }

            if (bidturn >= players.length - 1) {
                bidturn = 0;
            } else {
                bidturn++;
            }
            for (int i : passed) {
                if (i == bidturn) {
                    System.out.println("A passed players turn");
                    auction(false);
                }
            }
            if (passed.size() == players.length - 1) {
                System.out.println("We have a winner");

             
                for (Properties prop : game.properties) {
                    if (prop.returnPos() == players[turn].Player_position() + 1 && !prop.property_is_owned()) {
                        players[bidturn].property_buy(prop, price);
                        System.out.println(players[bidturn].characters_Player() + " won " + prop.space_name() + " for : " + price);
                        Announce(players[bidturn].characters_Player() + " won " + prop.space_name());
                        buybutton.setEnabled(false);
                        auctionbutton.setEnabled(false);
                        
                    }
                }

 
                auctioning = false;

                rollButton.setActionCommand("rolled");
                buybutton.setText(" BUY ");
                buybutton.setActionCommand("buy");
                buybutton.setEnabled(false);

                auctionbutton.setText(" AUCTION ");
                auctionbutton.setActionCommand("auction");
                auctionbutton.setEnabled(false);

                endbutton.setText(" END TURN ");
                endbutton.setActionCommand("endturn");
                UpdateUI();

            } else {

                auctionbutton.setText("£ " + price);
                auctionbutton.setActionCommand("");

                rollButton.setText(players[bidturn].characters_Player() + "'s TURN");
                rollButton.setActionCommand("");

                buybutton.setText("BID (+10)");
                buybutton.setActionCommand("bid");

                endbutton.setText("PASS");
                endbutton.setActionCommand("pass");
            }
        }
    }
    
    public void checkPlayers(){
        for(Player p : players){
            if(p.Player_balance() < price + 10){
                if(!passed.contains(Arrays.asList(players).indexOf(p))){
                    System.out.println(p.characters_Player() + " cannot afford to stay in the auction");
                    passed.add(Arrays.asList(players).indexOf(p));
                }
            }
        }
    }

}
