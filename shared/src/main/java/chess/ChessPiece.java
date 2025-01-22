package chess;

import java.util.Collection;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

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
        Set<ChessMove> PossibleMoves = new HashSet<>();
        switch (this.type) {
            case ROOK -> PossibleMoves.addAll(RookMove(board, myPosition));
            case KING -> PossibleMoves.addAll(KingMove(board, myPosition));
            case BISHOP -> PossibleMoves.addAll(BishopMove(board, myPosition));
            case KNIGHT -> PossibleMoves.addAll(KnightMove(board, myPosition));
            case QUEEN -> PossibleMoves.addAll(QueenMove(board, myPosition));
            case PAWN -> PossibleMoves.addAll(PawnMove(board, myPosition));
        }
        return PossibleMoves;
    }

    private Set<ChessMove> RookMove(ChessBoard board, ChessPosition myPosition) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if(ChessBoard.getPiece() = null){
                    board[x][y];
                }
                else {
                    if (pieceColor != this.pieceColor)
                        board[x][y];
                    break;
                }
            }
        }
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
    private Set<ChessMove> KingMove(ChessBoard board, ChessPosition position) {

    }
    private Set<ChessMove>  BishopMove(ChessBoard board, ChessPosition position) {

    }
    private Set<ChessMove>  KnightMove(ChessBoard board, ChessPosition position) {

    }
    private Set<ChessMove>  QueenMove(ChessBoard board, ChessPosition position) {

    }
    private Set<ChessMove>  PawnMove(ChessBoard board, ChessPosition position) {

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
