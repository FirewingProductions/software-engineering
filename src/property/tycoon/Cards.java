/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package property.tycoon;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author shitaab
 */







public class Cards {
   LinkedList<String> pot_luck_card_data;
    LinkedList<String> Opportunity_knocks_card_data ;
    public Cards(){
       pot_luck_card_data=new LinkedList<String>(Arrays.asList("You inherit £100","You have won 2nd prize in a beauty contest, collect £20","Go back to Crapper Street","Student loan refund. Collect £20","Bank error in your favour. Collect £200","Pay bill for text books of £100","Mega late night taxi bill pay £50","Advance to go","From sale of Bitcoin you get £50","Pay a £10 fine or take opportunity knocks","Pay insurance fee of £50","Savings bond matures, collect £100","Go to jail. Do not pass GO, do not collect £200"	,"Received interest on shares of £25","It's your birthday. Collect £10 from each player","Get out of jail free"));
        Opportunity_knocks_card_data =new LinkedList<String>(Arrays.asList("Bank pays you divided of £50","You have won a lip sync battle. Collect £100","Advance to Turing Heights","Advance to Han Xin Gardens. If you pass GO, collect £200","Fined £15 for speeding","Pay university fees of £150","Take a trip to Hove station. If you pass GO collect £200","Loan matures, collect £150","You are assessed for repairs, £40/house, £115/hotel","Advance to GO","You are assessed for repairs, £25/house, £100/hotel","Go back 3 spaces","Advance to Skywalker Drive. If you pass GO collect £200","Go to jail. Do not pass GO, do not collect £200","Drunk in charge of a skateboard. Fine £20","Get out of jail free"));	
    }
    
    
    public void shuffel_cards(LinkedList s){
        
        Collections.shuffle(s);
        
    }
    
    public void draw_card(int x){
        // we use number 1 to draw pot_luck_card_data and 2 for Opportunity_knocks_card_data
        
        if(x==1){
            if(pot_luck_card_data.getFirst()=="Get out of jail free"){
            System.out.println(pot_luck_card_data.getFirst()); 
            pot_luck_card_data.removeFirst();
               pot_luck_card_data.pollFirst();

            }
            else{
    System.out.println(pot_luck_card_data.getFirst()); 
     
   pot_luck_card_data.pollFirst();
            
            }
        }
        else if(x==2){

 if(Opportunity_knocks_card_data.getFirst()=="Get out of jail free"){
            System.out.println(Opportunity_knocks_card_data.getFirst()); 
            Opportunity_knocks_card_data.removeFirst();
               Opportunity_knocks_card_data.pollFirst();

            }
            else{
    System.out.println(Opportunity_knocks_card_data.getFirst()); 
     
   Opportunity_knocks_card_data.pollFirst();
            
            }
        }
        }
      
        
    }

