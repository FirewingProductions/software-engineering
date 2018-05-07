/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

public class CardSpace extends Space
{
    private CardType _cardType;

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
