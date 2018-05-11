/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

/**
 * The GoSpace represents the GO space on the board
 */
public class GoSpace extends InstructionSpace
{

    /**
     *  Constructor
     * @param title
     * @param _instruction
     */
    public GoSpace(String title, Instruction _instruction)
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
