package view;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.AmbientLight;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Card;

public class CardLabel extends Label
{
    private ImageView imageViewFrontCard;
    
    public CardLabel()
    {
        super();
        this.getStyleClass().add("card");
        
        
    }
    
    public void updateCard(Card card, boolean animation) 
    {
        if (card != null)
        {
            String fileName = cardToFileName(card);
            Image image = new Image(this.getClass().getClassLoader()
                    .getResourceAsStream("images/" + fileName));
            imageViewFrontCard = new ImageView(image);
            imageViewFrontCard.fitWidthProperty().bind(this.widthProperty());
            imageViewFrontCard.fitHeightProperty().bind(this.heightProperty());
            imageViewFrontCard.setPreserveRatio(true);
            this.setGraphic(imageViewFrontCard);
        }
        if(animation) 
        {
            ImageView imageViewBackCard = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("images/pokerCardBack.jpg")));
            imageViewBackCard.fitWidthProperty().bind(this.widthProperty());
            imageViewBackCard.fitHeightProperty().bind(this.heightProperty());
            imageViewBackCard.setPreserveRatio(true);
            rotation(imageViewBackCard, 180, 90);
            rotation(imageViewFrontCard, 90, 0);
        }
        else
        {
            ImageView imageViewBackCard = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("images/pokerCardBack.jpg")));
            imageViewBackCard.fitWidthProperty().bind(this.widthProperty());
            imageViewBackCard.fitHeightProperty().bind(this.heightProperty());
            imageViewBackCard.setPreserveRatio(true);
            this.setGraphic(imageViewBackCard);
        }
    }
    
    private void rotation(ImageView imageView, int angle1, int angle2)
    {
        this.setGraphic(imageView);
        RotateTransition rotator = new RotateTransition(Duration.millis(400), imageView);
        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(angle1);
        rotator.setToAngle(angle2);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.play();
    }

    private String cardToFileName(Card card)
    {
        String rank = card.getRank().toString();
        String suit = card.getSuit().toString();
        return rank + "_of_" + suit + ".png";
    }
}
