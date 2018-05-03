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
public class Properties extends Space {

    private int cost;
    private ArrayList<Integer> rent;
    private String colour;
    private int cost_house;
    private Player owned;
    private Boolean is_owned = false;
    private int pos;
    private Boolean mortgage;
    private int house;

    public Properties(int position, String action, String space_name, String colour, int cost, int cost_house) {
        super(position, action, space_name);
        rent = new ArrayList<Integer>();
        this.pos = position;
        this.colour = colour;
        this.cost = cost;
        this.cost_house = cost_house;

    }

    public void rent(int x) {

        rent.add(x);
    }

    public int getrent(int x, ArrayList<Properties> prop) {

        if (owned != null && owned.player_has_set(colour, prop) && house == 0) {
            System.out.println("Returning rent for colour set");
            return (rent.get(x - 1) * 2);
        } else {
            return rent.get(x - 1);
        }

    }

    public int getrent(int x) {

        return rent.get(x - 1);

    }

    public int getcost() {

        return cost;

    }

    public int getcost_house() {

        return cost_house;

    }

    public String getcolour() {

        return colour;

    }

    public boolean property_is_owned() {

        return is_owned;

    }

    public Player property_owener() {

        return owned;

    }

    public String property_buy(Player p) {

        if (p.Player_balance() >= cost && is_owned == false) {
            is_owned = true;
            owned = p;
            p.Player_balance_de(cost);
            return "Property bought";
        }
        return "Failed to buy property";
    }

    public void property_buy(Player p, int price) {

        if (p.Player_balance() >= price && is_owned == false) {
            is_owned = true;
            owned = p;
            p.Player_balance_de(price);

        }

    }

    public String property_sell(Player p) {
        if (house == 0) {
            p.Player_balance_in(cost);
            owned = null;
            is_owned = false;
            p.properties.get(colour).remove(this);
            return "Property sold";
        } else {
            if (house > 4) {
                //selling hotel piece
                p.Player_balance_in(cost_house * 5);
                house--;
                return "Hotel sold";
            } else {
                //selling house piece
                p.Player_balance_in(cost_house);
                house--;
                return "House sold";
            }
        }
    }

    public String buy_house(Player p) {

        if (!colour.contains("Station") || !colour.contains("Utilities")) {

            if (house < 4) {
                
                if (p.Player_balance() >= cost_house) {
                    p.Player_balance_de(cost_house);
                    house++;
                    return("House bought");
                } else {
                    return "Not enough money";
                }

            } else {
                return("Failed to buy house, too many");

            }
        } else {
            return("Cant buy houses for these properties");
        }
    }

    public String buy_hotel(Player p) {
        if (!colour.contains("Station") || !colour.contains("Utilities")) {

            if (house >= 4) {
                if (p.Player_balance() >= cost_house) {
                    
                    p.Player_balance_de(cost_house * 5);
                    house++;
                    return ("Hotel bought");
                } else {
                    return("Not enough money");
                }

            } else {
                return("Failed to buy house, too many");

            }
        } else {
            return("Cant buy houses for these properties");
        }
    }

    public void mortgage(Player p) {

        if (p == owned && mortgage == false) {
            p.Player_balance_in(cost / 2);
            mortgage = true;
        }

    }

    public void un_mortgage(Player p) {
        if (p == owned && mortgage == true) {
            p.Player_balance_de(cost / 2);
            mortgage = false;

        }
    }

    public int returnPos() {
        return pos;
    }

    public int return_house_amount() {
        return house;
    }
}
