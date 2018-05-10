/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.util.ArrayList;

/**
 * The GameModel class is the top-level class for all game data.
 */
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
    
    private boolean _isAbridgedGame;
    private boolean _isTradingAllowed;
    
    private long _abridgedGameLengthMillis;
    private long _abridgedGameMillisLeft;
    
    private ArrayList<String> _globalInstructionLog = new ArrayList<String>();

    /**
     * Constructor
     */
    public GameModel()
    {
        _gameStage = GameStageType.StartOfTurn;
        
        _properties = new ArrayList<Property>();
        _players = new ArrayList<Player>();
        _spaces = new ArrayList<Space>();
        _potLuckCards = new ArrayList<Card>();
        _opportunityCards = new ArrayList<Card>();
                        
        for (int playerIndex = 0; playerIndex < 6; ++playerIndex)
        {
            _players.add(new Player(PlayerTokenType.values()[playerIndex], false, false, 1500));
        }
    }
    
    /**
     * Gets the index of the specified property in the properties list
     * @param property
     * @return
     */
    public int getPropertyIndex(Property property)
    {
        for (int propertyIndex = 0; propertyIndex < _properties.size(); propertyIndex++)
        {
            if (property.getPropertyName().equals(_properties.get(propertyIndex).getPropertyName()))
            {
                return propertyIndex;
            }
        }
        return -1; //not found
    }
    
    /**
     * Checks whether the player is in jail
     * @param playerIndex
     * @return
     */
    public boolean isInJail(int playerIndex)
    {
        if (playerIndex < 0 || playerIndex >= _players.size())
            return false;
        
        Player player = _players.get(playerIndex);
        Space playerSpace = _spaces.get(player.getCurrentSpaceIndex());
        if (playerSpace.getTitle().toLowerCase().contains("jail") && !player.getIsJustVisiting())
        {
            return true;
        }
        return false;
    }
    
    public ArrayList<String> getGlobalInstructionLog()
    {
        return _globalInstructionLog;
    }

    public void setGlobalInstructionLog(ArrayList<String> _globalInstructionLog)
    {
        this._globalInstructionLog = _globalInstructionLog;
    }
    
    public boolean isIsAbridgedGame()
    {
        return _isAbridgedGame;
    }

    /**
     * Updates the amount of time left in the abridged game
     * @param startTimeMillis
     */
    public void updateTimeLeft(long startTimeMillis)
    {
        _abridgedGameMillisLeft = _abridgedGameLengthMillis - (System.currentTimeMillis() - startTimeMillis);
    }
    
    public void setIsAbridgedGame(boolean _isAbridgedGame)
    {
        this._isAbridgedGame = _isAbridgedGame;
    }

    public long getAbridgedGameLengthMillis()
    {
        return _abridgedGameLengthMillis;
    }

    public void setAbridgedGameLengthMillis(long _abridgedGameLengthMillis)
    {
        this._abridgedGameLengthMillis = _abridgedGameLengthMillis;
    }

    public long getAbridgedGameMillisLeft()
    {
        return _abridgedGameMillisLeft;
    }

    public void setAbridgedGameMillisLeft(long _abridgedGameMillisLeft)
    {
        this._abridgedGameMillisLeft = _abridgedGameMillisLeft;
    }
 
    public boolean isIsTradingAllowed()
    {
        return _isTradingAllowed;
    }

    public void setIsTradingAllowed(boolean _isTradingAllowed)
    {
        this._isTradingAllowed = _isTradingAllowed;
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

    /**
     *
     * @param _finesTotal
     */
    public void setFinesTotal(int _finesTotal)
    {
        this._finesTotal = _finesTotal;
    }

    /**
     * Checks whether the game is over
     * @return boolean
     */
    public boolean isGameOver()
    {
        if (_isAbridgedGame)
        {
            return _abridgedGameMillisLeft < 0 
                    && _currentPlayerIndex == 0 
                    && _gameStage == GameStageType.StartOfTurn;
        }
        else
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
    }
    
    /**
     * Decides who has won the game
     * @return player index
     */
    public int getWinnerIndex()
    {
        int winnerIndex = 0;
        if (_isAbridgedGame)
        {
            int highestWorth = 0;
            for (int playerIndex = 0; playerIndex < _players.size(); playerIndex++)
            {
                if (_players.get(playerIndex).getIsActive())
                {
                    int worth = getPlayerWorth(playerIndex);
                    if (worth > highestWorth)
                    {
                        highestWorth = worth;
                        winnerIndex = playerIndex;
                    }
                }
            }
        }
        else
        {
            //just return the first active player
            for (int playerIndex = 0; playerIndex < _players.size(); playerIndex++)
            {
                if (_players.get(playerIndex).getIsActive())
                {
                    winnerIndex = playerIndex;
                    break;
                }
            }
        }
        return winnerIndex;
    }
    
    /**
     * Calculates the worth of a player
     * @param playerIndex
     * @return total worth
     */
    public int getPlayerWorth(int playerIndex)
    {
        Player player = _players.get(playerIndex);
        int worth = player.getBalance();
        for (Property property : getPlayerProperties())
        {
            int propertyWorth = property.getPurchasePrice() + (property.getNumberOfHouses() * property.getHousePrice());
            if (property.getIsMortgaged())
                propertyWorth /= 2;
            worth += propertyWorth;
        }
        return worth;
    }
    
    /**
     * Sets the current player to the next player
     */
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
    
    /**
     * Initialises the game
     * @param numHumanPlayers
     * @param numAutoPlayers
     */
    public void initialise(int numHumanPlayers, int numAutoPlayers)
    {
        initialisePlayers(numHumanPlayers, numAutoPlayers);
        _currentPlayerIndex = 0;
        _gameStage = GameStageType.StartOfTurn;
    }

    /**
     * Initialises the players
     * @param numHumanPlayers
     * @param numAutoPlayers
     */
    public void initialisePlayers(int numHumanPlayers, int numAutoPlayers)
    {
        _players.clear();
        int playerIndex = 0;
        for (; playerIndex < numHumanPlayers; ++playerIndex)
        {
            _players.add(new Player(PlayerTokenType.values()[playerIndex], true, false, 1500));
        }
        for (;playerIndex < numHumanPlayers + numAutoPlayers; ++playerIndex)
        {
            _players.add(new Player(PlayerTokenType.values()[playerIndex], true, true, 1500));
        }
        for (;playerIndex < 6; ++playerIndex)
        {
            _players.add(new Player(PlayerTokenType.values()[playerIndex], false, false, 1500));
        }
    }

    /**
     *
     * @return
     */
    public Player getCurrentPlayer()
    {
        return _players.get(_currentPlayerIndex);
    }

    /**
     * Return the get-out-of-jail card to the correct pile
     * @param player
     */
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
    
    /**
     * Returns the space index where the current player is located
     * @return
     */
    public Space getCurrentPlayerSpace()
    {
        int spaceIndex = getCurrentPlayer().getCurrentSpaceIndex();
        return _spaces.get(spaceIndex);
    }
    
    /**
     * Executes the specified instruction for the current player
     * @param instruction
     * @return any error messages for the user
     */
    public String executeInstruction(Instruction instruction)
    {
        return executeInstruction(instruction, _currentPlayerIndex);
    }
        
    /**
     * Executes the specified instruction for the specified player
     * @param instruction
     * @param currentPlayerIndex
     * @return any error messages for the user
     */
    public String executeInstruction(Instruction instruction, int currentPlayerIndex)
    {
        Player currentPlayer = _players.get(currentPlayerIndex);
        int currentSpaceIndex = currentPlayer.getCurrentSpaceIndex();
        Space currentSpace = _spaces.get(currentSpaceIndex);
        String message = "";
        
        currentPlayer.logInstruction(instruction);
        
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
                    currentPlayer.incrementGoCount();
                }
                currentPlayer.setCurrentSpaceIndex(currentSpaceIndex);
                _gameStage = GameStageType.MovedToNewSpace;
                break;
            }
            case PayFine:
            {
                int amount1 = instruction.getAmount1();
                currentPlayer.setBalance(currentPlayer.getBalance() - amount1);
                _finesTotal += amount1;
                _gameStage = GameStageType.EndOfTurn;
                break;
            }
            case PayBank:
            {
                int amount1 = instruction.getAmount1();
                currentPlayer.setBalance(currentPlayer.getBalance() - amount1);
                _gameStage = GameStageType.EndOfTurn;
                break;
            }
            case ReceiveMoney:
            {
                int amount1 = instruction.getAmount1();
                currentPlayer.setBalance(currentPlayer.getBalance() + amount1);
                _gameStage = GameStageType.EndOfTurn;
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
                _gameStage = GameStageType.EndOfTurn;
                break;
            }
            case GoDirectTo:
            {
                int targetSpaceIndex = instruction.getTargetSpaceIndex();
                currentPlayer.setCurrentSpaceIndex(targetSpaceIndex);
                _gameStage = GameStageType.MovedToNewSpace;
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
                _gameStage = GameStageType.EndOfTurn;
                break;
            }
            case SetJustVisitingFlag: 
            {
                currentPlayer.setIsJustVisiting(true);
                currentPlayer.setTurnsToMiss(0);
                _gameStage = GameStageType.EndOfTurn;
                break;
            }
            case SetDoubleThrown:
            {
                currentPlayer.setWasDoubleThrown(true);
                currentPlayer.setExtraTurns(1);
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
                _gameStage = GameStageType.MovedToNewSpace;
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
                _gameStage = GameStageType.MovedToNewSpace;
                break;
            }
            case AdvanceTo:
            {
                int targetSpaceIndex = instruction.getTargetSpaceIndex();
                if (targetSpaceIndex < currentSpaceIndex)
                {
                    currentPlayer.setBalance(currentPlayer.getBalance() + GO_BENEFIT);
                    currentPlayer.incrementGoCount();
                }
                currentPlayer.setCurrentSpaceIndex(targetSpaceIndex);
                _gameStage = GameStageType.MovedToNewSpace;
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
                _gameStage = GameStageType.EndOfTurn;
                break;
            }
            case CollectFines:
            {
                currentPlayer.setBalance(currentPlayer.getBalance() + _finesTotal);
                _finesTotal = 0;
                _gameStage = GameStageType.EndOfTurn;
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
                property.setOwnerIndex(_currentPlayerIndex);
                currentPlayer.setBalance(currentPlayer.getBalance() - property.getPurchasePrice());
                _gameStage = GameStageType.EndOfTurn;
                break;
            }
            case AuctionProperty:
            {
                int amount1 = instruction.getAmount1();
                int buyerIndex = instruction.getTargetSpaceIndex(); 
                
                Player buyer = _players.get(buyerIndex);
                if (buyer.getTimesPlayerHasPassedGo() > 0)
                {
                    Property property = ((PropertySpace)currentSpace).getProperty();
                    property.setOwnerIndex(buyerIndex);
                    buyer.setBalance(buyer.getBalance() - amount1);
                }
                _gameStage = GameStageType.EndOfTurn;
                break;
            } 
             case ImproveProperty:
            {
                Property property = ((PropertySpace)currentSpace).getProperty();
                int numberOfHouses = property.getNumberOfHouses();
                property.setNumberOfHouses(numberOfHouses + 1);
                int priceOfHouse = property.getHousePrice();
                currentPlayer.setBalance(currentPlayer.getBalance() - priceOfHouse);
                _gameStage = GameStageType.EndOfTurn;
                break;
            } 
             case PayJailFine:
            {
                currentPlayer.setBalance(currentPlayer.getBalance() - 50);
                _finesTotal += 50;
                currentPlayer.setIsJustVisiting(true);
                _gameStage = GameStageType.EndOfTurn;
                break;
            }
             case SellHouse:
            {
                int propertyIndex = instruction.getTargetSpaceIndex();
                Property property = _properties.get(propertyIndex);
                int numberOfHouses = property.getNumberOfHouses();
                property.setNumberOfHouses(numberOfHouses - 1);
                int priceOfHouse = property.getHousePrice();
                currentPlayer.setBalance(currentPlayer.getBalance() + priceOfHouse);
                break;
            }
             case SellProperty:
            {
                int propertyIndex = instruction.getTargetSpaceIndex();
                Property property = _properties.get(propertyIndex);
                property.setOwnerIndex(-1);
                currentPlayer.setBalance(currentPlayer.getBalance() + property.getPurchasePrice());
                break;
            }
            case MortgageProperty:
            {
                int propertyIndex = instruction.getTargetSpaceIndex();
                Property property = _properties.get(propertyIndex);
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
                currentPlayer.setSelectOpportunityKnocks(true);
                break;
            }
            case MoveToNextPlayer:
            {
                moveToNextPlayer();
                break;
            }
            case LeaveGame:
            {
                currentPlayer.setIsActive(false);
                moveToNextPlayer();
                break;
            }
            case PayRent:
            {
                int rentAmount = instruction.getAmount1();
                int ownerIndex = instruction.getTargetSpaceIndex();
                Player owner = _players.get(ownerIndex);
                currentPlayer.setBalance(currentPlayer.getBalance() - rentAmount);
                owner.setBalance(owner.getBalance() + rentAmount);
                _gameStage = GameStageType.EndOfTurn;
                break;
            }
            case SkipTurn:
            {
                currentPlayer.setTurnsMissed(currentPlayer.getTurnsMissed() + 1);
                _gameStage = GameStageType.EndOfTurn;
                break;
            }
            case PayFineOrOpportunityKnocks:
                break;
            case SetPlayerToBankrupt:
                currentPlayer.setIsActive(false);
                moveToNextPlayer();

                break;
            case TradeProperties:
                int otherPlayerIndex = instruction.getAmount1();
                int propertyIndex1 = instruction.getAmount2();
                int propertyIndex2 = instruction.getTargetSpaceIndex();
                Property property1 = _properties.get(propertyIndex1);
                Property property2 = _properties.get(propertyIndex2);
                if (property1.getOwnerIndex() == currentPlayerIndex && property2.getOwnerIndex() == otherPlayerIndex)
                {
                    property1.setOwnerIndex(otherPlayerIndex);
                    property2.setOwnerIndex(currentPlayerIndex);
                    message += executeInstruction(new Instruction("End of turn", InstructionType.MoveToNextPlayer, 0, 0, 0));
                }
                else if (property1.getOwnerIndex() == otherPlayerIndex && property2.getOwnerIndex() == currentPlayerIndex)
                {
                    property1.setOwnerIndex(_currentPlayerIndex);
                    property2.setOwnerIndex(otherPlayerIndex);
                    message += executeInstruction(new Instruction("End of turn", InstructionType.MoveToNextPlayer, 0, 0, 0));
                }
                else
                {
                    message += " - Properties could not be traded because at least one is not owned by the specified player";
                }
                break;
            default:
            {
                break;
            }
        }
        return message;
    }

    private void moveToNextPlayer()
    {
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.setWasDoubleThrown(false);
        currentPlayer.setSelectOpportunityKnocks(false);
        if (!isGameOver())
        {
            if (currentPlayer.getExtraTurns() > 0)
            {
                currentPlayer.setExtraTurns(currentPlayer.getExtraTurns() - 1);
            }
            else
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
            }
            _gameStage = GameStageType.StartOfTurn;
        }
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
    
    /**
     * Determines whether the specifies player is bankrupt
     * @param playerIndex
     * @return
     */
    public boolean isBankrupt(int playerIndex)
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
    
    /**
     * Returns the properties owned by the current player
     * @return
     */
    public ArrayList<Property> getPlayerProperties()
    {
        return GameModel.this.getPlayerProperties(_currentPlayerIndex);
    }
    
    /**
     * Returns the properties owned by the specified player
     * @param playerIndex
     * @return
     */
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
    
    /**
     * Gets all of the properties in the specified colour group
     * @param groupColour
     * @return
     */
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
    
    /**
     * Gets all of the properties in the specified colour group for
     * the specified player
     * @param groupColour
     * @param playerIndex
     * @return
     */
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
    
    /**
     * Gets all of the Utility properties owned by a player
     * @param playerIndex
     * @return
     */
    public ArrayList<Property> getPlayerUtilities(int playerIndex)
    {
        ArrayList<Property> result = new ArrayList<Property>();
        
        for (Property property : _properties)
        {
            if (property.getPropertyType()== PropertyType.Utility && property.getOwnerIndex() == playerIndex)
            {
                result.add(property);
            }
        }
        return result;
    }
    
    /**
     * Gets all of the Station properties owned by a player
     * @param playerIndex
     * @return
     */
    public ArrayList<Property> getPlayerStations(int playerIndex)
    {
        ArrayList<Property> result = new ArrayList<Property>();
        
        for (Property property : _properties)
        {
            if (property.getPropertyType()== PropertyType.Station && property.getOwnerIndex() == playerIndex)
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
