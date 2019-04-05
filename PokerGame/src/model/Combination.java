package model;

import java.util.ArrayList;

public class Combination
{
    private final HandType handType;
    private final ArrayList<Card> cardCombinationList;

    public Combination(HandType handType, ArrayList<Card> cardCombinationList)
    {
        this.handType = handType;
        this.cardCombinationList = cardCombinationList;
    }

    public HandType getHandType()
    {
        return handType;
    }

    public ArrayList<Card> getCardCombinationList()
    {
        return cardCombinationList;
    }
}
