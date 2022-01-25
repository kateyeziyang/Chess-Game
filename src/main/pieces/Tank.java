package main.pieces;

import main.Board;

/**
 * Tank subclass of Piece. It can only move horizontally by 1 unit.
 */
public class Tank extends Piece {
    /**
     * Create Tank of player pid.
     * @param pid the player
     */
    public Tank(char pid) {
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
        return board != null && board.isValidLocation(r, c) && Math.abs(r - rowLoc) <= 1;
    }

    /**
     * Return the name of this piece.
     * @return the name of this piece.
     */
    public String getType() {
        return "Tank";
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
            System.out.print("T");
        } else {
            System.out.print("t");
        }
    }
}

