package player;

import com.google.gson.*;

import java.util.ArrayList;

public class Squad {
    private ArrayList<Dude> dudes;
    private Deck deck;
    private int team;

    public Squad(String jsonString){
        JsonObject squadObj = new JsonParser().parse(jsonString).getAsJsonObject();
        JsonArray dudeArray = squadObj.get("dudes").getAsJsonArray();
        for(JsonElement dudeElement : dudeArray){
            Gson gson = new Gson();
            Dude dude = gson.fromJson(dudeElement, Dude.class);
            dudes.add(dude);
        }
        deck = new Deck(null);//TODO fix this <
    }

    public void setTeam(int team){
        for(Dude dude : dudes){
            dude.setTeam(team);
        }
        this.team = team;
    }

    public int getTeam(){
        return team;
    }

    public ArrayList<Dude> getDudes() {
        return dudes;
    }
}
