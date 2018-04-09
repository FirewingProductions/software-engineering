package propertytycoon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Random;

/**
 *
 * @author 
 */
public class Dice {
    
    public int dice[]=new int[2];
    
    
    public  Dice(){
        

}
    public int[] throw_dice(){
        Random ran_no1 = new Random();
        Random ran_no2 = new Random();
        dice[0]= ran_no1.nextInt(6) + 1;
        dice[1]= ran_no1.nextInt(6) + 1;
       System.out.println("dice value: "+dice[0]+" "+dice[1]) ;

      return   dice;
      
      
        
    }

    
    
}
