package main;

import main.pieces.*;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * The main class to set board.
 */
public class Chess {
    private Board board;
    private char curPlayer = 'W';
    private String whiteName = "White", blackName = "Black";
    private Piece kingW, kingB;
    private HashMap<String, int[]> scores = new HashMap<>();
    private Stack<Step> steps = new Stack<>();

    /**
     * Default constructor creating a 8x8 board with 'W' as the first and lower player.
     */
    public Chess() {
        board = new Board();
        setup('W', true, false);
        setup('B', false, false);
        scores.put(whiteName, new int[]{0, 0, 0});
        scores.put(blackName, new int[]{0, 0, 0});
    }

    /**
     * Constructor to setup customized board and players.
     * @param WIsFirstPlayer true if 'W' should be the first player.
     * @param WIsLowerPlayer true if 'W' should be the lower player.
     * @param boardRows number of rows of the board.
     * @param boardCols number of columns of the board.
     * @param wk white king's name
     * @param bk black king's name
     * @param useCustomPieces will replace pawns by custom pieces if true
     */
    public Chess(boolean WIsFirstPlayer, boolean WIsLowerPlayer,
                 int boardRows, int boardCols, String wk, String bk, boolean useCustomPieces) {
        setGame(WIsFirstPlayer, WIsLowerPlayer, boardRows, boardCols, wk, bk, useCustomPieces);
        scores.put(whiteName, new int[]{0, 0, 0});
        scores.put(blackName, new int[]{0, 0, 0});
    }

    /**
     * Helper function to set game.
     * @param WIsFirstPlayer true if 'W' should be the first player.
     * @param WIsLowerPlayer true if 'W' should be the lower player.
     * @param boardRows number of rows of the board.
     * @param boardCols number of columns of the board.
     * @param wk white king's name
     * @param bk black king's name
     * @param useCustomPieces will replace pawns by custom pieces if true
     */
    private void setGame(boolean WIsFirstPlayer, boolean WIsLowerPlayer, int boardRows, int boardCols,
                         String wk, String bk, boolean useCustomPieces) {
        board = new Board(boardRows, boardCols);
        if (!WIsFirstPlayer) { curPlayer = 'B'; }
        whiteName = wk;
        blackName = bk;

        if (WIsLowerPlayer) {
            setup('W', true, useCustomPieces);
            setup('B', false, useCustomPieces);
        } else {
            setup('B', true, useCustomPieces);
            setup('W', false, useCustomPieces);
        }
    }

    /**
     * Setup pieces on board at the beginning of the game.
     * Only used by constructor.
     * @param player set up pieces for 'W' or 'B'.
     * @param lower true if we are setting pieces at lower part of the board.
     * @param useCustomPieces will replace pawns by custom pieces if true
     */
    private void setup(char player, boolean lower, boolean useCustomPieces) {
        int kingRow = 0, pawnRow = 1;
        boolean pawnDirDown = true;
        if (lower) { kingRow = board.getNumRows()-1; pawnRow = kingRow - 1; pawnDirDown = false;}
        
        // Rook
        Piece r1 = new Rook(player), r2 = new Rook(player);
        board.setPieceAt(r1, kingRow, 0);
        board.setPieceAt(r2, kingRow, 7);

        // Knight
        Piece k1 = new Knight(player), k2 = new Knight(player);
        board.setPieceAt(k1, kingRow, 1);
        board.setPieceAt(k2, kingRow, 6);

        // Bishop
        Piece b1 = new Bishop(player), b2 = new Bishop(player);
        board.setPieceAt(b1, kingRow, 2);
        board.setPieceAt(b2, kingRow, 5);

        // King & Queen
        Piece k = new King(player), q = new Queen(player);
        board.setPieceAt(q, kingRow, 3);
        board.setPieceAt(k, kingRow, 4);
        if (player == 'W') kingW = k;
        else kingB = k;

        if (useCustomPieces) {
            Piece t = new Tank(player);
            Piece s = new Soldier(player);
            board.setPieceAt(t, pawnRow, 1);
            board.setPieceAt(s, pawnRow, 6);
        }

        // Pawns
        for (int i = 0; i < 8; i++) {
            Piece p = new Pawn(player, pawnDirDown);
            board.setPieceAt(p, pawnRow, i);
        }
    }

    /**
     * Return game board.
     * @return game board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Return current player of the player.
     * @return current player of the game.
     */
    public char getCurPlayer() {
        return curPlayer;
    }

    /**
     * Set current player of the game.
     * @param player player to be set as the current player.
     */
    public void setCurPlayer(char player) {
        curPlayer = player;
    }

    /**
     * Similar to setPieceAt() in Board class, but intended to be used by players.
     * Only moves if it's a valid movement for the player.
     * Should check game condition after using this method.
     * @param p Piece to be moved
     * @param r the row number
     * @param c the column number
     * @return true if piece is moved successfully.
     */
    public boolean movePieceTo(char player, Piece p, int r, int c) {
        if (p == null) return false;
        if (!p.differentLocation(r, c)) return false;
        if (p.getPlayer() != player) return false;

        boolean ok = p.isValidMove(r, c, board) && board.setPieceAt(p, r, c);
        if (ok) {
            // set first move condition to false
            p.setFirstMoveFalse();
        }
        return ok;
    }

    /**
     * Return the piece at (i, j). This method calls the board method.
     * @param i row number
     * @param j column number
     * @return the piece if there is a piece at (r, c), null otherwise.
     */
    public Piece getPieceAt(int i, int j) {
        return board.getPieceAt(i, j);
    }

    /**
     * Set p at (r, c). This method calls the board method.
     * @param p Piece to be set
     * @param r the row number
     * @param c the column number
     * @return true if set successfully.
     */
    public boolean setPieceAt(Piece p, int r, int c) {
        return board.setPieceAt(p, r, c);
    }

    /**
     * Return true if the game is in checkmate.
     * @param player the active player
     * @return true if the game is in checkmate.
     */
    public boolean checkCheckmate(char player) {
        Piece inDangerKing = player == 'W' ? kingB : kingW;
        int kRow = inDangerKing.getRow(), kCol = inDangerKing.getCol();

        for (int r = 0; r < board.getNumRows(); r++) {
            for (int c = 0; c < board.getNumCols(); c++) {
                Piece p = board.getPieceAt(r, c);
                if (p != null && p.getPlayer() == player) {
                    if (p.isValidMove(kRow, kCol, board)) return true;
                }
            }
        }
        return false;
    }

    /**
     * Print board in terminal.
     */
    public void printBoard() {
        int r = 1;
        for (int j = 0; j < board.getNumRows(); j++) {
            System.out.print(r);
            r++;
            for (int i = 0; i < board.getNumCols(); i++) {
                System.out.print("\t");
                Piece p = board.getPieceAt(j, i);
                if (p != null) {
                    p.print();
                } else {
                    System.out.print("\u2022");
                }
            }
            System.out.println();
        }

        for (char a = 'a'; a < 'i'; a++) {
            System.out.print("\t");
            System.out.print(a);
        }
        System.out.println();
    }

    /**
     * Print simple prompt to ask user for input in each turn.
     */
    public void printPrompt() {
        String curName = curPlayer == 'W' ? whiteName : blackName;
        System.out.print(curPlayer + " " + curName + "> ");
    }

    /**
     * Get string for prompt, used for asking user input
     * @param player player to be used in string
     * @return string for prompt, used for asking user input
     */
    public String getPrompt(char player) {
        String str = player == 'W' ? whiteName : blackName;
        return player + " " + str + "> ";
    }

    /**
     * Simple helper function used in main to ask for valid input.
     * @param in Scanner to be used repeatedly
     * @param question question to be printed
     * @return true if input is "y"
     */
    public static boolean getBoolInput(Scanner in, String question) {
        System.out.println(question);
        printHelper(1);
        String s = in.nextLine();
        boolean ok = s.equals("y") || s.equals("n");
        while (!ok) {
            System.out.println("Please give a valid input ('y' or 'n').");
            s = in.nextLine();
            System.out.println("isit????");
            ok = s.equals("y") || s.equals("n");
        }
        return s.equals("y");
    }

    /**
     * Print question and get answer.
     * @param in Scanner to be used repeatedly
     * @param question question to be printed
     * @return user's input
     */
    public static String getStrInput(Scanner in, String question) {
        System.out.println(question);
        return in.nextLine();
    }

    /**
     * Helper function to print repeatedly used strings.
     * @param mode flag for which string to print
     */
    public static void printHelper(int mode) {
        switch (mode) {
            case 1:
                System.out.println("Type single letter 'y' for yes, 'n' for no.");
                break;
            case 2:
                System.out.println("Please give a valid input! (four integers in the same line, " +
                        "separated by single space)!");
                break;
            case 3:
                System.out.println("Your piece cannot move in such way!");
                break;
            case 4:
                System.out.println("Please give a different name for black king!");
                break;
            case 5:
                System.out.println("There are no actions to be undo.");
                break;
            default:
        }
    }

    /**
     * Read user's input and act accordingly
     * @param str user's input
     * @param in Scanner to be used repeatedly
     * @return true if the game should continue
     */
    public boolean readStr(String str, Scanner in) {
        if (str.equals("exit")) {
            return false;
        }

        if (str.equals("undo")) {
            if (steps.empty()) {
                printHelper(5);
                return true;
            }
            Step step = steps.pop();
            int[] loc = step.movement;
            board.setPieceAt(step.activePiece, loc[0], loc[1]);
            if (step.capturedPiece != null) board.setPieceAt(step.capturedPiece, loc[2], loc[3]);
            curPlayer = curPlayer == 'W' ? 'B' : 'W';
            return true;
        }

        if (str.equals("forfeit")) {
            curPlayer = curPlayer == 'W' ? 'B' : 'W';
            addScoreForPlayer();
            return resetGame(in);
        }

        if (str.equals("restart")) {
            char anotherPlayer = curPlayer == 'W' ? 'B' : 'W';
            boolean ok = getBoolInput(in, getPrompt(anotherPlayer) + "Do you also want to restart?");
            boolean continueGame = true;
            if (ok) {
                scores.get(whiteName)[1]++;
                scores.get(blackName)[1]++;
                continueGame = resetGame(in);
            } else System.out.println("Sorry, one of the players doesn't want to restart.");
            return continueGame;
        }

        int[] loc = getIntsFromStr(str);
        if (loc == null) return true;
        return movePieceByInts(in, loc);
    }

    /**
     * Move piece by int array.
     * @param in  Scanner to be used repeatedly
     * @param loc first two ints are current location and the next two are future location.
     * @return true if game should continue.
     */
    private boolean movePieceByInts(Scanner in, int[] loc) {
        Piece p = board.getPieceAt(loc[0], loc[1]);
        Piece cp = board.getPieceAt(loc[2], loc[3]);
        boolean ok = movePieceTo(curPlayer, p, loc[2], loc[3]);
        if (!ok) {
            printHelper(3);
            return true;
        }

        steps.push(new Step(p, cp, loc));

        curPlayer = curPlayer == 'W' ? 'B' : 'W';
        ok = checkCheckmate(curPlayer);
        if (!ok) return true;

        addScoreForPlayer();
        return resetGame(in);
    }

    /**
     * Add score as current game ends.
     */
    private void addScoreForPlayer() {
        String winner = curPlayer == 'W' ? whiteName : blackName;
        String loser = curPlayer == 'W' ? blackName : whiteName;
        scores.get(winner)[0]++;
        scores.get(loser)[2]++;
    }

    /**
     * Parse string to a int array of length 4.
     * @param str input string
     * @return the int array parsed
     */
    public static int[] getIntsFromStr(String str) {
        int spaceCount = 0;
        int[] loc = new int[4];
        int prev = 0;
        for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(c == ' ') {
                loc[spaceCount] = Integer.parseInt(str.substring(prev, i));
                spaceCount++;
                prev = i+1;
            }
            if(c < '0' || c > '9') {
                printHelper(2);
                return null;
            }
        }
        return loc;
    }

    /**
     * Get another unique name for player.
     * @param existedName the name already given
     * @param in Scanner
     * @return unique name
     */
    public static String getUniqueName(String existedName, Scanner in) {
        String bk = getStrInput(in, "Please type in black king's name.");
        if (bk.equals(existedName)) {
            printHelper(4);
            bk = getStrInput(in, "Please type in black king's name.");
        }
        return bk;
    }

    /**
     * Reset game if a new one should start
     * @param in Scanner
     * @return true if game should continue.
     */
    private boolean resetGame(Scanner in) {
        boolean ok;
        ok = getBoolInput(in, "Would you like to start a new game?");
        if (!ok) { return false; }

        ok = getBoolInput(in, "Would you like to continue with current players?");
        String wk, bk;
        if (ok) {
            ok = getBoolInput(in, "Is new white king the player with name " + whiteName +" ?");
            wk = ok ? whiteName : blackName;
            bk = ok ? blackName : whiteName;
        } else {
            scores.clear();
            wk = getStrInput(in, "Please type in white king's name.");
            bk = getUniqueName(wk, in);
        }
        steps.clear();

        boolean WIsFirst = getBoolInput(in, "White king, would you like to start first?");
        boolean WIsLower = getBoolInput(in, "White king, would you like to be placed at lower side?");
        boolean useCustomPieces = getBoolInput(in, "Do you want some pawns being replaced by custom pieces?");
        setGame(WIsFirst, WIsLower, 8, 8, wk, bk, useCustomPieces);
        return true;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Chess game;
        String str;

        if (!getBoolInput(in, "Would you like default setup for the game? (Skip setting)\n"
            +"In default setting, white king is be placed at the lower side and moves first.")) {
            boolean WIsFirst = getBoolInput(in, "White king, would you like to start first?");
            boolean WIsLower = getBoolInput(in, "White king, would you like to be placed at lower side?");

            String wk = getStrInput(in, "Please type in white king's name.");
            String bk = getUniqueName(wk, in);

            boolean useCustomPieces = getBoolInput(in, "Do you want some pawns being replaced by custom pieces?");

            game = new Chess(WIsFirst, WIsLower, 8, 8, wk, bk, useCustomPieces);
        } else game = new Chess();

        game.printBoard();
        boolean continueGame = true;
        while (continueGame) {
            game.printPrompt();
            str = in.nextLine();
            continueGame = game.readStr(str, in);
        }
    }
}
