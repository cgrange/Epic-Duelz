package player.cards;

import player.Player;

public class BasicCombatCard extends Card {
    public BasicCombatCard(int attack, int defense, String text, boolean isSpecial, String characterName){
        super(attack, defense, text, isSpecial, characterName);
    }

    @Override
    public void attackEffect(int damage, Player Owner, Character receiver) {
        // does not exist
    }

    @Override
    public void defenseEffect(int damage, Player owner, Character receiver) {
        // does not exist
    }
}
