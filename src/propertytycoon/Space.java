/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import propertytycoon.Instruction.InstructionSpace;

/**
 * This is the superclass representing all board spaces
 */
public class Space 
{

    protected String _title;

    /**
     * Constructor
     * @param _title
     */
    public Space(String _title)
    {
        this._title = _title;
    }
    
    /**
     * Constructor
     */
    public Space()
    {
    }

    public void setTitle(String _title)
    {
        this._title = _title;
    }

    public String getTitle()
    {
        return _title;
    }
    
    public static class JailSpace extends Space
{

    /**
     * Constructor
     * @param title
     */
    public JailSpace(String title)
    {
        super(title);
    }

    /**
     * Constructor
     */
    public JailSpace()
    {
    }
    
}
public static class GoSpace extends InstructionSpace
{

    /**
     *  Constructor
     * @param title
     * @param _instruction
     */
    public  GoSpace(String title, Instruction _instruction)
    {
        super(title,_instruction);
    }

    /**
     * Constructor
     */
    public GoSpace()
    {
        super();
    }

       
    
}

}
