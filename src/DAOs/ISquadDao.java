package DAOs;

import player.Squad;

public interface ISquadDao {
    public Squad getSquad(String squadName, int team);
    public Squad getSquad(String squadName);
}
