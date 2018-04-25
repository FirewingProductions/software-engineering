/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon_2;

public class PlayerOptionResponse
{
    private PlayerOption _option;
    private Boolean _result;

    public PlayerOptionResponse(PlayerOption _option, Boolean _result)
    {
        this._option = _option;
        this._result = _result;
    }
    
    public PlayerOption getOption()
    {
        return _option;
    }

    public void setOption(PlayerOption _option)
    {
        this._option = _option;
    }

    public Boolean getResult()
    {
        return _result;
    }

    public void setResult(Boolean _result)
    {
        this._result = _result;
    }
}
