/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

public class PlayerOptionResponse
{
    private PlayerOption _selectedOption;

    public PlayerOptionResponse(PlayerOption selectedOption)
    {
        _selectedOption = selectedOption;
    }

    public PlayerOption getSelectedOption()
    {
        return _selectedOption;
    }

    public void setSelectedOption(PlayerOption _selectedOption)
    {
        this._selectedOption = _selectedOption;
    }
    

}
