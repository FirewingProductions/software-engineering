/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.util.ArrayList;

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

    public Player(PlayerTokenType _token, boolean _isActive, boolean _isAuto, int initialBalance)
    {
        this._token = _token;
        this._isActive = _isActive;
        this._isAuto = _isAuto;
        this._balance = initialBalance;
    }

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

}
