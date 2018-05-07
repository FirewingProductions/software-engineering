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
import propertytycoon.GameController;
import propertytycoon.GameModel;
import propertytycoon.PlayerOptionType;
import propertytycoon.UserChoiceRequest;
import propertytycoon.UserChoiceResponse;

public class GameControllerUnitTests
{
    
    private GameModel _gameModel;
    private GameController _gameController;
    
    public GameControllerUnitTests()
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
        } 
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(GameControllerUnitTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown()
    {
    }

    @Test
    public void loadStaticDataTest()
    {
        assertNotNull(_gameModel);
        assertTrue(_gameModel.getPlayers().size() == 6);
        assertTrue(_gameModel.getSpaces().size() == 40);
        assertTrue(_gameModel.getProperties().size() == 28);
        assertTrue(_gameModel.getOpportunityCards().size() == 16);
        assertTrue(_gameModel.getPotLuckCards().size() == 16);
    }
    
    @Test
    public void startGameTest()
    {
        try
        {
            _gameController.startGame(3, 2);
        } 
        catch (Exception ex)
        {
            assertTrue("Exception thrown - " + ex.getMessage(), false);
        }

        assertTrue(_gameModel.getPlayers().get(0).getIsActive() == true);
        assertTrue(_gameModel.getPlayers().get(0).getIsAuto() == false);
        
        assertTrue(_gameModel.getPlayers().get(1).getIsActive() == true);
        assertTrue(_gameModel.getPlayers().get(1).getIsAuto() == false);
        
        assertTrue(_gameModel.getPlayers().get(2).getIsActive() == true);
        assertTrue(_gameModel.getPlayers().get(2).getIsAuto() == false);
        
        assertTrue(_gameModel.getPlayers().get(3).getIsActive() == true);
        assertTrue(_gameModel.getPlayers().get(3).getIsAuto() == true);
        
        assertTrue(_gameModel.getPlayers().get(4).getIsActive() == true);
        assertTrue(_gameModel.getPlayers().get(4).getIsAuto() == true);
        
        assertTrue(_gameModel.getPlayers().get(5).getIsActive() == false);
    }
    
    @Test
    public void processUserResponseTest()
    {
        try
        {
            _gameController.startGame(3, 2);
        } 
        catch (Exception ex)
        {
            assertTrue("Exception thrown - " + ex.getMessage(), false);
        }
        UserChoiceResponse choiceResponse = new UserChoiceResponse(null);
        UserChoiceRequest choiceRequest = _gameController.processUserResponse(choiceResponse);
        
        assertNotNull(choiceRequest);
        assertTrue(choiceRequest.getOptions().size() == 2);
        assertTrue(choiceRequest.getMessage().equals("Start your turn"));
        assertTrue(choiceRequest.getOptions().get(0).getOptionType() == PlayerOptionType.ThrowDice);
        assertTrue(choiceRequest.getOptions().get(1).getOptionType() == PlayerOptionType.LeaveGame);
    }

    @Test
    public void getAbridgedGameOverTest()
    {
        _gameModel.setIsAbridgedGame(true);
        _gameController.setGameStartMillis(System.currentTimeMillis() - 2001);
        _gameModel.setAbridgedGameLengthMillis(2000);
        _gameModel.updateTimeLeft(_gameController.getGameStartMillis());
        
        _gameModel.getPlayers().get(0).setBalance(500);
        _gameModel.getPlayers().get(0).setIsActive(true);
        _gameModel.getProperties().get(0).setOwner(0);
        _gameModel.getProperties().get(0).setNumberOfHouses(3);
        _gameModel.getProperties().get(1).setOwner(0);
        _gameModel.getProperties().get(1).setNumberOfHouses(2);
        _gameModel.getProperties().get(1).setIsMortgaged(true);

        _gameModel.getPlayers().get(1).setBalance(1500);
        _gameModel.getPlayers().get(1).setIsActive(true);
        _gameModel.getProperties().get(2).setOwner(1);
        _gameModel.getProperties().get(2).setNumberOfHouses(4);
        _gameModel.getProperties().get(3).setOwner(1);
        _gameModel.getProperties().get(3).setNumberOfHouses(3);
        _gameModel.getProperties().get(3).setIsMortgaged(false);

        assertTrue(_gameModel.isGameOver() == true);
        int winnwerIndex = _gameModel.getWinnerIndex();
        assertTrue(winnwerIndex == 1);
    }
 
}
