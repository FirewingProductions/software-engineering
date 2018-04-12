package propertytycoon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shitaab
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import static jdk.nashorn.internal.objects.NativeString.toLowerCase;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Game  {
            Player[] player; // a list of players 
            Space[] space;
            Dice a= new Dice();  // the two dices
            Properties[] properties;  

    
    public Game( Player.character[] b) {
        // initialization of players class by defining the characters of each player
         player= new Player[b.length];
         
         for(int x=0;x<b.length;x++)
         player[x] = new Player(b[x]);  
       
    }
    
   
    

    public void new_turn(Player player_no){
        // 1) throw the dice 2) check if player has passed go(location number 0) if so we go back to  (location -40 )
        //3) we check if the player has got double if so then they play again 
        a.throw_dice();
        
        player_no.Player_move(a.dice[0]+a.dice[1]);
       
        if (player_no.Player_position>=40){
            
           player_no.Player_position=0+(player_no.Player_position-40);
        }
       
        
        if(a.dice[0]==a.dice[1]){
            new_turn(player_no);
        }
        
    }
    
    //new method added to crate spaces 
public void create_space()throws BiffException, IOException {
    //loading the exel fine
    FileInputStream fs = new FileInputStream("src/propertytycoon/properties.xls");
    Workbook wb = Workbook.getWorkbook(fs);
		// TO get the access to the sheet
    Sheet sh = wb.getSheet("Sheet1");

	// To get the number of rows present in sheet -4 are the rows wich dont have the needed data
		int totalNoOfRows = sh.getRows()-4;
                //creating an array of spaces 
                space=new Space[totalNoOfRows];
                //28 rep the number of propirties
                properties= new Properties[28];
		// To get the number of columns present in sheet
                int i=0; // the counter for propirties
for (int x = 0; x < totalNoOfRows; x++) {
   //adding the space position and the action
  space[x]= new Space(Integer.parseInt(sh.getCell(0,x+4).getContents()),sh.getCell(4,x+4).getContents());
    
           //if the space can be bought then its count as a propirty
    if(sh.getCell(5,x+4).getContents().contains("Yes")){
        //creating a new propirtie with: location , cost, colour and diffrient rent prices for ex 0 will get no house and 1 will get the rent with a house 
        properties[i]=new Properties(Integer.parseInt(sh.getCell(0,x+4).getContents()),sh.getCell(4,x+4).getContents(),sh.getCell(3,x+4).getContents(),Integer.parseInt(sh.getCell(7,x+4).getContents()));
       
      if( !sh.getCell(8,x+4).getContents().contains("See notes")){
          properties[i].rent(Integer.parseInt(sh.getCell(8,x+4).getContents()));
          properties[i].rent(Integer.parseInt(sh.getCell(10,x+4).getContents()));
          properties[i].rent(Integer.parseInt(sh.getCell(11,x+4).getContents()));
          properties[i].rent(Integer.parseInt(sh.getCell(12,x+4).getContents()));
          properties[i].rent(Integer.parseInt(sh.getCell(13,x+4).getContents()));
          properties[i].rent(Integer.parseInt(sh.getCell(14,x+4).getContents()));
                     System.out.println(properties[i].position+" "+properties[i].colour+" "+properties[i].cost+" "+properties[i].getrent(1)+" "+properties[i].getrent(2)+" "+properties[i].getrent(3)+" "+properties[i].getrent(4)+" "+properties[i].getrent(5)+" "+properties[i].getrent(6));

      }
      else{
                               System.out.println(properties[i].position+" "+properties[i].colour+" "+properties[i].cost+" ");

      }

        i++;
 
    }
    
     
}



	
}
}
