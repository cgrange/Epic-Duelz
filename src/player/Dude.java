package player;

public class Dude {
    int health;
    int damage;
    String name;
    int team;
    boolean civilized;

    public Dude(int health, int damage, String name, int team, boolean civilized){
        this.health = health;
        this.damage = damage;
        this.name = name;
        this.team = team;
        this.civilized = civilized;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    public void addDamage(int amount) {
        damage += amount;
    }

    public void heal(int amount){
        damage -= amount;
    }

    public int getTeam(){
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public boolean isCivilized(){ return civilized; }

    public boolean sameTeam(Dude dude) {
        return dude.getTeam() == this.team;
    }
}
