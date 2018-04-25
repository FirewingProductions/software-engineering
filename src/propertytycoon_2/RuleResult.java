/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon_2;

import java.util.ArrayList;
import java.util.Arrays;

public class RuleResult
{
    private ArrayList<Instruction> _instructions;
    private ArrayList<PlayerOption> _options;

    public RuleResult()
    {
        _instructions = new ArrayList<Instruction>();
        _options = new ArrayList<PlayerOption>();
    }

    public void addInstruction(Instruction instruction)
    {
        _instructions.add(instruction);
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

    public ArrayList<PlayerOption> getOptions()
    {
        return _options;
    }

    public void setOptions(ArrayList<PlayerOption> _options)
    {
        this._options = _options;
    }
}
