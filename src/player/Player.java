package player;

import java.util.ArrayList;

public class Player {
    ArrayList<Deck> decks;
    ArrayList<Squad> squads;
    int team;
    String name;

    public Player(String name, int team){
        squads = new ArrayList<Squad>();
        this.team = team;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int getTeam(){
        return team;
    }

    public ArrayList<Squad> getSquads(){
        return squads;
    }

    public void addSquad(Squad squad) {
        squads.add(squad);
    }
}
