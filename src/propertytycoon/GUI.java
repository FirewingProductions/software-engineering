package propertytycoon;

/**
 *
 * @author Dion
 */
import java.awt.BorderLayout;
import java.awt.Color;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import jxl.read.biff.BiffException;

public class GUI extends javax.swing.JFrame implements ActionListener {

    public GUI() throws IOException, BiffException {
        UI();
    }

    private Map<Integer, String> tilesmap;
    private Map<String, JLabel> tokenlabels;

    private JLabel label;
    private JButton button;

    private JButton rollButton;

    private JOptionPane statspopup;

    private JFrame infoFrame;

    private JFrame mainFrame;
    private JPanel boardpanel;
    private JPanel bpanel1;
    private JPanel bpanel2;
    private JPanel bpanel3;
    private JPanel bpanel4;

    private JPanel opanel1;
    private JPanel opanelin;
    private JPanel inneropanel1;
    private JPanel inneropanel2;

    private JPanel opanel3;
    private JPanel opanel4;

    private JLabel announcementlabel;

    private JPanel middlepanel;

    private Game game;

    private JLabel[] labelTiles;

    private Integer turn;

    private Player[] players;

    private Boolean rolled = false;
    private Boolean showed = false;

    public void UI() throws IOException, BiffException {
        System.out.println(getClass().getClassLoader().getResource("hat.png"));

        infoFrame = new JFrame("Info Pane");
        infoFrame.setSize(600, 1000);
        infoFrame.setVisible(true);

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

        String path = getClass().getClassLoader().getResource("").toString().substring(5);
        tilesmap = new HashMap<>();

        tilesmap.put(1, "Go.png");
        tilesmap.put(2, "crappernew2.png");  //brown1
        tilesmap.put(3, "PotLuck.png");
        tilesmap.put(4, "Brown2.png");
        tilesmap.put(5, "IncomeTax.png");  // tax
        tilesmap.put(6, "BrightonStation.png");
        tilesmap.put(7, "Blue1.png");
        tilesmap.put(8, "Knocks.png");
        tilesmap.put(9, "Blue2.png");
        tilesmap.put(10, "Blue3.png");
        tilesmap.put(11, "Jail.png");
        tilesmap.put(12, "Purple1.png");
        tilesmap.put(13, "Tesla.png");   //tesla
        tilesmap.put(14, "Purple2.png");
        tilesmap.put(15, "Purple3.png");
        tilesmap.put(16, "HoveStation.png");
        tilesmap.put(17, "Orange1.png");
        tilesmap.put(18, "PotLuck.png");
        tilesmap.put(19, "Orange2.png");
        tilesmap.put(20, "Orange3.png");
        tilesmap.put(21, "GoJail.png");
        tilesmap.put(22, "Yellow1.png");
        tilesmap.put(23, "Knocks.png");
        tilesmap.put(24, "Yellow2.png");
        tilesmap.put(25, "Yellow3.png");
        tilesmap.put(26, "FalmerStation.png");
        tilesmap.put(27, "Red1.png");
        tilesmap.put(28, "Red2.png");
        tilesmap.put(29, "EdisonWater.png");  //edison water
        tilesmap.put(30, "Red3.png");
        tilesmap.put(31, "FreeParking.png");
        tilesmap.put(32, "DarkBlue1.png");
        tilesmap.put(33, "DarkBlue2.png");
        tilesmap.put(34, "PotLuck.png");
        tilesmap.put(35, "Green1.png");
        tilesmap.put(36, "LewesStation.png");
        tilesmap.put(37, "Knocks.png");
        tilesmap.put(38, "Green2.png");
        tilesmap.put(39, "SuperTax.png");  //super tax
        tilesmap.put(40, "EdisonWater.png");  //edison water

        mainFrame = new JFrame("Property Tycoon");
        mainFrame.setSize(1600, 1600);

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

        label = new JLabel("TEST TEST");
        label.setSize(100, 100);
        label.setFont(new Font("Arial", Font.PLAIN, 30));

        button = new JButton("TEST BUTTON");
        button.setSize(100, 100);

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

        for (int i = 0; i < 6; i++) {
            opanelin = new JPanel(new GridLayout(0, 2));
            opanel1.add(opanelin);

            inneropanel1 = new JPanel();
            inneropanel2 = new JPanel(new GridLayout(4, 4));
            opanelin.add(inneropanel1);
            opanelin.add(inneropanel2);

            JButton brown = new JButton("0");
            brown.setBackground(new Color(139, 69, 19));
            JButton purple = new JButton("0");
            purple.setBackground(Color.magenta);
            JButton red = new JButton("0");
            red.setBackground(Color.red);
            JButton green = new JButton("0");
            green.setBackground(Color.green);
            JButton blue = new JButton("0");
            blue.setBackground(Color.cyan);
            JButton orange = new JButton("0");
            orange.setBackground(Color.orange);
            JButton yellow = new JButton("0");
            yellow.setBackground(Color.yellow);
            JButton darkblue = new JButton("0");
            darkblue.setBackground(new Color(30, 144, 255));

            inneropanel2.add(brown);
            inneropanel2.add(purple);
            inneropanel2.add(red);
            inneropanel2.add(green);
            inneropanel2.add(blue);
            inneropanel2.add(orange);
            inneropanel2.add(yellow);
            inneropanel2.add(darkblue);

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
            dimg = wPic.getScaledInstance(50, 50,
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

        button = new JButton(" BUY ");
        button.setPreferredSize(new Dimension(100, 100));
        button.setForeground(Color.blue);
        button.addActionListener(this);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setActionCommand("buy");
        opanel4.add(button);

        button = new JButton(" PLACEHOLDER  ");
        button.setPreferredSize(new Dimension(100, 100));
        button.setForeground(Color.blue);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        opanel4.add(button);

        button = new JButton(" END TURN ");
        button.setPreferredSize(new Dimension(100, 100));
        button.setForeground(Color.blue);
        button.addActionListener(this);
        button.setActionCommand("endturn");
        button.setFont(new Font("Arial", Font.BOLD, 15));
        opanel4.add(button);

        //tiles top
        for (int i = 0; i < 11; i++) {
            System.out.println(path + tilesmap.get(i + 1));
            label = new JLabel();
            label.setSize(136, 98);
            wPic = ImageIO.read(new File(path + tilesmap.get(i + 1)));
            dimg = wPic.getScaledInstance(label.getWidth(), label.getHeight(),
                    Image.SCALE_SMOOTH);
            label = new JLabel(new ImageIcon(dimg));
            label.setForeground(Color.BLACK);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            bpanel1.add(label);
            labelTiles[i] = label;
        }

        //tiles left
        for (int i = 0; i < 9; i++) {
            label = new JLabel();
            label.setSize(136, 98);
            wPic = ImageIO.read(new File(path + tilesmap.get(31 + i + 1)));
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
            label = new JLabel();
            label.setSize(136, 98);
            wPic = ImageIO.read(new File(path + tilesmap.get(11 + i + 1)));
            dimg = wPic.getScaledInstance(label.getWidth(), label.getHeight(),
                    Image.SCALE_SMOOTH);
            label = new JLabel(new ImageIcon(dimg));
            label.setForeground(Color.green);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            bpanel3.add(label);
            labelTiles[11 + i] = label;

        }
        //tiles bottom
        for (int i = 0; i < 11; i++) {
            label = new JLabel();
            label.setSize(136, 98);
            wPic = ImageIO.read(new File(path + tilesmap.get(20 + i + 1)));
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
    }

    public static void main(String args[]) throws IOException, BiffException {
        GUI gui = new GUI();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("rolled".equals(e.getActionCommand())) {
            if (!rolled) {
                System.out.println(players[turn].characters_Player() + " at space --> " + players[turn].Player_position());
                System.out.println("- Rolled -");
                game.player_turn(players[turn]);
                rolled = true;
                UpdateUI();
            }

        }
        if ("buy".equals(e.getActionCommand())) {
            for (Properties prop : game.properties) {
                if (prop.returnPos() == players[turn].Player_position() + 1) {
                    players[turn].property_buy(prop);
                    System.out.println(players[turn].characters_Player() + " bought " + prop.space_name());
                    Announce(players[turn].characters_Player() + " bought " + prop.space_name());
                }
            }
            UpdateUI();
        }

        if ("endturn".equals(e.getActionCommand())) {
            nextPlayer();

        }

        if ("boot".equals(e.getActionCommand())) {
            statspopup = new JOptionPane();
            String b = "";
            for (Properties p : game.properties) {
                if (p.property_owener() == Player.character.boot) {
                    b = b + " - " + p.space_name();
                }
            }
            statspopup.showMessageDialog(mainFrame, b);

        }
        if ("cat".equals(e.getActionCommand())) {
            statspopup = new JOptionPane();
            String b = "";
            for (Properties p : game.properties) {
                if (p.property_owener() == Player.character.cat) {
                    b = b + " - " + p.space_name();
                }
            }
            statspopup.showMessageDialog(mainFrame, b);

        }
        if ("goblet".equals(e.getActionCommand())) {
            statspopup = new JOptionPane();
            String b = "";
            for (Properties p : game.properties) {
                if (p.property_owener() == Player.character.goblet) {
                    b = b + " - " + p.space_name();
                }
            }
            statspopup.showMessageDialog(mainFrame, b);

        }
        if ("hatstand".equals(e.getActionCommand())) {
            statspopup = new JOptionPane();
            String b = "";
            for (Properties p : game.properties) {
                if (p.property_owener() == Player.character.hatstand) {
                    b = b + " - " + p.space_name();
                }
            }
            statspopup.showMessageDialog(mainFrame, b);

        }
        if ("smartphone".equals(e.getActionCommand())) {
            statspopup = new JOptionPane();
            String b = "";
            for (Properties p : game.properties) {
                if (p.property_owener() == Player.character.smartphone) {
                    b = b + " - " + p.space_name();
                }
            }
            statspopup.showMessageDialog(mainFrame, b);

        }
        if ("spoon".equals(e.getActionCommand())) {
            statspopup = new JOptionPane();
            String b = "";
            for (Properties p : game.properties) {
                if (p.property_owener() == Player.character.spoon) {
                    b = b + " - " + p.space_name();
                }
            }
            statspopup.showMessageDialog(mainFrame, b);

        }

    }

    private void nextPlayer() {
        rolled = false;
        if (turn >= players.length - 1) {
            turn = 0;
        } else {
            turn++;
        }
        UpdateUI();

    }

    private void UpdateUI() {
        for (JLabel lab : labelTiles) {
            lab.setText("");
        }
        for (Player p : players) {
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

        }

        UpdateRollButton();
    }

    private void UpdateRollButton() {
        if (!rolled) {
            rollButton.setText(players[turn].characters_Player().toUpperCase() + "'S TURN , CLICK TO ROLL");
            showed = false;
        } else {
            if (!showed) {
                Announce(players[turn].characters_Player() + " landed on " + game.space.get(players[turn].Player_position()).space_name());
                showed = true;
            }
            rollButton.setText(players[turn].characters_Player().toUpperCase() + " HAS ROLLED. PRESS END TURN");
        }
    }

    private void Announce(String string) {
        announcementlabel.setText(string.toUpperCase());
    }

    public void checkSpace() {
        System.out.println("Called");
    }
}
