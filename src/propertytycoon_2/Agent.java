/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon_2;

import java.util.ArrayList;

public class Agent implements IUserChoice
{
    public UserChoiceResponse MakeChoice(UserChoiceRequest request)
    {
        ArrayList<PlayerOptionResponse> reponses = new ArrayList<PlayerOptionResponse>();
            
        UserChoiceResponse response = new UserChoiceResponse(reponses);
        
        return response;
    }
}
