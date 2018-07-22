package player.cards;

import player.Player;

public abstract class Card {
    private boolean isSpecial;
    private String text;
    private int attack;
    private int defense;
    private String characterName;

    public Card(int attack, int defense, String text, boolean isSpecial, String characterName){
        this.attack = attack;
        this.defense = defense;
        this.text = text;
        this.isSpecial = isSpecial;
        this.characterName = characterName;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public String getText() {
        return text;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public String getCharacterName() {
        return characterName;
    }

    /**
     * the special effect dictated by the text on a power combat card or special card
     * @param damage the resulting damage from the attack and defense
     * @param owner the person who played this card
     * @param receiver the Character whom the card is being played on
     */
    public abstract void attackEffect(int damage, Player owner, Character receiver);

    /**
     * the special effect dictated by the text on a power combat card or special card
     * @param damage the resulting damage from the attack and defense
     * @param owner the person who played this card
     * @param receiver the Character whom the card is being played on
     */
    public abstract void defenseEffect(int damage, Player owner, Character receiver);
}
