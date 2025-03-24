package ui;

import java.util.Map;
import chess.*;

public class DrawBoard {
    private final String[][] chessBoard = new String[10][10];
    private final ChessGame chessGame;

    public DrawBoard() {
        initializeBoard();
        chessGame = new ChessGame();
        placePieces(chessGame);
    }

    public void printBothBoards() {
        printBoard(getReversedBoard());
        printBoard(chessBoard);
        System.out.println(EscapeSequences.RESET_BG_COLOR + EscapeSequences.RESET_TEXT_COLOR);
    }

    private void printBoard(String[][] board) {
        System.out.println();
        for (String[] row : board) {
            for (String cell : row) {
                System.out.print(cell + EscapeSequences.RESET_BG_COLOR);
            }
            System.out.println();
        }
    }

    private void initializeBoard() {
        String[] colLabels = {" ", " A ", " B ", " C ", " D ", " E ", " F ", " G ", " H ", " "};
        String[] rowLabels = {" ", " 8 ", " 7 ", " 6 ", " 5 ", " 4 ", " 3 ", " 2 ", " 1 ", " "};

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (row == 0 || row == 9) chessBoard[row][col] = colLabels[col];
                else if (col == 0 || col == 9) chessBoard[row][col] = rowLabels[row];
                else chessBoard[row][col] = (row + col) % 2 == 0 ? EscapeSequences.SET_BG_COLOR_WHITE + " " : EscapeSequences.SET_BG_COLOR_BLACK + " ";
            }
        }
    }

    private void placePieces(ChessGame game) {
        ChessBoard board = game.getBoard();
        Map<ChessPosition, ChessPiece> pieceMap = board.getAllPieces();

        for (var entry : pieceMap.entrySet()) {
            ChessPiece piece = entry.getValue();
            ChessPosition pos = entry.getKey();
            int row = pos.getRow();
            int col = pos.getColumn();

            String bgColor = (row + col) % 2 == 0 ? EscapeSequences.SET_BG_COLOR_WHITE : EscapeSequences.SET_BG_COLOR_BLACK;
            String textColor = piece.getTeamColor() == ChessGame.TeamColor.BLACK ? EscapeSequences.SET_TEXT_COLOR_BLUE : EscapeSequences.SET_TEXT_COLOR_RED;
            chessBoard[row][col] = bgColor + textColor + getPieceSymbol(piece);
        }
    }

    private String getPieceSymbol(ChessPiece piece) {
        return switch (piece.getPieceType()) {
            case KING -> piece.getTeamColor() == ChessGame.TeamColor.BLACK ? EscapeSequences.BLACK_KING : EscapeSequences.WHITE_KING;
            case QUEEN -> piece.getTeamColor() == ChessGame.TeamColor.BLACK ? EscapeSequences.BLACK_QUEEN : EscapeSequences.WHITE_QUEEN;
            case ROOK -> piece.getTeamColor() == ChessGame.TeamColor.BLACK ? EscapeSequences.BLACK_ROOK : EscapeSequences.WHITE_ROOK;
            case BISHOP -> piece.getTeamColor() == ChessGame.TeamColor.BLACK ? EscapeSequences.BLACK_BISHOP : EscapeSequences.WHITE_BISHOP;
            case KNIGHT -> piece.getTeamColor() == ChessGame.TeamColor.BLACK ? EscapeSequences.BLACK_KNIGHT : EscapeSequences.WHITE_KNIGHT;
            case PAWN -> piece.getTeamColor() == ChessGame.TeamColor.BLACK ? EscapeSequences.BLACK_PAWN : EscapeSequences.WHITE_PAWN;
        };
    }

    private String[][] getReversedBoard() {
        String[][] reversed = new String[10][10];
        for (int row = 0; row < 10; row++) {
            System.arraycopy(chessBoard[9 - row], 0, reversed[row], 0, 10);
        }
        return reversed;
    }
}