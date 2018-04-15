/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;


/**
 *
 * @author shitaab
 */
public class Space {
   private int position;
   private  String action;
   private  String space_name;


public Space(int position,String action,String space_name) {
        
    this.action=action;
    this.position=position;
    this.space_name=space_name;
}

public int getposition(){
    
    return position;
        
}

public String getaction(){
        
    return action;
    
}

public String space_name(){
        
    return space_name;
    
}
 
}