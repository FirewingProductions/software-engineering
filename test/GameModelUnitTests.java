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
import propertytycoon.GameStageType;
import propertytycoon.Instruction;
import propertytycoon.InstructionType;

public class GameModelUnitTests
{
    private GameModel _gameModel;
    
    public GameModelUnitTests()
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
    public void getPlayerWorthTest()
    {
        _gameModel.getPlayers().get(0).setBalance(500);
        _gameModel.getPlayers().get(0).setIsActive(true);
        _gameModel.getProperties().get(0).setOwner(0);
        _gameModel.getProperties().get(0).setNumberOfHouses(3);
        _gameModel.getProperties().get(1).setOwner(0);
        _gameModel.getProperties().get(1).setNumberOfHouses(2);
        _gameModel.getProperties().get(1).setIsMortgaged(true);

        int worth = _gameModel.getPlayerWorth(0);
        
        assertTrue(worth == 700);
    }
    
    @Test
    public void executeInstruction_MoveSpaces_Test()
    {
        _gameModel.executeInstruction(new Instruction("Move 2 spaces", InstructionType.MoveSpaces, 2, 0, 0), 0);
        
        assertTrue(_gameModel.getCurrentPlayerIndex() == 0);
        assertTrue(_gameModel.getPlayers().get(0).getCurrentSpaceIndex() == 2);
        assertTrue(_gameModel.getGameStage() == GameStageType.MovedToNewSpace);
    }
}
