package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Player implements Comparable<Player>
{
    public static final int HAND_SIZE = 5;

    private final String playerName; // This is the ID
    private String madeUpName;
    private final ArrayList<Card> cards = new ArrayList<>();
    private Combination combination;
    private int roundsWon = 0;

    public Player(String playerName)
    {
        this.playerName = playerName;
        madeUpName = playerName;
    }

    public String getPlayerName()
    {
        return playerName;
    }
    
    public String getMadeUpName()
    {
        return madeUpName;
    }

    public void setMadeUpName(String madeUpName)
    {
        this.madeUpName = madeUpName;        
    }

    public ArrayList<Card> getCards()
    {
        return cards;
    }

    public void addCard(Card card)
    {
        if (cards.size() < HAND_SIZE)
        {
            cards.add(card);
        }
    }

    public void discardHand()
    {
        cards.clear();
        combination = null;
    }

    public int getNumCards()
    {
        return cards.size();
    }

    public int resetRoundsWon()
    {
        roundsWon = 0;
        return roundsWon;
    }

    public int roundWon()
    {
        roundsWon++;
        return roundsWon;
    }

    /**
     * If the hand has not been evaluated, but does have all cards, then
     * evaluate it.
     */
    public Combination evaluateHand()
    {
        if (combination == null && cards.size() == HAND_SIZE)
        {
            combination = HandType.evaluateHand(cards);
        }
        return combination;
    }

    /**
     * Hands are compared, based on the evaluation they have.
     */
    @Override
    public int compareTo(Player o)
    {
        return combination.getHandType().compareTo(o.combination.getHandType());
    }

    public ArrayList<Player> CheckWinner(ArrayList<Player> currentWinners,
            Player competitor)
    {
        if (currentWinners == null
                || currentWinners.get(0).compareTo(competitor) <= -1)
        {
            ArrayList<Player> newWinnerList = new ArrayList<>();
            newWinnerList.add(competitor);
            return newWinnerList;
        }
        else if (currentWinners.get(0).compareTo(competitor) >= 1)
        {
            return currentWinners;
        }
        for (int i = 0; i < currentWinners.get(0).combination
                .getCardCombinationList().size(); i++)
        {
            if (currentWinners.get(0).combination.getCardCombinationList()
                    .get(i).getRank().ordinal() > competitor.combination
                            .getCardCombinationList().get(i).getRank()
                            .ordinal())
            {
                return currentWinners;
            }
            else if (currentWinners.get(0).combination.getCardCombinationList()
                    .get(i).getRank().ordinal() < competitor.combination
                            .getCardCombinationList().get(i).getRank()
                            .ordinal())
            {
                ArrayList<Player> newWinnerList = new ArrayList<>();
                newWinnerList.add(competitor);
                return newWinnerList;
            }
        }
        currentWinners.add(competitor);
        return currentWinners;
    }
}
