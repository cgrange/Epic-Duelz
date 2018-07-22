package model;

import DAOs.ISquadDao;
import DAOs.MockSquadDao;
import board.Board;
import board.exceptions.CannotOccupySpaceException;
import board.exceptions.SpaceOccupiedException;
import model.exceptions.PlayerAlreadyExistsException;
import player.Dude;
import player.Player;
import player.Squad;

import java.util.ArrayList;

public class Model {
    String boardJson = "[" +
            "[{'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}]," + //1
            "[{'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'w', 'dude': null}]," + //2
            "[{'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}]," + //3
            "[{'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'o', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}]," + //4
            "[{'type': 'w', 'dude': null}, {'type': 'o', 'dude': null}, {'type': 'o', 'dude': null}, {'type': 'o', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}]," + //5
            "[{'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'o', 'dude': null}, {'type': 'o', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'w', 'dude': null}]," + //6
            "[{'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}]" + //7
        "]";
    private Board board;
    private ArrayList<Player> players;
    private ISquadDao squadDao;

    public Model() {
        board = new Board(boardJson);
        players = new ArrayList<Player>();
        squadDao = new MockSquadDao();//TODO change < to be a real DAO
        //TODO implement model select team which will place the dudes in the arraylist and get rid of this v
    }

    public void addPlayer(String name, int team) throws PlayerAlreadyExistsException {
        for(Player p : players){
            if(p.getName().equalsIgnoreCase(name)){
                throw new PlayerAlreadyExistsException();
            }
        }
        players.add(new Player(name, team));
    }

    public void addSquad(String playerName, String squadName){
        for(Player p : players){
            if(p.getName().equalsIgnoreCase(playerName)){
                Squad squad = squadDao.getSquad(squadName, p.getTeam());
                p.addSquad(squad);
            }
        }
    }

    private Dude getDude(String dudeName){
        for(Player p : players){
            for(Squad s : p.getSquads()) {
                for (Dude dude : s.getDudes()) {
                    if (dude.getName().equals(dudeName)) {
                        return dude;
                    }
                }
            }
        }
        return null;
    }

    public void placeDude(String dudeName, int rowIdx, int spaceIdx) throws CannotOccupySpaceException, SpaceOccupiedException {
        Dude theDude = getDude(dudeName);
        board.placeDude(theDude, rowIdx, spaceIdx);
    }

    private int calculateDamage(int attack, int defense){
        int damage = attack - defense;
        if(damage < 0) damage = 0;
        return damage;
    }

    public Board getBoard() {
        //TODO delete this method, it is just for testing purposes
        return board;
    }

//    public void resolveCards(Card attackCard, Card defenseCard){
//       int damage = calculateDamage(attackCard.getAttack(), defenseCard.getDefense());
//       defenseCard.defenseEffect(damage);
//       attackCard.attackEffect(damage);
//    }
}
