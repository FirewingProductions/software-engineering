/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.util.ArrayList;

/**
 * The PlayerOption class represents one of the various options that the player
 * if offered at each stage of the game. It contains a list of instructions that
 * should be executed if the option is chosen
 */
public class PlayerOption
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
    public PlayerOption(String title, PlayerOptionType optionType, String name1, Instruction instruction1, Instruction instruction2)
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
