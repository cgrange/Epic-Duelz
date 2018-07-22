package board;

import player.Dude;
import board.exceptions.CannotOccupySpaceException;
import board.exceptions.SpaceOccupiedException;

public interface ISpace {
    /**
     * @return true if you can walk over this space, false otherwise
     */
    public boolean isWalkable(Dude walker);

    /**
     * @return true if you can't SHOOT over this space, false otherwise. Not to be confused with isWalkable.
     */
    public boolean isObstacle();

    /**
     * @return the Character that occupies this space, or null if empty
     */
    public Dude getOccupant();
    public void setOccupant(Dude occupant) throws CannotOccupySpaceException, SpaceOccupiedException;

    public int getRowIdx();
    public int getSpaceIdx();
}
