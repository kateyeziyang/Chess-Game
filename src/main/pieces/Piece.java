package main.pieces;

import main.Board;

/**
 * Abstract class for chess pieces.
 */
public abstract class Piece {
    protected int rowLoc = -1, colLoc = -1;
    protected char player = 'N';

    /**
     * Return row number of this piece.
     * @return row number of this piece.
     */
    public int getRow() {
        return rowLoc;
    }

    /**
     * Return column number of this piece.
     * @return column number of this piece.
     */
    public int getCol() {
        return colLoc;
    }

    /**
     * Set the location of this piece.
     * @param r row number to be set
     * @param c column number to be set
     */
    public void setLoc(int r, int c) {
        rowLoc = r;
        colLoc = c;
    }

    /**
     * Return the player of this piece.
     * @return the player of this piece.
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Set the player of this piece.
     * @param c the player to be set.
     */
    public void setPlayer(char c) {
        player = c;
    }

    /**
     * Helper function for Rook and Queen's movement.
     * @param r the row number
     * @param c the column number
     * @param board current board
     * @return true if it's a valid straight movement.
     */
    public boolean isStraightMove(int r, int c, Board board) {
        if (board != null && board.isValidLocation(r, c)) {
            if (r == rowLoc && c != colLoc) {
                int smaller = colLoc, bigger = c;
                if (c < colLoc) { smaller = c; bigger = colLoc; }
                for (int i = smaller + 1; i < bigger; i++) {
                    if (board.getPieceAt(r, i) != null) return false;
                }
                return true;
            }
            if (c == colLoc && r != rowLoc) {
                int smaller = rowLoc, bigger = r;
                if (c < colLoc) { smaller = r; bigger = rowLoc; }
                for (int i = smaller + 1; i < bigger; i++) {
                    if (board.getPieceAt(i, c) != null) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Helper function for Bishop and Queen's movement.
     * @param r the row number
     * @param c the column number
     * @param board current board
     * @return true if it's a valid diagonal movement.
     */
    public boolean isDiagonalMove(int r, int c, Board board) {
        if (board != null && board.isValidLocation(r, c)) {
            int rDiff = r - rowLoc;
            int cDiff = c - colLoc;
            if (rDiff == cDiff) {
                for (int i = 1; i < rDiff; i++) {
                    if (board.getPieceAt(rowLoc+i, colLoc+i) != null) return false;
                }
                return true;
            }
            if (rDiff == -cDiff) {
                for (int i = 1; i < rDiff; i++) {
                    if (board.getPieceAt(rowLoc+i, colLoc-i) != null) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if (r, c) doesn't equal current location.
     * @param r the row number
     * @param c the column number
     * @return true if (r, c) doesn't equal current location.
     */
    public boolean differentLocation(int r, int c) {
        return r != rowLoc || c != colLoc;
    }

    /**
     * Return true if it's a valid movement for this piece.
     * @param r the row number
     * @param c the column number
     * @param board current board
     * @return true if it's a valid movement for this piece.
     */
    public abstract boolean isValidMove(int r, int c, Board board);

    /**
     * Return the name of this piece.
     * @return the name of this piece.
     */
    public abstract String getType();

    /**
     * For pawn, set its firstMove attribute to false.
     */
    public abstract void setFirstMoveFalse();

    /**
     * Print the unicode of each chess piece.
     */
    public abstract void print();
}
