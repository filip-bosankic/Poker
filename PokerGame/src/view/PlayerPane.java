package view;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Card;
import model.Combination;
import model.HandType;
import model.Player;

public class PlayerPane extends VBox
{
    private Label lblName = new Label();
    private HBox hboxCards = new HBox();
    private Label lblEvaluation = new Label("---");
    private Label lblWinnerCounter = new Label("Rounds won: 0");
    private Label lblWinOrLost = new Label("---");

    // Link to player object
    private Player player;

    public PlayerPane()
    {
        super(); // Always call super-constructor first !!
        this.getStyleClass().add("player"); // CSS style class

        // Add child nodes
        this.getChildren().addAll(lblName, hboxCards, lblEvaluation,
                lblWinnerCounter, lblWinOrLost);

        // Add CardLabels for the cards
        for (int i = 0; i < 5; i++)
        {
            Label lblCard = new CardLabel();
            hboxCards.getChildren().add(lblCard);
        }
    }

    public void setPlayer(Player player)
    {
        this.player = player;
        updatePlayerDisplay(false); // Immediately display the player information
    }

    public void updatePlayerDisplay(boolean animation)
    {
        lblName.setText(player.getMadeUpName());
        for (int i = 0; i < Player.HAND_SIZE; i++)
        {
            Card card = null;
            if (player.getCards().size() > i)
                card = player.getCards().get(i);
            CardLabel cl = (CardLabel) hboxCards.getChildren().get(i);
            cl.updateCard(card, animation);
            Combination combination = player.evaluateHand();
            if (combination != null)
            {
                lblEvaluation.setText(combination.getHandType().toString());
                lblWinOrLost.setText("LOST");
            }
            else
            {
                lblEvaluation.setText("---");
                lblWinOrLost.setText("---");
            }
        }
    }

    public void roundWon()
    {
        lblWinnerCounter.setText("Rounds won: " + player.roundWon());
        lblWinOrLost.setText("WON");
    }

    public void updateRoundsWon()
    {
        lblWinnerCounter.setText("Rounds won: " + 0);
        lblWinOrLost.setText("---");
    }
    
    public void updateNameLabel(String name) 
    {
        if (!name.equals("")) 
        {
            player.setMadeUpName(name);
            lblName.setText(player.getMadeUpName()); 
        }    
    }
}
