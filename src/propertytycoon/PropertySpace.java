/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

/**
 * This class represents a board space holding a property
 */
public class PropertySpace extends Space
{
    private Property _property;

    /**
     * Constructor
     * @param title
     * @param _property
     */
    public PropertySpace(String title, Property _property)
    {
        super(title);
        this._property = _property;
    }

    public Property getProperty()
    {
        return _property;
    }

    public PropertySpace()
    {
    }

    public void setProperty(Property _property)
    {
        this._property = _property;
    }
    
}
