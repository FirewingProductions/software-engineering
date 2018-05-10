/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import propertytycoon.Agent;
import propertytycoon.GameController;
import propertytycoon.GameModel;
import propertytycoon.Instruction;
import propertytycoon.InstructionType;
import propertytycoon.PlayerOption;
import propertytycoon.PlayerOptionType;
import propertytycoon.UserChoiceRequest;
import propertytycoon.UserChoiceResponse;

public class AgentUnitTests
{
    private GameModel _gameModel;
    private GameController _gameController;

    public AgentUnitTests()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
        try
        {
            _gameModel = GameController.loadStaticData("properties_test.xls");
            _gameController = new GameController(_gameModel);
            _gameController.startGame(0, 2);

        } 
        catch (Exception ex)
        {
            Logger.getLogger(GameControllerUnitTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown()
    {
    }

    @Test
    public void makeChoiceTest_ThowDice()
    {
        Agent agent = new Agent(_gameModel);
        
        UserChoiceRequest request = new UserChoiceRequest();
        
        request.setMessage("Start your turn");
        request.addOption(new PlayerOption("Throw dice", PlayerOptionType.ThrowDice, new Instruction("", InstructionType.SaveDiceValues, 2, 5, 0)));
        request.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));
                
        UserChoiceResponse choiceResponse = agent.makeChoice(request);
        
        assertNotNull(choiceResponse);
        assertTrue(choiceResponse.getSelectedOption().getOptionType() == PlayerOptionType.ThrowDice);
    }
    
    @Test
    public void makeChoiceTest_BuyProperty()
    {
        Agent agent = new Agent(_gameModel);
        
        _gameModel.setCurrentPlayerIndex(0);
        
        UserChoiceRequest request = new UserChoiceRequest();
        
        request.setMessage("You may buy the property Crapper Street");
        request.addOption(new PlayerOption("Buy property", PlayerOptionType.BuyProperty, new Instruction("", InstructionType.BuyProperty, 0, 0, 0)));
        request.addOption(new PlayerOption("Auction property", PlayerOptionType.AuctionProperty, new Instruction("", InstructionType.AuctionProperty, 0, 0, 0)));
        request.addOption(new PlayerOption("Leave Game", PlayerOptionType.LeaveGame, new Instruction("", InstructionType.LeaveGame, 0, 0, 0)));

        _gameModel.getCurrentPlayer().setBalance(2000);
        _gameModel.getCurrentPlayer().setCurrentSpaceIndex(1);
        UserChoiceResponse choiceResponse = agent.makeChoice(request);
        assertNotNull(choiceResponse);
        assertTrue(choiceResponse.getSelectedOption().getOptionType() == PlayerOptionType.BuyProperty);

        _gameModel.getCurrentPlayer().setBalance(10);
        choiceResponse = agent.makeChoice(request);
        assertNotNull(choiceResponse);
        assertTrue(choiceResponse.getSelectedOption().getOptionType() == PlayerOptionType.AuctionProperty);
    }
}
