/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import propertytycoon.Card;
import propertytycoon.GameController;
import propertytycoon.GameModel;
import propertytycoon.GameStageType;
import propertytycoon.Instruction;
import propertytycoon.InstructionType;
import propertytycoon.PlayerOptionType;
import propertytycoon.RuleProvider;
import propertytycoon.RuleResult;

public class RuleProviderUnitTests
{
    private GameModel _gameModel;
    private GameController _gameController;

    public RuleProviderUnitTests()
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
            _gameController.startGame(3, 2);
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
    public void anyStage_userBackrupt_Test()
    {
        _gameModel.getCurrentPlayer().setBalance(-1);
        RuleResult ruleResult = RuleProvider.CheckRules(_gameModel);
        
        assertTrue(ruleResult.getMessage().contains("bankrupt"));
        assertTrue(ruleResult.getOptions().size() == 1);
        assertTrue(ruleResult.getOptions().get(0).getOptionType() == PlayerOptionType.LeaveGame);
    }
    
    @Test
    public void anyStage_negativeBalance_Test()
    {
        _gameModel.setGameStage(GameStageType.MovedToNewSpace);  //should work at any stage
        _gameModel.setCurrentPlayerIndex(0);
        _gameModel.getCurrentPlayer().setBalance(-1);
        _gameModel.getProperties().get(0).setOwnerIndex(0); //brown
        _gameModel.getProperties().get(1).setOwnerIndex(0); //brown
        _gameModel.getProperties().get(0).setNumberOfHouses(1);
        _gameModel.getProperties().get(3).setOwnerIndex(0);
        
        RuleResult ruleResult = RuleProvider.CheckRules(_gameModel);
        
        assertTrue(ruleResult.getMessage().contains("You have run out of money"));
        assertTrue(ruleResult.getOptions().size() == 4);
        assertTrue(ruleResult.getOptions().get(0).getOptionType() == PlayerOptionType.SellHouse);
        assertTrue(ruleResult.getOptions().get(1).getOptionType() == PlayerOptionType.SellProperty);
        assertTrue(ruleResult.getOptions().get(2).getOptionType() == PlayerOptionType.MortgageProperty);
        assertTrue(ruleResult.getOptions().get(3).getOptionType() == PlayerOptionType.LeaveGame);
    }
    
    @Test
    public void diceThrown_double_Test()
    {
        _gameModel.getCurrentPlayer().setBalance(1500);
        _gameModel.setGameStage(GameStageType.DiceThrown);
        _gameModel.getPlayers().get(0).setDiceValue1(3);
        _gameModel.getPlayers().get(0).setDiceValue2(3);
        
        RuleResult ruleResult = RuleProvider.CheckRules(_gameModel);
        for (Instruction instruction : ruleResult.getOptions().get(0).getInstructions())
        {
            _gameModel.executeInstruction(instruction);
        }
        
        assertTrue(_gameModel.getCurrentPlayer().getWasDoubleThrown() == true);
        assertTrue(_gameModel.getCurrentPlayer().getExtraTurns() == 1);
        assertTrue(_gameModel.getCurrentPlayerIndex() == 0);
        assertTrue(_gameModel.getGameStage() == GameStageType.MovedToNewSpace);
    }
    
    @Test
    public void diceThrown_double_double_Test()
    {
        _gameModel.getPlayers().get(0).setBalance(1500);
        _gameModel.getPlayers().get(0).setWasDoubleThrown(true);
        _gameModel.setGameStage(GameStageType.DiceThrown);
        _gameModel.getPlayers().get(0).setDiceValue1(3);
        _gameModel.getPlayers().get(0).setDiceValue2(3);
        
        RuleResult ruleResult = RuleProvider.CheckRules(_gameModel);
        for (Instruction instruction : ruleResult.getOptions().get(0).getInstructions())
        {
            _gameModel.executeInstruction(instruction);
        }
        
        assertTrue(_gameModel.getPlayers().get(0).getWasDoubleThrown() == false);
        assertTrue(_gameModel.getPlayers().get(0).getExtraTurns() == 0);
        assertTrue(_gameModel.getCurrentPlayerIndex() == 0);
        assertTrue(_gameModel.getPlayers().get(0).getCurrentSpaceIndex() == 10);  //in jail
    }    
            
    @Test
    public void movedToNewSpace_cardSpace_Test1()
    {
        _gameModel.setGameStage(GameStageType.MovedToNewSpace);
        _gameModel.getCurrentPlayer().setCurrentSpaceIndex(7);  //opportunoty knocks
        _gameModel.getOpportunityCards().add(0, (new Card(new Instruction("Fined £15 for speeding", InstructionType.PayFine, 15, 0, 0))));
        
        RuleResult ruleResult = RuleProvider.CheckRules(_gameModel);
        
        assertTrue(ruleResult.getMessage().toLowerCase().contains("card"));
        assertTrue(ruleResult.getOptions().size() == 2);
        InstructionType instructionType = ruleResult.getOptions().get(0).getInstructions().get(0).getInstructionType();
        assertTrue(instructionType == InstructionType.PayFine);
        
        for (Instruction instruction : ruleResult.getOptions().get(0).getInstructions())
        {
            _gameModel.executeInstruction(instruction);
        }
        assertTrue(_gameModel.getCurrentPlayerIndex() == 0);
    }
    
    @Test
    public void movedToNewSpace_cardSpace_Test2()
    {
        _gameModel.setGameStage(GameStageType.MovedToNewSpace);
        _gameModel.getCurrentPlayer().setCurrentSpaceIndex(7);  //opportunoty knocks
        _gameModel.getOpportunityCards().add(0, (new Card(new Instruction("Fined £15 for speeding", InstructionType.PayFine, 15, 0, 0))));
        
        RuleResult ruleResult = RuleProvider.CheckRules(_gameModel);
        
        assertTrue(ruleResult.getMessage().toLowerCase().contains("card"));
        assertTrue(ruleResult.getOptions().size() == 2);
        assertTrue(ruleResult.getOptions().get(0).getInstructions().get(0).getInstructionType() == InstructionType.PayFine);
        
        for (Instruction instruction : ruleResult.getOptions().get(0).getInstructions())
        {
            _gameModel.executeInstruction(instruction);
        }
        assertTrue(_gameModel.getCurrentPlayerIndex() == 0);
    }    
    
    @Test
    public void movedToNewSpace_cardSpace_Test3()
    {
        _gameModel.setGameStage(GameStageType.MovedToNewSpace);
        _gameModel.getCurrentPlayer().setCurrentSpaceIndex(7);  //opportunoty knocks
        _gameModel.getOpportunityCards().add(0, new Card(new Instruction("Move Back 3 Spaces", InstructionType.GoBackNumSpaces, 3, 0, 0)));
        
        RuleResult ruleResult = RuleProvider.CheckRules(_gameModel);
        
        assertTrue(ruleResult.getMessage().toLowerCase().contains("card"));
        assertTrue(ruleResult.getOptions().size() == 2);
        assertTrue(ruleResult.getOptions().get(0).getInstructions().get(0).getInstructionType() == InstructionType.GoBackNumSpaces);
        
        for (Instruction instruction : ruleResult.getOptions().get(0).getInstructions())
        {
            _gameModel.executeInstruction(instruction);
        }
        assertTrue(_gameModel.getPlayers().get(0).getCurrentSpaceIndex() == 4);
        assertTrue(_gameModel.getCurrentPlayerIndex() == 0);
    }
   
    @Test
    public void movedToNewSpace_propertySpace_payRent_Test1()
    {
        _gameModel.setGameStage(GameStageType.MovedToNewSpace);  //should work at any stage
        _gameModel.setCurrentPlayerIndex(0);
        _gameModel.getCurrentPlayer().setBalance(1000);
        _gameModel.getCurrentPlayer().setCurrentSpaceIndex(1);
        _gameModel.getProperties().get(0).setOwnerIndex(1); //brown
        _gameModel.getProperties().get(1).setOwnerIndex(1); //brown
        _gameModel.getProperties().get(0).setNumberOfHouses(1);
        
        RuleResult ruleResult = RuleProvider.CheckRules(_gameModel);
        
        assertTrue(ruleResult.getMessage().contains("You must pay rent"));
        assertTrue(ruleResult.getOptions().size() == 2);
        assertTrue(ruleResult.getOptions().get(0).getOptionType() == PlayerOptionType.PayRent);
        int rentDue = ruleResult.getOptions().get(0).getInstructions().get(0).getAmount1();
        assertTrue(rentDue == 10);
        int ownerIndex = ruleResult.getOptions().get(0).getInstructions().get(0).getTargetSpaceIndex();
        assertTrue(ownerIndex == 1);
        assertTrue(ruleResult.getOptions().get(1).getOptionType() == PlayerOptionType.LeaveGame);
    }    

    @Test
    public void movedToNewSpace_propertySpace_payRent_Utility()
    {
        _gameModel.setGameStage(GameStageType.MovedToNewSpace);  //should work at any stage
        _gameModel.setCurrentPlayerIndex(0);
        _gameModel.getCurrentPlayer().setBalance(1000);
        _gameModel.getCurrentPlayer().setCurrentSpaceIndex(1);
        _gameModel.getProperties().get(0).setOwnerIndex(1); //brown
        _gameModel.getProperties().get(1).setOwnerIndex(1); //brown
        _gameModel.getProperties().get(0).setNumberOfHouses(1);
        
        RuleResult ruleResult = RuleProvider.CheckRules(_gameModel);
        
        assertTrue(ruleResult.getMessage().contains("You must pay rent"));
        assertTrue(ruleResult.getOptions().size() == 2);
        assertTrue(ruleResult.getOptions().get(0).getOptionType() == PlayerOptionType.PayRent);
        int rentDue = ruleResult.getOptions().get(0).getInstructions().get(0).getAmount1();
        assertTrue(rentDue == 10);
        int ownerIndex = ruleResult.getOptions().get(0).getInstructions().get(0).getTargetSpaceIndex();
        assertTrue(ownerIndex == 1);
        assertTrue(ruleResult.getOptions().get(1).getOptionType() == PlayerOptionType.LeaveGame);
    }    
}
