package chess;

import java.util.Collection;
import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor teamTurn;
    private ChessBoard board;

    public ChessGame() {
        this.teamTurn =  TeamColor.WHITE;
        this.board = new ChessBoard();
        this.board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();
        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null) {
            return null;
        }

        Collection<ChessMove> possibleMoves = piece.pieceMoves(board, startPosition);
        ChessBoard originalBoard = new ChessBoard(board);

        for (ChessMove move : possibleMoves) {
            //create new method to copy or create new board.
            ChessBoard boardCopy = new ChessBoard(board);
            boardCopy.addPiece(startPosition,null);
            // if PromotionPiece == null then ->board.addPiece(move.getEndPosition(), piece);
            ChessPiece newPiece;

            if(move.getPromotionPiece() != null){
                //else set piece to PromotionPiece
                newPiece = new ChessPiece(piece.getTeamColor(),move.getPromotionPiece());
            }
            else newPiece = piece;
            boardCopy.addPiece(move.getEndPosition(), newPiece);
            //Check if making that move in possible moves puts me in check then you can add it to valid moves.
            ChessGame tempGame = new ChessGame();
            tempGame.setBoard(boardCopy);
            if (!tempGame.isInCheck(piece.getTeamColor())) {
                // Call isInCheck for this function.
                validMoves.add(move);
            }
           // setBoard(originalBoard);
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece = board.getPiece(move.getStartPosition());

        if (piece == null || piece.getTeamColor() != teamTurn) {
            throw new InvalidMoveException("Invalid move, not your turn.");
        }
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        if (!validMoves.contains(move)) {
            throw new InvalidMoveException("Invalid move, not a valid move.");
        }
        ChessBoard boardCopy = new ChessBoard(board);
        boardCopy.addPiece(move.getStartPosition(), null);
        // if PromotionPiece == null then ->board.addPiece(move.getEndPosition(), piece);
        ChessPiece newPiece;
        if(move.getPromotionPiece() != null){
            //else set piece to PromotionPiece
            newPiece = new ChessPiece(piece.getTeamColor(),move.getPromotionPiece());
        }
        else newPiece = piece;
        boardCopy.addPiece(move.getEndPosition(), newPiece);

        board.addPiece(move.getStartPosition(), null);
        board.addPiece(move.getEndPosition(), newPiece);

        teamTurn = (teamTurn == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //Get KING position
        ChessPosition kingPosition = null;
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.getTeamColor() == teamColor
                        && piece.getPieceType() == ChessPiece.PieceType.KING) {
                    kingPosition = position;
                    break;
                }
            }
        }

        //get each of the opposite team's pieceType's possibleMoves
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);
                if(piece != null && piece.getTeamColor() != teamColor) {
                    //this is getting the possible moves and then loop through and see fi it is
                    Collection<ChessMove> moves = piece.pieceMoves(board,position);
                    for (ChessMove move : moves) {
                        if(move.getEndPosition().equals(kingPosition)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
       if(isInCheck(teamColor) && !areValidMoves(teamColor)){
           return true;
       }
       return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if(!isInCheck(teamColor)&& !areValidMoves(teamColor)){
           return true;
        }
        return false;
    }

    public boolean areValidMoves(TeamColor teamColor){
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);

                if (piece != null && piece.getTeamColor() == teamColor){
                    Collection<ChessMove> moves = validMoves(position);
                    if (moves != null && !moves.isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }


    @Override
    public String toString() {
        return "ChessGame{" +
                "teamTurn=" + teamTurn +
                ", board=" + board +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, board);
    }
}

/*
Here is where I am putting my think through logic
Get KING position and all of his possibleMoves.
then
get each of the opposite team's pieceType's possibleMoves
then
if opposite team's piece's possibleMoves is King current Position = check
if each of King's possibleMoves is also in check -> checkmate
 */