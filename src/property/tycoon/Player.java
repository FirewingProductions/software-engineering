package property.tycoon;

import java.util.LinkedList;

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
    int player_balance=1500;
    int Player_position=0;
    charechter player_characters;
    
    // need to be worked on
    
    String name;
    String[] Property; //will be changed to property class instead of string 
    boolean jailed;
    int jail_time;
    Cards[] player_cards;
    
    
    
    public Player(charechter x){
       
        player_characters=x;
        
    }
    public enum charechter{
        
        boot,smartphone,goblet,hatstand,spon,cat
    }
    public String characters_Player(){
        
       return player_characters.name();
        
    }
    
    public int Player_balance(){
        
        return player_balance;
    }
   public void Player_move(int x){
        Player_position=Player_position+x;
       
   }
   
    
}
