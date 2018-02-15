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
public class Main {

    /**
     * @param args the command line arguments
     */
        public static void main(String[] args) {
        // TODO code application logic here
        int[] b= new int[]{5,2,3,6};
        Player.charechter[] player= new Player.charechter[]{Player.charechter.boot,Player.charechter.cat,Player.charechter.goblet};
       Game n=new Game(player);
       
       /*
       // enum test
             System.out.println(n.player[0].player_characters);
             System.out.println(n.player[1].player_characters);
             System.out.println(n.player[2].player_characters);
*/
     
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
