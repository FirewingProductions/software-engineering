/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon_2;

public class Instruction 
{
    private String _message;
    private InstructionType _instructionType;
    private int _amount1;
    private int _amount2;
    private int _targetSpaceIndex;
        
    //default constructor to allow serialisation
    public Instruction()
    {
    }

    public Instruction(String message, InstructionType instructionType, int amount1, int amount2, int targetSpaceIndex)
    {
        _message = message;
        _instructionType = instructionType;
        _amount1 = amount1;
        _amount2 = amount2;
        _targetSpaceIndex = targetSpaceIndex;
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