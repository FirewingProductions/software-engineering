/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

public class Card 
{
    private Instruction _instruction;

    public Card(Instruction instruction)
    {
        this._instruction = instruction;
    }
        
    public Card()
    {
    }

    public Instruction getInstruction() 
    {
        return _instruction;
    }

    public void setInstruction(Instruction instruction) 
    {
        this._instruction = instruction;
    }



    
    
}
