/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon_2;

//This class checks rules against the GameModel and returns instructions and options.

import java.util.ArrayList;

//It should NEVER modify the model though. That is the job of the GameController
public class RuleProvider
{
    public static RuleResult checkAtStart(GameModel gameModel)
    {
        RuleResult result = new RuleResult();

        Player currentPlayer = gameModel.getCurrentPlayer();
        
        //first check that the player is not bankrupt
        if (currentPlayer.getBalance() < 0)
        {
            ArrayList<Property> properties = gameModel.GetPlayerProperties();
            if (properties.size() == 0)
            {
                result.addInstruction(new Instruction("Player is bankrupt!", InstructionType.SetPlayerToBankrupt, 0, 0, 0));
            }
            else
            {
                //build valid options for clearing the negative balance
                for (Property property : properties)
                {
                    result.addOption(new PlayerOption( PlayerOptionType.SellProperty, property.getPropertyName()));
                }
            }
        }
        
        if (currentPlayer.getIsJustVisiting())
        {
            if (currentPlayer.getHasGetOutOfJailFreeCardFromPotLuck())
            {
                //Rule 27: If a player has a “get out of jail free” card, then they place the card at the bottom of the “pot luck” or “opportunity knocks” pile as appropriate, the player token is moved to “just visiting” and the players turn ends. The player takes a normal turn in the next round
                result.addInstruction(new Instruction("", InstructionType.GoToJustVisiting, 0, 0, 0));
                result.addInstruction(new Instruction( "", InstructionType.ReturnGetOutOfJailFreeCard, 0, 0, 0));
                return result;
            }
            else if (currentPlayer.getBalance() >= 50)
            {
                result.addOption(new PlayerOption( PlayerOptionType.PayJailFine));
                return result;
            }
        }

        result.addOption(new PlayerOption( PlayerOptionType.ThrowDice));
        return result;
    }

    public static RuleResult checkAfterDiceThrown(GameModel game)
    {
        RuleResult result = new RuleResult();
        
        //Rule 5: If a player throws a double, then they take another turn. If a player throws another double at the third turn, then they “go to jail”.
        Player currentPlayer = game.getCurrentPlayer();
        int diceValue1 = currentPlayer.getDiceValue1();
        int diceValue2 = currentPlayer.getDiceValue2();
        if (diceValue1 == diceValue2)
        {
            if (currentPlayer.getWasDoubleThrown())
            {
                if (currentPlayer.getHasGetOutOfJailFreeCardFromPotLuck())
                {
                    //Rule 27: If a player has a “get out of jail free” card, then they place the card at the bottom of the “pot luck” or “opportunity knocks” pile as appropriate, the player token is moved to “just visiting” and the players turn ends. The player takes a normal turn in the next round
                    result.addInstruction(new Instruction("", InstructionType.GoToJustVisiting, 0, 0, 0));
                    result.addInstruction(new Instruction("", InstructionType.ReturnGetOutOfJailFreeCard, 0, 0, 0));
                }
                else
                {
                    result.addInstruction(new Instruction("Two successive doubles thrown - Go to jail", InstructionType.GoDirectTo, 0, 0, 10));
                }
                result.addInstruction(new Instruction("", InstructionType.ClearDoubleThrown, 0, 0, 0 ));
                return result;
            }        
            else
            {
                result.addInstruction(new Instruction("", InstructionType.SetDoubleThrown, 0, 0, 0 ));
            }
        }

        //move as normal
        result.addInstruction(new Instruction("Move to new space", InstructionType.MoveSpaces, diceValue1 + diceValue2, 0, 0 ));
        return result;
    }

    public static RuleResult checkPostMove(GameModel game)
    {
        RuleResult result = new RuleResult();
        
        
        return result;
    }

}

//Rule 17: Houses and hotels may only be purchased for properties where a player owns all of the properties in a particular colour coded group. 

