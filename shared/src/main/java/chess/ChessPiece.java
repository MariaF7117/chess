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
        Set<ChessMove> moves = new HashSet<>();
        for(int r = myPosition.getRow(); r <= 8; r++) {
            ChessPiece piece = board.getPiece(new ChessPosition(r, myPosition.getColumn()));
            if(piece != null) {
                if(getTeamColor() != this.pieceColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, myPosition.getColumn()), null));
                }break;
            }
            moves.add(new ChessMove(myPosition, new ChessPosition(r, myPosition.getColumn()), null));
        }
        for (int r = myPosition.getRow(); r >= 0; r--) {
            ChessPiece piece = board.getPiece(new ChessPosition(r, myPosition.getColumn()));
            if(piece != null) {
                if(getTeamColor() != this.pieceColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, myPosition.getColumn()), null));
                }break;
            }
            moves.add(new ChessMove(myPosition, new ChessPosition(r, myPosition.getColumn()), null));
        }
        for(int c = myPosition.getColumn(); c <= 8; c++) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow(), c));
            if(piece != null) {
                if(getTeamColor() != this.pieceColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), c), null));
                }break;
            }
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), c), null));
        }
        for (int c = myPosition.getColumn(); c >= 0; c--) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow(),c));
            if(piece != null) {
                if(getTeamColor() != this.pieceColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), c), null));
                }break;
            }
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(),c), null));
        }

       return moves;
    }
    private Set<ChessMove> KingMove(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> moves = new HashSet<>();
        for(int r = myPosition.getRow(); r >= 0; r--) {
            ChessPiece piece = board.getPiece(myPosition);
            if(piece != null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(r, myPosition.getColumn()),null));
            }
            else{
                if(getTeamColor() != this.pieceColor){
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, myPosition.getColumn()),null));
                }
                break;}
        }
        for(int c = myPosition.getColumn(); c <= 0; c--) {
            ChessPiece piece = board.getPiece(myPosition);
            if(piece != null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(c, myPosition.getColumn()),null));
            }
            else{
                if(getTeamColor() != this.pieceColor){
                    moves.add(new ChessMove(myPosition, new ChessPosition(c, myPosition.getColumn()),null));
                }
                break;}
        }
        return moves;
    }
    private Set<ChessMove>  BishopMove(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> moves = new HashSet<>();
        return moves;
    }
    private Set<ChessMove>  KnightMove(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> moves = new HashSet<>();
        return moves;
    }
    private Set<ChessMove>  QueenMove(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> moves = new HashSet<>();
        return moves;
    }
    private Set<ChessMove>  PawnMove(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> moves = new HashSet<>();
        return moves;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
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
