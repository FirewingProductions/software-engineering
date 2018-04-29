/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.util.ArrayList;
import java.util.Random;

public class Agent
{
    private static Random _random = new Random(System.currentTimeMillis());

    public UserChoiceResponse MakeChoice(UserChoiceRequest request)
    {
        ArrayList<PlayerOption> options = request.getOptions();
        
        //initially make simple random selection
        int optionIndex = 0;
        if (options.size() > 0)
        {
            optionIndex = _random.nextInt(options.size());
        }
        
        PlayerOption chosenOption = options.get(optionIndex);
           
        return new UserChoiceResponse(chosenOption);
    }
}
