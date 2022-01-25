package main.pieces;

import main.Board;

/**
 * Knight subclass of Piece.
 */
public class Knight extends Piece {
    /**
     * Create King of player pid.
     * @param pid the player
     */
    public Knight(char pid) {
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
        if (board != null && board.isValidLocation(r, c)) {
            int rDiff = Math.abs(r - rowLoc);
            int cDiff = Math.abs(c - colLoc);
            if (rDiff == 1 && cDiff == 2) return true;
            if (rDiff == 2 && cDiff == 1) return true;   
        }
        return false;
    }
    
    /**
     * Return the name of this piece.
     * @return the name of this piece.
     */
    public String getType() {
        return "Knight";
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
            System.out.print("\u2658");
        } else {
            System.out.print("\u265E");
        }
    }
}
