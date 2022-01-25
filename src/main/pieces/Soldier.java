package main.pieces;

import main.Board;

/**
 * Soldier subclass of Piece. It can move (leap) to anywhere within 2 units.
 */
public class Soldier extends Piece {
    /**
     * Create Soldier of player pid.
     * @param pid the player
     */
    public Soldier(char pid) {
        player = pid;
    }

    /**
     * Return true if it's a valid movement for this piece.
     * @param r the row number
     * @param c the column number
     * @param board current board
     * @return true if it's a valid movement for this piece.
     */
    public boolean isValidMove(int r, int c, Board board) {
        return board != null && board.isValidLocation(r, c)
                && Math.abs(r - rowLoc) <= 2 && Math.abs(c - colLoc) <= 2;
    }

    /**
     * Return the name of this piece.
     * @return the name of this piece.
     */
    public String getType() {
        return "Soldier";
    }

    /**
     * Does nothing for this piece.
     */
    public void setFirstMoveFalse() {}


    /**
     * Print the unicode of this piece.
     */
    public void print() {
        if (player == 'W') {
            System.out.print("S");
        } else {
            System.out.print("s");
        }
    }
}

