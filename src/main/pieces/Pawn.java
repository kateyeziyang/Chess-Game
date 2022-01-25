package main.pieces;

import main.Board;

/**
 * Pawn subclass of Piece.
 */
public class Pawn extends Piece {
    private boolean firstMove = true; // Special condition
    private boolean goDown; // Direction of Pawn

    /**
     * Create Pawn of player pid.
     * @param pid the player
     */
    public Pawn(char pid, boolean down) {
        player = pid;
        goDown = down;
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
            if (c == colLoc) {
                int step = goDown ? 1 : -1;
                int step2 = step;
                if (firstMove) step2 = 2 * step;

                if (r-rowLoc == step) return true;
                if (r-rowLoc == step2) {
                    if (board.getPieceAt(rowLoc+step, colLoc) == null) return true;
                }
            }
        }
        return false;
    }

    /**
     * Return the name of this piece.
     * @return the name of this piece.
     */
    public String getType() {
        return "Pawn";
    }

    public void setFirstMoveFalse() {
        firstMove = false;
    }

    /**
     * Print the unicode of this piece.
     */
    public void print() {
        if (player == 'W') {
            System.out.print("\u2659");
        } else {
            System.out.print("\u265F");
        }
    }
}
