package property.tycoon;

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
    String[] p= new String[]{"boot","smartphone","goblet","hatstand","spon","cat"};
    int player_balance=1500;
    int Player_position=0;
    String player_characters;
    
    public Player(int x){
       
        player_characters=p[x-1];
        
    }
    public String characters_Player(){
        
       return player_characters;
        
    }
    
    public int Player_balance(){
        
        return player_balance;
    }
   public void Player_move(int x){
        Player_position=Player_position+x;
       
   }
   
    
}
