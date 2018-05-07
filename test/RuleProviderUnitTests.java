

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
    public void startOfTurn_userBackrupt_Test()
    {
        _gameModel.getCurrentPlayer().setBalance(-1);
        RuleResult ruleResult = RuleProvider.CheckRules(_gameModel);
        
        assertTrue(ruleResult.getMessage().contains("bankrupt"));
        assertTrue(ruleResult.getOptions().size() == 1);
        assertTrue(ruleResult.getOptions().get(0).getOptionType() == PlayerOptionType.LeaveGame);
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
        assertTrue(ruleResult.getOptions().get(0).getInstructions().get(1).getInstructionType() == InstructionType.MoveToNextPlayer);
        
        for (Instruction instruction : ruleResult.getOptions().get(0).getInstructions())
        {
            _gameModel.executeInstruction(instruction);
        }
        assertTrue(_gameModel.getCurrentPlayerIndex() == 1);
    }
    
    @Test
    public void movedToNewSpace_cardSpace_Test2()
    {
        _gameModel.setGameStage(GameStageType.MovedToNewSpace);
        _gameModel.getCurrentPlayer().setCurrentSpaceIndex(7);  //opportunoty knocks
        _gameModel.getOpportunityCards().add(0, new Card(new Instruction("Pay a £10 fine or take opportunity knocks", InstructionType.PayFineOrOpportunityKnocks, 10, 0, 0)));
        
        RuleResult ruleResult = RuleProvider.CheckRules(_gameModel);
        
        assertTrue(ruleResult.getMessage().toLowerCase().contains("card"));
        assertTrue(ruleResult.getOptions().size() == 3);

        for (Instruction instruction : ruleResult.getOptions().get(0).getInstructions())
        {
            _gameModel.executeInstruction(instruction);
        }
        assertTrue(_gameModel.getCurrentPlayerIndex() == 0);
        
        ruleResult = RuleProvider.CheckRules(_gameModel);

        for (Instruction instruction : ruleResult.getOptions().get(0).getInstructions())
        {
            _gameModel.executeInstruction(instruction);
        }

        assertTrue(_gameModel.getCurrentPlayerIndex() == 1);

    }
}
