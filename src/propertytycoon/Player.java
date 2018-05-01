package propertytycoon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author shitaab
 */
public class Player {

    private int player_balance = 1500; //player starting balance
    private int Player_position = 0;   // the position of the player on the board
    private boolean passedgo;
    character player_characters;
    String name;    //the name of the player
    HashMap<String, ArrayList<Properties>> properties; //will be changed to property class instead of string 
    public boolean jailed;
    private int jail_time;
    LinkedList<String> player_cards; //if a player still has a card that he draw from the deck of cards
    ArrayList<String> sets;
    int houses;
    int hotels;

    String[] colours = {"Red", "Brown", "Purple", "Utilities", "Station", "Green", "Deep blue", "Blue", "Orange", "Yellow"};

    public Player(character x) {
        player_cards = new LinkedList<>();
        properties = new HashMap<String, ArrayList<Properties>>();
        player_characters = x;   // this method assigns the chosen character
        for (int y = 0; y < colours.length; y++) {
            properties.put(colours[y], new ArrayList<>());
        }

    }

    public enum character {

        boot, smartphone, goblet, hatstand, spoon, cat  // the possible characters that can be used 
    }

    public String characters_Player() {

        return player_characters.name();      //returns the chosen character

    }

    public int Player_balance() {

        return player_balance;     //returns the balansce 

    }

    public void Player_advance(int x, boolean s) {

        

        if (Player_position + x > 39) {
            passedgo = true;
            go(s);
            Player_position = (Player_position + x) - 39;

        }else{
            Player_position += x;  // moves a player to  by any number of steps (X)
        }
    }

    public void Player_move(int x, boolean s) {

        Player_position = Player_position + x;  // moves a player to  by any number of steps (X)

        if (Player_position > 39) {
            passedgo = true;
            go(s);
            Player_position -= 39;

        }
    }

    public int Player_position() {

        return Player_position;

    }

    public void Player_balance_in(int x) {
        // adds x to balance 
        player_balance = player_balance + x;
    }

    public void Player_balance_de(int x) {
        // takes x from the balance
        player_balance = player_balance - x;
    }

    public void is_jailed() {

        jailed = true;
        Player_position = 10;
    }

    public void player_has_a_Set() {

    }

    public void is_out_ofjail() {

        jail_time = 0;
        jailed = false;

    }

    public void Player_still_in_jail() {

        jail_time++;

        if (jail_time == 3) {

            jail_time = 0;
            jailed = false;

        }
    }

    public void pay_to_Get_out_of_jail() {
        if (jailed == true) {
            Player_balance_de(50);
            is_out_ofjail();
        }

    }

    public int Player_jail_time() {

        return jail_time;

    }

    public void add_card_to_player(String s) {

        player_cards.add(s);

    }

    public void use_get_out_of_jail(LinkedList<String> card, int x) {
        //x is the index number of the used card 
        if (card.contains("Get out of jail free")) {
            is_out_ofjail();
            player_cards.remove(x);
        }

    }

    public void go(boolean s) {
        if (s) {
            System.out.println(player_balance);
            Player_balance_in(200);
        }
    }

    public void property_buy(Properties p) {

        p.property_buy(this);
        properties.get(p.getcolour()).add(p);

    }
    public boolean checkPassedGo(){
        return passedgo;
    }
  
}
