/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

public class PlayerOption
{
    private String _title;
    private PlayerOptionType _optionType;
    private int _amount1;
    private String _name1;

   public PlayerOption(String title, PlayerOptionType optionType)
    {
        _title = title;
        _optionType = optionType;
    }

        public PlayerOption(String title, PlayerOptionType optionType, int amount1)
    {
        _title = title;
        _optionType = optionType;
        _amount1 = amount1;
    }
        
    public PlayerOption(String title, PlayerOptionType optionType, String name1)
    {
        _title = title;
        _optionType = optionType;
        _name1 = name1;
    }
    
    public PlayerOption(String title, PlayerOptionType optionType, String name1, int amount1)
    {
        _title = title;
        _optionType = optionType;
        _name1 = name1;
        _amount1 = amount1;
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
