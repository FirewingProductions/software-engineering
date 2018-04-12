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
    int cost;
    ArrayList<Integer> rent;
    String colour;

    
    public Properties(int position,String action,String colour,int cost){
        super(position,action);
        rent =new ArrayList<Integer>();
        this.colour=colour;
        this.cost=cost;
    
       
    }
    
   
   public void rent(int x){
       
       rent.add(x);
   }
   public int getrent(int x){
       return rent.get(x-1);
       
   }
   
}
