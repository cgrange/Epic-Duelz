package board;

import board.exceptions.InvalidMovementException;
import com.google.gson.JsonObject;
import player.Dude;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import board.exceptions.CannotMoveThatFarException;
import board.exceptions.CannotOccupySpaceException;
import board.exceptions.SpaceOccupiedException;

import java.util.ArrayList;

public class Board {
    public static final int NUM_ROWS = 7;
    public Row[] rows;

    public Board(String json) {
        rows = new Row[7];
        JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();
        for(int r = 0; r < jsonArray.size(); r++){
            rows[r] = new Row(jsonArray.get(r).getAsJsonArray(), r);
        }
    }

    public void placeDude(Dude dude, int rowIdx, int spaceIdx) throws CannotOccupySpaceException, SpaceOccupiedException {
        //TODO ? state pattern stuff (e.g. only allow this if starting game if in game force them to use move Dude)
        ISpace currentSpace = getLocation(dude);
        if(currentSpace != null){
            currentSpace.setOccupant(null);
        }
        if(rowIdx < 0 || spaceIdx < 0 || rowIdx >= Board.NUM_ROWS || spaceIdx >= Row.NUM_SPACES){
            throw new CannotOccupySpaceException();
        }
        ISpace newSpace = rows[rowIdx].getSpace(spaceIdx);
        newSpace.setOccupant(dude);
    }

    /**
     * find the space on the board where the dude currently is
     * @param dude the dude you are looking for
     * @return the space where the dude is or null if the dude is not on the board
     */
    public ISpace getLocation(Dude dude){
        for (Row row : rows) {
            for (int i = 0; i < Row.NUM_SPACES; i++) {
                ISpace space = row.getSpace(i);
                if (space.getOccupant() != null && space.getOccupant().equals(dude)){
                    return space;
                }
            }
        }
        return null;
    }

    private boolean rFoundIt(int spacesLeft,
                             SimpleSpace current,
                             SimpleSpace destination,
                             SimpleBoard simpleBoard) {
        if (current == destination) {
            return true;
          // if current space is off board or unwalkable
        } else if (current == null) {
            return false;
          // if you've already tried from the current space on a more direct route
        } else if (current.getSpacesLeft() >= spacesLeft) {
            return false;
        } else {
            current.setSpacesLeft(spacesLeft);
            spacesLeft -= 1;
            return (rFoundIt(spacesLeft, simpleBoard.above(current), destination, simpleBoard) ||
                    rFoundIt(spacesLeft, simpleBoard.below(current), destination, simpleBoard) ||
                    rFoundIt(spacesLeft, simpleBoard.leftOf(current), destination, simpleBoard) ||
                    rFoundIt(spacesLeft, simpleBoard.rightOf(current), destination, simpleBoard));
        }
    }

    private boolean canReachDestination(int maxDistance, ISpace start, ISpace destination)
            throws SpaceOccupiedException, CannotOccupySpaceException {
        if(destination.getOccupant() != null){
            throw new SpaceOccupiedException();
        }
        else if(!destination.isWalkable(start.getOccupant())){
            throw new CannotOccupySpaceException();
        }
        SimpleBoard simpleBoard = new SimpleBoard(start.getOccupant());
        SimpleSpace simpleStart =  simpleBoard.row(start.getRowIdx())
                                                .space(start.getSpaceIdx());
        SimpleSpace simpleDestination = simpleBoard.row(destination.getRowIdx())
                                                    .space(destination.getSpaceIdx());

        return rFoundIt(maxDistance, simpleStart, simpleDestination, simpleBoard);
    }

    /**
     * move a dude to a new location
     * @param dude the dude to move
     * @param maxDistance the furthest distance the dude can move
     * @param rowIdx the index of the row that you want to move the dude to
     * @param spaceIdx the index of the space, with respect to the row, that you want to move the dude to
     * @throws CannotOccupySpaceException
     * @throws SpaceOccupiedException
     * @throws CannotMoveThatFarException
     */
    public void moveDude(Dude dude, int maxDistance, int rowIdx, int spaceIdx)
            throws CannotOccupySpaceException, SpaceOccupiedException, CannotMoveThatFarException, InvalidMovementException {
        if(rowIdx < 0 || spaceIdx < 0 || rowIdx >= Board.NUM_ROWS || spaceIdx >= Row.NUM_SPACES){
            throw new CannotOccupySpaceException();
        }
        if(getLocation(dude).getRowIdx() == rowIdx && getLocation(dude).getSpaceIdx() == spaceIdx){
            throw new InvalidMovementException();
        }
        ISpace destination = rows[rowIdx].getSpace(spaceIdx);
        ISpace start = getLocation(dude);
        if(canReachDestination(maxDistance, start, destination)){
            start.setOccupant(null);
            destination.setOccupant(dude);
        } else {
            throw new CannotMoveThatFarException();
        }
    }

    private boolean adjacent(ISpace s0, ISpace s1){
        if(Math.abs(s0.getRowIdx() - s1.getRowIdx()) <= 1 &&
                Math.abs(s0.getSpaceIdx() - s1.getSpaceIdx()) <= 1) {
            return true;
        }
        return false;
    }

    /**
     * @param s0 location of attacker
     * @param s1 location of defender
     * @return true if and only if there is a straight line, vertical or horizontal, from s0 to s1 with no obstacles in the way
     */
    private boolean straightLine(ISpace s0, ISpace s1){
        if(s0.getRowIdx() == s1.getRowIdx()){
            int rowIdx = s0.getRowIdx();
            int minSpace = Math.min(s0.getSpaceIdx(), s1.getSpaceIdx());
            int maxSpace = Math.max(s0.getSpaceIdx(), s1.getSpaceIdx());
            boolean noObstacles = true;
            for(int i = minSpace+1; i < maxSpace; i++){
                if(rows[rowIdx].getSpace(i).isObstacle()){
                    noObstacles = false;
                }
            }
            return noObstacles;
        }
        else if(s0.getSpaceIdx() == s1.getSpaceIdx()){
            int spaceIdx = s0.getSpaceIdx();
            int minRow = Math.min(s0.getRowIdx(), s1.getRowIdx());
            int maxRow = Math.max(s0.getRowIdx(), s1.getRowIdx());
            boolean noObstacles = true;
            for(int i = minRow+1; i < maxRow; i++){
                if(rows[i].getSpace(spaceIdx).isObstacle()){
                    noObstacles = false;
                }
            }
            return noObstacles;
        }
        return false;
    }

    /**
     * @param leftSpace a location on the board diagonally left and below right
     * @param rightSpace a location on the board diagonally right and above left
     * @return true if there are no obstacles on the diagonal from left to right, else false
     */
    private boolean noObstaclesOnUpwardsDiagonal(ISpace leftSpace , ISpace rightSpace){
        for(int i = 1; i < rightSpace.getSpaceIdx() - leftSpace.getSpaceIdx(); i++){
            if(rows[leftSpace.getRowIdx()-i].getSpace(leftSpace.getSpaceIdx()+i).isObstacle()){
                return false;
            }
        }
        return true;
    }

    /**
     * @param leftSpace a location on the board diagonally left and above right
     * @param rightSpace a location on the board diagonally right and below left
     * @return true if there are no obstacles on the diagonal from left to right, else false
     */
    private boolean noObstaclesOnDownwardsDiagonal(ISpace leftSpace, ISpace rightSpace){
        for(int i = 1; i < rightSpace.getSpaceIdx() - leftSpace.getSpaceIdx(); i++){
            if(rows[leftSpace.getRowIdx()+i].getSpace(leftSpace.getSpaceIdx()+i).isObstacle()){
                return false;
            }
        }
        return true;
    }

    /**
     * @param s0 a space which is diagonal to s1
     * @param s1 a space which is diagonal to s0
     * @return true if there are no obstacles on the diagonal line between s0 and s1, false otherwise
     */
    private boolean noObstaclesOnDiagonal(ISpace s0, ISpace s1){
        if(s0.getSpaceIdx() < s1.getSpaceIdx()){    // s0 left of s1
            if(s0.getRowIdx() < s1.getRowIdx()){        // s0 above s1
                return noObstaclesOnDownwardsDiagonal(s0, s1);
            }
            else{                                       // s0 below s1
                return noObstaclesOnUpwardsDiagonal(s0, s1);
            }
        }
        else{                                       //s1 left of s0
            if(s1.getRowIdx() < s0.getRowIdx()){        // s1 above s0
                return noObstaclesOnDownwardsDiagonal(s1, s0);
            }
            else{                                       // s1 below s0
                return noObstaclesOnUpwardsDiagonal(s1, s0);
            }
        }
    }

    /**
     * @param s0 location of attacker
     * @param s1 location of defender
     * @return true if and only if there is a diagonal line from s0 to s1 with no obstacles in the way
     */
    private boolean diagonal(ISpace s0, ISpace s1){
        if(Math.abs(s0.getRowIdx() - s1.getRowIdx()) == Math.abs(s0.getSpaceIdx() - s1.getSpaceIdx())){
            return noObstaclesOnDiagonal(s0, s1);
        }
        return false;
    }

    public boolean canAttack(Dude attacker, Dude defender){
        if(attacker == defender){
            return false;
        }
        else if(attacker.isCivilized()){//melee
            if(adjacent(getLocation(attacker), getLocation(defender))){
                return true;
            }
            return false;
        } else {//blasters
            if(straightLine(getLocation(attacker), getLocation(defender)) ||
                    diagonal(getLocation(attacker), getLocation(defender))){
                return true;
            }
            return false;
        }
    }

    public JsonArray toJson() {
        JsonArray boardJson = new JsonArray();
        for(int r = 0; r < Board.NUM_ROWS; r++){
            JsonArray rowJson = new JsonArray();
            for(int s = 0; s < Row.NUM_SPACES; s++){
                JsonObject spaceObj = new JsonObject();
                ISpace space = rows[r].getSpace(s);
                char type = 'z';
                if(space.getClass() == WalkableSpace.class){
                    type = 'w';
                } else if(space.getClass() == ObstacleSpace.class){
                    type = 'o';
                } else if(space.getClass() == DropoffSpace.class){
                    type = 'd';
                }
                spaceObj.addProperty("type", type);
                if(space.getOccupant() != null){
                    JsonObject dudeObj = new JsonObject();
                    dudeObj.addProperty("name", space.getOccupant().getName());
                    //TODO add images to Dude
                    dudeObj.addProperty("img", "https://images-na.ssl-images-amazon.com/images/I/41Ol4gQpzaL._SL500_AC_SS350_.jpg");
                    spaceObj.add("dude", dudeObj);
                } else {
                    spaceObj.addProperty("dude", "null");
                }
                rowJson.add(spaceObj);
            }
            boardJson.add(rowJson);
        }
        return boardJson;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ PRIVATE INNER CLASSES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~ these aid in traversing the board ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private class SimpleBoard{
        private ArrayList<SimpleRow> board;

        private SimpleBoard(Dude walker){
            board = new ArrayList<SimpleRow>();

            for(int r = 0; r < Board.NUM_ROWS; r++){
                SimpleRow row = new SimpleRow(rows[r], r, walker);
                board.add(row);
            }
        }

        private SimpleRow row(int r){
            return board.get(r);
        }

        private SimpleSpace above(SimpleSpace space){
            if(space.getRowIndex() == 0){
                return null;
            } else {
                return row(space.getRowIndex() - 1).space(space.getSpaceIndex());
            }
        }

        private SimpleSpace below(SimpleSpace space){
            if(space.getRowIndex() == Board.NUM_ROWS - 1){
                return null;
            } else {
                return row(space.getRowIndex() + 1).space(space.getSpaceIndex());
            }
        }

        private SimpleSpace leftOf(SimpleSpace space){
            if(space.getSpaceIndex() == 0){
                return null;
            } else {
              return row(space.getRowIndex()).space(space.getSpaceIndex() - 1);
            }
        }

        private SimpleSpace rightOf(SimpleSpace space){
            if(space.getSpaceIndex() == Row.NUM_SPACES - 1){
                return null;
            } else {
                return row(space.getRowIndex()).space(space.getSpaceIndex() + 1);
            }
        }
    }

    private class SimpleRow{
        private ArrayList<SimpleSpace> row;

        private SimpleRow(Row row, int rowIdx, Dude walker){
            this.row = new ArrayList<SimpleSpace>();
            for(int s = 0; s < Row.NUM_SPACES; s++){
                ISpace space = row.getSpace(s);
                if(space.isWalkable(walker)){
                    this.row.add(new SimpleSpace(rowIdx, s));
                } else {
                    this.row.add(null);
                }
            }
        }

        private SimpleSpace space(int s){
            return row.get(s);
        }
    }

    private class SimpleSpace {
        private int spacesLeft;
        private int spaceIndex;
        private int rowIndex;

        private SimpleSpace (int rowIndex, int spaceIndex){
            this.rowIndex = rowIndex;
            this.spaceIndex = spaceIndex;
            this.spacesLeft = 0;
        }

        private int getRowIndex() {
            return rowIndex;
        }

        private int getSpaceIndex() {
            return spaceIndex;
        }

        private int getSpacesLeft(){
            return spacesLeft;
        }

        private void setSpacesLeft(int spacesLeft){
            this.spacesLeft = spacesLeft;
        }
    }
}



