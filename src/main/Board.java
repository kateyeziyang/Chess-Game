package main;

import main.pieces.Piece;

/**
 * The class controlling movements of pieces and their locations.
 */
public class Board {
    private int numRows, numCols;
    private Piece[][] board;

    /**
     * Default constructor to create a 8x8 board.
     */
    public Board() {
        numRows = 8;
        numCols = 8;
        board = new Piece[numRows][numCols];
    }

    /**
     * Parameterized constructor to create a rxc board.
     * @param r number of rows of the board.
     * @param c number of columns of the board.
     */
    public Board(int r, int c) {
        numRows = r;
        numCols = c;
        board = new Piece[numRows][numCols];
    }

    /**
     * Return number of rows of the board.
     * @return number of rows of the board.
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Return number of columns of the board.
     * @return number of columns of the board.
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Simple method to decide whether (x, y) is a valid location on this board.
     * @param x row number
     * @param y column number
     * @return true if (x, y) is a valid location on this board.
     */
    public boolean isValidLocation(int x, int y) {
        return (-1 < x && x < numRows && -1 < y && y < numCols);
    }

    /**
     * Return true if there is a piece at (r, c).
     * @param r row number
     * @param c column number
     * @return true if there is a piece at (r, c).
     */
    public boolean hasPieceAt(int r, int c) {
        return isValidLocation(r, c) && board[r][c] != null;
    }

    /**
     * Return the piece at (i, j).
     * @param i row number
     * @param j column number
     * @return the piece if there is a piece at (r, c), null otherwise.
     */
    public Piece getPieceAt(int i, int j) {
        if (isValidLocation(i, j)) return board[i][j];
        else return null;
    }

    /**
     * Remove the piece at (r, c).
     * @param r the row number
     * @param c the column number
     */
    public void removePieceAt(int r, int c) {
        if (isValidLocation(r, c)) board[r][c] = null;
    }

    /**
     * Set p at (r, c).
     * @param p Piece to be set
     * @param r the row number
     * @param c the column number
     * @return true if set successfully.
     */
    public boolean setPieceAt(Piece p, int r, int c) {
        if (isValidLocation(r, c)) {
            // remove piece (if exists) at next location
            Piece temp = getPieceAt(r, c);
            if (temp != null) {
                // fail to move if two piece belong to same player
                if (temp.getPlayer() == p.getPlayer()) return false;

                temp.setLoc(-1, -1);
            }

            // remove this piece at current location
            removePieceAt(p.getRow(), p.getCol());

            // put this piece at next location
            board[r][c] = p;
            p.setLoc(r, c);

            return true;
        }
        return false;
    }
}
