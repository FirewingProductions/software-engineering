/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

/**
 * These are the various types of instructions
 */
public enum InstructionType
{

    MoveSpaces, 
    PayFine, 
    PayFineOrOpportunityKnocks,
    PayBank,
    ReceiveMoney, 
    ReceiveMoneyFromEachPlayer, 
    GoDirectTo,
    GoToJail,
    SetJustVisitingFlag, 
    SetDoubleThrown, 
    ClearDoubleThrown, 
    SetSkipTurns, 
    ClearSkipTurns,
    GoBackTo,
    GoBackNumSpaces,
    AdvanceTo,
    PayForRepairs,
    CollectFines,
    GetOutOfJailFree,
    SetPlayerToBankrupt,
    SaveDiceValues, 
    BuyProperty, 
    AuctionProperty, 
    ImproveProperty, 
    PayJailFine, 
    SellHouse, 
    SellProperty,
    MortgageProperty,
    UnMortgageProperty,
    SelectOpportunityKnocks,
    MoveToNextPlayer,
    LeaveGame,
    PayRent,
    SkipTurn,
    TradeProperties,
    SetEndOfTurn
}
