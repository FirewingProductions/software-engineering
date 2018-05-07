/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

public class Space 
{
    protected String _title;

    public Space(String _title)
    {
        this._title = _title;
    }
    
    public Space()
    {

    }

    public void setTitle(String _title)
    {
        this._title = _title;
    }

    public String getTitle()
    {
        return _title;
    }
}
