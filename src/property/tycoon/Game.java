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
public class Game  {
            Player[] player; // a list of players 

             Dice a= new Dice();  // the two dices
    
    
    
    public Game( Player.character[] b) {
        // initialization of players class by defining the characters of each player
         player= new Player[b.length];
         
         for(int x=0;x<b.length;x++)
         player[x] = new Player(b[x]);  
       
    }
    
   
    

    public void new_turn(Player player_no){
        // 1) throw the dice 2) check if player has passed go(location number 0) if so we go back to  (location -40 )
        //3) we check if the player has got double if so then they play again 
        a.throw_dice();
        
        player_no.Player_move(a.dice[0]+a.dice[1]);
       
        if (player_no.Player_position>=40){
            
           player_no.Player_position=0+(player_no.Player_position-40);
        }
       
        
        if(a.dice[0]==a.dice[1]){
            new_turn(player_no);
        }
        
    }
    
    

    
}
