/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

public class Property
{
    private int _ownerIndex;
    private Boolean _isMortgaged;
    private int _numberOfHouses; //5 = hotel
    
    //static fields, loaded from config spreadsheet
    private String _propertyName;
    private PropertyType _propertyType;
    private PropertyGroupColour _propertyGroup;
    private int _purchasePrice;
    private int _housePrice;
    private int _mortgageValue;
    private int[] _rents; //for residential 0 = no houses, 1..4 houses, 5 = hotel
    

    public Property()
    {
        _rents = new int[6];
    }

    public Property(String propertyName, PropertyType propertyType, PropertyGroupColour propertyGroup)
    {
        this();
        this._propertyName = propertyName;
        this._propertyType = propertyType;
        this._propertyGroup = propertyGroup;
    }

    public int getMortgageValue()
    {
        return _mortgageValue;
    }

    public void setMortgageValue(int _mortgageValue)
    {
        this._mortgageValue = _mortgageValue;
    }

    public int getOwnerIndex()
    {
        return _ownerIndex;
    }

    public void setOwner(int ownerIndex)
    {
        this._ownerIndex = ownerIndex;
    }

    public Boolean getIsMortgaged()
    {
        return _isMortgaged;
    }

    public void setIsMortgaged(Boolean _isMortgaged)
    {
        this._isMortgaged = _isMortgaged;
    }

    public int getNumberOfHouses()
    {
        return _numberOfHouses;
    }

    public void setNumberOfHouses(int _numberOfHouses)
    {
        this._numberOfHouses = _numberOfHouses;
    }

    public String getPropertyName()
    {
        return _propertyName;
    }

    public void setPropertyName(String _propertyName)
    {
        this._propertyName = _propertyName;
    }

    public PropertyType getPropertyType()
    {
        return _propertyType;
    }

    public void setPropertyType(PropertyType _propertyType)
    {
        this._propertyType = _propertyType;
    }

    public PropertyGroupColour getPropertyGroup()
    {
        return _propertyGroup;
    }

    public void setPropertyGroup(PropertyGroupColour _propertyGroup)
    {
        this._propertyGroup = _propertyGroup;
    }

    public int getPurchasePrice()
    {
        return _purchasePrice;
    }

    public void setPurchasePrice(int _purchasePrice)
    {
        this._purchasePrice = _purchasePrice;
    }

    public int getHousePrice()
    {
        return _housePrice;
    }

    public void setHousePrice(int _housePrice)
    {
        this._housePrice = _housePrice;
    }

    public int[] getRents()
    {
        return _rents;
    }

    public void setRents(int[] _rents)
    {
        this._rents = _rents;
    }
    
    public boolean hasHotel()
    {
        if (_numberOfHouses == 5)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
}
