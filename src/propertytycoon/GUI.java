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
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import jxl.read.biff.BiffException;

public class GUI extends javax.swing.JFrame implements ActionListener {

    public GUI() throws IOException, BiffException {
        UI();
    }

    private ArrayList<Space> tilesmap;
    private Map<String, JLabel> tokenlabels;

    private JLabel label;
    private JButton button;

    private JButton rollButton, buybutton, endbutton;

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

    String[] colours = {"Red", "Brown", "Purple", "Utilities", "Station", "Green", "Deep blue", "Blue", "Orange", "Yellow"};

    public void UI() throws IOException, BiffException {

       
        tokenlabels = new HashMap<>();

        turn = 0;
        Player.character[] chars = new Player.character[6];
        chars[0] = Player.character.boot;
        chars[1] = Player.character.cat;
        chars[2] = Player.character.smartphone;
        chars[3] = Player.character.hatstand;
        chars[4] = Player.character.spoon;
        chars[5] = Player.character.goblet;

        players = new Player[6];
        players[0] = new Player(chars[0]);
        players[1] = new Player(chars[1]);
        players[2] = new Player(chars[2]);
        players[3] = new Player(chars[3]);
        players[4] = new Player(chars[4]);
        players[5] = new Player(chars[5]);

        game = new Game(chars);

        labelTiles = new JLabel[40];

        String path = getClass().getResource("").toString().substring(5);
        System.out.println(path);
        
        tilesmap = game.space;
 
        mainFrame = new JFrame("Property Tycoon");
        mainFrame.setSize(1650, 1650);

        mainFrame.setLayout(new GridBagLayout());
        opanel1 = new JPanel();

        opanel3 = new JPanel();
        opanel4 = new JPanel();

        opanel3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opanel4.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        opanel1.setSize(1000, 100);

        opanel3.setSize(200, 1600);

        opanel4.setSize(1000, 100);

        opanel1.setLayout(new GridLayout(1, 0));

        opanel3.setLayout(new GridLayout(0, 1));
        opanel4.setLayout(new GridLayout(1, 0));

        boardpanel = new JPanel(new BorderLayout());
        boardpanel.setSize(900, 900);
        boardpanel.setForeground(Color.red);

        mainFrame.setLayout(new BorderLayout());
       
        bpanel1 = new JPanel();
        bpanel2 = new JPanel();
        bpanel3 = new JPanel();
        bpanel4 = new JPanel();

        bpanel1.setSize(900, 100);
        bpanel2.setSize(100, 600);
        bpanel3.setSize(100, 600);
        bpanel4.setSize(900, 100);

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
        announcementlabel.setFont(new Font("Arial", Font.PLAIN, 30));

        BufferedImage Pic;
        Image img;

        Pic = ImageIO.read(new File(path + "background.png"));
        img = Pic.getScaledInstance(1384, 1302,
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
            opanelin = new JPanel(new GridLayout(0, 2));
            opanel1.add(opanelin);

            inneropanel1 = new JPanel();
            inneropanel2 = new JPanel(new GridLayout(5, 5));
            opanelin.add(inneropanel1);
            opanelin.add(inneropanel2);

            JButton brown = new JButton("");
            brown.setBackground(new Color(139, 69, 19));
            JButton purple = new JButton("");
            purple.setBackground(Color.magenta);
            JButton red = new JButton("");
            red.setBackground(Color.red);
            JButton green = new JButton("");
            green.setBackground(Color.green);
            JButton blue = new JButton("");
            blue.setBackground(Color.cyan);
            JButton orange = new JButton("");
            orange.setBackground(Color.orange);
            JButton yellow = new JButton("");
            yellow.setBackground(Color.yellow);
            JButton darkblue = new JButton("");
            darkblue.setBackground(new Color(30, 144, 255));
            JButton utilities = new JButton("");
            utilities.setBackground(Color.PINK);
            JButton stations = new JButton("");
            stations.setBackground(Color.WHITE);

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
            button.setSize(100, 100);
            System.out.println(path + paths.get(i) + ".png");
            wPic = ImageIO.read(new File(path + paths.get(i) + ".png"));
            dimg = wPic.getScaledInstance(button.getWidth(), button.getHeight(),
                    Image.SCALE_SMOOTH);
            button = new JButton(new ImageIcon(dimg));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            button.addActionListener(this);
            button.setActionCommand(paths.get(i));
            inneropanel1.add(button);

        }

        //RIGHT BUTTONS
        for (int i = 0; i < 6; i++) {
            label = new JLabel();
            label.setSize(200, 200);
            wPic = ImageIO.read(new File(path + paths.get(i) + ".png"));
            dimg = wPic.getScaledInstance(100, 100,
                    Image.SCALE_SMOOTH);
            label = new JLabel(new ImageIcon(dimg));
            label.setForeground(Color.green);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            opanel3.add(label);
            tokenlabels.put(paths.get(i), label);
        }

        //Bottom buttons
        rollButton = new JButton(" ROLL ");
        rollButton.addActionListener(this);
        rollButton.setPreferredSize(new Dimension(100, 100));
        rollButton.setFont(new Font("Arial", Font.BOLD, 15));
        rollButton.setActionCommand("rolled");
        opanel4.add(rollButton);

        buybutton = new JButton(" BUY ");
        buybutton.setPreferredSize(new Dimension(100, 100));
        buybutton.setForeground(Color.orange);
        buybutton.addActionListener(this);
        buybutton.setFont(new Font("Arial", Font.BOLD, 20));
        buybutton.setActionCommand("buy");
        buybutton.setEnabled(false);
        opanel4.add(buybutton);

        button = new JButton(" PLACEHOLDER  ");
        button.setPreferredSize(new Dimension(100, 100));
        button.setForeground(Color.PINK);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        opanel4.add(button);

        endbutton = new JButton(" END TURN ");
        endbutton.setPreferredSize(new Dimension(100, 100));
        endbutton.setForeground(Color.BLACK);
        endbutton.addActionListener(this);
        endbutton.setActionCommand("endturn");
        endbutton.setFont(new Font("Arial", Font.BOLD, 20));
        endbutton.setEnabled(false);
        opanel4.add(endbutton);

        //tiles top
        for (int i = 0; i < 11; i++) {
            System.out.println(path + tilesmap.get(i).space_name()  + ".png");
            label = new JLabel();
            label.setSize(136, 98);
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
            System.out.println(path + tilesmap.get(i + 31 ));
            label = new JLabel();
            label.setSize(136, 98);
            wPic = ImageIO.read(new File(path + tilesmap.get(31 + i).space_name()  + ".png"));
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
            System.out.println(path + tilesmap.get(i + 11 ));
            label = new JLabel();
            label.setSize(136, 98);
            wPic = ImageIO.read(new File(path + tilesmap.get(11 + i).space_name() + ".png"));
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
            System.out.println(path + tilesmap.get(i + 20 ));
            label = new JLabel();
            label.setSize(136, 98);
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
        infoFrame.setSize(600, 1000);
    }

    public static void main(String args[]) throws IOException, BiffException {
        GUI gui = new GUI();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if ("rolled".equals(e.getActionCommand())) {
            if (!rolled) {
                System.out.println(players[turn].characters_Player().toString() + "- Rolled -");
                game.player_turn(players[turn]);
                rolled = true;
                checkSpace();
                UpdateUI();
                
            }

        }
        if ("buy".equals(e.getActionCommand())) {
            for (Properties prop : game.properties) {
                if (prop.returnPos() == players[turn].Player_position() + 1) {
                    players[turn].property_buy(prop);
                    System.out.println(players[turn].characters_Player() + " bought " + prop.space_name());
                    Announce(players[turn].characters_Player() + " bought " + prop.space_name());
                    buybutton.setEnabled(false);
                }
            }
            UpdateUI();
        }

        if ("endturn".equals(e.getActionCommand())) {
            nextPlayer();

        }

        if ("boot".equals(e.getActionCommand())) {
            showPlayerCards(Player.character.boot);
        }
        if ("cat".equals(e.getActionCommand())) {

            showPlayerCards(Player.character.cat);
        }
        if ("goblet".equals(e.getActionCommand())) {

            showPlayerCards(Player.character.goblet);

        }
        if ("hatstand".equals(e.getActionCommand())) {

            showPlayerCards(Player.character.hatstand);
        }
        if ("smartphone".equals(e.getActionCommand())) {
            showPlayerCards(Player.character.smartphone);
        }
        if ("spoon".equals(e.getActionCommand())) {

            showPlayerCards(Player.character.spoon);
        }

 
    }

    private void showPlayerCards(Player.character pc) {
        BufferedImage wPic = null;
        Image dimg;
        String path = getClass().getResource("").toString().substring(5);
        Point loc = infoFrame.getLocation();
        infoFrame.setVisible(false);
        infoFrame.dispose();
        infoFrame = new JFrame("Info Pane");
        infoFrame.setAlwaysOnTop(true);
        infoFrame.setLocation(loc);
        infoFrame.setSize(600, 1000);
        ArrayList<String> myprops = new ArrayList<>();
        for (Properties p : game.properties) {
            if (p.property_owener() == pc) {
                myprops.add(p.space_name());
            }
        }
        infoFrame.setLayout(new GridLayout(5, 4));
        for (String s : myprops) {
            label = new JLabel();
            label.setSize(100, 100);
            System.out.println(path + s + ".png");
            try {

                wPic = ImageIO.read(new File(path + s + ".png"));
                dimg = wPic.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
                label = new JLabel(new ImageIcon(dimg));
                infoFrame.add(label);
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        SwingUtilities.updateComponentTreeUI(infoFrame);

        infoFrame.setVisible(true);
    }

    private void nextPlayer() {
        
        rolled = false;
        
        if (turn >= players.length - 1) {
            turn = 0;
        } else {
            turn++;
        }
        if(players[turn].jailed){
            System.out.println( players[turn].player_characters.toString() + " jailed, turn skipped");
            players[turn].Player_still_in_jail();
            nextPlayer();
            
        }else{
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

            labelTiles[p.Player_position()].setText(labelTiles[p.Player_position()].getText() + p.characters_Player().toUpperCase().substring(0, 3) + " - ");
            labelTiles[p.Player_position()].setFont(new Font("Arial", Font.BOLD, 20));
            labelTiles[p.Player_position()].setHorizontalTextPosition(JLabel.CENTER);
            labelTiles[p.Player_position()].setForeground(Color.black);

            JLabel curLabel = tokenlabels.get(p.player_characters.name());
            curLabel.setText("$ " + p.Player_balance());
            curLabel.setFont(new Font("Arial", Font.BOLD, 20));
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
                Announce(players[turn].characters_Player() + " landed on " + game.space.get(players[turn].Player_position()).space_name());
                showed = true;
            }
            rollButton.setText(players[turn].characters_Player().toUpperCase() + " HAS ROLLED. PRESS END TURN");
            endbutton.setEnabled(true);
        }
    }

    private void Announce(String string) {
        announcementlabel.setText(string.toUpperCase());
    }

    public void checkSpace() {
        game.check_player_location(players[turn]);
        System.out.println(game.space.get(players[turn].Player_position()).space_name());

        int index = game.properties.indexOf(game.space.get(players[turn].Player_position()));

        if (index != -1 && game.properties.get(index).property_owener() == null && players[turn].checkPassedGo()) {
            buybutton.setEnabled(true);
        } else {
            buybutton.setEnabled(false);
        }
    }
}
