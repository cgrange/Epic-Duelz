package board;

import player.Dude;
import board.exceptions.CannotOccupySpaceException;
import board.exceptions.SpaceOccupiedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlacementTests {
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

    // \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    // ================ PLACEMENT TESTS =====================
    // //////////////////////////////////////////////////////
    @Test
    void placeDudeOntoWalkableSpace(){
        try{
            board.placeDude(han, 0, 0);
        } catch (CannotOccupySpaceException e) {
            assertTrue(false);
            e.printStackTrace();
        } catch (SpaceOccupiedException e) {
            assertTrue(false);
            e.printStackTrace();
        }
        assertTrue(board.rows[0].getSpace(0).getOccupant() == han);
    }

    @Test
    void placeDudeOntoObstacleSpace(){
        assertThrows(CannotOccupySpaceException.class, ()->{
            board.placeDude(han, 3, 3);
        });
    }

    @Test
    void placeDudeOntoDropoffSpace(){
        assertThrows(CannotOccupySpaceException.class, ()->{
            board.placeDude(han,0,6);
        });
    }

    @Test
    void placeDudeOffBoard(){ //in every direction
        assertThrows(CannotOccupySpaceException.class, ()->{
            board.placeDude(han, -1, 0);
        });
        assertThrows(CannotOccupySpaceException.class, ()->{
            board.placeDude(han, 0, 10);
        });
        assertThrows(CannotOccupySpaceException.class, ()->{
            board.placeDude(han, 7, 0);
        });
        assertThrows(CannotOccupySpaceException.class, ()->{
            board.placeDude(han, 0, -1);
        });
    }

    @Test
    void placeDudeOnTopOfTeammate(){
        assertThrows(SpaceOccupiedException.class, ()->{
            board.placeDude(han, 0,0);
            board.placeDude(chewie, 0, 0);
        });
    }

    @Test
    void placeDudeOnTopOfEnemy(){
        assertThrows(SpaceOccupiedException.class, ()->{
            board.placeDude(han, 0,0);
            board.placeDude(boba, 0, 0);
        });
    }

    @Test
    void placeDudeAgain(){
        try{
            board.placeDude(han, 0, 0);
            board.placeDude(han, 0, 1);
            assertNull(board.rows[0].getSpace(0).getOccupant());
            assertTrue(board.rows[0].getSpace(1).getOccupant() == han);
            board.placeDude(han, 0, 1);
        } catch(CannotOccupySpaceException | SpaceOccupiedException e){
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
