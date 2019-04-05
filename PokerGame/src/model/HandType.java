package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import model.Card.Rank;
import model.Card.Suit;

public enum HandType
{
    HighCard, OnePair, TwoPair, ThreeOfAKind, Straight, Flush, FullHouse, FourOfAKind, StraightFlush, RoyalFlush;

    private static boolean debug = true; // Temporary Flag

    /**
     * Determine the value of this hand. Note that this does not account for any
     * tie-breaking
     */
    public static Combination evaluateHand(ArrayList<Card> cards)
    {
        ArrayList<Card> sortedCards = (ArrayList<Card>) cards.clone();

        Collections.sort(sortedCards, new Comparator<Card>()
        {
            @Override
            public int compare(Card card1, Card card2)
            {
                return card2.getRank().ordinal() - card1.getRank().ordinal();
            }
        });

        if (debug)
        {
// 			debugTestTwoPair(sortedCards);		// Working/Tested
//			debugTestThreeOfAKind(sortedCards);	// Working/Tested
//			debugTestFlush(sortedCards);			// Working/Tested
//			debugTestStreet(sortedCards);			// Working/Tested (Also with working with ace, 2, 3, 4, 5
//			debugTestFullHouse(sortedCards);		// Working/Tested
//			debugTestFourOfAKind(sortedCards);	// Working/Tested
//			debugTestStraightFlush(sortedCards);	// Working/Tested
//			debugTestRoyalFlush(sortedCards);		// Working/Tested
            // Collections.shuffle(sortedCards); // Don't use on straight
        }

        Combination combination;

        if (isRoyalFlush(sortedCards))
            combination = new Combination(HandType.RoyalFlush, sortedCards);
        else if (isStraightFlush(sortedCards))
            combination = new Combination(HandType.RoyalFlush, sortedCards);
        else if (isFourOfAKind(sortedCards))
            combination = new Combination(HandType.FourOfAKind, sortedCards);
        else if (isFullHouse(sortedCards))
            combination = new Combination(HandType.FullHouse, sortedCards);
        else if (isFlush(sortedCards))
            combination = new Combination(HandType.Flush, sortedCards);
        else if (isStraight(sortedCards))
            combination = new Combination(HandType.Straight, sortedCards);
        else if (isThreeOfAKind(sortedCards))
            combination = new Combination(HandType.ThreeOfAKind, sortedCards);
        else if (isTwoPair(sortedCards))
            combination = new Combination(HandType.TwoPair, sortedCards);
        else if (isOnePair(sortedCards))
            combination = new Combination(HandType.OnePair, sortedCards);
        else
            return new Combination(HandType.HighCard, sortedCards);

        return combination;
    }

    public static boolean isRoyalFlush(ArrayList<Card> cards)
    {
        if (isStraightFlush(cards) && cards.get(1).getRank().ordinal() == 11) // Street
                                                                              // could
                                                                              // also
                                                                              // be
                                                                              // interpreted
                                                                              // Ace(1),
                                                                              // 2,
                                                                              // 3,
                                                                              // 4,
                                                                              // 5
        {
            return true;
        }
        return false;
    }

    public static boolean isStraightFlush(ArrayList<Card> cards)
    {
        if (isFlush(cards) && isStraight(cards))
        {
            return true;
        }
        return false;
    }

    public static boolean isFourOfAKind(ArrayList<Card> cards)
    {
        boolean fourOfAKind = false;
        ArrayList<Card> refCardList = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++)
        {
            boolean twoOfAKind = false;
            boolean threeOfAKind = false;
            for (int j = i + 1; j < cards.size() && !fourOfAKind; j++)
            {
                if (cards.get(i).getRank() == cards.get(j).getRank())
                {
                    if (!twoOfAKind)
                    {
                        twoOfAKind = true;
                    }
                    else if (!threeOfAKind)
                    {
                        threeOfAKind = true;
                    }
                    else
                    {
                        fourOfAKind = true;
                        refCardList.add(0, new Card(cards.get(i).getSuit(),
                                cards.get(i).getRank()));
                    }
                }
            }
            if (!ListContainsCardNumber(refCardList, cards.get(i)))
                ;
            {
                refCardList.add(new Card(cards.get(i).getSuit(),
                        cards.get(i).getRank()));
            }
        }
        cards = fourOfAKind ? refCardList : cards;
        return fourOfAKind;
    }

    public static boolean isFullHouse(ArrayList<Card> cards)
    {
        int lastCard = -1;
        int numThreeOfAKind = 0;
        int numTwoOfAKind = 0;
        ArrayList<Card> refCardList = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++)
        {
            if (lastCard != cards.get(i).getRank().ordinal())
            {
                for (int j = i + 1; j < cards.size(); j++)
                {
                    if (cards.get(i).getRank() == cards.get(j).getRank())
                    {
                        lastCard = cards.get(i).getRank().ordinal();
                        if (numTwoOfAKind == 0)
                        {
                            numTwoOfAKind = lastCard + 2;
                        }
                        else
                        {
                            numTwoOfAKind = 0;
                            numThreeOfAKind = lastCard + 2;
                        }
                    }
                }
            }
            if (numThreeOfAKind > 0 && numTwoOfAKind > 0)
            {
                refCardList.add(new Card(cards.get(i).getSuit(),
                        Rank.getRank(numThreeOfAKind - 2)));
                cards = refCardList;
                return true;
            }
        }
        return false;
    }

    public static boolean isFlush(ArrayList<Card> cards)
    {
        if (cards.get(0).getSuit() == cards.get(1).getSuit()
                && cards.get(0).getSuit() == cards.get(2).getSuit()
                && cards.get(0).getSuit() == cards.get(3).getSuit()
                && cards.get(0).getSuit() == cards.get(4).getSuit())
            return true;

        return false;
    }

    public static boolean isStraight(ArrayList<Card> cards)
    {
        if (cards.get(4).getRank().ordinal() == cards.get(3).getRank().ordinal()
                && cards.get(3).getRank().ordinal() + 1 == cards.get(2)
                        .getRank().ordinal()
                && cards.get(2).getRank().ordinal() + 1 == cards.get(1)
                        .getRank().ordinal()
                && cards.get(1).getRank().ordinal() + 1 == cards.get(0)
                        .getRank().ordinal())
        {
            return true;
        }
        else if (cards.get(0).getRank().ordinal() == 12
                && cards.get(4).getRank().ordinal() == 0
                && cards.get(3).getRank().ordinal() == 1
                && cards.get(2).getRank().ordinal() == 2
                && cards.get(1).getRank().ordinal() == 3)
        {
            Card ace = cards.get(0);
            cards.remove(0);
            cards.add(ace);
            return true;
        }

        return false;
    }

    public static boolean isThreeOfAKind(ArrayList<Card> cards)
    {
        boolean threeOfAKind = false;
        ArrayList<Card> refCardList = new ArrayList<>();
        skipRefCardListAdd: for (int i = 0; i < cards.size(); i++)
        {
            boolean twoOfAKind = false;
            for (int j = i + 1; j < cards.size() && !threeOfAKind; j++)
            {
                if (cards.get(i).getRank() == cards.get(j).getRank())
                {
                    if (!twoOfAKind)
                    {
                        twoOfAKind = true;
                    }
                    else
                    {
                        threeOfAKind = true;
                        refCardList.add(0, new Card(cards.get(i).getSuit(),
                                cards.get(i).getRank()));
                        continue skipRefCardListAdd;
                    }
                }
            }
            if (!ListContainsCardNumber(refCardList, cards.get(i)))
                ;
            {
                refCardList.add(new Card(cards.get(i).getSuit(),
                        cards.get(i).getRank()));
            }
        }
        cards = threeOfAKind ? refCardList : cards;
        return threeOfAKind;
    }

    public static boolean isTwoPair(ArrayList<Card> cards)
    {
        int indexCardChecked = -1;
        boolean twoPairFound = false;
        ArrayList<Card> refCardList = new ArrayList<>();
        skipRefCardListAdd: for (int i = 0; i < cards.size(); i++)
        {
            for (int j = i + 1; j < cards.size(); j++)
            {
                if (cards.get(i).getRank() == cards.get(j).getRank())
                {
                    if (indexCardChecked != -1)
                    {
                        twoPairFound = true;
                        refCardList.add(1, new Card(cards.get(i).getSuit(),
                                cards.get(i).getRank()));
                        continue skipRefCardListAdd;
                    }
                    indexCardChecked = cards.get(i).getRank().ordinal();
                    refCardList.add(0, new Card(cards.get(i).getSuit(),
                            cards.get(i).getRank()));
                    break;
                }
            }
            if (!ListContainsCardNumber(refCardList, cards.get(i)))
                ;
            {
                refCardList.add(new Card(cards.get(i).getSuit(),
                        cards.get(i).getRank()));
            }
        }
        cards = twoPairFound ? refCardList : cards;
        return twoPairFound;
    }

    public static boolean isOnePair(ArrayList<Card> cards)
    {
        boolean onePairFound = false;
        ArrayList<Card> refCardList = new ArrayList<>();
        skipRefCardListAdd: for (int i = 0; i < cards.size(); i++)
        {
            for (int j = i + 1; j < cards.size() && !onePairFound; j++)
            {
                if (cards.get(i).getRank() == cards.get(j).getRank())
                {
                    onePairFound = true;
                    refCardList.add(0, new Card(cards.get(i).getSuit(),
                            cards.get(i).getRank()));
                    continue skipRefCardListAdd;
                }
            }
            if (!ListContainsCardNumber(refCardList, cards.get(i)))
                ;
            {
                refCardList.add(new Card(cards.get(i).getSuit(),
                        cards.get(i).getRank()));
            }
        }
        cards = onePairFound ? refCardList : cards;
        return onePairFound;
    }

    private static boolean ListContainsCardNumber(ArrayList<Card> refCardList,
            Card card)
    {
        for (Card c : refCardList)
        {
            if (c.getRank() == card.getRank())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Temporary Test-Method
     * 
     * @param tmp
     */
    private static void debugTestTwoPair(ArrayList<Card> tmp)
    {
        tmp.set(0, new Card(Card.Suit.Spades, Rank.Ace));
        tmp.set(1, new Card(Card.Suit.Spades, Rank.King));
        tmp.set(2, new Card(Card.Suit.Clubs, Rank.Two));
        tmp.set(3, new Card(Card.Suit.Hearts, Rank.Ace));
        tmp.set(4, new Card(Card.Suit.Hearts, Rank.King));
//		tmp.set(0, new Card(Card.Suit.Spades, Rank.Two));
//		tmp.set(1, new Card(Card.Suit.Spades, Rank.Three));
//		tmp.set(2, new Card(Card.Suit.Clubs, Rank.Two));
//		tmp.set(3, new Card(Card.Suit.Hearts, Rank.Three));
//		tmp.set(4, new Card(Card.Suit.Hearts, Rank.King));
    }

    /**
     * Temporary Test-Method
     * 
     * @param tmp
     */
    private static void debugTestThreeOfAKind(ArrayList<Card> tmp)
    {
        tmp.set(0, new Card(Card.Suit.Spades, Rank.Ace));
        tmp.set(1, new Card(Card.Suit.Hearts, Rank.Ace));
        tmp.set(2, new Card(Card.Suit.Spades, Rank.Three));
        tmp.set(3, new Card(Card.Suit.Clubs, Rank.Ace));
        tmp.set(4, new Card(Card.Suit.Spades, Rank.Two));
//		tmp.set(0, new Card(Card.Suit.Spades, Rank.Two));
//		tmp.set(1, new Card(Card.Suit.Spades, Rank.Two));
//		tmp.set(2, new Card(Card.Suit.Clubs, Rank.Two));
//		tmp.set(3, new Card(Card.Suit.Hearts, Rank.Three));
//		tmp.set(4, new Card(Card.Suit.Hearts, Rank.Four));
    }

    /**
     * Temporary Test-Method
     * 
     * @param tmp
     */
    private static void debugTestStreet(ArrayList<Card> tmp)
    {
        tmp.set(0, new Card(Card.Suit.Spades, Rank.Two));
        tmp.set(1, new Card(Card.Suit.Hearts, Rank.Three));
        tmp.set(2, new Card(Card.Suit.Diamonds, Rank.Four));
        tmp.set(3, new Card(Card.Suit.Spades, Rank.Five));
        tmp.set(4, new Card(Card.Suit.Spades, Rank.Ace));
//		tmp.set(0, new Card(Card.Suit.Spades, Rank.Ten));
//		tmp.set(1, new Card(Card.Suit.Hearts, Rank.Jack));
//		tmp.set(2, new Card(Card.Suit.Diamonds, Rank.Queen));
//		tmp.set(3, new Card(Card.Suit.Spades, Rank.King));
//		tmp.set(4, new Card(Card.Suit.Hearts, Rank.Ace));
    }

    /**
     * Temporary Test-Method
     * 
     * @param tmp
     */
    private static void debugTestFlush(ArrayList<Card> tmp)
    {
//		tmp.set(0, new Card(Card.Suit.Spades, Rank.Two));
//		tmp.set(1, new Card(Card.Suit.Spades, Rank.Four));
//		tmp.set(2, new Card(Card.Suit.Spades, Rank.Six));
//		tmp.set(3, new Card(Card.Suit.Spades, Rank.Eight));
//		tmp.set(4, new Card(Card.Suit.Spades, Rank.Ten));
        tmp.set(0, new Card(Card.Suit.Hearts, Rank.Six));
        tmp.set(1, new Card(Card.Suit.Hearts, Rank.Eight));
        tmp.set(2, new Card(Card.Suit.Hearts, Rank.Ten));
        tmp.set(3, new Card(Card.Suit.Hearts, Rank.Queen));
        tmp.set(4, new Card(Card.Suit.Hearts, Rank.Ace));
    }

    /**
     * Temporary Test-Method
     * 
     * @param tmp
     */
    private static void debugTestFullHouse(ArrayList<Card> tmp)
    {
//		tmp.set(0, new Card(Card.Suit.Spades, Rank.Ace));
//		tmp.set(1, new Card(Card.Suit.Hearts, Rank.Ace));
//		tmp.set(2, new Card(Card.Suit.Diamonds, Rank.Ace));
//		tmp.set(3, new Card(Card.Suit.Spades, Rank.King));
//		tmp.set(4, new Card(Card.Suit.Hearts, Rank.King));
        tmp.set(0, new Card(Card.Suit.Diamonds, Rank.Two));
        tmp.set(1, new Card(Card.Suit.Hearts, Rank.Three));
        tmp.set(2, new Card(Card.Suit.Spades, Rank.Two));
        tmp.set(3, new Card(Card.Suit.Hearts, Rank.Two));
        tmp.set(4, new Card(Card.Suit.Spades, Rank.Three));
    }

    /**
     * Temporary Test-Method
     * 
     * @param tmp
     */
    private static void debugTestFourOfAKind(ArrayList<Card> tmp)
    {
//		tmp.set(0, new Card(Card.Suit.Clubs, Rank.Ace));
//		tmp.set(1, new Card(Card.Suit.Diamonds, Rank.Ace));
//		tmp.set(2, new Card(Card.Suit.Spades, Rank.Two));
//		tmp.set(3, new Card(Card.Suit.Clubs, Rank.Ace));
//		tmp.set(4, new Card(Card.Suit.Spades, Rank.Ace));
        tmp.set(0, new Card(Card.Suit.Clubs, Rank.Two));
        tmp.set(1, new Card(Card.Suit.Diamonds, Rank.Ace));
        tmp.set(2, new Card(Card.Suit.Spades, Rank.Two));
        tmp.set(3, new Card(Card.Suit.Clubs, Rank.Two));
        tmp.set(4, new Card(Card.Suit.Spades, Rank.Two));

    }

    /**
     * Temporary Test-Method
     * 
     * @param tmp
     */
    private static void debugTestStraightFlush(ArrayList<Card> tmp)
    {
        tmp.set(0, new Card(Card.Suit.Hearts, Rank.Nine));
        tmp.set(1, new Card(Card.Suit.Hearts, Rank.Ten));
        tmp.set(2, new Card(Card.Suit.Hearts, Rank.Jack));
        tmp.set(3, new Card(Card.Suit.Hearts, Rank.Queen));
        tmp.set(4, new Card(Card.Suit.Hearts, Rank.King));
//		tmp.set(0, new Card(Card.Suit.Hearts, Rank.Two));
//		tmp.set(1, new Card(Card.Suit.Hearts, Rank.Three));
//		tmp.set(2, new Card(Card.Suit.Hearts, Rank.Four));
//		tmp.set(3, new Card(Card.Suit.Hearts, Rank.Five));
//		tmp.set(4, new Card(Card.Suit.Hearts, Rank.Six));
    }

    /**
     * Temporary Test-Method
     * 
     * @param tmp
     */
    private static void debugTestRoyalFlush(ArrayList<Card> tmp)
    {
//		tmp.set(0, new Card(Card.Suit.Spades, Rank.Ten));
//		tmp.set(1, new Card(Card.Suit.Spades, Rank.Jack));
//		tmp.set(2, new Card(Card.Suit.Spades, Rank.Queen));
//		tmp.set(3, new Card(Card.Suit.Spades, Rank.King));
//		tmp.set(4, new Card(Card.Suit.Spades, Rank.Ace));
        tmp.set(0, new Card(Card.Suit.Hearts, Rank.Ten));
        tmp.set(1, new Card(Card.Suit.Hearts, Rank.Jack));
        tmp.set(2, new Card(Card.Suit.Hearts, Rank.Queen));
        tmp.set(3, new Card(Card.Suit.Hearts, Rank.King));
        tmp.set(4, new Card(Card.Suit.Hearts, Rank.Ace));
    }
}
