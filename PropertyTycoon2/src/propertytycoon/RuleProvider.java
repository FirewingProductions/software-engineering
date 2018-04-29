/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

//This class checks rules against the GameModel and returns instructions and options.

import java.util.ArrayList;
import java.util.Random;

//It should NEVER modify the model though. That is the job of the GameController
public class RuleProvider
{
    private static Random _random = new Random(System.currentTimeMillis());
    
    private static int throwDice()
    {
        return _random.nextInt(6) + 1;
    }
    
    public static RuleResult CheckRules(PlayerOption selectedOption, GameModel gameModel)
    {
        RuleResult result = new RuleResult();
        Player currentPlayer = gameModel.getCurrentPlayer();
        int currentPlayerIndex = gameModel.getCurrentPlayerIndex();
        Space currentSpace = gameModel.getCurrentPlayerSpace();
        GameStageType gameStage = gameModel.getGameStage();
        ArrayList<Property> ownedProperties = gameModel.getPlayerProperties();
        
        //convert the player's selected option to instructions
        if (selectedOption != null)
        {
            result.addInstructions(processSelectedOption(selectedOption, gameModel));
            return result;
        }
        
        switch (gameStage)
        {
            case StartOfTurn:
                //first check that the player is not bankrupt
                if (currentPlayer.getBalance() < 0)
                {
                    ArrayList<Property> properties = gameModel.getPlayerProperties();
                    if (properties.isEmpty())
                    {
                        result.addInstruction(new Instruction("Player is bankrupt!", InstructionType.SetPlayerToBankrupt, 0, 0, 0));
                    }
                    else
                    {
                        //build valid options for clearing the negative balance
                        for (Property property : properties)
                        {
                            //TODO: Add all options to sell or motgage various properties
                            result.addOption(new PlayerOption("", PlayerOptionType.SellProperty, property.getPropertyName()));
                        }
                    }
                }   //check whether player is in Jail
                if (currentSpace.getTitle().startsWith("Jail") && !currentPlayer.getIsJustVisiting() && currentPlayer.getTurnsMissed() < currentPlayer.getTurnsToMiss())
                {
                    currentPlayer.setTurnsMissed(currentPlayer.getTurnsMissed() + 1);
                    if (currentPlayer.getBalance() >= 50)
                    {
                        result.addOption(new PlayerOption("Pay £50 to get out of jail", PlayerOptionType.PayJailFine));
                        return result;
                    }
                }
                result.addOption(new PlayerOption("Throw dice", PlayerOptionType.ThrowDice));
                break;
                
            case DiceThrown:
               
                //Rule 5: If a player throws a double, then they take another turn. If a player throws another double at the third turn, then they “go to jail”.
                int diceValue1 = currentPlayer.getDiceValue1();
                int diceValue2 = currentPlayer.getDiceValue2();
                if (diceValue1 == diceValue2)
                {
                    if (currentPlayer.getWasDoubleThrown())
                    {
                        result.addInstruction(new Instruction("Two successive doubles thrown - Go to jail", InstructionType.GoToJail, 0, 0, 0));
                        result.addInstruction(new Instruction("Clear double-thrown flag", InstructionType.ClearDoubleThrown, 0, 0, 0 ));
                        currentPlayer.setTurnsToMiss(0);
                        return result;
                    }
                    else
                    {
                        result.addInstruction(new Instruction("", InstructionType.SetDoubleThrown, 0, 0, 0 ));
                    }
                }   //move as normal
                result.addInstruction(new Instruction("Move to new space", InstructionType.MoveSpaces, diceValue1 + diceValue2, 0, 0 ));
                break;
            
            case MovedToNewSpace:
                
                if (currentSpace instanceof PropertySpace)
                {
                    Property currentProperty = ((PropertySpace)currentSpace).getProperty();
                    PropertyGroupColour colour = currentProperty.getPropertyGroup();
                    
                    if (currentProperty.getOwnerIndex() != currentPlayerIndex)
                    {
                        //player does not yet own the property
                        if (currentPlayer.getBalance() >= currentProperty.getPurchasePrice())
                        {
                            result.addOption(new PlayerOption("Buy property", PlayerOptionType.BuyProperty));
                            result.addOption(new PlayerOption("Auction property", PlayerOptionType.AuctionProperty));
                        }
                    }
                    else if (currentPlayer.getBalance() >= currentProperty.getHousePrice() && !currentProperty.hasHotel()) //player does own the property so can it be improved?
                    {
                        //Rule 17: Houses and hotels may only be purchased for properties where a player owns all of the properties in a particular colour coded group.
                        ArrayList<Property> groupProperties = gameModel.getGroupProperties(colour);
                        ArrayList<Property> playerGroupProperties = gameModel.getPlayerGroupProperties(colour, currentPlayerIndex);
                        if (groupProperties.size() == playerGroupProperties.size())
                        {
                            //player owns all of the properties in the group
                            //Rule 20: Where a coloured set of properties is owned and developed by a player, there may never be a difference of more than 1 house between the properties in that set
                            //(this means that all properties must first have the same number of houses)
                            int numHousesOnCurrentProperty = currentProperty.getNumberOfHouses();
                            for (Property property : playerGroupProperties)
                            {
                                if (numHousesOnCurrentProperty > property.getNumberOfHouses())
                                {
                                    //can't improve, so nothing else to do. Move to next player
                                    result.addInstruction(new Instruction("Two successive doubles thrown - Go to jail", InstructionType.GoToJail, 0, 0, 0));
                                }
                            }
                            result.addOption(new PlayerOption("Improve property", PlayerOptionType.ImproveProperty));
                        }
                    }
                }
                else if (currentSpace instanceof CardSpace)
                {
                    CardType cardType = ((CardSpace)currentSpace).getCardType();
                    ArrayList<Card> cards = null;
                    if (cardType == CardType.PotLuck)
                    {
                        cards = gameModel.getPotLuckCards();
                    }
                    else
                    {
                        cards = gameModel.getOpportunityCards();
                    }
                    Card card = gameModel.getPotLuckCards().get(0);
                    cards.remove(0);
                    cards.add(card);  //add to end of pack
                    
                    result.addInstruction(card.getInstruction());
                }
                else
                {
                    result.addInstruction(new Instruction("End of turn", InstructionType.MoveToNextPlayer, 0, 0, 0));
                }
                break;
        
            default:
                break;
        }
        
        return result;
    }

    private static ArrayList<Instruction> processSelectedOption(PlayerOption option, GameModel game)
    {
        Player currentPlayer = game.getCurrentPlayer();
        ArrayList<Instruction> instructions = new ArrayList<Instruction>();
        
        switch (option.getOptionType())
        {
            case ThrowDice:
            {
                instructions.add(new Instruction("", InstructionType.SaveDiceValues, throwDice(), throwDice(), 0));
                break;
            }
            case BuyProperty:
            {
                instructions.add(new Instruction("", InstructionType.BuyProperty, 0, 0, 0));
                break;
            }
            case AuctionProperty:
            {
                instructions.add(new Instruction("", InstructionType.AuctionProperty, 0, 0, 0));
                break;
            }            
            case ImproveProperty:
            {
                instructions.add(new Instruction("", InstructionType.ImproveProperty, 0, 0, 0));
                break;
            }
            case PayJailFine:
            {
                instructions.add(new Instruction("", InstructionType.PayJailFine, 0, 0, 0));
                break;
            }
            case LeaveGame:
            {
                instructions.add(new Instruction("", InstructionType.LeaveGame, 0, 0, 0));
                break;
            }
            case SellHouse: 
            {
                instructions.add(new Instruction("", InstructionType.SellHouse, 0, 0, 0));
                break;
            }
            case SellProperty:
            {
                instructions.add(new Instruction("", InstructionType.SellProperty, 0, 0, 0));
                break;
            }
            case MortgageProperty:
            {
                instructions.add(new Instruction("", InstructionType.MortgageProperty, 0, 0, 0));
                break;
            }
        }
        return instructions;
    }

}


