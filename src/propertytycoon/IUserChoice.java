/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

/**
 *
 * @author Alex
 */
public interface IUserChoice
{
    public UserChoiceResponse makeChoice(Enum UserChoiceRequest);
}