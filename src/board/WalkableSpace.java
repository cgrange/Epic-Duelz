package board;

import player.Dude;
import board.exceptions.CannotOccupySpaceException;
import board.exceptions.SpaceOccupiedException;

public class WalkableSpace implements ISpace {
    private Dude occupant;
    private int rowIdx, spaceIdx;

    public WalkableSpace(int rowIdx, int spaceIdx){
        this.rowIdx = rowIdx;
        this.spaceIdx = spaceIdx;
        this.occupant = null;
    }

    @Override
    public boolean isWalkable(Dude walker) {
        if(occupant != null) {
            if (!walker.sameTeam(occupant)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isObstacle() {
        if(getOccupant() != null) {
            return true;
        }
        return false;
    }

    @Override
    public Dude getOccupant() {
        return occupant;
    }

    @Override
    public void setOccupant(Dude occupant) throws CannotOccupySpaceException, SpaceOccupiedException {
        if(this.occupant != null && occupant != null){
            throw new SpaceOccupiedException();
        }
        else {
            this.occupant = occupant;
        }
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
