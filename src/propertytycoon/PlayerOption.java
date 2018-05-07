/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.util.ArrayList;

public class PlayerOption
{
    private String _title;
    private PlayerOptionType _optionType;
    private int _amount1;
    private int _amount2;
    private String _name1;
    private ArrayList<Instruction> _instructions = new ArrayList<Instruction>();

    public PlayerOption(String title, PlayerOptionType optionType, Instruction instruction)
    {
        _title = title;
        _optionType = optionType;
        _instructions.add(instruction);
    }

    public int getAmount2()
    {
        return _amount2;
    }

    public void setAmount2(int _amount2)
    {
        this._amount2 = _amount2;
    }

    public PlayerOption(String title, PlayerOptionType optionType, int amount1, Instruction instruction)
    {
        _title = title;
        _optionType = optionType;
        _amount1 = amount1;
        _instructions.add(instruction);
    }
        
    public PlayerOption(String title, PlayerOptionType optionType, String name1, Instruction instruction)
    {
        _title = title;
        _optionType = optionType;
        _name1 = name1;
        _instructions.add(instruction);
    }
    
    public PlayerOption(String title, PlayerOptionType optionType, String name1, int amount1, Instruction instruction)
    {
        _title = title;
        _optionType = optionType;
        _name1 = name1;
        _amount1 = amount1;
        _instructions.add(instruction);
    }

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
