package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuBarArea extends MenuBar
{
    public Menu fileMenu, editMenu, amountPlayers;
    public MenuItem rules, handCombinationsInfo, changeNames;
    public MenuItem[] menuItemsChangePlayers = new MenuItem[4];

    public MenuBarArea() 
    {
        super();
        this.getStyleClass().add("player"); // CSS style class
        
        fileMenu = new Menu("Game");
        rules = new MenuItem("Poker Rules");
        handCombinationsInfo = new MenuItem("Possible Combinations");
        fileMenu.getItems().addAll(rules, handCombinationsInfo);
        
        editMenu = new Menu("Edit");
        amountPlayers = new Menu("Players...");
        
        for (int i = 0; i < 4; i++)
        {
            menuItemsChangePlayers[i] = new MenuItem((i+1) + " Players");
        }
        
        changeNames = new MenuItem("Change Name");
        amountPlayers.getItems().addAll(menuItemsChangePlayers);
        editMenu.getItems().addAll(amountPlayers, changeNames);
        
        this.getMenus().add(fileMenu);
        this.getMenus().add(editMenu);
    }
}
