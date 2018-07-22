package board;

import player.Dude;
import board.exceptions.CannotOccupySpaceException;
import board.exceptions.SpaceOccupiedException;

public class DropoffSpace implements ISpace {
    int rowIdx, spaceIdx;

    public DropoffSpace(int rowIdx, int spaceIdx){
        this.rowIdx = rowIdx;
        this.spaceIdx = spaceIdx;
    }

    @Override
    public boolean isWalkable(Dude walker) {
        return false;
    }

    @Override
    public boolean isObstacle() {
        return false;
    }

    @Override
    public Dude getOccupant() {
        return null;
    }

    @Override
    public void setOccupant(Dude occupant) throws CannotOccupySpaceException, SpaceOccupiedException {
        throw new CannotOccupySpaceException();
    }

    @Override
    public int getRowIdx() {
        return rowIdx;
    }

    @Override
    public int getSpaceIdx() {
        return spaceIdx;
    }
}
