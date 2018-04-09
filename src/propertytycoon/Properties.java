/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author shitaab
 */
public class Properties {
    ArrayList<String> properties;

    public Properties() {
        try{
              properties =new ArrayList<String>();   

     BufferedReader in = new BufferedReader(new FileReader("../properties.text"));


String str;
String str1;



      
           while((str = in.readLine()) != null){
               
               properties.add(str);
           }    }   catch (IOException ex) {
                   
       }
    
}
}