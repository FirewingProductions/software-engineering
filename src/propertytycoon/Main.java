/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            GameModel gameModel = GameController.loadStaticData("properties.xls");
            GameController gameController = new GameController(gameModel);
            GUI_2 _gameUI = new GUI_2(gameController);
//            _gameUI.populateUI();
            _gameUI.UpdateUI();
        }
        catch (Exception ex)
        {
            int i = 0;
        }
    }
}
