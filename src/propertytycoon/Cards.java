/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
private   LinkedList<String> pot_luck_card_data;   //list of cards
private    LinkedList<String> Opportunity_knocks_card_data ;  //list of cards
    public Cards(){
        
               pot_luck_card_data=new LinkedList<String>();   
               Opportunity_knocks_card_data =new LinkedList<String>();	
                 create_cards();
    }
    private void create_cards(){
        
 try {
  BufferedReader in = new BufferedReader(new FileReader("src/propertytycoon/pot_luck_card.text"));
  BufferedReader in1 = new BufferedReader(new FileReader("src/propertytycoon/Opportunity_knocks_card.text"));

String str;
String str1;



      
           while((str = in.readLine()) != null){
               
               pot_luck_card_data.add(str);
           }  
 
  while((str1 = in1.readLine()) != null){
               
               Opportunity_knocks_card_data.add(str1);
           }   
 } catch (IOException ex) {
       }

        // i have added the cards to the list manually but this will be changed as now it only shows a string
       
        
    }
    
    public void shuffel_cards(LinkedList s){
        
        Collections.shuffle(s); // this method will shuffel the cards in the linkedlist entered 
        
    }
   public void activate_card(String s,Player p, ArrayList<Space> space){
       //have a look
       
       
       if(s.contains("Advance to ")||s.contains("Go to")){
              String[] v= s.split("to ");
        
             String position=v[1]; 
             
             String[] v1= position.split("£");
            System.out.println(v[0]);
            System.out.println(v1[1]);
          for(int d=0;d<space.size();d++){
            
            
          
            if(position.contains(space.get(d).space_name())){
                System.out.println(space.get(d).space_name());
                p.Player_move(space.get(d).getposition());
            }
            
            
        }
       }
       else{
        if(s.contains("Pay")||s.contains("pay")||s.contains("Fine")||s.contains("Fined")){
           
           
                                  System.out.println(p.Player_balance());
 
                                   String[] v= s.split("£");
                                   System.out.println(v[1]);
  
           p.Player_balance_de(Integer.parseInt(get_value_of_Card(v[1])));
           System.out.println(p.Player_balance());
      
       
       }
            
        else if(s.contains("Collect")||s.contains("inherit")||s.contains("collect")){
           
           
                                System.out.println(p.Player_balance());

                                  String[] v= s.split("£");
                                   System.out.println(v[1]);
  
           p.Player_balance_in(Integer.parseInt(get_value_of_Card(v[1])));
           System.out.println(p.Player_balance());
      
       
       }
       
   }}
    public void draw_card(LinkedList s){
        // we use number 1 to draw pot_luck_card_data and 2 for Opportunity_knocks_card_data
        
        
        
    System.out.println(s.getFirst());  // print the drawn card
     
   s.addLast(s.getFirst());
    s.removeFirst();
   //put the card to the the end of the deck
            
            
       
        }
    public LinkedList return_pot_luck_card_data(){
       return pot_luck_card_data;
        
    }
        public LinkedList return_Opportunity_knocks_card_data(){
       return Opportunity_knocks_card_data;
        
    } 
       public int return_pot_luck_card_size(){
       return pot_luck_card_data.size();
        
    }
        public int return_Opportunity_knocks_card_size(){
       return Opportunity_knocks_card_data.size();
        
    } 
        
        private String get_value_of_Card(String v){
            
                   StringBuilder builder = new StringBuilder();
    for (int i = 0; i < v.length(); i++) {
        char c = v.charAt(i);
        if (Character.isDigit(c)) {
            builder.append(c);
        }
    }
       return builder.toString(); }
        
    }

