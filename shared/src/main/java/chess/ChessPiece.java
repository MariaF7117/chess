package chess;

import java.util.*;

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

        for(int r = myPosition.getRow()+1; r <= 8; r++) {
            ChessPiece piece = board.getPiece(new ChessPosition(r, myPosition.getColumn()));
            if(piece != null) {
                if(piece.getTeamColor() != this.pieceColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, myPosition.getColumn()), null));
                }break;
            }
            moves.add(new ChessMove(myPosition, new ChessPosition(r, myPosition.getColumn()), null));
        }
        for (int r = myPosition.getRow()-1; r > 0; r--) {
            ChessPiece piece = board.getPiece(new ChessPosition(r, myPosition.getColumn()));
            if(piece != null) {
                if(piece.getTeamColor() != this.pieceColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, myPosition.getColumn()), null));
                }break;
            }
            moves.add(new ChessMove(myPosition, new ChessPosition(r, myPosition.getColumn()), null));
        }
        for(int c = myPosition.getColumn()+1; c <= 8; c++) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow(), c));
            if(piece != null) {
                if(piece.getTeamColor() != this.pieceColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), c), null));
                }break;
            }
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), c), null));
        }
        for (int c = myPosition.getColumn()-1; c > 0; c--) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow(),c));
            if(piece != null) {
                if(piece.getTeamColor() != this.pieceColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), c), null));
                }break;
            }
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(),c), null));
        }

       return moves;
    }
    private Set<ChessMove> KingMove(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> moves = new HashSet<>();
        if (myPosition.getRow() < 7) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()));
            if (piece == null || piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
            }
        }
        if (myPosition.getRow() > 1) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()));
            if (piece == null || piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
            }
        }
        if (myPosition.getColumn() < 7) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn() + 1));
            if (piece == null || piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() + 1), null));
            }
        }
        if (myPosition.getColumn() > 1) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn() - 1));
            if (piece == null || piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - 1), null));
            }
        }
        if (myPosition.getRow() < 7 && myPosition.getColumn() < 7) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1));
            if (piece == null || piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), null));
            }
        }
        if (myPosition.getRow() < 7 && myPosition.getColumn() > 1) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1));
            if (piece == null || piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), null));
            }
        }
        if (myPosition.getRow() > 1 && myPosition.getColumn() < 7) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1));
            if (piece == null || piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1), null));
            }
        }
        if (myPosition.getRow() > 1 && myPosition.getColumn() > 1) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1));
            if (piece == null || piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
            }
        }
        return moves;
    }
    private Set<ChessMove>  BishopMove(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> moves = new HashSet<>();
        for (int r = myPosition.getRow() + 1, c = myPosition.getColumn() + 1; r <= 8 && c <= 8; r++, c++){
            ChessPiece piece = board.getPiece(new ChessPosition(r, c));
            if (piece != null) {
                if(piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
            }break;}
            moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
        }
        for (int r = myPosition.getRow()+1, c =myPosition.getColumn()-1; r <= 8 && c > 0; r++, c--){
            ChessPiece piece = board.getPiece(new ChessPosition(r, c));
            if (piece != null){
                if(piece.getTeamColor() != this.pieceColor){
                moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
            }break;}
            moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
        }
        for(int r = myPosition.getRow() - 1, c =myPosition.getColumn()+1; r > 0 && c <= 8; r--, c++){
            ChessPiece piece = board.getPiece(new ChessPosition(r, c));
            if (piece != null) {
                if(piece.getTeamColor() != this.pieceColor){
                moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
            }break;}
            moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
        }
        for(int r = myPosition.getRow()-1, c =myPosition.getColumn()-1; r > 0 && c > 0; r--, c--){
            ChessPiece piece = board.getPiece(new ChessPosition(r, c));
            if (piece != null){
                if(piece.getTeamColor() != this.pieceColor){
                moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
            }break;}
            moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
        }
        return moves;
    }
    private Set<ChessMove> KnightMove(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> moves = new HashSet<>();
        if (myPosition.getRow()+2 <= 8 && myPosition.getColumn() +1 <= 8) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()+ 1));
            if (piece == null || piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()+1), null));
            }
        }
        if (myPosition.getRow()+2 <= 8 && myPosition.getColumn()-1 > 0) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn() - 1));
            if (piece == null || piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn() - 1), null));
            }
        }
        if (myPosition.getRow()-2 > 0 && myPosition.getColumn()+1 <= 8) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()+1));
            if (piece == null || piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()+1), null));
            }
        }
        if (myPosition.getRow()-2 > 0 && myPosition.getColumn()-1 > 0) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()-1));
            if (piece == null || piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()-1), null));
            }
        }
        if (myPosition.getRow()+1 <= 8 && myPosition.getColumn()+2 <= 8) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+2));
            if (piece == null || piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+2), null));
            }
        }
        if (myPosition.getRow()+1 <= 8 && myPosition.getColumn()-2 > 0) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-2));
            if (piece == null || piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-2), null));
            }
        }
        if (myPosition.getRow()-1 > 0 && myPosition.getColumn()+2 <= 8) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+2));
            if (piece == null || piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+2), null));
            }
        }
        if (myPosition.getRow()-1 > 0 && myPosition.getColumn()-2 > 0) {
            ChessPiece piece = board.getPiece(new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-2));
            if (piece == null || piece.getTeamColor() != this.pieceColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-2), null));
            }
        }
        return moves;
    }
    private Set<ChessMove>  QueenMove(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> moves = new HashSet<>();
        moves.addAll(RookMove(board, myPosition));
        moves.addAll(BishopMove(board, myPosition));
        BishopMove(board, myPosition);
        return moves;
    }
    private Set<ChessMove>  PawnMove(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> moves = new HashSet<>();
        int direction = (this.pieceColor == ChessGame.TeamColor.WHITE) ? 1 : -1;

        boolean isFirstMove = (this.pieceColor == ChessGame.TeamColor.WHITE && myPosition.getRow() == 2) ||
                (this.pieceColor == ChessGame.TeamColor.BLACK && myPosition.getRow() == 7);

        int r1 = myPosition.getRow() + direction;
        int c1 = myPosition.getColumn();
        if (r1 > 0 && r1 <= 8) {
            ChessPiece piece = board.getPiece(new ChessPosition(r1, c1));
            if (piece == null) {
                if ((this.pieceColor == ChessGame.TeamColor.WHITE && r1 == 8) ||
                        (this.pieceColor == ChessGame.TeamColor.BLACK && r1 == 1)) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r1, c1), PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, new ChessPosition(r1, c1), PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, new ChessPosition(r1, c1), PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, new ChessPosition(r1, c1), PieceType.BISHOP));
                } else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r1, c1), null));
                }

                if (isFirstMove) {
                    int r2 = myPosition.getRow() + 2 * direction;
                    ChessPiece pieceTwoSteps = board.getPiece(new ChessPosition(r2, c1));
                    if (pieceTwoSteps == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(r2, c1), null));
                    }
                }
            }
        }

        int r3 = myPosition.getRow() + direction;
        int c3 = myPosition.getColumn() + 1;
        if (r3 > 0 && r3 <= 8 && c3 > 0 && c3 <= 8) {
            ChessPiece piece = board.getPiece(new ChessPosition(r3, c3));
            if (piece != null && piece.getTeamColor() != this.pieceColor) {
                if ((this.pieceColor == ChessGame.TeamColor.WHITE && r3 == 7) ||
                        (this.pieceColor == ChessGame.TeamColor.BLACK && r3 == 0)) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r3, c3), type.QUEEN));
                    moves.add(new ChessMove(myPosition, new ChessPosition(r3, c3), type.ROOK));
                    moves.add(new ChessMove(myPosition, new ChessPosition(r3, c3), type.BISHOP));
                    moves.add(new ChessMove(myPosition, new ChessPosition(r3, c3), type.KNIGHT));
                } else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r3, c3), null));
                }
            }
        }

        int r4 = myPosition.getRow() + direction;
        int c4 = myPosition.getColumn() - 1;
        if (r4 > 0 && r4 <= 8 && c4 > 0 && c4 <= 8) {
            ChessPiece piece = board.getPiece(new ChessPosition(r4, c4));
            if (piece != null && piece.getTeamColor() != this.pieceColor) {
                if ((this.pieceColor == ChessGame.TeamColor.WHITE && r4 == 8) ||
                        (this.pieceColor == ChessGame.TeamColor.BLACK && r4 == 1)) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r4, c4), type.QUEEN));
                    moves.add(new ChessMove(myPosition, new ChessPosition(r4, c4), type.ROOK));
                    moves.add(new ChessMove(myPosition, new ChessPosition(r4, c4), type.BISHOP));
                    moves.add(new ChessMove(myPosition, new ChessPosition(r4, c4), type.KNIGHT));
                } else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r4, c4), null));
                }
            }
        }
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
