/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon_2;

public class PlayerOption
{
    private PlayerOptionType _optionType;
    private int _amount1;
    private String _name1;

    public PlayerOption(PlayerOptionType _optionType)
    {
        this._optionType = _optionType;
    }

    public PlayerOption(PlayerOptionType _optionType, int amount1)
    {
        this._optionType = _optionType;
        _amount1 = amount1;
    }
        
    public PlayerOption(PlayerOptionType _optionType, String name1)
    {
        this._optionType = _optionType;
        _name1 = name1;
    }
    
        public PlayerOption(PlayerOptionType _optionType, String name1, int amount1)
    {
        this._optionType = _optionType;
        _name1 = name1;
        _amount1 = amount1;
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
