package view;


import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.PokerGameModel;
import pokerHandler.PokerGame;

public class PokerGameView
{
    private GridPane players;
    private ControlArea controls;
    private PokerGameModel model;
    private MenuBarArea menuBar;
    private ArrayList<PlayerPane> playerPaneList = new ArrayList<PlayerPane>();
    private ChangeNamePane changeNamePane;
    
    public PokerGameView(Stage stage, PokerGameModel model)
    {
        this.model = model;

        menuBar = new MenuBarArea();
        // Create all of the player panes we need, and put them into an HBox
        players = new GridPane();
        players.setAlignment(Pos.CENTER);

        for (int i = 0; i < PokerGame.NUM_PLAYERS; i++)
        {
            PlayerPane pp = new PlayerPane();
            pp.setPlayer(model.getPlayer(i)); // link to player object in the logic
            playerPaneList.add(pp);
            if (i < 2)
            {
                players.add(pp, i, 0);
            }
            else if (i < 4)
            {
                players.add(pp, i % 2, 1);
            }
        }

        // Create the control area
        controls = new ControlArea();
        controls.linkDeck(model.getDeck()); // link DeckLabel to DeckOfCards in
                                            // the logic

        // Put players and controls into a BorderPane
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(players);
        root.setBottom(controls);
        root.setId("root");
        
        // Disallow resizing - which is difficult to get right with images
        stage.setResizable(false);

        // Create the scene using our layout; then display it
        Scene scene = new Scene(root);
        scene.setCamera(new PerspectiveCamera());
        scene.getStylesheets()
                .add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle("Poker Miniproject");
        stage.setScene(scene);
        stage.getIcons().add(new Image(this.getClass().getClassLoader()
                .getResourceAsStream("images/previewIcon.png")));
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
    }

    public PlayerPane getPlayerPane(int i)
    {
        return (PlayerPane) players.getChildren().get(i);
    }
    
    public MenuItem getMenuItemRules()
    {
        return menuBar.rules;
    }
    
    public MenuItem getMenuItemHandCombinationsInfo()
    {
        return menuBar.handCombinationsInfo;
    }

    public MenuItem[] getMenuItemsChangePlayers()
    {
        return menuBar.menuItemsChangePlayers;
    }

    public MenuItem getMenuItemChangeNames()
    {
        return menuBar.changeNames;
    }

    public Label getInterpratorAndSongNameLabel()
    {
        return controls.lblInterpratorAndSongName;
    }

    public Button getExitButton()
    {
        return controls.btnExit;
    }

    public Button getBackButton()
    {
        return controls.btnBack;
    }

    public Button getChangeMusicButton()
    {
        return controls.btnChangeMusic;
    }

    public Button getStopMusicButton()
    {
        return controls.btnStopMusic;
    }

    public Button getNextButton()
    {
        return controls.btnNext;
    }

    public Button getResetButton()
    {
        return controls.btnResetScores;
    }

    public Button getShuffleButton()
    {
        return controls.btnShuffle;
    }

    public Button getDealButton()
    {
        return controls.btnDeal;
    }
        
    public ChangeNamePane getChangeNamePane()
    {
        return changeNamePane;
    }
    
    public Button getButtonChangePlayerNames()
    {
        return changeNamePane.buttonChangePlayerNames;
    }

    public void resetDisplay()
    {
        players.getChildren().clear();
    }

    public void showHandCombinationsInfo()
    {
        Image img = new Image(this.getClass().getClassLoader().getResourceAsStream("images/pokerHandCombinations.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(img);
        
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(imageView);
        stackPane.setMaxWidth(img.getWidth());
        stackPane.setMaxHeight(img.getWidth());
        Scene secondScene = new Scene(stackPane, img.getWidth(), img.getHeight());

        Stage newWindow = new Stage();
        newWindow.setTitle("Possible Combinations");
        newWindow.setScene(secondScene);

        newWindow.show();
    }

    public void updatePlayerPanes()
    {
        playerPaneList.clear();
        for (int i = 0; i < PokerGame.NUM_PLAYERS; i++)
        {
            PlayerPane pp = new PlayerPane();
            pp.setPlayer(model.getPlayer(i)); // link to player object in the logic
            playerPaneList.add(pp);
            if (i < 2)
            {
                players.add(pp, i, 0);
            }
            else if (i < 4)
            {
                players.add(pp, i % 2, 1);
            }    
        }
    }

    public void showChangePlayerNamesWindow()
    {
        changeNamePane = new ChangeNamePane();
    }
}
