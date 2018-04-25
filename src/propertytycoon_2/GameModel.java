/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon_2;

import java.util.ArrayList;

public class GameModel 
{
    private static final int GO_BENEFIT = 200;
    
    private int _currentPlayerIndex;
    
    private ArrayList<Property> _properties;
    private ArrayList<Player> _players;
    private ArrayList<Space> _spaces;
    private ArrayList<Card> _potLuckCards;
    private ArrayList<Card> _opportunityCards;
    
    private int _finesTotal;
        
    public GameModel()
    {
        _properties = new ArrayList<Property>();
        _players = new ArrayList<Player>();
        _spaces = new ArrayList<Space>();
        _potLuckCards = new ArrayList<Card>();
        _opportunityCards = new ArrayList<Card>();
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
                ++_currentPlayerIndex;
            }
        }
    }
    
    public void Initialise(int numHumanPlayers, int numAutoPlayers)
    {
        int playerIndex = 0;
        for (; playerIndex < numHumanPlayers; ++playerIndex)
        {
            _players.add(new Player(PlayerTokenType.values()[playerIndex], true, false));
        }
        playerIndex = 0;
        for (;playerIndex < numAutoPlayers; ++playerIndex)
        {
            _players.add(new Player(PlayerTokenType.values()[playerIndex], true, true));
        }
        _currentPlayerIndex = 0;
    }

    public ArrayList<PlayerOption> executeInstruction(Instruction instruction)
    {
        return executeInstruction(instruction, _currentPlayerIndex);
    }

    public Player getCurrentPlayer()
    {
        return _players.get(_currentPlayerIndex);
    }
    
    public ArrayList<PlayerOption> executeInstruction(Instruction instruction, int currentPlayerIndex)
    {
        ArrayList<PlayerOption> options = new ArrayList<PlayerOption>();
        Player currentPlayer = _players.get(currentPlayerIndex);
        
        switch (instruction.getInstructionType())
        {
            case MoveSpaces:
            {
                int amount1 = instruction.getAmount1();
                int currentSpaceIndex = currentPlayer.getCurrentSpaceIndex();
                currentSpaceIndex += amount1;
                if (currentSpaceIndex >= _spaces.size())
                {
                    currentSpaceIndex -= _spaces.size();
                    currentPlayer.setBalance(currentPlayer.getBalance() + GO_BENEFIT);
                }                
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
                PlayerOption option = new PlayerOption(PlayerOptionType.ChoosePayMoneyOrOpportunityKnocks, instruction.getAmount1());
                options.add(option);
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
            case GoToJustVisiting: 
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
            case ReturnGetOutOfJailFreeCard:
            {
                if (currentPlayer.getHasGetOutOfJailFreeCardFromPotLuck())
                {
                    currentPlayer.setHasGetOutOfJailFreeCardFromPotLuck(false);
                    _potLuckCards.add(new Card(new Instruction("Get out of jail free", InstructionType.GetOutOfJailFree, 0, 0, 0)));
                }
                else if (currentPlayer.getHasGetOutOfJailFreeCardFromOpportunity())
                {
                    currentPlayer.setHasGetOutOfJailFreeCardFromOpportunity(false);
                    _opportunityCards.add(new Card(new Instruction("Get out of jail free", InstructionType.GetOutOfJailFree, 0, 0, 0)));
                }
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
                int currentSpaceIndex = currentPlayer.getCurrentSpaceIndex();
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
                int currentSpaceIndex = currentPlayer.getCurrentSpaceIndex();
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
                ArrayList<Property> properties = GetPlayerProperties(currentPlayerIndex);
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
                break;
            }
            default:
            {
                break;
            }
        }
        return options;
    }
        
    public Boolean isBankrupt(int playerIndex)
    {
        Player player = _players.get(playerIndex);
        if (player.getBalance() > 0)
        {
            return false;
        }
        ArrayList<Property> properties = GetPlayerProperties(playerIndex);
        if (properties.size() > 0)
        {
            return false;
        }
        return true;
    }
    
    public ArrayList<Property> GetPlayerProperties()
    {
        return GetPlayerProperties(_currentPlayerIndex);
    }
    
    public ArrayList<Property> GetPlayerProperties(int playerIndex)
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
    
    public ArrayList<Card> getCards()
    {
        return _potLuckCards;
    }

    public void setCards(ArrayList<Card> _cards)
    {
        this._potLuckCards = _cards;
    }
   
}
