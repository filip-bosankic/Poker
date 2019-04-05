package controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Card;
import model.DeckOfCards;
import model.Player;
import model.PokerGameModel;
import pokerHandler.PokerGame;
import view.PlayerPane;
import view.PokerGameView;

public class PokerGameController
{
    private PokerGameModel model;
    private PokerGameView view;

    private MediaPlayer mediaPlayer;
    private File[] musicFileNames;
    private int currentSongIndex;

    public PokerGameController(PokerGameModel model, PokerGameView view)
    {
        this.model = model;
        this.view = view;

        musicFileNames = new File(".\\src\\music").listFiles();
        if (musicFileNames != null)
        {
            view.getBackButton().setOnAction(e -> back());
            view.getChangeMusicButton().setOnAction(e -> playRandomSong());
            view.getStopMusicButton().setOnAction(e -> stopCurrentSong());
            view.getNextButton().setOnAction(e -> next());
            changeMusic();
        }

        view.getExitButton().setOnAction(e -> exit());
        view.getResetButton().setOnAction(e -> reset());
        view.getShuffleButton().setOnAction(e -> shuffle());
        view.getDealButton().setOnAction(e -> deal());
        
        view.getMenuItemRules().setOnAction(e -> OpenRuleWebsite());
        view.getMenuItemHandCombinationsInfo().setOnAction(e -> view.showHandCombinationsInfo());
        for (MenuItem mI : view.getMenuItemsChangePlayers())
        {
            mI.setOnAction(e -> updateDisplay((int)(mI.getText().charAt(0) - '0')));
        }
        view.getMenuItemChangeNames().setOnAction(e -> ChangeNames());
    }    
    
    private void exit()
    {
        System.exit(0);
    }

    private void back()
    {
        currentSongIndex--;
        currentSongIndex = currentSongIndex < 0 ? musicFileNames.length - 1
                : currentSongIndex;
        changeMusic();
    }

    private void playRandomSong()
    {
        Random rnd = new Random();
        int newSongIndex = rnd.nextInt(musicFileNames.length);
        if (currentSongIndex == newSongIndex)
        {
            next();
            return;
        }
        currentSongIndex = newSongIndex;
        changeMusic();
    }

    private void stopCurrentSong()
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    private void next()
    {
        currentSongIndex++;
        currentSongIndex = currentSongIndex >= musicFileNames.length ? 0
                : currentSongIndex;
        changeMusic();
    }

    private void changeMusic()
    {
        stopCurrentSong();
        Media musicFile = new Media(
                musicFileNames[currentSongIndex].toURI().toString());
        String fileName = musicFileNames[currentSongIndex].getName();
        view.getInterpratorAndSongNameLabel()
                .setText(fileName.substring(0, fileName.lastIndexOf('.')));
        mediaPlayer = new MediaPlayer(musicFile);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable()
        {
            @Override
            public void run()
            {
                mediaPlayer.stop();
                next();
            }
        });
    }

    private void reset()
    {
        for (int i = 0; i < PokerGame.NUM_PLAYERS; i++)
        {
            model.getPlayer(i).resetRoundsWon();
            PlayerPane pp = view.getPlayerPane(i);
            pp.updateRoundsWon();
        }
    }

    /**
     * Remove all cards from players hands, and shuffle the deck
     */
    private void shuffle()
    {
        for (int i = 0; i < PokerGame.NUM_PLAYERS; i++)
        {
            model.getPlayer(i).discardHand();
            PlayerPane pp = view.getPlayerPane(i);
            pp.updatePlayerDisplay(false);
        }

        model.getDeck().shuffle();
    }

    /**
     * Deal each player five cards, then evaluate the two hands
     */
    private void deal()
    {
        int cardsRequired = PokerGame.NUM_PLAYERS * Player.HAND_SIZE;
        DeckOfCards deck = model.getDeck();
        if (cardsRequired <= deck.getCardsRemaining())
        {
            ArrayList<Player> winners = null;
            // Evaluate hands
            for (int i = 0; i < PokerGame.NUM_PLAYERS; i++)
            {
                Player p = model.getPlayer(i);
                p.discardHand();
                for (int j = 0; j < Player.HAND_SIZE; j++)
                {
                    Card card = deck.dealCard();
                    p.addCard(card);
                }
                p.evaluateHand();
                winners = p.CheckWinner(winners, p);
                PlayerPane pp = view.getPlayerPane(i);
                pp.updatePlayerDisplay(true);
            }
            for (int i = 0; i < winners.size(); i++)
            {
                int playerNumber = (int) winners.get(i).getPlayerName()
                        .charAt(winners.get(i).getPlayerName().length() - 1)
                        - '1';
                PlayerPane pp = view.getPlayerPane(playerNumber);
                pp.roundWon();
            }
        }
        else
        {
            shuffle();
            deal();
        }
    }
    
    private void OpenRuleWebsite()
    {
        try {
            URI uri = new URI("https://www.contrib.andrew.cmu.edu/~gc00/reviews/pokerrules");
            java.awt.Desktop.getDesktop().browse(uri);

        } catch (URISyntaxException | IOException e) {
            //System.out.println("THROW::: make sure we handle browser error");
            e.printStackTrace();
        }
    }
    
    private void updateDisplay(int newPlayerCount)
    {
        if(model != null && view != null)
        {
            PokerGame.NUM_PLAYERS = newPlayerCount;
            model.resetPlayers();
            view.resetDisplay();
            for (int i = 0; i < PokerGame.NUM_PLAYERS; i++)
            {
                model.addPlayer(new Player("Player " + (i+1)));
            }
            view.updatePlayerPanes();
        }
    }
    
    private void ChangeNames()
    {
        view.showChangePlayerNamesWindow();
        view.getButtonChangePlayerNames().setOnAction(d->{
            for(int i = 0; i < view.getChangeNamePane().textFieldPlayerNameList.size(); i++)
            {
                    String nameXYZ = view.getChangeNamePane().textFieldPlayerNameList.get(i).getText();
                    view.getPlayerPane(i).updateNameLabel(nameXYZ);
                    
            }
            view.getChangeNamePane().stage.close();
        });
    }
}