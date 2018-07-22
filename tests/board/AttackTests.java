package board;

import board.exceptions.CannotOccupySpaceException;
import board.exceptions.SpaceOccupiedException;
import player.Dude;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AttackTests {
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
    Dude han, chewie, boba, greedo, obi_wan;

    @BeforeEach
    void SetUp(){
        board = new Board(kaminoJson);
        han = new Dude(13, 0, "Han Solo", 0, false);
        chewie = new Dude(15, 0, "Chewbacca", 0, false);
        boba = new Dude(14, 0, "Boba Fett", 1, false);
        greedo = new Dude(7, 0, "Greedo", 1, false);
        obi_wan = new Dude(18, 0, "Obi Wan Kenobi", 0, true);
    }

    /**
     * Place the dude and fail if there are any exceptions with placement since that isn't what we're testing here. Must use in movement test cases that contain assertThrows statements.
     * @param dude
     * @param rowIdx
     * @param spaceIdx
     */
    void placeDudeInAttackTest(Dude dude, int rowIdx, int spaceIdx){
        try{
            board.placeDude(dude, rowIdx,spaceIdx);
        } catch (SpaceOccupiedException | CannotOccupySpaceException e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    void meleeAttack(){
        placeDudeInAttackTest(boba, 1, 1);
        placeDudeInAttackTest(obi_wan, 0, 0);
        assertTrue(board.canAttack(obi_wan, boba));
        placeDudeInAttackTest(obi_wan, 0, 1);
        assertTrue(board.canAttack(obi_wan, boba));
        placeDudeInAttackTest(obi_wan, 0, 2);
        assertTrue(board.canAttack(obi_wan, boba));
        placeDudeInAttackTest(obi_wan, 1, 2);
        assertTrue(board.canAttack(obi_wan, boba));
        placeDudeInAttackTest(obi_wan, 2, 2);
        assertTrue(board.canAttack(obi_wan, boba));
        placeDudeInAttackTest(obi_wan, 2, 1);
        assertTrue(board.canAttack(obi_wan, boba));
        placeDudeInAttackTest(obi_wan, 2, 0);
        assertTrue(board.canAttack(obi_wan, boba));
        placeDudeInAttackTest(obi_wan, 1, 0);
        assertTrue(board.canAttack(obi_wan, boba));
        placeDudeInAttackTest(obi_wan, 3, 1);
        assertFalse(board.canAttack(obi_wan, boba));
        placeDudeInAttackTest(obi_wan, 1, 3);
        assertFalse(board.canAttack(obi_wan, boba));
        placeDudeInAttackTest(obi_wan, 3, 2);
        assertFalse(board.canAttack(obi_wan, boba));
    }

    @Test
    void horizontalShooting(){
        placeDudeInAttackTest(boba, 0, 0);
        placeDudeInAttackTest(han, 0, 3);
        assertTrue(board.canAttack(boba, han));
        placeDudeInAttackTest(boba, 0, 1);
        placeDudeInAttackTest(han, 0, 0);
        assertTrue(board.canAttack(boba, han));
        placeDudeInAttackTest(han, 2, 1);
        placeDudeInAttackTest(boba, 0, 0);
        assertFalse(board.canAttack(boba, han));
    }

    @Test
    void verticalShooting(){
        placeDudeInAttackTest(boba, 1, 0);
        placeDudeInAttackTest(han, 0, 0);
        assertTrue(board.canAttack(boba, han));
        placeDudeInAttackTest(han, 5, 0);
        placeDudeInAttackTest(boba, 0, 0);
        assertTrue(board.canAttack(boba, han));
        placeDudeInAttackTest(boba, 0, 0);
        placeDudeInAttackTest(han, 1, 2);
        assertFalse(board.canAttack(boba, han));
    }

    @Test
    void diagonalShooting(){
        placeDudeInAttackTest(boba, 0, 0);
        placeDudeInAttackTest(han, 1, 1);
        assertTrue(board.canAttack(boba, han));
        placeDudeInAttackTest(boba, 2, 2);
        placeDudeInAttackTest(han, 0, 0);
        assertTrue(board.canAttack(boba, han));
        placeDudeInAttackTest(boba, 2, 0);
        placeDudeInAttackTest(han, 0, 2);
        assertTrue(board.canAttack(boba, han));
        placeDudeInAttackTest(boba, 0, 3);
        placeDudeInAttackTest(han, 3, 0);
        assertTrue(board.canAttack(boba, han));
        placeDudeInAttackTest(boba, 0, 2);
        assertFalse(board.canAttack(boba, han));
    }

    @Test
    void shootingWithObstacles(){
        //straight
        placeDudeInAttackTest(han, 5, 1);
        placeDudeInAttackTest(boba, 0, 1);
        assertFalse(board.canAttack(boba, han));
        placeDudeInAttackTest(boba, 5, 4);
        assertFalse(board.canAttack(boba, han));
        //diagonal
        placeDudeInAttackTest(boba, 2, 4);
        assertFalse(board.canAttack(boba, han));
        assertFalse(board.canAttack(han, boba));
        placeDudeInAttackTest(boba, 5, 5);
        placeDudeInAttackTest(han, 0, 0);
        assertFalse(board.canAttack(boba, han));
        assertFalse(board.canAttack(han, boba));
    }

    @Test
    void shootingOverDropoff(){
        //straight
        placeDudeInAttackTest(han, 5, 4);
        placeDudeInAttackTest(boba, 5, 9);
        assertTrue(board.canAttack(boba, han));
        //diagonal
        placeDudeInAttackTest(han, 6, 5);
        placeDudeInAttackTest(boba, 4, 7);
        assertTrue(board.canAttack(boba, han));
        assertTrue(board.canAttack(han, boba));
        placeDudeInAttackTest(han, 0, 5);
        placeDudeInAttackTest(boba, 4, 9);
        assertTrue(board.canAttack(boba, han));
        assertTrue(board.canAttack(han, boba));
    }

    @Test
    void shootingWithPeopleInTheWay(){
        //straight
        placeDudeInAttackTest(han, 0, 0);
        placeDudeInAttackTest(greedo, 0, 1);
        placeDudeInAttackTest(boba, 0, 2);
        assertFalse(board.canAttack(boba, han));
        assertFalse(board.canAttack(han, boba));
        placeDudeInAttackTest(greedo, 2, 0);
        placeDudeInAttackTest(boba, 4, 0);
        assertFalse(board.canAttack(boba, han));
        assertFalse(board.canAttack(han, boba));
        assertTrue(board.canAttack(han, greedo));

        //diagonal
        placeDudeInAttackTest(greedo, 1, 1);
        placeDudeInAttackTest(boba, 2, 2);
        assertFalse(board.canAttack(boba, han));
        assertFalse(board.canAttack(han, boba));
        assertTrue(board.canAttack(han, greedo));
        placeDudeInAttackTest(han, 4, 0);
        placeDudeInAttackTest(boba, 0, 4);
        placeDudeInAttackTest(greedo, 2, 2);
        assertFalse(board.canAttack(boba, han));
        assertFalse(board.canAttack(han, boba));
        assertTrue(board.canAttack(han, greedo));
    }

    @Test
    void attackSelf(){
        placeDudeInAttackTest(obi_wan, 0, 0);
        placeDudeInAttackTest(boba, 1, 1);
        assertFalse(board.canAttack(obi_wan, obi_wan));
        assertFalse(board.canAttack(boba, boba));
    }

    @Test
    void attackTeam(){
        placeDudeInAttackTest(boba, 0, 0);
        placeDudeInAttackTest(greedo, 0, 1);
        assertTrue(board.canAttack(greedo, boba));
    }
}
