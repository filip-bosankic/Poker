package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.DeckOfCards;

public class ControlArea extends VBox
{
    private DeckLabel lblDeck = new DeckLabel();
    private Region spacer = new Region(); // Empty spacer

    Label lblInterpratorAndSongName = new Label("---");
    Button btnExit = new Button("Exit");
    Button btnBack = new Button("<--");
    Button btnChangeMusic = new Button("Change Music");
    Button btnStopMusic = new Button("Stop Music");
    Button btnNext = new Button("-->");
    Button btnResetScores = new Button("Reset Scores");
    Button btnShuffle = new Button("Shuffle");
    Button btnDeal = new Button("Deal");

    public ControlArea()
    {
        super(); // Always call super-constructor first !!
        
        HBox hBox = new HBox();
        
        btnExit.setId("button");
        btnBack.setId("button");
        btnChangeMusic.setId("button");
        btnStopMusic.setId("button");
        btnNext.setId("button");
        btnResetScores.setId("button");
        btnShuffle.setId("button");
        btnDeal.setId("button");
        
        hBox.getChildren().addAll(lblDeck, spacer, btnExit, btnBack, btnChangeMusic, btnStopMusic, btnNext,
                btnResetScores, btnShuffle, btnDeal);
        hBox.setHgrow(spacer, Priority.ALWAYS); // Use region to absorb resizing
        hBox.setId("controlArea"); // Unique ID in the CSS
        
        this.getChildren().addAll(lblInterpratorAndSongName, hBox);
    }

    public void linkDeck(DeckOfCards deck)
    {
        lblDeck.setDeck(deck);
    }
}
