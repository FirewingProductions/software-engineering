package propertytycoon;

import java.io.IOException;

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
        public static void main(String[] args) throws IOException {
        // TODO code application logic here
        int[] b= new int[]{5,2,3,6};
        Player.character[] player= new Player.character[]{Player.character.boot,Player.character.cat,Player.character.goblet};
       Game n=new Game(player);
       
       /*
       // enum test
             System.out.println(n.player[0].player_characters);
             System.out.println(n.player[1].player_characters);
             System.out.println(n.player[2].player_characters);
       */
       
       // testing drawing cards
             Cards s;
       
             s= new Cards();
          Properties   s1= new Properties();
          
                    
          s.shuffel_cards(s.pot_luck_card_data);
        s.shuffel_cards(s.Opportunity_knocks_card_data);
    
               
             for(int i=0;i<s.pot_luck_card_data.size();i++){
            System.out.println(s.pot_luck_card_data.get(i)); 


        }
              System.out.println("\n"); 
                  for(int i=0;i<s.Opportunity_knocks_card_data.size();i++){
            System.out.println(s.Opportunity_knocks_card_data.get(i)); 


        }
                  
                       for(int i=0;i<s1.properties.size();i++){
            System.out.println(s1.properties.get(i)); 

    
               
                       }
                
                       
/* testing player movements 
     
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
