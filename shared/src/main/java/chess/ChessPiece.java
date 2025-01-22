package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.pieceColor;
        // throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
        // throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        switch (this.type) {
            case ROOK -> RookMove(board, myPosition);
            case KING -> KingMove(board, myPosition);
            case BISHOP -> BishopMove(board, myPosition);
            case KNIGHT -> KnightMove(board, myPosition);
            case QUEEN -> QueenMove(board, myPosition);
            case PAWN -> PawnMove(board, myPosition);
        }

    }

    public ChessMove RookMove(ChessBoard board, ChessPosition myPosition) {
        /* FOR ROOK
         * for(int i = myPosition.row; i<8; i++
         * for(int i = myPosition.row; i<8; i--
         * for(int i = myPosition.col; i<8; i--
         *   if ChessBoard.getPiece = null;
         *         hashSet(addPosition(row,col+i));
         *   else {
         *       if pieceColor != this.pieceColor
         *           add hashSet(addPsition(row,col+i)
         *       break;
         *         }
         *
         *
         * */
    }
    public ChessMove KingMove(ChessBoard board, ChessPosition position) {

    }
    public ChessMove BishopMove(ChessBoard board, ChessPosition position) {

    }
    public ChessMove KnightMove(ChessBoard board, ChessPosition position) {

    }
    public ChessMove QueenMove(ChessBoard board, ChessPosition position) {

    }
    public ChessMove PawnMove(ChessBoard board, ChessPosition position) {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}
