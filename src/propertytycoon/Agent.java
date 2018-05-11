/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.util.ArrayList;
import java.util.Random;
import propertytycoon.Player.PlayerOption;
import propertytycoon.Player.PlayerOptionType;
import propertytycoon.Property.PropertySpace;
import propertytycoon.UserChoiceRequest.UserChoiceResponse;

/**
 * The automated agent that plays the game for any player set to 'Auto'
 */
public class Agent
{
    private static final Random _random = new Random(System.currentTimeMillis());
    private final GameModel _gameModel;

    /**
     * Constructor
     * @param _gameModel
     */
    public Agent(GameModel _gameModel)
    {
        this._gameModel = _gameModel;
    }

    /**
     * Method that chooses from the various options available to the player
     * at each step of the game
     * @param request contains a set of options from which the player must choose
     * @return object containing the selected option
     */
    public UserChoiceResponse makeChoice(UserChoiceRequest request)
    {
        ArrayList<PlayerOption> options = request.getOptions();
        int currentPlayerIndex = _gameModel.getCurrentPlayerIndex();
        Player currentPlayer = _gameModel.getPlayers().get(currentPlayerIndex);
        
        //never choose to leave the game!
        ArrayList<PlayerOption> validOptions = new ArrayList<PlayerOption>();
        if (options.size() > 0)
        {
            for (PlayerOption option : options)
            {
                if (option.getOptionType() != PlayerOptionType.LeaveGame)
                {
                    validOptions.add(option);
                }
            }
        }
        
        int selectedOptionIndex = 0;
        if (validOptions.size() > 0)
        {
            //by default make simple random selection
            selectedOptionIndex = _random.nextInt(validOptions.size());

            for (int optionIndex = 0; optionIndex < validOptions.size(); optionIndex++)
            {
                PlayerOption option = validOptions.get(optionIndex);
                if (option.getOptionType() == PlayerOptionType.BuyProperty)
                {
                    Space space = _gameModel.getCurrentPlayerSpace();
                    if (space instanceof PropertySpace)
                    {
                        Property property = ((PropertySpace)space).getProperty();
                        if (currentPlayer.getBalance() - property.getPurchasePrice() >= 500)
                        {
                            selectedOptionIndex = optionIndex;
                            break;
                        }
                        else
                        {
                            validOptions.remove(optionIndex);
                            selectedOptionIndex = _random.nextInt(validOptions.size());
                        }
                    }
                }
            }
        }
        
        PlayerOption chosenOption = validOptions.get(selectedOptionIndex);
           
        return new UserChoiceResponse(chosenOption);
    }
}
