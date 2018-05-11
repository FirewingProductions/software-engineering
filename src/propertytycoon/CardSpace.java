/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

/**
 * The CardSpace class represents a board space that requires the player to take a card
 */
public class CardSpace extends Space
{
    private CardType _cardType;

    /**
     * Constructor
     * @param title
     * @param cardType
     */
    public CardSpace(String title, CardType cardType)
    {
        super(title);
        _cardType = cardType;
    }
    
    public CardType getCardType()
    {
        return _cardType;
    }

    public void setCardType(CardType _cardType)
    {
        this._cardType = _cardType;
    }

}
