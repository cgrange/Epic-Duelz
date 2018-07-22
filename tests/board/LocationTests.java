package board;

import player.Dude;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTests {
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

    @Test
    void getLocationAfterPlacing() {
        try{
            board.placeDude(chewie, 5, 0);
        }
        catch(Exception e){
            assertTrue(false);
            e.printStackTrace();
        }
        ISpace space = board.getLocation(chewie);
        assertTrue(space.getOccupant() == chewie);
        assertEquals(5, space.getRowIdx());
        assertEquals(0, space.getSpaceIdx());
        assertTrue(board.rows[5].getSpace(0).getOccupant() == chewie);
    }

    @Test
    void getLocationBeforePlacing() {
        assertNull(board.getLocation(boba));
    }

    @Test
    void getLocationAfterMoving() {
        try{
            board.placeDude(chewie, 5, 0);
            board.moveDude(chewie, 5, 3, 1);
        }
        catch(Exception e){
            assertTrue(false);
            e.printStackTrace();
        }
        ISpace space = board.getLocation(chewie);
        assertTrue(space.getOccupant() == chewie);
        assertEquals(3, space.getRowIdx());
        assertEquals(1, space.getSpaceIdx());
    }

    @Test
    void dropoffSpaceTest(){
        assertEquals(0, board.rows[0].getSpace(8).getRowIdx());
        assertEquals(8, board.rows[0].getSpace(8).getSpaceIdx());
    }

    @Test
    void obstaceSpaceTest(){
        assertEquals(5, board.rows[5].getSpace(2).getRowIdx());
        assertEquals(2, board.rows[5].getSpace(2).getSpaceIdx());
    }

}