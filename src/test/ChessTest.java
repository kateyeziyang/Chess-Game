package test;

import main.*;
import main.pieces.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static main.Chess.*;
import static org.junit.jupiter.api.Assertions.*;

public class ChessTest {

    /**
     * Test Board default constructor.
     */
    @Test
    public void createDefaultBoard() {
        Board board = new Board();
        assertNotNull(board);
        assertEquals(board.getNumRows(), 8);
        assertEquals(board.getNumCols(), 8);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertNull(board.getPieceAt(i, j));
            }
        }
    }

    /**
     * Test that we can create 6 kinds of chess pieces and their basic functionality works.
     */
    @Test
    public void createBasicPiece() {
        Piece p1 = new Bishop('W');
        Piece p2 = new King('W');
        Piece p3 = new Knight('W');
        Piece p4 = new Pawn('W', true);
        Piece p5 = new Queen('W');
        Piece p6 = new Queen('B');
        assertNotNull(p1);
        assertNotNull(p2);
        assertNotNull(p3);
        assertNotNull(p4);
        assertNotNull(p5);
        assertNotNull(p6);
        assertEquals(p3.getRow(), -1);
        assertEquals(p3.getCol(), -1);
        assertEquals(p4.getType(), "Pawn");
        assertEquals(p5.getPlayer(), 'W');
        assertEquals(p6.getPlayer(), 'B');

        p1.setLoc(0, 0);
        p2.setPlayer('B');
        p4.setFirstMoveFalse();
        assertEquals(p1.getRow(), 0);
        assertEquals(p1.getCol(), 0);
        assertEquals(p2.getPlayer(), 'B');
    }

    /**
     * Test Chess default constructor.
     */
    @Test
    public void createDefaultChess() {
        Chess game = new Chess();
        Board board = game.getBoard();
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                assertNull(board.getPieceAt(i, j));
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                assertNotNull(board.getPieceAt(i, j));
            }
        }
        for (int i = 6; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertNotNull(board.getPieceAt(i, j));
            }
        }
    }

    /**
     * Test Chess constructor with input.
     * For now, only covers 8x8 cases;
     */
    @Test
    public void createCustomizedChess() {
        Chess game = new Chess(false, false, 8, 8, "abc", "xyz", false);
        Board board = game.getBoard();
        assertEquals(board.getNumRows(), 8);
        assertEquals(board.getNumCols(), 8);
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                assertNull(board.getPieceAt(i, j));
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                assertNotNull(board.getPieceAt(i, j));
            }
        }
        for (int i = 6; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertNotNull(board.getPieceAt(i, j));
            }
        }
        assertEquals(game.getCurPlayer(), 'B');
        Piece lowerKing = board.getPieceAt(7, 4);
        assertEquals(lowerKing.getType(), "King");
        assertEquals(lowerKing.getPlayer(), 'B');
    }

    /**
     * Test the isValidMove functions in Piece class works
     */
    @Test
    public void canVerifyValidMove() {
        Chess game = new Chess();
        Board board = game.getBoard();
        Piece pawn10 = board.getPieceAt(1, 0);
        assertFalse(pawn10.isValidMove(1, 0, board));
        assertTrue(pawn10.isValidMove(2, 0, board));
        assertTrue(pawn10.isValidMove(3, 0, board));
        assertFalse(pawn10.isValidMove(3, 4, board));
        assertFalse(pawn10.isValidMove(1, -1, board));
    }

    /**
     * Test basic functionality of board
     */
    @Test
    public void canUseBoardFunctions() {
        Board board = new Board();

        assertFalse(board.hasPieceAt(3 ,3));

        Piece pawn = new Pawn('W', true);
        board.setPieceAt(pawn,3, 3);
        assertTrue(board.hasPieceAt(3 ,3));

        assertTrue(board.setPieceAt(pawn, 4, 3));
        assertFalse(board.hasPieceAt(3 ,3));
        assertTrue(board.hasPieceAt(4 ,3));

        board.removePieceAt(4, 3);
        assertFalse(board.hasPieceAt(4 ,3));
    }

    /**
     * Simple test for checkmate ending.
     */
    @Test
    public void canVerifyCheckmate() {
        Chess game = new Chess();
        assertFalse(game.checkCheckmate('W'));

        Board board = game.getBoard();
        Piece kingB = board.getPieceAt(0, 4);
        board.setPieceAt(kingB, 4, 4);
        Piece kingA = board.getPieceAt(7, 4);
        board.setPieceAt(kingA,5,4);
        assertTrue(game.checkCheckmate('W'));
    }

    /**
     * Simple tests for functions in all classes.
     */
    @Test
    public void someSimpleTests() {
        Chess game = new Chess();
        game.setCurPlayer('B');
        assertEquals(game.getCurPlayer(), 'B');

        Board board = game.getBoard();
        Piece p = board.getPieceAt(7, 4);
        assertFalse(game.movePieceTo('B', p, 4, 4));
        Piece p2 = board.getPieceAt(1, 4);
        assertTrue(game.movePieceTo('B', p2, 2, 4));
        assertTrue(p2.isDiagonalMove(4, 6, board));
        assertTrue(p2.isStraightMove(4, 4, board));

        Chess game2 = new Chess(false, false, 8 , 8, "abc", "xyz", false);
        assertEquals(game2.getCurPlayer(), 'B');
        Board b = game2.getBoard();
        Piece r = b.getPieceAt(0, 0);
        assertEquals(r.getType(), "Rook");
        r.setFirstMoveFalse();
        r = b.getPieceAt(0, 1);
        r.setFirstMoveFalse();
        assertEquals(r.getType(), "Knight");
        r = b.getPieceAt(0, 2);
        r.setFirstMoveFalse();
        assertEquals(r.getType(), "Bishop");
        r = b.getPieceAt(0, 3);
        r.setFirstMoveFalse();
        assertEquals(r.getType(), "Queen");
        r = b.getPieceAt(0, 4);
        r.setFirstMoveFalse();
        assertEquals(r.getType(), "King");

        Chess game3 = new Chess(false, true, 8 , 8, "abc", "xyz", false);
        Board b2 = game3.getBoard();
        Piece r2 = b2.getPieceAt(0, 0);
        assertEquals(r2.getPlayer(), 'B');
    }

    /**
     * Simple tests for functions newly added in 1.1.
     */
    @Test
    public void someSimpleTests2() {
        Chess game = new Chess();
        Board b = game.getBoard();
        game.printBoard();
        Piece k = game.getPieceAt(7, 4);
        assertEquals(k.getType(), "King");
        assertFalse(game.setPieceAt(k, 6, 4));
        assertTrue(game.setPieceAt(k, 1, 4));
        assertFalse(game.setPieceAt(k, 100, 4));

        Piece p = new Tank('B');
        assertEquals(p.getType(), "Tank");
        game.setPieceAt(p, 4, 4);
        assertTrue(p.isValidMove(4, 5, b));
        p.setFirstMoveFalse();
        assertTrue(p.isStraightMove(4, 6, b));
        assertTrue(p.isDiagonalMove(6, 2, b));

        Piece e = new Soldier('B');
        assertEquals(e.getType(), "Soldier");
        game.setPieceAt(e, 5, 5);
        assertTrue(e.isValidMove(3, 3, b));
        assertNull(game.getPieceAt(100, 100));
        e.setFirstMoveFalse();

        game.printBoard();
        p.setPlayer('W');
        e.setPlayer('W');
        game.printBoard();
        game.printPrompt();
    }

    /**
     * Simple tests for functions newly added in 1.2.
     */
    @Test
    public void someSimpleTests3() {
        Rook r = new Rook('B');
        Queen q = new Queen('W');
        int[] intarr = new int[4];
        Step step = new Step(r, q, intarr);
        assertEquals(step.activePiece, r);

        Chess game = new Chess(false, true, 8 , 8, "abc", "xyz", true);
        assertEquals(game.getPieceAt(1, 1).getType(), "Tank");
        assertEquals(game.getPrompt('W'), "W abc> ");

        System.out.println("here1");
        String str = "yes\nn\nhappy\ny\ny\ny\ny\ny\ny\ny\ny\ny\ny\ny\ny\ny\ny\ny\ny\ny\n";
        InputStream inp = new ByteArrayInputStream(str.getBytes());
        Scanner in = new Scanner(System.in);
        System.setIn(inp);

        printHelper(1);
        printHelper(2);
        printHelper(3);
        printHelper(4);
        printHelper(5);
        printHelper(6);

        System.out.println("here3");
        str = "y\ny\ny\ny\ny\ny\ny\ny\ny\ny\ny\ny\ny\ny\ny\ny\ny\n";
        inp = new ByteArrayInputStream(str.getBytes());
        System.setIn(inp);
        assertFalse(game.readStr("exit", in));
        assertTrue(game.readStr("undo", in));

        System.out.println("here8");
        assertTrue(game.readStr("6 0 5 0", in));
        assertTrue(game.readStr("1 0 4 0", in));
        assertTrue(game.readStr("undo", in));

        System.out.println("here7");
        assertNull(getIntsFromStr("aelgiaga"));

        str = "green\nwright";
        inp = new ByteArrayInputStream(str.getBytes());
        System.setIn(inp);
        System.out.println("here6");
    }
}