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
            Player[] player;

             Dice a= new Dice();
    
    
    
    public Game(int a,int[] b) {
         player= new Player[a];
         
         for(int x=0;x<a;x++)
         player[x] = new Player(b[x]);
       
    }
    
   
    

    public void new_turn(int x){
        a.throw_dice();
        player[x-1].Player_move(a.dice[0]+a.dice[1]);
       
        if (player[x-1].Player_position>=40){
            
            player[x-1].Player_position=0+(player[x-1].Player_position-40);
        }
       
        
        if(a.dice[0]==a.dice[1]){
            new_turn(x);
        }
        
    }
    
    
    
    

    
}
