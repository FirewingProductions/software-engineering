/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

/**
 * This class represents board spaces that contain instructions for the player to follow
 */
public class InstructionSpace extends Space
{
    private Instruction _instruction; 

    /**
     *  Constructor
     * @param title
     * @param _instruction
     */
    public InstructionSpace(String title, Instruction _instruction)
    {
        super(title);
        this._instruction = _instruction;
    }

        public InstructionSpace()
    {
    }

    public Instruction getInstruction()
    {
        return _instruction;
    }

    public void setInstruction(Instruction _instruction)
    {
        this._instruction = _instruction;
    }
}
