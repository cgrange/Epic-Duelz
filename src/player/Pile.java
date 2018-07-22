package player;

import player.cards.Card;
import exceptions.CardNotInPileException;
import exceptions.EmptyPileException;

import java.util.ArrayList;
import java.util.List;

public class Pile {
    List<Card> cards;
    boolean isViewable;

    /**
     * sets this pile's list of cards to the input.
     * @param cards
     * @throws Exception
     */
    public Pile(List<Card> cards) throws Exception {
        if(cards == null){
            throw new Exception("the input cards for the pile was null");
        }
        this.cards = cards;
    }

    /**
     * instantiate's this piles list of cards to be an empty List
     */
    public Pile(){
        this.cards = new ArrayList<Card>();
    }

    /**
     * randomly rearranges the order of the List cards
     */
    public void shuffle(){
        //TODO implement
    }

    /**
     * adds the given player.cards.Card to the front of the current List of cards for the pile
     * @param card
     */
    public void addCard(Card card){
        //TODO implement
    }

    /**
     * adds the List of cards, in the order that they are given, to the front of the current List of cards for the pile
     * @param cards
     */
    public void addCards(List<Card> cards){
        //TODO implement
    }

    /**
     * returns the first player.cards.Card in cards and removes that player.cards.Card from the list
     * @return the first player.cards.Card in cards
     */
    public Card drawCard() throws EmptyPileException{
        //TODO implement
        return null;
    }

    /**
     * returns a player.cards.Card in cards that matches the input card and removes that card from the list
     * @return a player.cards.Card in cards that matches the input card
     */
    public Card drawSpecificCard(Card card) throws EmptyPileException, CardNotInPileException {
        //TODO implement
        return null;
    }

    /**
     * returns cards
     * @return List<player.cards.Card> cards
     */
    public List<Card> revealCards() {
        return cards;
        //TODO change this to instead generate and return json or a string so that cards will be immutable.
    }

    /**
     * returns the first n cards of the pile
     * @param n
     * @return the first n cards of the pile
     */
    public List<Card> revealNCards(int n){
        return null;
        //TODO change this to instead generate and return json or a string so that cards will be immutable.
    }

    public boolean isViewable() {
        return isViewable;
    }

    public void setViewable(boolean viewable) {
        isViewable = viewable;
    }

    /**
     * Creates a deep copy of cards and returns that. Then removes all of the cards from this pile.
     * @return a deep copy of all the cards in this pile.
     */
    public List<Card> takeCards() {
        ArrayList<Card> deepCopy = new ArrayList<Card>();
        for(Card card : cards){
            deepCopy.add(card);
        }
        cards.removeAll(deepCopy);
        //make sure that the deep copy is complete and that cards is empty
        if(cards.size() != 0){
            Thread.dumpStack();
            System.out.println("for some reason the deep copy didn't work properly");
            System.exit(1);
        }
        return deepCopy;
    }
}
