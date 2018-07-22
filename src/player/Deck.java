package player;

import player.cards.Card;
import exceptions.*;

import java.util.List;

public class Deck {
    Pile drawPile;
    Pile discardPile;
    Pile temp;
    Hand hand;

    /**
     * adds the list of cards to draw pile, draws four of them and puts them into the hand, and initializes discard pile
     * @param cards
     */
    public Deck(List<Card> cards) {
        try{
            this.drawPile = new Pile(cards);
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        discardPile = new Pile();
        drawPile.shuffle();
        //add the first four cards to the hand
        try{
            for(int i = 0; i < 4; i++){
                hand.addCard(drawPile.drawCard());
            }
        }catch(EmptyPileException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * this is for if you are in the middle of a game. it initializes the deck to match the input exactly
     * @param drawPileCards
     * @param discardPileCards
     * @param handCards
     */
    public Deck(List<Card> drawPileCards, List<Card> discardPileCards, List<Card> handCards){
        try{
            this.drawPile = new Pile(drawPileCards);
            this.discardPile = new Pile(discardPileCards);
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        this.hand = new Hand(handCards);
    }

    public List<Card> revealDrawPile(){
        return drawPile.revealCards();
    }

    public List<Card> revealDiscardPile(){
        return discardPile.revealCards();
    }

    public List<Card> revealHand(){
        return hand.revealHand();
    }

    /**
     * removes the specified card from the hand and places it in the discard pile
     * @param card
     * @throws EmptyHandException
     * @throws CardNotInHandException
     */
    public void discardCard(Card card) throws EmptyHandException, CardNotInHandException{
        hand.removeSpecificCard(card);
        discardPile.addCard(card);
    }

    /**
     * takes the top card from the draw pile and puts it in the hand
     */
    public void drawCard(){
        try{
            hand.addCard(drawPile.drawCard());
        }catch(EmptyPileException e){
            drawPile.addCards(discardPile.takeCards());
            drawPile.shuffle();
            try{
                hand.addCard(drawPile.drawCard());
            }catch(EmptyPileException e1){
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
    }

    /**
     * attempts to take the specified card from the draw pile and put it in the hand
     * @param card
     * @throws CardNotInPileException
     */
    public void drawSpecificCard(Card card) throws CardNotInPileException{
        try{
            hand.addCard(drawPile.drawSpecificCard(card));
        }catch(EmptyPileException e) {
            drawPile.addCards(discardPile.takeCards());
            drawPile.shuffle();
            try {
                hand.addCard(drawPile.drawSpecificCard(card));
            } catch (EmptyPileException e1) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
    }

    /**
     * attempts to take the specified card from the discard pile and place it in the hand
     * @param card
     * @throws EmptyPileException
     * @throws CardNotInPileException
     */
    public void drawCardFromDiscardPile(Card card) throws EmptyPileException, CardNotInPileException {
        hand.addCard(discardPile.drawSpecificCard(card));
    }

    /**
     * returns the first n cards of the draw pile and removes them from the draw pile
     * @param n
     */
    public void takeNCards(int n){

    }

    /**
     * adds the cards to the top of the discard pile in the order that they are given
     * @param cards
     */
    public void addCardsToDiscardPile(List<Card> cards){

    }

    /**
     * adds the cards to the top of the draw pile in the order that they are given
     * @param cards
     */
    public void addCardsToDrawPile(List<Card> cards){

    }

    /**
     * adds the cards to the hand
     * @param cards
     */
    public void addCardsToHand(List<Card> cards){

    }
}
