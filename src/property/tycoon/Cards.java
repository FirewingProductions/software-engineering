/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package property.tycoon;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shitaab
 */







public class Cards {
   LinkedList<String> pot_luck_card_data;   //list of cards
    LinkedList<String> Opportunity_knocks_card_data ;  //list of cards
    public Cards(){
        
               pot_luck_card_data=new LinkedList<String>();   
               Opportunity_knocks_card_data =new LinkedList<String>();	

 try {
  BufferedReader in = new BufferedReader(new FileReader("../pot_luck_card.text"));
  BufferedReader in1 = new BufferedReader(new FileReader("../Opportunity_knocks_card.text"));

String str;
String str1;



      
           while((str = in.readLine()) != null&&(str1 = in1.readLine()) != null){
               
               pot_luck_card_data.add(str);
               Opportunity_knocks_card_data.add(str1);
           }      } catch (IOException ex) {
       }

        // i have added the cards to the list manually but this will be changed as now it only shows a string
       
    }
    
    
    public void shuffel_cards(LinkedList s){
        
        Collections.shuffle(s); // this method will shuffel the cards in the linkedlist entered 
        
    }
   public void activate_card(String s,Player p){
       //have a look
       
       
       if(s.contains("advance to ")){
       //  String[]v= s.split("advance to ");
         //  int x=get_location(v[1]);
           //p.Player_move(x);
       }
       else if(s==""){
           
           
           
       }
       else if (s==""){
           
       }
       
   }
    public void draw_card(int x){
        // we use number 1 to draw pot_luck_card_data and 2 for Opportunity_knocks_card_data
        
        if(x==1){
        
    System.out.println(pot_luck_card_data.getFirst());  // print the drawn card
     
   pot_luck_card_data.pollFirst();  //put the card to the the end of the deck
            
            
        }
        else if(x==2){
   System.out.println(Opportunity_knocks_card_data.getFirst());  // print the drawn card
   Opportunity_knocks_card_data.pollFirst();  //put the card to the the end of the deck
          
        }
        }
      
        
    }

