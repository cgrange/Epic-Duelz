package board;

import board.exceptions.InvalidMovementException;
import player.Dude;
import board.exceptions.CannotMoveThatFarException;
import board.exceptions.CannotOccupySpaceException;
import board.exceptions.SpaceOccupiedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovementTests {
    final String kaminoJson = "[" +
            "[{'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}]," + //1
            "[{'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'w', 'dude': null}]," + //2
            "[{'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}]," + //3
            "[{'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'o', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}]," + //4
            "[{'type': 'w', 'dude': null}, {'type': 'o', 'dude': null}, {'type': 'o', 'dude': null}, {'type': 'o', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}]," + //5
            "[{'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'o', 'dude': null}, {'type': 'o', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'w', 'dude': null}]," + //6
            "[{'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'w', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}, {'type': 'd', 'dude': null}]" + //7
            "]";

    Board board;
    Dude han, chewie, boba, greedo;

    @BeforeEach
    void SetUp(){
        board = new Board(kaminoJson);
        han = new Dude(13, 0, "Han Solo", 0, false);
        chewie = new Dude(15, 0, "Chewbacca", 0, false);
        boba = new Dude(14, 0, "Boba Fett", 1, false);
        greedo = new Dude(7, 0, "Greedo", 1, false);
    }

    /**
     * Place the dude and fail if there are any exceptions with placement since that isn't what we're testing here. Must use in movement test cases that contain assertThrows statements.
     * @param dude
     * @param rowIdx
     * @param spaceIdx
     */
    void placeDudeInMovementTest(Dude dude, int rowIdx, int spaceIdx){
        try{
            board.placeDude(dude, rowIdx,spaceIdx);
        } catch (SpaceOccupiedException | CannotOccupySpaceException e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    void moveMaxDistance(){
        try {
            board.placeDude(han, 0, 0);
            //horizontal
            board.moveDude(han, 2, 0, 2);
            assertTrue(board.rows[0].getSpace(2).getOccupant() == han);
            assertNull(board.rows[0].getSpace(0).getOccupant());
            //vertical
            board.moveDude(han, 2, 2,2);
            assertTrue(board.rows[2].getSpace(2).getOccupant() == han);
            assertNull(board.rows[0].getSpace(2).getOccupant());
            //diagonal
            board.moveDude(han, 2,1,1);
            assertTrue(board.rows[1].getSpace(1).getOccupant() == han);
            assertNull(board.rows[2].getSpace(2).getOccupant());
        } catch (CannotOccupySpaceException | CannotMoveThatFarException | SpaceOccupiedException | InvalidMovementException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    void moveLessThanMaxDistance(){
        try {
            board.placeDude(han, 0, 0);
            board.moveDude(han, 5, 1,3);
            assertTrue(board.rows[1].getSpace(3).getOccupant() == han);
            assertNull(board.rows[0].getSpace(0).getOccupant());
        } catch (CannotOccupySpaceException | CannotMoveThatFarException | SpaceOccupiedException | InvalidMovementException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    void moveFurtherThanMaxDistance(){
        placeDudeInMovementTest(han,0,0);

        assertThrows(CannotMoveThatFarException.class, ()->{
            board.moveDude(han, 5, 1, 5);
        });
        assertThrows(CannotMoveThatFarException.class, ()->{
            board.moveDude(han, 5,6,0);
        });
        assertThrows(CannotMoveThatFarException.class, ()->{
            board.moveDude(han, 5,6,1);
        });
    }

    @Test
    void moveOffScreen(){
        placeDudeInMovementTest(han, 0, 0);
        assertThrows(CannotOccupySpaceException.class, ()->{
            board.moveDude(han, 5, -1, 0);
        });
        assertThrows(CannotOccupySpaceException.class, ()->{
            board.moveDude(han, 5, 0, -1);
        });
        placeDudeInMovementTest(chewie, 6, 0);
        assertThrows(CannotOccupySpaceException.class, ()->{
            board.moveDude(chewie, 5, 7, 0);
        });
        placeDudeInMovementTest(boba, 3, 9);
        assertThrows(CannotOccupySpaceException.class, ()->{
            board.moveDude(boba, 5, 3, 10);
        });
    }

    @Test
    void moveOntoObstacle(){
        placeDudeInMovementTest(han, 5, 1);
        assertThrows(CannotOccupySpaceException.class, ()->{
            board.moveDude(han, 5, 5,2);
        });
        assertThrows(CannotOccupySpaceException.class, ()->{
            board.moveDude(han, 5, 4,1);
        });
    }

    @Test
    void moveOntoDropoff(){
        placeDudeInMovementTest(han, 0, 5);
        assertThrows(CannotOccupySpaceException.class, ()->{
            board.moveDude(han, 5, 0,6);
        });
    }

    @Test
    void moveToSameSpace(){
        placeDudeInMovementTest(han, 0, 0);
        assertThrows(InvalidMovementException.class, ()->{
            board.moveDude(han, 5, 0, 0);
        });
    }

    @Test
    void moveTooFarWithObstacle(){
        placeDudeInMovementTest(han, 5, 1);
        assertThrows(CannotMoveThatFarException.class, ()->{
            board.moveDude(han, 3, 3, 1);
        });
    }

    @Test
    void moveMaxDistanceWithObstacle(){
        placeDudeInMovementTest(han, 5, 1);
        try{
            board.moveDude(han, 4, 3, 1);
        } catch (CannotOccupySpaceException | CannotMoveThatFarException | SpaceOccupiedException | InvalidMovementException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    void moveTooFarWithDropoff(){
        placeDudeInMovementTest(han, 1, 9);
        assertThrows(CannotMoveThatFarException.class, ()->{
            board.moveDude(han, 5, 1, 5);
        });
        assertTrue(board.rows[1].getSpace(9).getOccupant() == han);
        assertNull(board.rows[1].getSpace(5).getOccupant());
    }

    @Test
    void moveMaxDistanceWithDropoff(){
        placeDudeInMovementTest(han, 1, 9);
        try{
            board.moveDude(han, 6, 1, 5);
            assertTrue(board.rows[1].getSpace(5).getOccupant() == han);
            assertNull(board.rows[1].getSpace(9).getOccupant());
        } catch (CannotOccupySpaceException | CannotMoveThatFarException | SpaceOccupiedException | InvalidMovementException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    void moveOnTopOfTeammate(){
        placeDudeInMovementTest(han, 0, 0);
        placeDudeInMovementTest(chewie, 0, 1);
        assertThrows(SpaceOccupiedException.class, ()->{
            board.moveDude(han, 8, 0, 1);
        });
    }

    @Test
    void moveOnTopOfEnemy(){
        placeDudeInMovementTest(han, 0, 0);
        placeDudeInMovementTest(boba, 0, 1);
        assertThrows(SpaceOccupiedException.class, ()->{
            board.moveDude(han, 8, 0, 1);
        });
    }

    @Test
    void moveOverTeammate(){
        placeDudeInMovementTest(han, 6, 2);
        placeDudeInMovementTest(chewie, 6, 1);
        try {
            board.moveDude(han, 2, 5, 1);
        } catch (CannotOccupySpaceException | CannotMoveThatFarException | SpaceOccupiedException | InvalidMovementException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    void moveOverEnemy(){
        // too far (because enemy)
        placeDudeInMovementTest(han, 6, 2);
        placeDudeInMovementTest(boba, 6, 1);
        assertThrows(CannotMoveThatFarException.class, ()->{
            board.moveDude(han, 2, 5, 1);
        });
        // just far enough (because enemy)
        placeDudeInMovementTest(chewie, 6, 4);
        placeDudeInMovementTest(greedo, 5, 4);
        try {
            board.moveDude(chewie, 4, 4, 4);
        } catch (CannotOccupySpaceException | CannotMoveThatFarException | SpaceOccupiedException | InvalidMovementException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        // boxed in
        placeDudeInMovementTest(greedo, 6, 3);
        assertThrows(CannotMoveThatFarException.class, ()->{
            board.moveDude(han, 5, 6, 0);
        });
        assertThrows(CannotMoveThatFarException.class, ()->{
            board.moveDude(han, 5, 6, 4);
        });
    }
}
