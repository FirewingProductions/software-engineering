/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.util.ArrayList;

/**
 *
 * @author shitaab
 */
public class Properties extends Space{
   private  int cost;
   private  ArrayList<Integer> rent;
   private  String colour;
   private  int cost_house;
            String owned_by;
   private Player.character  owend;
   private Boolean  aauctioned;
   private  int house;
    public Properties(int position,String action,String space_name,String colour,int cost,int cost_house){
        super(position,action,space_name);
        rent =new ArrayList<Integer>();
        this.colour=colour;
        this.cost=cost;
        this.cost_house=cost_house;
       
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
     public void buy_property(Player p){
         p.Player_balance_de(cost);
         owned_by=p.name;
         owend=p.player_characters;
     }
      
      public void buy_house(Player p){
          if(!colour.contains("Station")||!colour.contains("Utilities")){
          if(house!=5){
         p.Player_balance_de(cost_house);
         house++;
         
          }
          }
     }
      
   
}
