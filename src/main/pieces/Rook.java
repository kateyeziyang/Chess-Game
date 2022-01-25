package main.pieces;

import main.Board;

/**
 * Rook subclass of Piece.
 */
public class Rook extends Piece {
    /**
     * Create Rook of player pid.
     * @param pid the player
     */
    public Rook(char pid) {
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
        return isStraightMove(r, c, board);
    }

    /**
     * Return the name of this piece.
     * @return the name of this piece.
     */
    public String getType() {
        return "Rook";
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
            System.out.print("\u2656");
        } else {
            System.out.print("\u265C");
        }
    }
}
