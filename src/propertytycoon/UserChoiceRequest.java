/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.util.ArrayList;

public class UserChoiceRequest
{
    private ArrayList<PlayerOption> _options;
    private String _message;
    private String _latestLogEntry;

    public UserChoiceRequest(ArrayList<PlayerOption> options, String message)
    {
        this();
        this._options = options;
        this._message = message;
    }

    public String getLatestLogEntry()
    {
        return _latestLogEntry;
    }

    public void setLatestLogEntry(String _latestLogEntry)
    {
        this._latestLogEntry = _latestLogEntry;
    }

    public String getMessage()
    {
        return _message;
    }

    public void addOption(PlayerOption option)
    {
        _options.add(option);
    }
    
    public void setMessage(String _message)
    {
        this._message = _message;
    }
    
    public ArrayList<PlayerOption> getOptions()
    {
        return _options;
    }

    public void setOptions(ArrayList<PlayerOption> _options)
    {
        this._options = _options;
    }

    public UserChoiceRequest()
    {
        _options = new ArrayList<PlayerOption>();
    }
    
}
