/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package propertytycoon_2;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            GameModel gameModel = GameController.loadStaticData("properties.xls");

            GameController gameController = new GameController(gameModel);

            gameController.runGame(2,2);
        }
        catch (Exception ex)
        {
            int i = 0;
        }
    }
}
