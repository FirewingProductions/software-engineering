/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

/**
 * The Card class represents a card on the Opportunity Knocks or Pot-Luck piles
 */
public class Card 
{
    private Instruction _instruction;

    /**
     * Constructor
     * @param instruction
     */
    public Card(Instruction instruction)
    {
        this._instruction = instruction;
    }
        
    /**
     * Constructor
     */
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
