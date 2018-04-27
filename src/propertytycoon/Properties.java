/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shitaab
 */
public class Properties extends Space{
   private   int cost;
   private   ArrayList<Integer> rent;
   private   String colour;
   private   int cost_house;
   private   Player.character  owned;
   private   Boolean  is_owend=false;
   private   int pos;
   private   Boolean  mortgage;
   private   int house;
   
public Properties(int position,String action,String space_name,String colour,int cost,int cost_house){
    super(position,action,space_name);
    pos = position;
    rent =new ArrayList<Integer>();
    this.colour=colour;
    this.cost=cost;
    this.cost_house=cost_house;
       
}
    
public Integer returnPos(){
    return pos;
} 

public void rent(int x){
       
    rent.add(x);
}

public int getrent(int x){
    return rent.get(x-1);
       
}

public int getcost(){
    
    return cost;
       
}
public int getcost_house(){
    
    return cost_house;
       
}

public String getcolour(){
    
    return colour;
      
    
}
public boolean property_is_owend(){
    
    return is_owend;
    
}
public Player.character property_owener(){
    
   return owned;
   
}

public  void property_buy(Player p){
    
    if(p.Player_balance()>cost&&is_owend==false){
        is_owend=true;
        owned=p.player_characters;
        p.Player_balance_de(cost);
    } 
    
}
     
public void buy_house(Player p){
    
    if(!colour.contains("Station")||!colour.contains("Utilities")){
        
        if(house!=6){
            
            p.Player_balance_de(cost_house);
            house++;
         
        }
    }   
}
      
public void  mortgage(Player p){
    
    if(p.player_characters==owned&&mortgage==false){
        p.Player_balance_in(cost/2);
        mortgage=true;
    }
          
}

public void  un_mortgage(Player p){
    if(p.player_characters==owned&&mortgage==true){
        p.Player_balance_de(cost/2);
        mortgage=false;

              
    }
}
   
}
