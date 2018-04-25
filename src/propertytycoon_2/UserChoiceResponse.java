/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon_2;

import java.util.ArrayList;

public class UserChoiceResponse
{
    
    private ArrayList<PlayerOptionResponse> _reponses;

    public UserChoiceResponse(ArrayList<PlayerOptionResponse> _reponses)
    {
        this._reponses = _reponses;
    }
    
    public ArrayList<PlayerOptionResponse> getReponses()
    {
        return _reponses;
    }

    public void setReponses(ArrayList<PlayerOptionResponse> _reponses)
    {
        this._reponses = _reponses;
    }

}
