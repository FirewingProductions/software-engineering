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
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author shitaab
 */
public class Cards {

    private LinkedList<String> pot_luck_card_data;              //list of cards
    private LinkedList<String> Opportunity_knocks_card_data;   //list of cards
    private Player[] player;
    ArrayList<Space> space;

    public Cards(ArrayList<Space> space) {
        this.space = space;
        pot_luck_card_data = new LinkedList<String>();
        Opportunity_knocks_card_data = new LinkedList<String>();
        create_cards();
    }

    private void create_cards() {

        try {
            BufferedReader in = new BufferedReader(new FileReader("src/propertytycoon/pot_luck_card.text"));
            BufferedReader in1 = new BufferedReader(new FileReader("src/propertytycoon/Opportunity_knocks_card.text"));

            String str;
            String str1;

            while ((str = in.readLine()) != null) {

                pot_luck_card_data.add(str);
            }

            while ((str1 = in1.readLine()) != null) {

                Opportunity_knocks_card_data.add(str1);

            }

        } catch (IOException ex) {
        }

    }

    public void shuffle_cards(LinkedList s) {

        Collections.shuffle(s); // this method will shuffel the cards in the linkedlist entered 

    }

    public void activate_card(String s, Player p, Player p1[]) {

        if (s.contains("Advance to ") || s.contains("Go to") || s.contains("go")) {
            System.out.println(p.Player_balance());

            String[] v = s.split("to ");

            String position = v[1];

            System.out.println(position);
            for (int d = 0; d < space.size(); d++) {

                if (position.contains(space.get(d).space_name())) {

                    System.out.println(space.get(d).space_name());
                    if (s.contains(". If you pass GO, collect £200")) {
                        p.Player_advance(space.get(d).getposition(), true);
                        System.out.println("true");

                    } else {
                        p.Player_advance(space.get(d).getposition(), false);

                    }
                }

            }
            System.out.println(p.Player_balance());

        } else if (s.contains("Go back") || s.contains("go back")) {
            if (s.contains("spaces")) {
                p.Player_move(-Integer.parseInt(get_value_of_Card(s)), false);
                System.out.println(p.Player_position());

            } else {

                String[] v = s.split("to ");
                String position = v[1];

                System.out.println(position);
                for (int d = 0; d < space.size(); d++) {

                    if (position.contains(space.get(d).space_name())) {

                        p.Player_advance(space.get(d).getposition(), false);

                    }

                }

            }

            System.out.println(p.Player_balance());

        } else {
            if (s.contains("Pay") || s.contains("pay")) {

                System.out.println(p.Player_balance());
                String[] v = s.split("£");
                System.out.println(v[1]);
                p.Player_balance_de(Integer.parseInt(get_value_of_Card(v[1])));
                System.out.println(p.Player_balance());

            } else if (s.contains("Fine") || s.contains("Fined")) {

                System.out.println(p.Player_balance());
                String[] v = s.split("£");
                System.out.println(v[1]);
                p.Player_balance_de(Integer.parseInt(get_value_of_Card(v[1])));
                Game.add_parking_fine(Integer.parseInt(get_value_of_Card(v[1])));
                System.out.println(p.Player_balance());

            } else if (s.contains("Collect") || s.contains("inherit") || s.contains("collect")) {

                if (s.contains("from each player")) {

                    System.out.println(p.Player_balance());
                    String[] v = s.split("£");
                    p.Player_balance_in(Integer.parseInt(get_value_of_Card(v[1])));
                    for (int x = 0; x < p1.length; x++) {

                        if (p1[x].player_characters != p.player_characters) {
                            p1[x].Player_balance_de(Integer.parseInt(get_value_of_Card(v[1])));
                            System.out.println(p1[x].Player_balance());

                        }
                    }

                } else {
                    System.out.println(p.Player_balance());
                    String[] v = s.split("£");
                    System.out.println(v[1]);
                    p.Player_balance_in(Integer.parseInt(get_value_of_Card(v[1])));
                    System.out.println(p.Player_balance());

                }
            } else if (s.contains("You are assessed for repairs")) {

                System.out.println(p.Player_balance());
                String[] v = s.split("£");
                p.Player_balance_de(Integer.parseInt(get_value_of_Card(v[1])) * p.houses);
                p.Player_balance_de(Integer.parseInt(get_value_of_Card(v[2])) * p.hotels);
                System.out.println(p.Player_balance());

            }

        }

    }

    private String draw_card(LinkedList s) {
// we use number 1 to draw pot_luck_card_data and 2 for Opportunity_knocks_card_data 
        System.out.println(s.getFirst());  // print the drawn card
        String drawncard = s.getFirst().toString();
        s.addLast(s.getFirst());
        s.removeFirst();
        //put the card to the the end of the deck
        
        return drawncard;
    }

    public String take_card(int choice){
        if(choice == 1){
            return draw_card(pot_luck_card_data);
        }else{
            return draw_card(Opportunity_knocks_card_data);
        }
    }
    
    public LinkedList return_pot_luck_card_data() {
        return pot_luck_card_data;

    }

    public LinkedList return_Opportunity_knocks_card_data() {
        return Opportunity_knocks_card_data;

    }

    public int return_pot_luck_card_size() {
        return pot_luck_card_data.size();

    }

    public int return_Opportunity_knocks_card_size() {
        return Opportunity_knocks_card_data.size();

    }

    private String get_value_of_Card(String v) {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < v.length(); i++) {
            char c = v.charAt(i);
            if (Character.isDigit(c)) {
                builder.append(c);
            }
        }

        return builder.toString();
    }

}
