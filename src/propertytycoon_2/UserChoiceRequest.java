/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon_2;

import java.util.ArrayList;

public class UserChoiceRequest
{
    private ArrayList<PlayerOption> _options;

    public UserChoiceRequest(ArrayList<PlayerOption> options)
    {
        this._options = options;
    }

    public UserChoiceRequest()
    {
    }
    
}
