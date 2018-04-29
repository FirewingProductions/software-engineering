/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.util.ArrayList;

public class UserChoiceRequest
{
    private ArrayList<PlayerOption> _options;

    public ArrayList<PlayerOption> getOptions()
    {
        return _options;
    }

    public void setOptions(ArrayList<PlayerOption> _options)
    {
        this._options = _options;
    }

    public UserChoiceRequest(ArrayList<PlayerOption> options)
    {
        this._options = options;
    }

    public UserChoiceRequest()
    {
    }
    
}
