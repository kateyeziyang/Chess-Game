package main.pieces;

import main.Board;

/**
 * Queen subclass of Piece.
 */
public class Queen extends Piece {
    /**
     * Create Queen of player pid.
     * @param pid the player
     */
    public Queen(char pid) {
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
        return isStraightMove(r, c, board) || isDiagonalMove(r, c, board);
    }

    /**
     * Return the name of this piece.
     * @return the name of this piece.
     */
    public String getType() {
        return "Queen";
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
            System.out.print("\u2655");
        } else {
            System.out.print("\u265B");
        }
    }
}
