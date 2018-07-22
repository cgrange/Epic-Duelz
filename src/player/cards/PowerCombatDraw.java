package player.cards;

import player.Player;

public class PowerCombatDraw extends Card {
    int numCards;

    public PowerCombatDraw(int attack, int defense, String text, boolean isSpecial, String characterName, int numCards){
        super(attack, defense, text, isSpecial, characterName);
        this.numCards = numCards;
    }

    @Override
    public void attackEffect(int damage, Player owner, Character receiver) {
//        for(int i = 0; i < numCards; i++){
//            owner.drawCard();
//        }
    }

    @Override
    public void defenseEffect(int damage, Player owner, Character receiver) {
//        for(int i = 0; i < numCards; i++){
//            owner.drawCard();
//        }
    }
}
