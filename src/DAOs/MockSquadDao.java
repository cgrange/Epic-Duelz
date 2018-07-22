package DAOs;

import com.google.gson.JsonObject;
import player.Squad;

public class MockSquadDao implements ISquadDao {
    private final String hanAndChewieJsonString = "{" +
                "dudes: [" +
                    "{health: 13, damage: 0, name: \"Han Solo\", team: -1, civilized: false}," +
                    "{health: 15, damage: 0, name: \"Chewbacca\", team: -1, civilized: false}" +
                "]," +
                "deck: null" +
            "}";
    private final String bobaAndGreedoJsonString = "{" +
                "dudes: [" +
                    "{health: 14, damage: 0, name: \"Boba Fett\", team: -1, civilized: false}," +
                    "{health: 7, damage: 0, name: \"Greedo\", team: -1, civilized: false}" +
                "]," +
                "deck: null" +
            "}";

    public Squad getSquad(String squadName){
        String squadJsonString;
        switch(squadName){
            case "hanAndChewie":
                squadJsonString = hanAndChewieJsonString;
                break;
            case "bobaAndGreedo":
                squadJsonString = bobaAndGreedoJsonString;
                break;
            default:
                throw new IllegalArgumentException();
        }
        Squad squad = new Squad(squadJsonString);
        return squad;
    }

    public Squad getSquad(String squadName, int team){
        String squadJsonString;
        switch(squadName){
            case "hanAndChewie":
                squadJsonString = hanAndChewieJsonString;
                break;
            case "bobaAndGreedo":
                squadJsonString = bobaAndGreedoJsonString;
                break;
            default:
                throw new IllegalArgumentException();
        }
        Squad squad = new Squad(squadJsonString);
        squad.setTeam(team);
        return squad;
    }
}
