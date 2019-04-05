package view;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import model.DeckOfCards;

public class DeckLabel extends Label
{
    public DeckLabel()
    {
        super();
        this.getStyleClass().add("deck");
    }

    // Bind the label to the CardsRemaining property of the deck
    public void setDeck(DeckOfCards deck)
    {
        this.textProperty().bind(Bindings.concat(deck.getCardsRemainingProperty().asString(), " cards remaining"));
    }
}
