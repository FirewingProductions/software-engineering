package propertytycoon;


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
    
   private int player_balance=1500; //player starting balance
   int Player_position=0;   // the position of the player on the board
   character player_characters;  
   String name;    //the name of the player
   Properties[] Property; //will be changed to property class instead of string 
   boolean jailed;   
   int jail_time;  
   Cards[] player_cards; //if a player still has a card that he draw from the deck of cards
    
public Player(character x){
       
    player_characters=x;   // this method assigns the chosen character 
        
}

public enum character{
        
    boot,smartphone,goblet,hatstand,spon,cat  // the possible characters that can be used 
}

public String characters_Player(){
        
    return player_characters.name();      //returns the chosen character
        
}
    
public int Player_balance(){
        
    return player_balance;     //returns the balance 
}

public void Player_move(int x){
    Player_position=x;  // moves a player to  by any number of steps (X)
       
}

public void Player_balance_in(int x){
    // adds x to balance 
    player_balance=player_balance+x;     
}

public void Player_balance_de(int x){
    // takes x from the balance
    player_balance=player_balance-x;     
}
   

   
    
}
