/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.util.ArrayList;

/**
 * This class holds the options and instructions returned by the RuleProvider
 */
public class RuleResult
{
    String _message;
    private ArrayList<Instruction> _instructions;
    private ArrayList<PlayerOption> _options;

    /**
     * Constructor
     */
    public RuleResult()
    {
        _instructions = new ArrayList<Instruction>();
        _options = new ArrayList<PlayerOption>();
    }

    public void addInstruction(Instruction instruction)
    {
        _instructions.add(instruction);
    }
    
    public void addInstructions(ArrayList<Instruction> instructions)
    {
        _instructions.addAll(instructions);
    }

    public void addOption(PlayerOption option)
    {
        _options.add(option);
    }
        
    public ArrayList<Instruction> getInstructions()
    {
        return _instructions;
    }

    public void setInstructions(ArrayList<Instruction> _instructions)
    {
        this._instructions = _instructions;
    }

    public String getMessage()
    {
        return _message;
    }

    public void setMessage(String _message)
    {
        this._message = _message;
    }

    public ArrayList<PlayerOption> getOptions()
    {
        return _options;
    }

    public void setOptions(ArrayList<PlayerOption> _options)
    {
        this._options = _options;
    }
}
