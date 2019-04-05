package model;

import java.util.ArrayList;

import pokerHandler.PokerGame;

public class PokerGameModel
{
    private ArrayList<Player> players = new ArrayList<>();
    private DeckOfCards deck;

    public PokerGameModel()
    {
        for (int i = 0; i < PokerGame.NUM_PLAYERS; i++)
        {
            players.add(new Player("Player " + (i + 1)));
        }

        deck = new DeckOfCards();
    }

    public Player getPlayer(int i)
    {
        return players.get(i);
    }

    public DeckOfCards getDeck()
    {
        return deck;
    }
    
    public void resetPlayers()
    {
        players.clear();
    }

    public void addPlayer(Player player)
    {
        players.add(player);
    }
}
