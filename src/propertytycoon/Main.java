package propertytycoon;

import java.io.IOException;
import jxl.read.biff.BiffException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shitaab
 */
public class Main {

    /**
     * @param args the command line arguments
     */
        public static void main(String[] args) throws IOException, BiffException {
        // TODO code application logic here
       
        Player.character[] player= new Player.character[]{Player.character.boot,Player.character.cat,Player.character.goblet};
       Game n=new Game(player);
       
       /*
       // enum test
             System.out.println(n.player[0].player_characters);
             System.out.println(n.player[1].player_characters);
             System.out.println(n.player[2].player_characters);
       */
       //testing space
       //testing cards
              Cards s;
             s= new Cards();
          
                    
          s.shuffel_cards(s.return_Opportunity_knocks_card_data());
        s.shuffel_cards(s.return_pot_luck_card_data());
        n.player[0].Player_move(30,true);
               System.out.println( n.player[0].Player_position());
n.player[0].hotels=2;
        s.activate_card("It's your birthday. Collect £10 from each player", n.player[0],n.space,n.player,n.parking);
       
         System.out.println( n.player[0].Player_position());
   
               
             for(int i=0;i<s.return_pot_luck_card_size();i++){
           s.draw_card(s.return_pot_luck_card_data()); 


        }
                  for(int i=0;i<s.return_Opportunity_knocks_card_size();i++){
           s.draw_card(s.return_Opportunity_knocks_card_data()); 


        }
                
                       
/*testing player movements 
     
      System.out.println(n.player[0].Player_position);
n.new_turn(n.player[1]);

             System.out.println(n.player[1].Player_position);

n.new_turn(n.player[1]);
             System.out.println(n.player[1].Player_position);

n.new_turn(n.player[1]);
             System.out.println(n.player[1].Player_position);

n.new_turn(n.player[1]);
             System.out.println(n.player[1].Player_position);


n.new_turn(n.player[1]);
             System.out.println(n.player[1].Player_position);


n.new_turn(n.player[1]);
             System.out.println(n.player[1].Player_position);


n.new_turn(n.player[1]);
             System.out.println(n.player[1].Player_position);


n.new_turn(n.player[1]);
             System.out.println(n.player[1].Player_position);



n.new_turn(n.player[1]);
             System.out.println(n.player[1].Player_position);



     
       
       
       
       /* testing game class making new players  
       System.out.println(n.player[0].player_characters);
              System.out.println(n.player[1].player_characters);
       System.out.println(n.player[2].player_characters);
       System.out.println(n.player[3].player_characters);
  */
       
       
       
       
       /* testing player class
    Player player =new Player();
    player.characters_Player(b[0]);
       System.out.println(player.player_characters);
       System.out.println(player.player_balance);
*/
       
    }
    
}
