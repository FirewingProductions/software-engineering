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
import java.util.HashMap;
import java.util.LinkedList;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Game {
    Cards cards ;

    Player[] players; // a list of players 
    ArrayList<Space> space;
    Dice a = new Dice();  // the two dices
    ArrayList<Properties> properties;
    HashMap<String, ArrayList<Properties>> properties_final; //will be changed to property class instead of string 
    String[] colours = {"Red", "Brown", "Purple", "Utilities", "Station", "Green", "Deep blue", "Blue", "Orange", "Yellow"};
    private int bankFunds = 50000;
    static int parking = 0;

    public Game(Player.character[] b) throws BiffException, IOException {
        // initialization of players class by defining the characters of each player
        players = new Player[b.length];
        for (int x = 0; x < b.length; x++) {
            players[x] = new Player(b[x]);
        }
        properties_final = new HashMap<String, ArrayList<Properties>>();
        for (int y = 0; y < colours.length; y++) {
            properties_final.put(colours[y], new ArrayList<>());
        }

        create_space();
   
      cards = new Cards(space);
      cards.shuffle_cards(cards.return_Opportunity_knocks_card_data());
      cards.shuffle_cards(cards.return_pot_luck_card_data());
    }

    public void player_turn(Player player) {

        throw_dice(player);

      //  if (space.get(player.Player_position()).getaction() == null) {

        //} else {

        //}

    }

    private void throw_dice(Player player) {
        // 1) throw the dice 2) check if player has passed go(location number 0) if so we go back to  (location -40 )
        player.Player_move(a.throw_dice(), true);

        int i = 1; //double counter
        while (a.Double()) {

            player.Player_move(a.throw_dice(), true);
            i++;
            if (i == 3) {
                //go to jail
                player.is_jailed();
                player.Player_move(10, false);

                break;
            }

        }

    }

    public String check_player_location(Player player) {

        if (space.get(player.Player_position()).getaction().isEmpty()) {
            
            //System.out.println(space.get(player.Player_position()).space_name() + " has no action");
            
            if(space.get(player.Player_position()).space_name().equalsIgnoreCase("Go to Jail")){
                System.out.println(player.player_characters.toString() + " has been jailed");
                player.is_jailed();
                return (player.player_characters.toString() + " has been jailed");
            }
            
            if(properties.contains(space.get(player.Player_position()))){
                int index = properties.indexOf(space.get(player.Player_position()));
                if(properties.get(index).property_is_owned() && properties.get(index).property_owener() != player ){
                    //if the property is owned by someone else.. pay rent
                    System.out.println(player.player_characters.toString() + " landed on someone elses property.. paying rent");
                    int rentamount = properties.get(index).getrent(properties.get(index).return_house_amount() + 1 , properties_final.get(properties.get(index).getcolour()));
                    System.out.println("Rent amount : " + rentamount);
                    player.Player_balance_de(rentamount);
                    properties.get(index).property_owener().Player_balance_in(rentamount);
                    System.out.println(properties.get(index).property_owener().characters_Player().toString() + " has been paid " + rentamount + " in rent");
                    return(player.player_characters.toString() + " landed on " + properties.get(index).property_owener().characters_Player().toString() + "'s property and must pay : " + rentamount);
                }
            }

        } else {
            System.out.println(space.get(player.Player_position()).getaction());
            switch(space.get(player.Player_position()).getaction()){
                case "Take card":
                    if(space.get(player.Player_position()).space_name().equals("Opportunity Knocks")){
                        String card =cards.take_card(2);
                        player.add_card_to_player(card);
                        cards.activate_card(player.player_cards.get(0), player, players);
                        return player.characters_Player() + " Took an opportunity knocks card <br/>" + card;
                    }else{
                        String card =cards.take_card(1);
                        player.add_card_to_player(card);
                        cards.activate_card(player.player_cards.get(0), player, players);
                        return player.characters_Player() + " Took a pot luck card <br/>" + card;
                    }
                    
                case "Pay �200":
                    System.out.println("Paying 200 pounds");
                    parking = add_parking_fine(200);
                    return player.characters_Player() + " has been fined £200";
                    
                case "Pay �100":
                    System.out.println("Paying 100 pounds");
                    parking = add_parking_fine(100);
                    return player.characters_Player() + " has been fined £100";
                    
                case "Collect fines":
                    System.out.println("Collecting parking fines");
                    player.Player_balance_in(parking);
                    return player.characters_Player() + " has collected from free parking : " + parking;
            }
        }
        return "";
    }

    //new method added to crate spaces 
    public void create_space() throws BiffException, IOException {
        //loading the exel fine
        FileInputStream fs = new FileInputStream("src/propertytycoon/properties.xls");
        Workbook wb = Workbook.getWorkbook(fs);
        // TO get the access to the sheet
        Sheet sh = wb.getSheet("Sheet1");

        // To get the number of rows present in sheet -4 are the rows wich dont have the needed data
        int totalNoOfRows = sh.getRows() - 4;
        //creating an array of spaces 
        space = new ArrayList<Space>();
        //28 rep the number of propirties
        properties = new ArrayList<Properties>();
        // To get the number of columns present in sheet
        int i = 0; // the counter for propirties

        for (int x = 0; x < totalNoOfRows; x++) {

            if (sh.getCell(5, x + 4).getContents().contains("Yes")) {

                //creating a new propirtie with: location , cost, colour and diffrient rent prices for ex 0 will get no house and 1 will get the rent with a house 
                properties.add(new Properties(Integer.parseInt(sh.getCell(0, x + 4).getContents()), sh.getCell(4, x + 4).getContents(), sh.getCell(1, x + 4).getContents(), sh.getCell(3, x + 4).getContents(), Integer.parseInt(sh.getCell(7, x + 4).getContents()), Integer.parseInt(sh.getCell(15, x + 4).getContents())));
                space.add(properties.get(i));

                if (!sh.getCell(8, x + 4).getContents().contains("See notes")) {

                    properties.get(i).rent(Integer.parseInt(sh.getCell(8, x + 4).getContents()));
                    for (int y = 0; y < 5; y++) {
                        properties.get(i).rent(Integer.parseInt(sh.getCell(y + 10, x + 4).getContents()));
                    }
                   System.out.println(space.get(x).getposition() + " " + properties.get(i).getcolour() + " " + properties.get(i).getcost() + " " + properties.get(i).getrent(1) + " " + properties.get(i).getrent(2) + " " + properties.get(i).getrent(3) + " " + properties.get(i).getrent(4) + " " + properties.get(i).getrent(5) + " " + properties.get(i).getrent(6));

                } else if (properties.get(i).getcolour().contains("Station")) {

                    for (int y = 0; y < 4; y++) {
                        properties.get(i).rent(Integer.parseInt(sh.getCell(y + 10, x + 4).getContents()));
                    }
                    System.out.println(space.get(x).getposition() + " " + properties.get(i).getcolour() + " " + properties.get(i).getcost() + " " + properties.get(i).getrent(1) + " " + properties.get(i).getrent(2) + " " + properties.get(i).getrent(3) + " " + properties.get(i).getrent(4));

                } else if (properties.get(i).getcolour().contains("Utilities")) {

                    for (int y = 0; y < 2; y++) {
                        properties.get(i).rent(Integer.parseInt(sh.getCell(y + 10, x + 4).getContents()));
                    }
                    System.out.println(space.get(x).getposition() + " " + properties.get(i).getcolour() + " " + properties.get(i).getcost() + " " + properties.get(i).getrent(1) + " " + properties.get(i).getrent(2));
                }

                i++;
            } else {
                //adding the space position and the action
                space.add(new Space(Integer.parseInt(sh.getCell(0, x + 4).getContents()), sh.getCell(4, x + 4).getContents(), sh.getCell(1, x + 4).getContents()));
                //if the space can be bought then its count as a propirty
            }

        }
        adding_properties();
    }

    public static int add_parking_fine(int x) {
        return parking += x;

    }

    public void adding_properties() {

        for (int x = 0; x < properties.size(); x++) {

            properties_final.get(properties.get(x).getcolour()).add(properties.get(x));
        }
        System.out.println(properties_final.get("Green").size() + "         ----");
    }
    
    public void payBank(int amount){
        bankFunds += amount;
    }

}
