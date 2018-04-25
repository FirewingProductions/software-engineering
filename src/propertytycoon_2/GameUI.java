/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon_2;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class GameUI extends JFrame implements IUserChoice,WindowListener,ActionListener 
{
    private GameModel _model;
    // dynamic tables
    private JTable _boardInfo;
    private JTable _playerStatus;
    // options panel
    private JButton _nextTurnButton;
    private JButton _exitGameButton;
    private JComboBox _optionsBox;
    // game start panel
    private JButton _startGameButton;
    private JButton _quitGameButton;
    private JButton _saveGameButton;
    private JButton _loadGameButton;
    private JComboBox _numOfRealPlayersBox;
    private JComboBox _numOfAutoPlayersBox;
    
    public GameUI(GameModel _model)
    {
        super("Property Tycoon");
        this._model = _model;
        // Initialize dynamic tables
        _boardInfo = new JTable(_model.getSpaces().size(),7); // columns: name,colour,instruction,player,owner,no. houses,hotel?
        ArrayList<Player> players = _model.getPlayers();
        _playerStatus = new JTable(_model.getPlayers().size(),4); // columns: token,balance,double?,just visiting
        // Initialize options panel
        _nextTurnButton = new JButton("Go!");
        _exitGameButton = new JButton("Exit");
        _optionsBox = new JComboBox();
        // Initialize game start panel
        _startGameButton = new JButton("Start");
        _quitGameButton = new JButton("Quit");
        _saveGameButton = new JButton("Save Game");
        _loadGameButton = new JButton("Load Game");
        _numOfRealPlayersBox = new JComboBox();
        _numOfAutoPlayersBox = new JComboBox();
    }
    
    public void updateUI()
    {
        ArrayList<Space> spaces = _model.getSpaces();
        ArrayList<Player> players = _model.getPlayers();
        ArrayList<Property> properties = _model.getProperties();
        
        // set up _boardInfo data
        for (int spaceIndex = 0; spaceIndex < spaces.size(); spaceIndex++)
        {
            Space space = spaces.get(spaceIndex);
            if (space instanceof PropertySpace)
            {
                Property property = ((PropertySpace) space).getProperty();
                // _boardInfo column 2 colour
                _boardInfo.setValueAt(property.getPropertyGroup(), spaceIndex, 1);
                // _boardInfo column 5 owner
                _boardInfo.setValueAt(players.get(property.getOwnerIndex()).getToken(), spaceIndex, 4);
               // _boardInfo column 6 no. houses
                _boardInfo.setValueAt(property.getNumberOfHouses(), spaceIndex, 5);
                // _boardInfo column 7 hotel?
                _boardInfo.setValueAt(property.hasHotel(), spaceIndex, 6);
            }       
            // _boardInfo column 1 name
            _boardInfo.setValueAt(space.getTitle(), spaceIndex, 0);
            // _boardInfo column 3 instruction
            if (space instanceof InstructionSpace)
            {
                _boardInfo.setValueAt(((InstructionSpace)space).getInstruction().getMessage(), spaceIndex, 2);
            }
            // _boardInfo column 4 player
            _boardInfo.setValueAt(players.get(_model.getCurrentPlayerIndex()), spaceIndex, 3);
            
        }
        
        // set up _playerStatus data
        for (int playerIndex = 0; playerIndex < players.size(); playerIndex++)
        {
            Player player = players.get(playerIndex);
            // _playerStatus column 1 token
            _playerStatus.setValueAt(player.getToken().toString(), playerIndex, 0);
            // _playerStatus column 2 balance
            _playerStatus.setValueAt(player.getBalance(), playerIndex, 1);
            // _playerStatus column 3 double?
            _playerStatus.setValueAt(player.hasDouble(), playerIndex, 2);
            // _playerStatus column 4 just visiting
            _playerStatus.setValueAt(player.getIsJustVisiting(), playerIndex, 3);
        }
        
        // set up view
        addWindowListener(this);
        setSize(1100,1000);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // dynamic tables
        _boardInfo.setSize(498, 670); // 498
        _playerStatus.setSize(498, 670); // 498        
        _boardInfo.setLocation(20, 1);
        _playerStatus.setLocation(550, 1);
        _boardInfo.setEnabled(false);
        _playerStatus.setEnabled(false);
        add(_boardInfo);
        add(_playerStatus);
        
        // options panel
        _nextTurnButton.addActionListener(this);
        _exitGameButton.addActionListener(this);
        
        _nextTurnButton.setSize(90, 60);
        _exitGameButton.setSize(90, 60);
        _optionsBox.setSize(170, 30);
        
        _nextTurnButton.setLocation(200, 720);
        _exitGameButton.setLocation(300, 720);
        _optionsBox.setLocation(5, 740);
        JLabel label = new JLabel("Options: ");
        label.setDisplayedMnemonic('s');
        label.setLabelFor(_optionsBox);
        
        add(_nextTurnButton);
        add(_exitGameButton);
        add(_optionsBox);
        
        // game start panel
        _startGameButton.addActionListener(this);
        _quitGameButton.addActionListener(this);
        _saveGameButton.addActionListener(this);
        _loadGameButton.addActionListener(this);
        
        _startGameButton.setSize(90, 60);
        _quitGameButton.setSize(90, 60);
        _saveGameButton.setSize(90, 60);
        _loadGameButton.setSize(90, 60); 
        _numOfRealPlayersBox.setSize(170, 30);
        _numOfAutoPlayersBox.setSize(170, 30);
        
        _startGameButton.setLocation(750, 720);
        _quitGameButton.setLocation(850, 720);
        _saveGameButton.setLocation(750, 820);
        _loadGameButton.setLocation(850, 820); 
        _numOfRealPlayersBox.setLocation(540, 720);
        _numOfAutoPlayersBox.setLocation(540, 790);
        
        add(_startGameButton);
        add(_quitGameButton);
        add(_saveGameButton);
        add(_loadGameButton);
        add(_numOfRealPlayersBox);
        add(_numOfAutoPlayersBox);
        
        setLayout(null);
        setResizable(true);
        setVisible(true);
    }
    
    @Override
    public void windowClosing(WindowEvent e)
    {
        dispose();
        System.exit(0);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        
    }
    
    public void windowOpened(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    
    public UserChoiceResponse MakeChoice(UserChoiceRequest request)
    {
        ArrayList<PlayerOptionResponse> responses = new ArrayList<PlayerOptionResponse>();
            
        UserChoiceResponse response = new UserChoiceResponse(responses);
        
        return response;
    }
}
