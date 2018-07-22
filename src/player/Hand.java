package player;

import player.cards.Card;
import exceptions.CardNotInHandException;
import exceptions.EmptyHandException;

import java.util.List;
import java.util.Random;

public class Hand {
    private List<Card> cards;

    /**
     * sets this list of cards to the input if the input != null. else it will instantiate cards
     * @param cards the cards that start out your hand
     */
    public Hand(List<Card> cards){
        this.cards = cards;
    }

    /**
     * adds the given card to the hand
     * @param card the card that is to be added
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * adds the given cards to the hand
     * @param cards the cards that are to be added to the hand
     */
    public void addCards(List<Card> cards) {
        cards.addAll(cards);
    }

    /**
     * If there are any cards in this hand it will remove a random card from the hand and return it
     * @return the card that is removed from the hand
     * @throws EmptyHandException
     */
    public Card removeRandomCard() throws EmptyHandException {
        if(cards.size() == 0) throw new EmptyHandException();
        Random rand = new Random();
        int randomIndex = rand.nextInt() % cards.size();
        Card removedCard = cards.remove(randomIndex);
        return removedCard;
    }

    /**
     * If this hand is not empty and a card that matches the specified card is in this hand it will remove a card that matches the specified card from this hand and return it
     * @param card the card that is to be removed
     * @throws EmptyHandException
     * @throws CardNotInHandException
     */
    public void removeSpecificCard(Card card) throws EmptyHandException, CardNotInHandException {
        if(cards.size() == 0) throw new EmptyHandException();
        if(!cards.contains(card)) throw new CardNotInHandException();
        cards.remove(card);
    }

    /**
     * @return the List of cards that comprise this hand
     */
    public List<Card> revealHand(){
        return cards;
        //TODO perhaps change this to generate and return a string or json so that cards will be immutable
    }

    public int getNumCards(){
        return cards.size();
    }
}
