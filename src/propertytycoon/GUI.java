/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

/**
 *
 * @author Surface
 */
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.WindowConstants;
import jxl.read.biff.BiffException;

public class GUI extends javax.swing.JFrame implements ActionListener {

    /**
     * Creates new form GUI
     */
    public GUI() throws IOException, BiffException {
        UI();
    }

    private Map<Integer, String> tilesmap;
    private Map<String, JLabel> tokenlabels;
    
    private JLabel label;
    private JButton button;
    
    private JButton rollButton;
    
    private JFrame mainFrame;
    private JPanel boardpanel;
    private JPanel bpanel1;
    private JPanel bpanel2;
    private JPanel bpanel3;
    private JPanel bpanel4;

    private JPanel opanel1;
    private JPanel opanel3;
    private JPanel opanel4;
    private Game game;

    private JLabel[] labelTiles;

    private Integer turn;

    private Player[] players;

    private Boolean rolled = false;
    
    public void UI() throws IOException, BiffException {
        
        tokenlabels = new HashMap<>();

        turn = 0;
        Player.character[] chars = new Player.character[3];
        chars[0] = Player.character.boot;
        chars[1] = Player.character.cat;
        chars[2] = Player.character.smartphone;

        players = new Player[3];
        players[0] = new Player(chars[0]);
        players[1] = new Player(chars[1]);
        players[2] = new Player(chars[2]);

        game = new Game(chars);

        labelTiles = new JLabel[40];

        String path = "C:\\Users\\Surface\\Desktop\\software-engineering-master\\software-engineering-master\\UI\\Board\\";
        tilesmap = new HashMap<>();

        tilesmap.put(1, "Go.png");
        tilesmap.put(2, "Brown1.png");
        tilesmap.put(3, "PotLuck.png");
        tilesmap.put(4, "Brown2.png");
        tilesmap.put(5, "PotLuck.png");  // tax
        tilesmap.put(6, "BrightonStation.png");
        tilesmap.put(7, "Blue1.png");
        tilesmap.put(8, "Knocks.png");
        tilesmap.put(9, "Blue2.png");
        tilesmap.put(10, "Blue3.png");
        tilesmap.put(11, "Jail.png");
        tilesmap.put(12, "Purple1.png");
        tilesmap.put(13, "Knocks.png");   //tesla
        tilesmap.put(14, "Purple2.png");
        tilesmap.put(15, "Purple3.png");
        tilesmap.put(16, "HoveStation.png");
        tilesmap.put(17, "Orange1.png");
        tilesmap.put(18, "PotLuck.png");
        tilesmap.put(19, "Orange2.png");
        tilesmap.put(20, "Orange3.png");
        tilesmap.put(21, "FreeParking.png");
        tilesmap.put(22, "Red1.png");
        tilesmap.put(23, "Knocks.png");
        tilesmap.put(24, "Red2.png");
        tilesmap.put(25, "Red3.png");
        tilesmap.put(26, "FalmerStation.png");
        tilesmap.put(27, "Yellow1.png");
        tilesmap.put(28, "Yellow2.png");
        tilesmap.put(29, "Knocks.png");  //edison water
        tilesmap.put(30, "Yellow3.png");
        tilesmap.put(31, "GoJail.png");
        tilesmap.put(32, "Green1.png");
        tilesmap.put(33, "Green2.png");
        tilesmap.put(34, "PotLuck.png");
        tilesmap.put(35, "Green3.png");
        tilesmap.put(36, "LewesStation.png");
        tilesmap.put(37, "Knocks.png");
        tilesmap.put(38, "DarkBlue1.png");
        tilesmap.put(39, "PotLuck.png");  //super tax
        tilesmap.put(40, "DarkBlue2.png");  //edison water

        mainFrame = new JFrame("Property Tycoon");
        mainFrame.setSize(1600, 1600);

        opanel1 = new JPanel();

        opanel3 = new JPanel();
        opanel4 = new JPanel();

        opanel1.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        opanel3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opanel4.setBorder(BorderFactory.createLineBorder(Color.ORANGE));

        opanel1.setSize(1000, 100);

        opanel3.setSize(100, 800);

        opanel4.setSize(1000, 100);

        opanel1.setLayout(new GridLayout(1, 0));

        opanel3.setLayout(new GridLayout(0, 1));
        opanel4.setLayout(new GridLayout(1, 0));

        boardpanel = new JPanel(new BorderLayout());
        boardpanel.setSize(900, 900);
        boardpanel.setForeground(Color.red);

        mainFrame.setLayout(new BorderLayout());
        mainFrame.getContentPane().setBackground(Color.red);

        bpanel1 = new JPanel();
        bpanel2 = new JPanel();
        bpanel3 = new JPanel();
        bpanel4 = new JPanel();

        bpanel1.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        bpanel2.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        bpanel3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        bpanel4.setBorder(BorderFactory.createLineBorder(Color.ORANGE));

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

        rollButton = new JButton("Roll");
        rollButton.addActionListener(this);
        rollButton.setPreferredSize(new Dimension(100, 100));
        rollButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        rollButton.setFont(new Font("Arial", Font.PLAIN, 40));
        rollButton.setActionCommand("rolled");
        boardpanel.add(rollButton, BorderLayout.CENTER);

        
        
        mainFrame.add(opanel1, BorderLayout.PAGE_START);

        mainFrame.add(opanel3, BorderLayout.LINE_END);
        mainFrame.add(opanel4, BorderLayout.PAGE_END);

        //top panel tokens
        BufferedImage wPic;
        Image dimg;

        label = new JLabel();
        label.setSize(100, 100);
        wPic = ImageIO.read(new File("C:\\Users\\Surface\\Desktop\\software-engineering-master\\software-engineering-master\\UI\\Buttons\\Bootproperties.png"));
        dimg = wPic.getScaledInstance(label.getWidth(), label.getHeight(),
                Image.SCALE_SMOOTH);
        label = new JLabel(new ImageIcon(dimg));
        label.setForeground(Color.green);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opanel1.add(label);

        label = new JLabel();
        label.setSize(100, 100);
        wPic = ImageIO.read(new File("C:\\Users\\Surface\\Desktop\\software-engineering-master\\software-engineering-master\\UI\\Buttons\\Catproperties.png"));
        dimg = wPic.getScaledInstance(label.getWidth(), label.getHeight(),
                Image.SCALE_SMOOTH);
        label = new JLabel(new ImageIcon(dimg));
        label.setForeground(Color.green);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opanel1.add(label);

        label = new JLabel();
        label.setSize(100, 100);
        wPic = ImageIO.read(new File("C:\\Users\\Surface\\Desktop\\software-engineering-master\\software-engineering-master\\UI\\Buttons\\Gobletproperties.png"));
        dimg = wPic.getScaledInstance(label.getWidth(), label.getHeight(),
                Image.SCALE_SMOOTH);
        label = new JLabel(new ImageIcon(dimg));
        label.setForeground(Color.green);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opanel1.add(label);

        label = new JLabel();
        label.setSize(100, 100);
        wPic = ImageIO.read(new File("C:\\Users\\Surface\\Desktop\\software-engineering-master\\software-engineering-master\\UI\\Buttons\\Hatstandproperties.png"));
        dimg = wPic.getScaledInstance(label.getWidth(), label.getHeight(),
                Image.SCALE_SMOOTH);
        label = new JLabel(new ImageIcon(dimg));
        label.setForeground(Color.green);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opanel1.add(label);

        label = new JLabel();
        label.setSize(100, 100);
        wPic = ImageIO.read(new File("C:\\Users\\Surface\\Desktop\\software-engineering-master\\software-engineering-master\\UI\\Buttons\\Smartphoneproperties.png"));
        dimg = wPic.getScaledInstance(label.getWidth(), label.getHeight(),
                Image.SCALE_SMOOTH);
        label = new JLabel(new ImageIcon(dimg));
        label.setForeground(Color.green);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opanel1.add(label);

        label = new JLabel();
        label.setSize(100, 100);
        wPic = ImageIO.read(new File("C:\\Users\\Surface\\Desktop\\software-engineering-master\\software-engineering-master\\UI\\Buttons\\spoonproperties.png"));
        dimg = wPic.getScaledInstance(label.getWidth(), label.getHeight(),
                Image.SCALE_SMOOTH);
        label = new JLabel(new ImageIcon(dimg));
        label.setForeground(Color.green);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opanel1.add(label);

        /*
        for (int i = 0; i < 5; i++) {

            JButton rButton = new JButton("RIGHT " + (i + 1));
            rButton.setPreferredSize(new Dimension(100, 100));
            rButton.setForeground(Color.blue);
            opanel3.add(rButton);

          
        }
*/
        
        //RIGHT BUTTONS
        String temppath = "C:\\Users\\Surface\\Desktop\\software-engineering-master\\software-engineering-master\\UI\\tokens\\";
        
        label = new JLabel();
        label.setSize(100, 100);
        wPic = ImageIO.read(new File(temppath +  "boot.png"));
        dimg = wPic.getScaledInstance(50 , 50,
                Image.SCALE_SMOOTH);
        label = new JLabel(new ImageIcon(dimg));
        label.setForeground(Color.green);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opanel3.add(label);
        tokenlabels.put("boot", label);
        
        
        label = new JLabel();
        label.setSize(100, 100);
        wPic = ImageIO.read(new File(temppath +  "cat.png"));
        dimg = wPic.getScaledInstance(50 , 50,
                Image.SCALE_SMOOTH);
        label = new JLabel(new ImageIcon(dimg));
        label.setForeground(Color.green);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opanel3.add(label);
        tokenlabels.put("cat", label);
        

        label = new JLabel();
        label.setSize(100, 100);
        wPic = ImageIO.read(new File(temppath +  "goblet.png"));
        dimg = wPic.getScaledInstance(50 , 50,
                Image.SCALE_SMOOTH);
        label = new JLabel(new ImageIcon(dimg));
        label.setForeground(Color.green);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opanel3.add(label);
        tokenlabels.put("goblet", label);
        
        

        label = new JLabel();
        label.setSize(100, 100);
        wPic = ImageIO.read(new File(temppath +  "hatstand.png"));
        dimg = wPic.getScaledInstance(50, 50,
                Image.SCALE_SMOOTH);
        label = new JLabel(new ImageIcon(dimg));
        label.setForeground(Color.green);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opanel3.add(label);
        tokenlabels.put("hatstand", label);
        

        label = new JLabel();
        label.setSize(100, 100);
        wPic = ImageIO.read(new File(temppath +  "smartphone.png"));
        dimg = wPic.getScaledInstance(50, 50,
                Image.SCALE_SMOOTH);
        label = new JLabel(new ImageIcon(dimg));
        label.setForeground(Color.green);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opanel3.add(label);
        tokenlabels.put("smartphone", label);
        

        label = new JLabel();
        label.setSize(100, 100);
        wPic = ImageIO.read(new File(temppath +  "spoon.png"));
        dimg = wPic.getScaledInstance(50, 50,
                Image.SCALE_SMOOTH);
        label = new JLabel(new ImageIcon(dimg));
        label.setForeground(Color.green);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        opanel3.add(label);
        tokenlabels.put("spoon", label);
        

        
        //Bottom buttons
            button = new JButton(" BUY ");
            button.setPreferredSize(new Dimension(100, 100));
            button.setForeground(Color.blue);
            button.addActionListener(this);
            button.setActionCommand("buy");
            opanel4.add(button);
            
            button = new JButton(" PLACEHOLDER ");
            button.setPreferredSize(new Dimension(100, 100));
            button.setForeground(Color.blue);
            opanel4.add(button);
            
            button = new JButton(" PLACEHOLDER  ");
            button.setPreferredSize(new Dimension(100, 100));
            button.setForeground(Color.blue);
            opanel4.add(button);
            
            button = new JButton(" END TURN ");
            button.setPreferredSize(new Dimension(100, 100));
            button.setForeground(Color.blue);
            button.addActionListener(this);
            button.setActionCommand("endturn");
            opanel4.add(button);
            

        //tiles top
        for (int i = 0; i < 11; i++) {

            label = new JLabel();
            label.setSize(100, 100);
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
            label.setSize(100, 100);
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
            label.setSize(100, 100);
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
            label.setSize(100, 100);
            wPic = ImageIO.read(new File(path + tilesmap.get(20 + i + 1)));
            dimg = wPic.getScaledInstance(label.getWidth(), label.getHeight(),
                    Image.SCALE_SMOOTH);
            label = new JLabel(new ImageIcon(dimg));
            label.setForeground(Color.green);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            bpanel4.add(label);
            labelTiles[20 + i] = label;

        }

        boardpanel.setBorder(BorderFactory.createLineBorder(Color.RED));
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

    // Variables declaration - do not modify                     
    // End of variables declaration                   
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("rolled".equals(e.getActionCommand())) {
            if(!rolled){
                System.out.println(players[turn].characters_Player() + " at space --> " + players[turn].Player_position());
                System.out.println("- Rolled -");
                game.player_turn(players[turn]);
                rolled = true;
                UpdateUI();
            }

        }
        if ("buy".equals(e.getActionCommand())) {
            for (Properties prop : game.properties){
                if(prop.returnPos() == players[turn].Player_position() + 1){
                    players[turn].property_buy(prop);
                    System.out.println(players[turn].characters_Player() + " bought " +  prop.space_name());
                }
            }
           UpdateUI();
        }
        
        if ("endturn".equals(e.getActionCommand())) {
            nextPlayer();
           
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
            curLabel.setText("  Balance :  " + p.Player_balance());
            curLabel.setForeground(Color.black);
            curLabel.setHorizontalTextPosition(JLabel.CENTER);
            curLabel.setVerticalTextPosition(JLabel.BOTTOM);
        
           
        }
       UpdateRollButton();
    }
    
    private void UpdateRollButton(){
        if(!rolled){
         rollButton.setText(players[turn].characters_Player().toUpperCase() + "'S TURN , CLICK TO ROLL");
        }else{
         rollButton.setText(players[turn].characters_Player().toUpperCase() + " HAS ROLLED. PRESS END TURN");   
        }
    }
}
