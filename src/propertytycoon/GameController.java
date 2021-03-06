/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import jxl.Sheet;
import jxl.Workbook;

/**
 * The GameController provides methods that the GUI can call to start the game
 * and to process the latest player choice.
 */
public class GameController 
{
    private GameModel _gameModel;
    private Agent _agent;
    private static Random _random = new Random(System.currentTimeMillis());
    private long _gameStartMillis;

    /**
     * Constructor
     * @param gameModel
     */
    public GameController(GameModel gameModel)
    {
        _gameModel = gameModel;
        _agent = new Agent(_gameModel);
    }

    public GameModel getGameModel()
    {
        return _gameModel;
    }

    public void setGameModel(GameModel _gameModel)
    {
        this._gameModel = _gameModel;
    }

    public long getGameStartMillis()
    {
        return _gameStartMillis;
    }

    public void setGameStartMillis(long _gameStartMillis)
    {
        this._gameStartMillis = _gameStartMillis;
    }
        
    /**
     * Starts the game with the specified players
     * @param numHumanPlayers
     * @param numAutoPlayers
     * @throws Exception
     */
    public void startGame(int numHumanPlayers, int numAutoPlayers) throws Exception
    {
        int totalPlayers = numHumanPlayers + numAutoPlayers;
        if (totalPlayers < 1 || totalPlayers > 6)
        {
            throw new Exception("Total players must be between 2 and 6");
        }
        _gameModel.initialise(numHumanPlayers, numAutoPlayers);
        _gameStartMillis = System.currentTimeMillis();
        
    }

    /**
     * Starts the game with the specified game model
     * @param gameModel
     */
    public void startGame(GameModel gameModel)
    {
        _gameModel = gameModel;
        _gameStartMillis = System.currentTimeMillis();
    }
    
    /**
     * This method is the core of the game. It is called by the UI to process the selected option
     * and to determine the next set of option
     * @param choiceResponse
     * @return The nest set of player options
     */
    public UserChoiceRequest processUserResponse(UserChoiceResponse choiceResponse)
    {
        while (!_gameModel.isGameOver())
        {
            _gameModel.updateTimeLeft(_gameStartMillis);
            PlayerOption option = choiceResponse.getSelectedOption();
            
            //execute all instructions in the selected option and update the logs and user messages
            StringBuilder latestLogEntries = new StringBuilder();
            StringBuilder instructionMessages = new StringBuilder();
            if (option != null)
            {
                for (Instruction instruction : option.getInstructions())
                {
                    instruction.setMessage(option.getTitle());
                    if (option.getOptionType() == PlayerOptionType.AuctionProperty || option.getOptionType() == PlayerOptionType.TradeProperties)
                    {
                        instruction.setAmount(option.getAmount1());
                        instruction.setAmount2(option.getAmount2());
                        instruction.setTargetSpaceIndex(option.getAmount3());
                    }
                    
                    String message = _gameModel.executeInstruction(instruction);
                    
                    instructionMessages.append(message);
                    if (latestLogEntries.length() > 0)
                        latestLogEntries.append("\r\n");
                    latestLogEntries.append(instruction.toString(_gameModel.getCurrentPlayer().getToken().toString()));
                }
            }
             
            //get the next set of options from the Rule Provider
            RuleResult ruleResult = RuleProvider.CheckRules(_gameModel);

            Player currentPlayer = _gameModel.getCurrentPlayer();
            ArrayList<PlayerOption> options = ruleResult.getOptions();

            //send the options to the UI or Agent (for auto players)
            UserChoiceRequest request = new UserChoiceRequest(options, ruleResult.getMessage() + instructionMessages.toString());
            request.setLatestLogEntry(latestLogEntries.toString());
            _gameModel.getGlobalInstructionLog().add(latestLogEntries.toString());
            if (!currentPlayer.getIsAuto())
            {
                return request;
            }
            else
            {
                choiceResponse = _agent.makeChoice(request);
            }
        }
        return new UserChoiceRequest(new ArrayList<>(),"");
    }
    
    //used to initialise the game model from the config spreadsheet

    /**
     * Loads the GameModel from the configuration Excel Spreadsheet
     * Note that Cards were too complex to read from the spreadsheet and have been hard-coded
     * @param filename
     * @return Game Model
     * @throws FileNotFoundException
     */
    public static GameModel loadStaticData(String filename) throws FileNotFoundException
    {
        GameModel gameModel = new GameModel();
        try
        {
            FileInputStream fs = new FileInputStream(filename);
            Workbook wb = Workbook.getWorkbook(fs); 
     
            // TO get the access to the sheet
            Sheet sh = wb.getSheet("Sheet1");

            // To get the number of rows present in sheet -4 are the rows wich dont have the needed data
            int totalNoOfRows = sh.getRows()-4;
            
            //creating an array of spaces 
            ArrayList<Space> spaces = new ArrayList<Space>();
            ArrayList<Property> properties = new ArrayList<Property>();
            
            String[] rentStrings = new String[6];
            
            for (int x = 0; x < totalNoOfRows; x++) 
            {
                Space space = null;
                
                //extract row values as strings
                String name = sh.getCell(1,x+4).getContents();
                String colourStr = sh.getCell(3,x+4).getContents();
                String action = sh.getCell(4,x+4).getContents();
                String canBeBoughtStr = sh.getCell(5,x+4).getContents();
                String propertyCostStr = sh.getCell(7,x+4).getContents();
                rentStrings[0] = sh.getCell(8,x+4).getContents();
                rentStrings[1] = sh.getCell(10,x+4).getContents();
                rentStrings[2] = sh.getCell(11,x+4).getContents();
                rentStrings[3] = sh.getCell(12,x+4).getContents();
                rentStrings[4] = sh.getCell(13,x+4).getContents();
                rentStrings[5] = sh.getCell(14,x+4).getContents();
                String houseCostStr = sh.getCell(15,x+4).getContents();
                String mortgageValueStr = sh.getCell(16,x+4).getContents();
                
                if (name.equals("Go"))
                {
                    String amountStr = action.substring(9);
                    int amount = Integer.parseInt(amountStr);
                    space = new GoSpace(name, new Instruction(action, InstructionType.ReceiveMoney, amount, 0, 0));
                }
                else if (name.equals("Jail/Just visiting"))
                {
                    space = new JailSpace(name);
                    gameModel.setJailSpaceIndex(spaces.size());
                }
                else if (name.equals("Pot Luck"))
                {
                    space = new CardSpace(name, CardType.PotLuck);
                }
                else if (name.equals("Opportunity Knocks"))
                {
                    space = new CardSpace(name, CardType.Opportunity);
                }
                else if (canBeBoughtStr.equals("Yes"))
                {
                    int propertyPrice = Integer.parseInt(propertyCostStr);
                    int housePrice = Integer.parseInt(houseCostStr);
                    int motgageValue = Integer.parseInt(mortgageValueStr);
                    
                    if (colourStr.equals("Utilities"))
                    {
                        Property property = new Property(name, PropertyType.Utility, PropertyGroupColour.White);
                        int[] rents = property.getRents();
                        for (int i = 1;i < 3;++i)
                        {
                            rents[i] = Integer.parseInt(rentStrings[i]);
                        }
                        property.setPurchasePrice(propertyPrice);
                        property.setHousePrice(housePrice);
                        property.setMortgageValue(motgageValue);
                        space = new PropertySpace(name, property);
                        properties.add(property);
                    }
                    else if (colourStr.equals("Station"))
                    {
                        Property property = new Property(name, PropertyType.Station, PropertyGroupColour.White);
                        int[] rents = property.getRents();
                        for (int i = 1;i < 5;++i)
                        {
                            rents[i] = Integer.parseInt(rentStrings[i]);
                        }
                        property.setPurchasePrice(propertyPrice);
                        property.setHousePrice(housePrice);
                        property.setMortgageValue(motgageValue);
                        space = new PropertySpace(name, property);
                        properties.add(property);
                    }
                    else
                    {
                        if (colourStr.equals("Deep blue"))
                            colourStr = "DarkBlue";
                        PropertyGroupColour colour = PropertyGroupColour.valueOf(colourStr);
                        Property property = new Property(name, PropertyType.Standard, colour);
                        int[] rents = property.getRents();
                        for (int i = 0;i < rentStrings.length;++i)
                        {
                            rents[i] = Integer.parseInt(rentStrings[i]);
                        }
                        property.setPurchasePrice(propertyPrice);
                        property.setHousePrice(housePrice);
                        property.setMortgageValue(motgageValue);
                        space = new PropertySpace(name, property);
                        properties.add(property);
                    }
                }
                else
                {
                    if (name.equals("Free Parking"))
                    {
                        Instruction instruction = new Instruction(name + " - " + action, InstructionType.CollectFines, 0, 0, 0);
                        space = new InstructionSpace(name, instruction);
                    }
                    else if (name.equals("Go to Jail"))
                    {
                        Instruction instruction = new Instruction(action, InstructionType.GoToJail, 0, 0, 0);
                        space = new InstructionSpace(name, instruction);
                    }
                    else if (action.startsWith("Collect "))
                    {
                        int amount = Integer.parseInt(action.substring(9));
                        Instruction instruction = new Instruction(action, InstructionType.ReceiveMoney, amount, 0, 0);
                        space = new InstructionSpace(name, instruction);
                    }
                    else
                    if (action.startsWith("Pay "))
                    {
                        int amount = Integer.parseInt(action.substring(5));
                        Instruction instruction = new Instruction(action, InstructionType.PayFine, amount, 0, 0);
                        space = new InstructionSpace(name, instruction);
                    }
                }
                spaces.add(space);
            }
            
            //cards are too complex to load from excel, so need to be hard coded
            ArrayList<Card> potLuckCards = new ArrayList<Card>();
            potLuckCards.add(new Card(new Instruction("You inherit £100", InstructionType.ReceiveMoney, 100, 0, 0)));
            potLuckCards.add(new Card(new Instruction("You have won 2nd prize in a beauty contest, collect £20", InstructionType.ReceiveMoney, 20, 0, 0)));
            potLuckCards.add(new Card(new Instruction("Go back to Crapper Street", InstructionType.GoBackTo, 0, 0, 1)));
            potLuckCards.add(new Card(new Instruction("Student loan refund. Collect £20", InstructionType.ReceiveMoney, 20, 0, 0)));
            potLuckCards.add(new Card(new Instruction("Bank error in your favour. Collect £200", InstructionType.ReceiveMoney, 200, 0, 0)));
            potLuckCards.add(new Card(new Instruction("Pay bill for text books of £100", InstructionType.PayBank, 100, 0, 0)));
            potLuckCards.add(new Card(new Instruction("Mega late night taxi bill pay £50", InstructionType.PayBank, 50, 0, 0)));
            potLuckCards.add(new Card(new Instruction("Advance to go", InstructionType.AdvanceTo, 0, 0, 0)));
            potLuckCards.add(new Card(new Instruction("From sale of Bitcoin you get £50", InstructionType.ReceiveMoney, 50, 0, 0)));
            potLuckCards.add(new Card(new Instruction("Pay a £10 fine or take opportunity knocks", InstructionType.PayFineOrOpportunityKnocks, 10, 0, 0)));
            potLuckCards.add(new Card(new Instruction("Pay insurance fee of £50", InstructionType.PayFine, 50, 0, 0)));
            potLuckCards.add(new Card(new Instruction("Savings bond matures, collect £100", InstructionType.ReceiveMoney, 100, 0, 0)));
            potLuckCards.add(new Card(new Instruction("Go to jail. Do not pass GO, do not collect £200", InstructionType.GoToJail, 0, 0, 0)));
            potLuckCards.add(new Card(new Instruction("Received interest on shares of £25", InstructionType.ReceiveMoney, 25, 0, 0)));
            potLuckCards.add(new Card(new Instruction("It's your birthday. Collect £10 from each player", InstructionType.ReceiveMoneyFromEachPlayer, 10, 0, 0)));
            potLuckCards.add(new Card(new Instruction("Get out of jail free", InstructionType.GetOutOfJailFree, 0, 0, 0)));
            
            ArrayList<Card> opportunityCards = new ArrayList<Card>();
            opportunityCards.add(new Card(new Instruction("Bank pays you divided of £50", InstructionType.ReceiveMoney, 50, 0, 0)));
            opportunityCards.add(new Card(new Instruction("You have won a lip sync battle. Collect £100", InstructionType.ReceiveMoney, 100, 0, 0)));
            opportunityCards.add(new Card(new Instruction("Advance to Turing Heights", InstructionType.AdvanceTo, 50, 0, 39)));
            opportunityCards.add(new Card(new Instruction("Advance to Han Xin Gardens. If you pass GO, collect £200", InstructionType.AdvanceTo, 0, 0, 24)));
            opportunityCards.add(new Card(new Instruction("Fined £15 for speeding", InstructionType.PayFine, 15, 0, 0)));
            opportunityCards.add(new Card(new Instruction("Pay university fees of £150", InstructionType.PayBank, 150, 0, 0)));
            opportunityCards.add(new Card(new Instruction("Take a trip to Hove station. If you pass GO collect £200", InstructionType.AdvanceTo, 0, 0, 15)));
            opportunityCards.add(new Card(new Instruction("Loan matures, collect £150", InstructionType.ReceiveMoney, 150, 0, 0)));
            opportunityCards.add(new Card(new Instruction("You are assessed for repairs, £40/house, £115/hotel", InstructionType.PayForRepairs, 40, 115, 0)));
            opportunityCards.add(new Card(new Instruction("Advance to GO", InstructionType.AdvanceTo, 0, 0, 0)));
            opportunityCards.add(new Card(new Instruction("You are assessed for repairs, £25/house, £100/hotel", InstructionType.PayForRepairs, 25, 100, 0)));
            opportunityCards.add(new Card(new Instruction("Go back 3 spaces", InstructionType.GoBackNumSpaces, 3, 0, 0)));
            opportunityCards.add(new Card(new Instruction("Advance to Skywalker Drive. If you pass GO collect £200", InstructionType.AdvanceTo, 0, 0, 11)));
            opportunityCards.add(new Card(new Instruction("Go to jail. Do not pass GO, do not collect £200", InstructionType.GoToJail ,0, 0, 0)));
            opportunityCards.add(new Card(new Instruction("Drunk in charge of a skateboard. Fine £20", InstructionType.PayFine, 20, 0, 0)));
            opportunityCards.add(new Card(new Instruction("Get out of jail free", InstructionType.GetOutOfJailFree, 0, 0, 0)));
            
            gameModel.setSpaces(spaces);
            gameModel.setProperties(properties);
            gameModel.setPotLuckCards(shuffleCards(potLuckCards));
            gameModel.setOpportunityCards(shuffleCards(opportunityCards));
            
            gameModel.initialise(0,0);
        }
        catch (Exception ex)
        {
            int i = 0;
        }
        
        return gameModel;
    }
    
    //Shuffles the cards
    private static ArrayList<Card> shuffleCards(ArrayList<Card> cards)
    {
        ArrayList<Card> result = new ArrayList<Card>();
        for (Card card : cards)
        {
            int count = result.size();
            if (count > 0)
            {
                int insertIndex = _random.nextInt(count);
                result.add(insertIndex, card);
            }
            else
            {
                result.add(card);
            }
        }
        return result;
    }
    
    /**
     * Saves the current game to XML
     * @param gameModel
     * @param filename
     */
    public static void SaveGame(GameModel gameModel, String filename)
    {
        XMLEncoder encoder = null;
        try
        {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
        }
        catch (Exception ex)
        {
            System.out.println("ERROR: While Creating or Opening the File" + ex.toString());
            return;
        }
        encoder.writeObject(gameModel);
        encoder.close();
    }

    /**
     * Reads the current game from XML
     * @param filename
     * @return
     */
    public static GameModel LoadGame(String filename)
    {
        XMLDecoder decoder = null;
        try 
        {
            decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
        } 
        catch (Exception ex) 
        {
            System.out.println("ERROR: File not found" + ex.toString());
            return null;
        }
        GameModel gameModel = (GameModel)decoder.readObject();
        decoder.close();
        return gameModel;
    }
}
