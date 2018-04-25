/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon_2;

import java.util.ArrayList;

public class Player 
{
    private PlayerTokenType _token;
    
    private Boolean _isActive;
    private Boolean _isAuto;
    private int _currentSpaceIndex;
    
    private int _diceValue1;
    private int _diceValue2;
    private Boolean _wasDoubleThrown;
    private int _extraTurns;
    private int _turnsToMiss;
    private Boolean _isJustVisiting;
    private Boolean _hasGetOutOfJailFreeCardFromPotLuck;
    private Boolean _hasGetOutOfJailFreeCardFromOpportunity;

    private int _balance;

    private Boolean _isTurnComplete; //set by the game rules and logic to indicate that processing for this player is complete

    public Player(PlayerTokenType _token, Boolean _isActive, Boolean _isAuto)
    {
        this._token = _token;
        this._isActive = _isActive;
        this._isAuto = _isAuto;
    }

    public Boolean getIsTurnComplete()
    {
        return _isTurnComplete;
    }

    public void setIsTurnComplete(Boolean _isTurnComplete)
    {
        this._isTurnComplete = _isTurnComplete;
    }

    public Boolean getIsActive()
    {
        return _isActive;
    }

    public void setIsActive(Boolean _isActive)
    {
        this._isActive = _isActive;
    }

    public Boolean getIsAuto()
    {
        return _isAuto;
    }

    public void setIsAuto(Boolean _isAuto)
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

    public Boolean getHasGetOutOfJailFreeCardFromPotLuck()
    {
        return _hasGetOutOfJailFreeCardFromPotLuck;
    }

    public void setHasGetOutOfJailFreeCardFromPotLuck(Boolean _hasGetOutOfJailFreeCard)
    {
        this._hasGetOutOfJailFreeCardFromPotLuck = _hasGetOutOfJailFreeCard;
    }

    public Boolean getHasGetOutOfJailFreeCardFromOpportunity()
    {
        return _hasGetOutOfJailFreeCardFromOpportunity;
    }

    public void setHasGetOutOfJailFreeCardFromOpportunity(Boolean _hasGetOutOfJailFreeCardFromOpportunity)
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

    public Boolean getIsJustVisiting()
    {
        return _isJustVisiting;
    }

    public void setIsJustVisiting(Boolean _isInJail)
    {
        this._isJustVisiting = _isInJail;
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

    public Boolean getWasDoubleThrown()
    {
        return _wasDoubleThrown;
    }

    public void setWasDoubleThrown(Boolean _wasDoubleThrown)
    {
        this._wasDoubleThrown = _wasDoubleThrown;
    }

}
