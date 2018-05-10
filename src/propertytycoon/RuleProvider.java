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
    
    public static RuleResult CheckRules(GameModel gameModel)
    {
        RuleResult result = new RuleResult();
        Player currentPlayer = gameModel.getCurrentPlayer();
        int currentPlayerIndex = gameModel.getCurrentPlayerIndex();
        Space currentSpace = gameModel.getCurrentPlayerSpace();
        GameStageType gameStage = gameModel.getGameStage();
        ArrayList<Property> ownedProperties = gameModel.getPlayerProperties();
        Instruction endOfTurnInstruction = new Instruction("", InstructionType.MoveToNextPlayer, 0, 0, 0);
        
        //first check that the player does not have a negative balance or is bankrupt
        if (currentPlayer.getBalance() < 0)
        {
            ArrayList<Property> properties = gameModel.getPlayerProperties();
            if (properties.isEmpty())
            {
                result.setMessage("You are bankrupt!");
                result.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));
                return result;
            }
            else
            {
                //build valid options for clearing the negative balance
                result.setMessage("You have run out of money, so need to sell something");

                //Add all options to sell or motgage various properties
                ArrayList<Property> playerProperties = gameModel.getPlayerProperties(currentPlayerIndex);

                //find out which groups the player owns
                ArrayList<PropertyGroupColour> playerGroups = new ArrayList<>();
                for (Property playerProperty : playerProperties)
                {
                    PropertyGroupColour groupColor = playerProperty.getPropertyGroup();
                    if (!playerGroups.contains(groupColor))
                    {
                        playerGroups.add(groupColor);
                    }
                }

                //find out which houses can be sold
                for (PropertyGroupColour groupColour : playerGroups)
                {
                    ArrayList<Property> playerGroupProperties = gameModel.getPlayerGroupProperties(groupColour, currentPlayerIndex);
                    int maxNumberOfHouses = 0;
                    for (Property groupProperty : playerGroupProperties)
                    {
                        if (groupProperty.getNumberOfHouses() > maxNumberOfHouses)
                            maxNumberOfHouses = groupProperty.getNumberOfHouses();
                    }
                    ArrayList<Property> propertiesWithMaxHouses = new ArrayList<>();
                    for (Property groupProperty : playerGroupProperties)
                    {
                        if (groupProperty.getNumberOfHouses() == maxNumberOfHouses)
                            propertiesWithMaxHouses.add(groupProperty);
                    }
                    for (Property groupProperty : propertiesWithMaxHouses)
                    {
                        int groupPropertyIndex = gameModel.getPropertyIndex(groupProperty);
                        if (groupProperty.getNumberOfHouses() > 0)
                        {
                            result.addOption(new PlayerOption("Sell House on " + groupProperty.getPropertyName() + " for " + groupProperty.getHousePrice(), PlayerOptionType.SellHouse, groupProperty.getPropertyName(), 
                                new Instruction("", InstructionType.SellHouse, 0, 0, groupPropertyIndex)));
                        }
                        else
                        {
                            result.addOption(new PlayerOption("Sell Property " + groupProperty.getPropertyName() + " for " + groupProperty.getPurchasePrice(), PlayerOptionType.SellProperty, groupProperty.getPropertyName(), new Instruction("", InstructionType.SellProperty, 0, 0, 0)));
                            result.addOption(new PlayerOption("Mortgage Property " + groupProperty.getPropertyName(), PlayerOptionType.MortgageProperty, groupProperty.getPropertyName(), new Instruction("", InstructionType.MortgageProperty, 0, 0, 0)));
                        }
                    }
                }

                result.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));
                return result;
            }
        }   
      
        switch (gameStage)
        {
            case StartOfTurn:
                //check whether player is in Jail
                if (currentSpace.getTitle().startsWith("Jail") && !currentPlayer.getIsJustVisiting() && currentPlayer.getTurnsMissed() < currentPlayer.getTurnsToMiss())
                {
                    currentPlayer.setTurnsMissed(currentPlayer.getTurnsMissed() + 1);
                    if (currentPlayer.getBalance() >= 50)
                    {
                        result.setMessage("You are in Jail");
                        result.addOption(new PlayerOption("Pay £50 to get out of jail", PlayerOptionType.PayJailFine, new Instruction("", InstructionType.PayJailFine, 0, 0, 0)));
                        result.addOption(new PlayerOption("Skip turn", PlayerOptionType.SkipTurn, new Instruction("", InstructionType.SkipTurn, 0, 0, 0)));
                        result.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));
                        return result;
                    }
                }
                result.setMessage("Start your turn");
                result.addOption(new PlayerOption("Throw dice", PlayerOptionType.ThrowDice, new Instruction("", InstructionType.SaveDiceValues, throwDice(), throwDice(), 0)));
                result.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));
                break;
                
            case DiceThrown:
               
                //Rule 5: If a player throws a double, then they take another turn. If a player throws another double at the third turn, then they “go to jail”.
                int diceValue1 = currentPlayer.getDiceValue1();
                int diceValue2 = currentPlayer.getDiceValue2();
                boolean wasDoubleThrown = diceValue1 == diceValue2;
                if (wasDoubleThrown)
                {
                    if (currentPlayer.getWasDoubleThrown())
                    {
                        result.setMessage("Two successive doubles thrown - Go to jail");
                        PlayerOption option = new PlayerOption("Go to Jail", PlayerOptionType.GoToJail, new Instruction("", InstructionType.GoToJail, 0, 0, 0));
                        option.addInstruction(new Instruction("", InstructionType.ClearDoubleThrown, 0, 0, 0 ));
                        result.addOption(option);
                        result.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));
                        currentPlayer.setTurnsToMiss(0);
                        return result;
                    }
                }   //move as normal
                int totalSpacesToMove = diceValue1 + diceValue2;
                String message = "Dice values thrown: " + diceValue1 + " and " + diceValue2;
                if (wasDoubleThrown)
                {
                    message += " - Double thrown so get an extra turn";
                }
                result.setMessage(message);
                PlayerOption option = new PlayerOption("Move " + totalSpacesToMove + " spaces", PlayerOptionType.MoveSpaces, new Instruction("", InstructionType.MoveSpaces, totalSpacesToMove, 0, 0 ));
                if (wasDoubleThrown)
                {
                    option.addInstruction(new Instruction("", InstructionType.SetDoubleThrown, 0, 0, 0 ));
                }
                result.addOption(option);
                result.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));
                return result;
            
            case MovedToNewSpace:
                
                if (currentSpace instanceof PropertySpace)
                {
                    Property currentProperty = ((PropertySpace)currentSpace).getProperty();
                    PropertyGroupColour currentPropertyColour = currentProperty.getPropertyGroup();
                    
                    if (currentProperty.getOwnerIndex() != currentPlayerIndex) //player does not yet own the property
                    {
                        if (currentProperty.getOwnerIndex() < 0)  //no-one owns it yet
                        {
                            if (currentPlayer.getBalance() >= currentProperty.getPurchasePrice() 
                                    && currentPlayer.getTimesPlayerHasPassedGo() > 0)  //Rule 8: Players may not purchase property until they have completed one complete circuit of the board by passing the Go space
                            {
                                result.setMessage("You may buy the property " + currentProperty.getPropertyName());
                                result.addOption(new PlayerOption("Buy property", PlayerOptionType.BuyProperty, new Instruction("", InstructionType.BuyProperty, 0, 0, 0)));
                                result.addOption(new PlayerOption("Auction property", PlayerOptionType.AuctionProperty, new Instruction("", InstructionType.AuctionProperty, 0, 0, 0)));
                                if (gameModel.isIsTradingAllowed())
                                {
                                    result.addOption(new PlayerOption("Trade properties", PlayerOptionType.TradeProperties, new Instruction("", InstructionType.TradeProperties, 0, 0, 0)));
                                }
                                result.addOption(new PlayerOption("End Turn", PlayerOptionType.EndOfTurn, new Instruction("", InstructionType.MoveToNextPlayer, 0, 0, 0)));
                                result.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));
                                return result;
                            }
                        }
                        else if (!currentProperty.getIsMortgaged() ////someone else owns it and it's not mortgaged,
                                && !gameModel.isInJail(currentProperty.getOwnerIndex())) //and the owner is not in jail so rent is due
                        {
                            int rentDue = 0;

                            if (currentProperty.getPropertyType() != null)
                            switch (currentProperty.getPropertyType())
                            {
                                case Utility:
                                {
                                    //Rules from spreadsheet:
                                    //If one utility is owned by a player, rent is 4 times value shown on dice.
                                    //If both utilities are owned by a player, rent is 10 time value shown on dice.
                                    ArrayList<Property> utilitiesOwned = gameModel.getPlayerUtilities(currentProperty.getOwnerIndex());
                                    if (utilitiesOwned.size() == 1)
                                    {
                                        int totalDiceValue = currentPlayer.getDiceValue1() + currentPlayer.getDiceValue2();
                                        rentDue = totalDiceValue * 4;
                                    }                                    
                                    else if (utilitiesOwned.size() == 2)
                                    {
                                        int totalDiceValue = currentPlayer.getDiceValue1() + currentPlayer.getDiceValue2();
                                        rentDue = totalDiceValue * 10;
                                    }                                    
                                    break;
                                } 
                                case Station:
                                {
                                    //Rules from spreadsheet:
                                    //If player owns 1 station, rent is £25
                                    //If player owns 2 stations, rent is £50
                                    //If player owns 3 stations, rent is £100
                                    //If player owns 4 stations, rent is £200
                                    ArrayList<Property> stationsOwned = gameModel.getPlayerStations(currentProperty.getOwnerIndex());
                                    if (stationsOwned.size() == 1)
                                    {
                                        rentDue = 25;
                                    }                                    
                                    else if (stationsOwned.size() == 2)
                                    {
                                        rentDue = 50;
                                    }  
                                    else if (stationsOwned.size() == 3)
                                    {
                                        rentDue = 100;
                                    }  
                                    else if (stationsOwned.size() == 4)
                                    {
                                        rentDue = 200;
                                    }  
                                    break;
                                }
                                case Standard:
                                {
                                    //Rule 13: If a property is improved with houses or hotels, then the rent to be paid is as shown on the card.
                                    if (currentProperty.getNumberOfHouses() > 0)
                                    {
                                        rentDue = currentProperty.getRents()[currentProperty.getNumberOfHouses()];
                                    }
                                    else
                                    {
                                        //Rule 12: If a player owns all of the properties in a colour coded group, but the properties are otherwise not developed further with houses and hotels, then the rent due is doubled.
                                        ArrayList<Property> groupPropertiesOwned = gameModel.getPlayerGroupProperties(currentPropertyColour, currentProperty.getOwnerIndex());
                                        ArrayList<Property> totalGroupProperties = gameModel.getGroupProperties(currentPropertyColour);
                                        if (groupPropertiesOwned.size() == totalGroupProperties.size())
                                        {
                                            int totalNumHouses = 0;
                                            for (Property property : groupPropertiesOwned)
                                            {
                                                totalNumHouses += property.getNumberOfHouses();
                                            }
                                            rentDue = totalNumHouses > 0 ? currentProperty.getRents()[0] * 2 : currentProperty.getRents()[0];
                                        }
                                    }
                                    break;
                                }
                                default:
                                    break;
                            }
                            
                            if (rentDue > 0)
                            {
                                result.setMessage("You must pay rent of £" + rentDue);
                                result.addOption(new PlayerOption("Pay Rent", PlayerOptionType.PayRent, new Instruction("Pay Rent", InstructionType.PayRent, rentDue, 0, currentProperty.getOwnerIndex())));
                                result.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));
                                return result;
                            }
                            else
                            {
                                result.setMessage("End of turn - move to next player");
                                result.addOption(new PlayerOption("Move to next player", PlayerOptionType.EndOfTurn, new Instruction("End of turn", InstructionType.MoveToNextPlayer, 0, 0, 0)));
                                if (gameModel.isIsTradingAllowed())
                                {
                                    result.addOption(new PlayerOption("Trade properties", PlayerOptionType.TradeProperties, new Instruction("", InstructionType.TradeProperties, 0, 0, 0)));
                                }
                                result.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));
                                return result;
                            }
                        }
                    }
                    else if (currentProperty.getIsMortgaged())
                    {
                        result.setMessage("Property is Mortgaged");
                        result.addOption(new PlayerOption("Un-mortgage property", PlayerOptionType.UnmortgageProperty, new Instruction("End of turn", InstructionType.UnMortgageProperty, 0, 0, 0)));
                        result.addOption(new PlayerOption("Move to next player", PlayerOptionType.EndOfTurn, new Instruction("End of turn", InstructionType.MoveToNextPlayer, 0, 0, 0)));
                        if (gameModel.isIsTradingAllowed())
                        {
                            result.addOption(new PlayerOption("Trade properties", PlayerOptionType.TradeProperties, new Instruction("", InstructionType.TradeProperties, 0, 0, 0)));
                        }
                        result.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));
                        return result;
                    }
                    else if (currentProperty.getPropertyType() == PropertyType.Standard 
                            && currentPlayer.getBalance() >= currentProperty.getHousePrice() 
                            && !currentProperty.hasHotel()) //player does own the property so can it be improved?
                    {
                        //Rule 17: Houses and hotels may only be purchased for properties where a player owns all of the properties in a particular colour coded group.
                        ArrayList<Property> groupProperties = gameModel.getGroupProperties(currentPropertyColour);
                        ArrayList<Property> playerGroupProperties = gameModel.getPlayerGroupProperties(currentPropertyColour, currentPlayerIndex);
                        if (groupProperties.size() == playerGroupProperties.size())
                        {
                            //player owns all of the properties in the group
                            //Rule 20: Where a coloured set of properties is owned and developed by a player, there may never be a difference of more than 1 house between the properties in that set
                            int numHousesOnCurrentProperty = currentProperty.getNumberOfHouses();
                            boolean canImprove = true;
                            for (Property property : playerGroupProperties)
                            {
                                if (numHousesOnCurrentProperty > property.getNumberOfHouses())
                                {
                                    //can't improve, so nothing else to do. Move to next player
                                    canImprove = false;
                                }
                            }
                            if (canImprove)
                            {
                                result.setMessage("Property can be improved: " + currentProperty.getPropertyName());
                                result.addOption(new PlayerOption("Improve property", PlayerOptionType.ImproveProperty, new Instruction("", InstructionType.ImproveProperty, 0, 0, 0)));
                                result.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));
                                return result;
                            }
                        }
                    }
                    result.setMessage("End of turn - move to next player");
                    result.addOption(new PlayerOption("Move to next player", PlayerOptionType.EndOfTurn, new Instruction("End of turn", InstructionType.MoveToNextPlayer, 0, 0, 0)));
                    if (gameModel.isIsTradingAllowed())
                    {
                        result.addOption(new PlayerOption("Trade properties", PlayerOptionType.TradeProperties, new Instruction("", InstructionType.TradeProperties, 0, 0, 0)));
                    }
                    result.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));
                    return result;
                }
                else if (currentSpace instanceof CardSpace)
                {
                    CardType cardType = ((CardSpace)currentSpace).getCardType();
                    ArrayList<Card> cards = null;
                    if (cardType == CardType.PotLuck && !currentPlayer.getSelectOpportunityKnocks())
                    {
                        cards = gameModel.getPotLuckCards();
                    }
                    else
                    {
                        cards = gameModel.getOpportunityCards();
                        currentPlayer.setSelectOpportunityKnocks(false);
                    }
                    Card card = cards.get(0);
                    cards.remove(0);
                    cards.add(card);  //add to end of pack
                    
                    result.setMessage("Card Instruction: " + card.getInstruction().getMessage());
                    if (card.getInstruction().getInstructionType() == InstructionType.PayFineOrOpportunityKnocks)
                    {
                        result.addOption(new PlayerOption("Pay £" + card.getInstruction().getAmount1() + " Fine",PlayerOptionType.PayFine, card.getInstruction().getAmount1(), card.getInstruction()));
                        result.addOption(new PlayerOption("Select Opportunity Knocks", PlayerOptionType.SelectOpportunityKnocks, 0, new Instruction("", InstructionType.SelectOpportunityKnocks, 0, 0, 0)));
                        result.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));
                    }
                    else
                    {
                        result.addOption(new PlayerOption("Follow card instruction", PlayerOptionType.EndOfTurn, card.getInstruction()));
                        result.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));
                    }
                    return result;

                }
                else if (currentSpace instanceof InstructionSpace)
                {
                    InstructionSpace instructionSpace = (InstructionSpace)currentSpace;
                    result.setMessage(instructionSpace.getInstruction().getMessage());
                    result.addOption(new PlayerOption(instructionSpace.getTitle(), PlayerOptionType.FollowInstruction, 0, instructionSpace.getInstruction()));
                    result.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));
                }
                else
                {
                    result.setMessage("End of turn - move to next player");
                    result.addOption(new PlayerOption("Move to next player", PlayerOptionType.EndOfTurn, new Instruction("End of turn", InstructionType.MoveToNextPlayer, 0, 0, 0)));
                    return result;
                }
        
            case EndOfTurn:
            {
                if (gameModel.isIsTradingAllowed())
                {
                    result.addOption(new PlayerOption("Trade properties", PlayerOptionType.TradeProperties, new Instruction("", InstructionType.TradeProperties, 0, 0, 0)));
                }
                result.addOption(new PlayerOption("End Turn", PlayerOptionType.EndOfTurn, new Instruction("", InstructionType.MoveToNextPlayer, 0, 0, 0)));
                result.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));

                break;
            }
            default:
                break;
        }
        
        return result;
    }



}


