/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.util.ArrayList;

/**
 * This class contains data for the state of the player
 */
public class Player 
{
    private PlayerTokenType _token;
    
    private boolean _isActive;
    private boolean _isAuto;
    private int _currentSpaceIndex;
    
    private int _diceValue1;
    private int _diceValue2;
    private boolean _wasDoubleThrown;
    private int _extraTurns;
    private int _turnsToMiss;
    private int _turnsMissed;
    private boolean _isJustVisiting;

    private boolean _hasGetOutOfJailFreeCardFromPotLuck;
    private boolean _hasGetOutOfJailFreeCardFromOpportunity;
    private boolean _selectOpportunityKnocks;
    private int _timesPlayerHasPassedGo;
    
    private ArrayList<String> _instructionLog = new ArrayList<String>();

    private int _balance;

    private boolean _isTurnComplete; //set by the game rules and logic to indicate that processing for this player is complete

    /**
     * Constructor
     * @param _token
     * @param _isActive
     * @param _isAuto
     * @param initialBalance
     */
    public Player(PlayerTokenType _token, boolean _isActive, boolean _isAuto, int initialBalance)
    {
        this._token = _token;
        this._isActive = _isActive;
        this._isAuto = _isAuto;
        this._balance = initialBalance;
    }

    /**
     *
     * @param _instructionLog
     */
    public void setInstructionLog(ArrayList<String> _instructionLog)
    {
        this._instructionLog = _instructionLog;
    }
    
    public ArrayList<String> getInstructionLog()
    {
        return _instructionLog;
    }
    
    public void logInstruction(Instruction instruction)
    {
        _instructionLog.add(instruction.toString(_token.toString()));
    }
        
    public void incrementGoCount()
    {
        _timesPlayerHasPassedGo++;
    }

    public int getTimesPlayerHasPassedGo()
    {
        return _timesPlayerHasPassedGo;
    }

    public void setTimesPlayerHasPassedGo(int _timesPlayerHasPassedGo)
    {
        this._timesPlayerHasPassedGo = _timesPlayerHasPassedGo;
    }
    
    public boolean getSelectOpportunityKnocks()
    {
        return _selectOpportunityKnocks;
    }

    public void setSelectOpportunityKnocks(boolean _selectOpportunityKnocks)
    {
        this._selectOpportunityKnocks = _selectOpportunityKnocks;
    }
    
    public int getTurnsMissed()
    {
        return _turnsMissed;
    }

    public void setTurnsMissed(int _turnsMissed)
    {
        this._turnsMissed = _turnsMissed;
    }
    
    public boolean hasGetOutOfJailCard()
    {
        return _hasGetOutOfJailFreeCardFromOpportunity || _hasGetOutOfJailFreeCardFromPotLuck;
    }

    public boolean getIsTurnComplete()
    {
        return _isTurnComplete;
    }

    public void setIsTurnComplete(boolean _isTurnComplete)
    {
        this._isTurnComplete = _isTurnComplete;
    }

    public boolean getIsActive()
    {
        return _isActive;
    }

    public void setIsActive(boolean _isActive)
    {
        this._isActive = _isActive;
    }

    public boolean getIsAuto()
    {
        return _isAuto;
    }

    public void setIsAuto(boolean _isAuto)
    {
        this._isAuto = _isAuto;
    }
    
    /**
     * Constructor
     */
    public Player()
    {
    }
    
    public PlayerTokenType getToken()
    {
        return _token;
    }

    public void setToken(PlayerTokenType _token)
    {
        this._token = _token;
    }

    public int getCurrentSpaceIndex()
    {
        return _currentSpaceIndex;
    }

    public void setCurrentSpaceIndex(int _currentSpaceIndex)
    {
        this._currentSpaceIndex = _currentSpaceIndex;
    }

    public int getBalance()
    {
        return _balance;
    }

    public void setBalance(int _balance)
    {
        this._balance = _balance;
    }

    public boolean getHasGetOutOfJailFreeCardFromPotLuck()
    {
        return _hasGetOutOfJailFreeCardFromPotLuck;
    }

    public void setHasGetOutOfJailFreeCardFromPotLuck(boolean _hasGetOutOfJailFreeCard)
    {
        this._hasGetOutOfJailFreeCardFromPotLuck = _hasGetOutOfJailFreeCard;
    }

    public boolean getHasGetOutOfJailFreeCardFromOpportunity()
    {
        return _hasGetOutOfJailFreeCardFromOpportunity;
    }

    public void setHasGetOutOfJailFreeCardFromOpportunity(boolean _hasGetOutOfJailFreeCardFromOpportunity)
    {
        this._hasGetOutOfJailFreeCardFromOpportunity = _hasGetOutOfJailFreeCardFromOpportunity;
    }
    
    public int getExtraTurns()
    {
        return _extraTurns;
    }

    public void setExtraTurns(int _extraTurns)
    {
        this._extraTurns = _extraTurns;
    }

    public boolean getIsJustVisiting()
    {
        return _isJustVisiting;
    }

    public void setIsJustVisiting(boolean isJustVisiting)
    {
        this._isJustVisiting = isJustVisiting;
    }

    public int getTurnsToMiss()
    {
        return _turnsToMiss;
    }

    public void setTurnsToMiss(int _turnsToMiss)
    {
        this._turnsToMiss = _turnsToMiss;
    }
    
    public int getDiceValue1()
    {
        return _diceValue1;
    }

    public void setDiceValue1(int _diceValue1)
    {
        this._diceValue1 = _diceValue1;
    }

    public int getDiceValue2()
    {
        return _diceValue2;
    }

    public void setDiceValue2(int _diceValue2)
    {
        this._diceValue2 = _diceValue2;
    }
    
    /**
     * Checks whether the two dice values are the same
     * @return
     */
    public boolean hasDouble()
    {
        if (_diceValue1 == _diceValue2)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean getWasDoubleThrown()
    {
        return _wasDoubleThrown;
    }

    public void setWasDoubleThrown(boolean _wasDoubleThrown)
    {
        this._wasDoubleThrown = _wasDoubleThrown;
    }

    
    
    public static class PlayerOption
{
    private String _title;
    private PlayerOptionType _optionType;
    private int _amount1;
    private int _amount2;
    private int _amount3;

    private String _name1;
    private ArrayList<Instruction> _instructions = new ArrayList<Instruction>();

    /**
     * Constructor
     * @param title
     * @param optionType
     * @param instruction
     */
    public PlayerOption(String title, PlayerOptionType optionType, Instruction instruction)
    {
        _title = title;
        _optionType = optionType;
        _instructions.add(instruction);
    }

    /**
     * Constructor
     * @param title
     * @param optionType
     * @param instruction1
     * @param instruction2
     */
    public PlayerOption(String title, PlayerOptionType optionType, Instruction instruction1, Instruction instruction2)
    {
        _title = title;
        _optionType = optionType;
        _instructions.add(instruction1);
        _instructions.add(instruction2);
    }
        
    public int getAmount3()
    {
        return _amount3;
    }

    public void setAmount3(int _amount3)
    {
        this._amount3 = _amount3;
    }

    public int getAmount2()
    {
        return _amount2;
    }

    public void setAmount2(int _amount2)
    {
        this._amount2 = _amount2;
    }

    /**
     * Constructor
     * @param title
     * @param optionType
     * @param amount1
     * @param instruction
     */
    public PlayerOption(String title, PlayerOptionType optionType, int amount1, Instruction instruction)
    {
        _title = title;
        _optionType = optionType;
        _amount1 = amount1;
        _instructions.add(instruction);
    }
        
    /**
     * Constructor
     * @param title
     * @param optionType
     * @param name1
     * @param instruction
     */
    public PlayerOption(String title, PlayerOptionType optionType, String name1, Instruction instruction)
    {
        _title = title;
        _optionType = optionType;
        _name1 = name1;
        _instructions.add(instruction);
    }
    
    
    /**
     * Constructor
     * @param title
     * @param optionType
     * @param name1
     * @param amount1
     * @param instruction1
     * @param instruction2
     */
    public  PlayerOption(String title, PlayerOptionType optionType, String name1, Instruction instruction1, Instruction instruction2)
    {
        _title = title;
        _optionType = optionType;
        _name1 = name1;
        _instructions.add(instruction1);
        _instructions.add(instruction2);
    }

    /**
     * adds an instruction
     * @param instruction
     */
    public void addInstruction(Instruction instruction)
    {
        _instructions.add(instruction);
    }
    
    public ArrayList<Instruction> getInstructions()
    {
        return _instructions;
    }

    public void setInstructions(ArrayList<Instruction> _instructions)
    {
        this._instructions = _instructions;
    }

    public String getTitle()
    {
        return _title;
    }

    public void setTitle(String _title)
    {
        this._title = _title;
    }    
    
    public String getName1()
    {
        return _name1;
    }

    public void setName1(String _name1)
    {
        this._name1 = _name1;
    }

    public int getAmount1()
    {
        return _amount1;
    }

    public void setAmount1(int _amount1)
    {
        this._amount1 = _amount1;
    }

    public PlayerOptionType getOptionType()
    {
        return _optionType;
    }

    public void setOptionType(PlayerOptionType _optionType)
    {
        this._optionType = _optionType;
    }
   
    
}
public  static class PlayerOptionResponse
{
    private propertytycoon.Player.PlayerOption _selectedOption;

    /**
     * Constructor
     * @param selectedOption
     */
    public PlayerOptionResponse(propertytycoon.Player.PlayerOption selectedOption)
    {
        _selectedOption = selectedOption;
    }

    public propertytycoon.Player.PlayerOption getSelectedOption()
    {
        return _selectedOption;
    }

    public void setSelectedOption(propertytycoon.Player.PlayerOption _selectedOption)
    {
        this._selectedOption = _selectedOption;
    }
    

}
public static enum PlayerOptionType
{

    ThrowDice, 
    BuyProperty, 
    AuctionProperty, 
    ImproveProperty, 
    PayJailFine, 
    SellHouse, 
    SellProperty,
    MortgageProperty,
    UnmortgageProperty,
    PayFine,
    SelectOpportunityKnocks,
    LeaveGame,
    SkipTurn,
    GoToJail,
    MoveSpaces,
    PayRent,
    EndOfTurn,
    SetDoubleThrown,
    TradeProperties,
    FollowInstruction
}

public static enum PlayerTokenType
{
    Boot, 
    Cat, 
    Goblet, 
    Hatstand, 
    Smartphone, 
    Spoon
}

}
