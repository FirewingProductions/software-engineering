/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Instruction class is used by the RulesProvider to instruct the GameModel
 * to modify the model state
 */
public class Instruction 
{
    private String _message;
    private InstructionType _instructionType;
    private int _amount1;
    private int _amount2;
    private int _targetSpaceIndex;
        
    //default constructor to allow serialisation

    /**
     * Constructor
     */
    public Instruction()
    {
    }

    /**
     * Constructor
     * @param message
     * @param instructionType
     * @param amount1
     * @param amount2
     * @param targetSpaceIndex
     */
    public Instruction(String message, InstructionType instructionType, int amount1, int amount2, int targetSpaceIndex)
    {
        _message = message;
        _instructionType = instructionType;
        _amount1 = amount1;
        _amount2 = amount2;
        _targetSpaceIndex = targetSpaceIndex;
    }
    
    /**
     * converts an instruction to a string for the logs
     * @param playerTokenName
     * @return
     */
    public String toString(String playerTokenName)
    {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        StringBuilder sb = new StringBuilder(timestamp);
        sb.append(" - Player: ");
        sb.append(playerTokenName);
        sb.append("  Type: ");
        sb.append(_instructionType);
        sb.append("  Message: ");
        sb.append(_message);
        sb.append("  Amt1: ");
        sb.append(_amount1);
        sb.append("  Amt2: ");
        sb.append(_amount2);
        sb.append("  Index: ");
        sb.append(_targetSpaceIndex);

        return sb.toString();
    }
    
    public int getTargetSpaceIndex()
    {
        return _targetSpaceIndex;
    }

    public void setTargetSpaceIndex(int _targetSpaceIndex)
    {
        this._targetSpaceIndex = _targetSpaceIndex;
    }
    
    public String getMessage() 
    {
        return _message;
    }

    public void setMessage(String message)
    {
        _message = message;
    }

    public InstructionType getInstructionType()
    {
        return _instructionType;
    }

    public void setInstructionType(InstructionType _instructionType)
    {
        this._instructionType = _instructionType;
    }

    public int getAmount1()
    {
        return _amount1;
    }

    public void setAmount(int _amount1)
    {
        this._amount1 = _amount1;
    }
    
    public int getAmount2()
    {
        return _amount2;
    }

    public void setAmount2(int _amount2)
    {
        this._amount2 = _amount2;
    }
    
}