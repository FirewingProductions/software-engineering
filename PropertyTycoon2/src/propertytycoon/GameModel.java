/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import jxl.write.DateTime;

public class GameModel 
{
    private static final int GO_BENEFIT = 200;
    
    private int _currentPlayerIndex;
    private GameStageType _gameStage;    
    private ArrayList<Property> _properties;
    private ArrayList<Player> _players;
    private ArrayList<Space> _spaces;
    private ArrayList<Card> _potLuckCards;
    private ArrayList<Card> _opportunityCards;
    
    private int _finesTotal;
    private int _jailSpaceIndex = 10;
    
   
    public GameModel()
    {
        _gameStage = GameStageType.StartOfTurn;
        
        _properties = new ArrayList<Property>();
        _players = new ArrayList<Player>();
        _spaces = new ArrayList<Space>();
        _potLuckCards = new ArrayList<Card>();
        _opportunityCards = new ArrayList<Card>();
    }
    
    public GameStageType getGameStage()
    {
        return _gameStage;
    }

    public void setGameStage(GameStageType _gameStage)
    {
        this._gameStage = _gameStage;
    }
    
    public int getJailSpaceIndex()
    {
        return _jailSpaceIndex;
    }

    public void setJailSpaceIndex(int _jailSpaceIndex)
    {
        this._jailSpaceIndex = _jailSpaceIndex;
    }    
    
    public int getCurrentPlayerIndex()
    {
        return _currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int _currentPlayerIndex)
    {
        this._currentPlayerIndex = _currentPlayerIndex;
    }

    public ArrayList<Property> getProperties()
    {
        return _properties;
    }

    public void setProperties(ArrayList<Property> _properties)
    {
        this._properties = _properties;
    }

    public ArrayList<Player> getPlayers()
    {
        return _players;
    }

    public void setPlayers(ArrayList<Player> _players)
    {
        this._players = _players;
    }

    public ArrayList<Space> getSpaces()
    {
        return _spaces;
    }

    public void setSpaces(ArrayList<Space> _spaces)
    {
        this._spaces = _spaces;
    }

    public ArrayList<Card> getPotLuckCards()
    {
        return _potLuckCards;
    }

    public void setPotLuckCards(ArrayList<Card> _potLuckCards)
    {
        this._potLuckCards = _potLuckCards;
    }

    public ArrayList<Card> getOpportunityCards()
    {
        return _opportunityCards;
    }

    public void setOpportunityCards(ArrayList<Card> _opportunityCards)
    {
        this._opportunityCards = _opportunityCards;
    }

    public int getFinesTotal()
    {
        return _finesTotal;
    }

    public void setFinesTotal(int _finesTotal)
    {
        this._finesTotal = _finesTotal;
    }

    public Boolean isGameOver()
    {
        int numActivePlayers = 0;
        for (Player player : _players)
        {
            if (player.getIsActive())
            {
                numActivePlayers++;
            }
        }
        return numActivePlayers < 2;
    }
    
    public void setNextPlayer()
    {
        //move to next active player
        if (!isGameOver())
        {
            ++_currentPlayerIndex;
            while (!_players.get(_currentPlayerIndex).getIsActive())
            {
                _currentPlayerIndex++;

            }
            _gameStage = GameStageType.StartOfTurn;
        }
    }
    
    public void Initialise(int numHumanPlayers, int numAutoPlayers)
    {
        int playerIndex = 0;
        for (; playerIndex < numHumanPlayers; ++playerIndex)
        {
            _players.add(new Player(PlayerTokenType.values()[playerIndex], true, false, 1500));
        }
        playerIndex = 0;
        for (;playerIndex < numAutoPlayers; ++playerIndex)
        {
            _players.add(new Player(PlayerTokenType.values()[playerIndex], true, true, 1500));
        }
        _currentPlayerIndex = 0;
        _gameStage = GameStageType.StartOfTurn;
    }

    public Player getCurrentPlayer()
    {
        return _players.get(_currentPlayerIndex);
    }

    public void returnJailCard(Player player)
    {
        if (player.getHasGetOutOfJailFreeCardFromPotLuck())
        {
            player.setHasGetOutOfJailFreeCardFromPotLuck(false);
            _potLuckCards.add(new Card(new Instruction("Go to jail. Do not pass GO, do not collect £200", InstructionType.GoToJail, 0, 0, 0)));
        }
        else if (player.getHasGetOutOfJailFreeCardFromOpportunity())
        {
            player.setHasGetOutOfJailFreeCardFromOpportunity(false);
            _opportunityCards.add(new Card(new Instruction("Go to jail. Do not pass GO, do not collect £200", InstructionType.GoToJail, 0, 0, 0)));
        }
    }
    
    public Space getCurrentPlayerSpace()
    {
        int spaceIndex = getCurrentPlayer().getCurrentSpaceIndex();
        return _spaces.get(spaceIndex);
    }
    
    public ArrayList<PlayerOption> executeInstruction(Instruction instruction)
    {
        return executeInstruction(instruction, _currentPlayerIndex);
    }
        
    public ArrayList<PlayerOption> executeInstruction(Instruction instruction, int currentPlayerIndex)
    {
        ArrayList<PlayerOption> options = new ArrayList<PlayerOption>();
        Player currentPlayer = _players.get(currentPlayerIndex);
        int currentSpaceIndex = currentPlayer.getCurrentSpaceIndex();
        Space currentSpace = _spaces.get(currentSpaceIndex);
        
        switch (instruction.getInstructionType())
        {
            case MoveSpaces:
            {
                int amount1 = instruction.getAmount1();
                currentSpaceIndex += amount1;
                if (currentSpaceIndex >= _spaces.size())
                {
                    currentSpaceIndex -= _spaces.size();
                    currentPlayer.setBalance(currentPlayer.getBalance() + GO_BENEFIT);
                }
                _gameStage = GameStageType.MovedToNewSpace;
                break;
            }
            case PayFine:
            {
                int amount1 = instruction.getAmount1();
                currentPlayer.setBalance(currentPlayer.getBalance() - amount1);
                _finesTotal += amount1;
                
                //allow a player to have a temporary negative balance that must be fixed at the start of his next turn
                currentPlayer.setIsTurnComplete(true);
                break;
            }
            case PayBank:
            {
                int amount1 = instruction.getAmount1();
                currentPlayer.setBalance(currentPlayer.getBalance() - amount1);
                
                //allow a player to have a temporary negative balance that must be fixed at the start of his next turn
                currentPlayer.setIsTurnComplete(true);
                break;
            }
            case PayFineOrOpportunityKnocks:
            {
                options.add(new PlayerOption("",PlayerOptionType.PayFine, instruction.getAmount1()));
                options.add(new PlayerOption("",PlayerOptionType.SelectOpportunityKnocks, 0));
                break;
            }
            case ReceiveMoney:
            {
                int amount1 = instruction.getAmount1();
                currentPlayer.setBalance(currentPlayer.getBalance() + amount1);
                currentPlayer.setIsTurnComplete(true);
                break;
            }
            case ReceiveMoneyFromEachPlayer: 
            {
                int amount1 = instruction.getAmount1();
                int totalAmount = 0;
                for (Player player : _players)
                {
                    if (player.getIsActive() && player.getToken() != currentPlayer.getToken())
                    {
                        totalAmount += amount1;
                        player.setBalance(player.getBalance() - amount1);
                    }
                }
                currentPlayer.setBalance(currentPlayer.getBalance() + totalAmount);
                currentPlayer.setIsTurnComplete(true);
                break;
            }
            case GoDirectTo:
            {
                int targetSpaceIndex = instruction.getTargetSpaceIndex();
                currentPlayer.setCurrentSpaceIndex(targetSpaceIndex);
                break;
            }
            case GoToJail:
            {
                currentPlayer.setCurrentSpaceIndex(_jailSpaceIndex);
                if (currentPlayer.hasGetOutOfJailCard())
                {
                    //Rule 27: If a player has a “get out of jail free” card, then they place the card at the bottom of the “pot luck” or “opportunity knocks” pile as appropriate, the player token is moved to “just visiting” and the players turn ends. The player takes a normal turn in the next round
                    currentPlayer.setIsJustVisiting(true);
                    currentPlayer.setTurnsToMiss(0);
                    returnJailCard(currentPlayer);
                }
                currentPlayer.setIsJustVisiting(false);
                currentPlayer.setTurnsToMiss(2);
                break;
            }
            case SetJustVisitingFlag: 
            {
                currentPlayer.setIsJustVisiting(true);
                currentPlayer.setTurnsToMiss(0);
                currentPlayer.setIsTurnComplete(true);
                break;
            }
            case SetDoubleThrown:
            {
                currentPlayer.setWasDoubleThrown(true);
                break;
            }
            case ClearDoubleThrown:
            {
                currentPlayer.setWasDoubleThrown(false);
                break;
            }
            case SetSkipTurns:
            {
                currentPlayer.setTurnsToMiss(instruction.getAmount1());
                break;
            }
            case ClearSkipTurns:
            {
                currentPlayer.setTurnsToMiss(0);
                break;
            }
            case GoBackTo:
            {
                int targetSpaceIndex = instruction.getTargetSpaceIndex();
                currentPlayer.setCurrentSpaceIndex(targetSpaceIndex);
                break;
            }
            case GoBackNumSpaces:
            {
                int amount1 = instruction.getAmount1();
                currentSpaceIndex -= amount1;
                if (currentSpaceIndex < 0)
                {
                    currentSpaceIndex += _spaces.size();
                }                
                currentPlayer.setCurrentSpaceIndex(currentSpaceIndex);
                break;
            }
            case AdvanceTo:
            {
                int targetSpaceIndex = instruction.getTargetSpaceIndex();
                if (targetSpaceIndex < currentSpaceIndex)
                {
                    currentPlayer.setBalance(currentPlayer.getBalance() + GO_BENEFIT);
                }
                currentPlayer.setCurrentSpaceIndex(targetSpaceIndex);
                break;
            }
            case PayForRepairs:
            {
                int amount1 = instruction.getAmount1();
                int amount2 = instruction.getAmount2();
                ArrayList<Property> properties = GameModel.this.getPlayerProperties(currentPlayerIndex);
                int totalAmount = 0;
                for (Property property : properties)
                {
                    int numberOfHouses = property.getNumberOfHouses();
                    if (numberOfHouses > 4)
                        totalAmount += amount2; //one hotel
                    else totalAmount += amount1 * property.getNumberOfHouses();
                }
                currentPlayer.setBalance(currentPlayer.getBalance() - totalAmount);
                
                //allow a player to have a temporary negative balance that must be fixed at the start of his next turn
                currentPlayer.setIsTurnComplete(true);
                break;
            }
            case CollectFines:
            {
                currentPlayer.setBalance(currentPlayer.getBalance() + _finesTotal);
                _finesTotal = 0;
                currentPlayer.setIsTurnComplete(true);
                break;
            }
            case GetOutOfJailFree:
            {
                //nothing to do - this just represents a get out of jail free card
                break;
            }
            case SaveDiceValues:
            {
                int amount1 = instruction.getAmount1();
                int amount2 = instruction.getAmount2();
                currentPlayer.setDiceValue1(amount1);
                currentPlayer.setDiceValue2(amount2);
                _gameStage = GameStageType.DiceThrown;
                currentPlayer.setIsJustVisiting(false);
                currentPlayer.setTurnsMissed(0);
                currentPlayer.setTurnsToMiss(0);
                break;
            }
             case BuyProperty:
            {
                Property property = ((PropertySpace)currentSpace).getProperty();
                property.setOwner(_currentPlayerIndex);
                currentPlayer.setBalance(currentPlayer.getBalance() - property.getPurchasePrice());
                break;
            }
            case AuctionProperty:
            {
                int amount1 = instruction.getAmount1();
                int buyerIndex = instruction.getTargetSpaceIndex(); 
                
                Player buyer = _players.get(buyerIndex);
                Property property = ((PropertySpace)currentSpace).getProperty();
                property.setOwner(buyerIndex);

                buyer.setBalance(buyer.getBalance() - property.getPurchasePrice());
                break;
            } 
             case ImproveProperty:
            {
                Property property = ((PropertySpace)currentSpace).getProperty();
                int numberOfHouses = property.getNumberOfHouses();
                property.setNumberOfHouses(numberOfHouses + 1);
                int priceOfHouse = property.getHousePrice();
                currentPlayer.setBalance(currentPlayer.getBalance() - priceOfHouse);
                break;
            } 
             case PayJailFine:
            {
                currentPlayer.setBalance(currentPlayer.getBalance() - 50);
                _finesTotal += 50;
                currentPlayer.setIsJustVisiting(true);
                break;
            }
             case SellHouse:
            {
                Property property = ((PropertySpace)currentSpace).getProperty();
                int numberOfHouses = property.getNumberOfHouses();
                property.setNumberOfHouses(numberOfHouses - 1);
                int priceOfHouse = property.getHousePrice();
                currentPlayer.setBalance(currentPlayer.getBalance() + priceOfHouse);
                break;
            }
             case SellProperty:
            {
                Property property = ((PropertySpace)currentSpace).getProperty();
                property.setOwner(0);
                currentPlayer.setBalance(currentPlayer.getBalance() + property.getPurchasePrice());
                break;
            }
            case MortgageProperty:
            {
                Property property = ((PropertySpace)currentSpace).getProperty();
                property.setIsMortgaged(true);
                currentPlayer.setBalance(currentPlayer.getBalance() + property.getPurchasePrice() / 2);
                break;
            }
            case UnMortgageProperty:
            {
                Property property = ((PropertySpace)currentSpace).getProperty();
                property.setIsMortgaged(false);
                currentPlayer.setBalance(currentPlayer.getBalance() - property.getPurchasePrice() / 2);
                break;
            }
             case SelectOpportunityKnocks:
            {
                Card card = _opportunityCards.get(0);
                Instruction cardInstruction = card.getInstruction();
                _opportunityCards.remove(0);
                _opportunityCards.add(card);
                executeInstruction(cardInstruction);
                break;
            }
            case MoveToNextPlayer:
            {
                if (!isGameOver())
                {
                    _currentPlayerIndex++;
                    if (_currentPlayerIndex >= _players.size())
                    {
                        _currentPlayerIndex = 0;
                    }
                    while (!_players.get(_currentPlayerIndex).getIsActive())
                    {
                        _currentPlayerIndex++;
                        if (_currentPlayerIndex >= _players.size())
                        {
                            _currentPlayerIndex = 0;
                        }
                    }
                    _gameStage = GameStageType.StartOfTurn;
                }
                break;
            }
            case LeaveGame:
            {
                currentPlayer.setIsActive(false);
                break;
            }
            default:
            {
                break;
            }
        }
        return options;
    }

    private ArrayList<Player> getActivePlayers()
    {
        ArrayList<Player> players = new ArrayList<Player>();
        
        for (Player player : _players)
        {
            if (player.getIsActive())
            {
                players.add(player);
            }
        }
        
        return players;
    }
    
    public Boolean isBankrupt(int playerIndex)
    {
        Player player = _players.get(playerIndex);
        if (player.getBalance() > 0)
        {
            return false;
        }
        ArrayList<Property> properties = GameModel.this.getPlayerProperties(playerIndex);
        if (properties.size() > 0)
        {
            return false;
        }
        return true;
    }
    
    public ArrayList<Property> getPlayerProperties()
    {
        return GameModel.this.getPlayerProperties(_currentPlayerIndex);
    }
    
    public ArrayList<Property> getPlayerProperties(int playerIndex)
    {
        ArrayList<Property> result = new ArrayList<Property>();
        
        for (Property property : _properties)
        {
            if (property.getOwnerIndex() == playerIndex)
            {
                result.add(property);
            }
        }
        return result;
    }
    
    public ArrayList<Property> getGroupProperties(PropertyGroupColour groupColour)
    {
        ArrayList<Property> result = new ArrayList<Property>();
        
        for (Property property : _properties)
        {
            if (property.getPropertyGroup() == groupColour)
            {
                result.add(property);
            }
        }
        return result;
    }
    
    public ArrayList<Property> getPlayerGroupProperties(PropertyGroupColour groupColour, int playerIndex)
    {
        ArrayList<Property> result = new ArrayList<Property>();
        
        for (Property property : _properties)
        {
            if (property.getPropertyGroup() == groupColour && property.getOwnerIndex() == playerIndex)
            {
                result.add(property);
            }
        }
        return result;
    }
    
    public ArrayList<Card> getCards()
    {
        return _potLuckCards;
    }

    public void setCards(ArrayList<Card> _cards)
    {
        this._potLuckCards = _cards;
    }
   
}
