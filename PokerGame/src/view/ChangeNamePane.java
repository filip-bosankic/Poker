package view;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pokerHandler.PokerGame;

public class ChangeNamePane extends GridPane
{
    public ArrayList<TextField> textFieldPlayerNameList = new ArrayList<TextField>();
    public Button buttonChangePlayerNames;
    public Stage stage;
    
    public ChangeNamePane()
    {
        // Dynamische Pane erzeugen je nach MenuItem
        super();
        stage = new Stage();

        for (int i = 0; i < PokerGame.NUM_PLAYERS; i++)
        {
            textFieldPlayerNameList.add(new TextField());
            this.add(new Label("Player" + (i + 1)), 0, i + 1);
            this.add(textFieldPlayerNameList.get(textFieldPlayerNameList.size()-1), 1, i + 1);
        }

        buttonChangePlayerNames = new Button("Change Names");
        this.add(buttonChangePlayerNames, 0, PokerGame.NUM_PLAYERS + 1);

        stage.getIcons().add(new Image(this.getClass().getClassLoader()
                .getResourceAsStream("images/previewIcon.png")));

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Change Player Names");
        stage.setMinWidth(10);

        Scene scene = new Scene(this);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
