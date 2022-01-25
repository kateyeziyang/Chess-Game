package main;

import main.pieces.Piece;

/**
 * class to create a step for usage in undo method
 */
public class Step {
    public Piece activePiece;
    public Piece capturedPiece;
    public int[] movement;

    /**
     * default constructor
     * @param ap active Piece
     * @param cp the piece at future location
     * @param m representation of movement
     */
    public Step(Piece ap, Piece cp, int[] m) {
        activePiece = ap;
        capturedPiece = cp;
        movement = m;
    }
}
