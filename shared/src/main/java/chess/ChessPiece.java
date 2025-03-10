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
        Set<ChessMove> possibleMoves = new HashSet<>();
        switch (this.type) {
            case ROOK -> possibleMoves.addAll(rookMove(board, myPosition));
            case KING -> possibleMoves.addAll(kingMove(board, myPosition));
            case BISHOP -> possibleMoves.addAll(bishopMove(board, myPosition));
            case KNIGHT -> possibleMoves.addAll(knightMove(board, myPosition));
            case QUEEN -> possibleMoves.addAll(queenMove(board, myPosition));
            case PAWN -> possibleMoves.addAll(pawnMove(board, myPosition));
        }
        return possibleMoves;
    }

    private void addMovesForDirection(Set<ChessMove> moves, int row, int col, ChessPosition myPosition, ChessBoard board){
        int r = myPosition.getRow() + row;
        int c = myPosition.getColumn() + col;

        while (r > 0 && r <= 8 && c >0 && c <= 8){
            ChessPiece piece = board.getPiece(new ChessPosition(r, c));
            if(piece != null) {
                if(piece.getTeamColor() != this.pieceColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
                }
                break;
            }
            moves.add(new ChessMove(myPosition, new ChessPosition(r, c), null));
            r += row;
            c += col;
        }
    }

    private Set<ChessMove> rookMove(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> moves = new HashSet<>();

        addMovesForDirection(moves,1,0, myPosition,board);
        addMovesForDirection(moves,0,1,myPosition,board);
        addMovesForDirection(moves,-1,0,myPosition,board);
        addMovesForDirection(moves,0,-1,myPosition,board);

       return moves;
    }
    private Set<ChessMove> kingMove(ChessBoard board, ChessPosition myPosition) {
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
    private Set<ChessMove> bishopMove(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> moves = new HashSet<>();
        addMovesForDirection(moves,1,1,myPosition,board);
        addMovesForDirection(moves,1,-1,myPosition,board);
        addMovesForDirection(moves,-1,1,myPosition,board);
        addMovesForDirection(moves,-1,-1,myPosition,board);
        return moves;
    }
    private Set<ChessMove> knightMove(ChessBoard board, ChessPosition myPosition) {
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
    private Set<ChessMove> queenMove(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> moves = new HashSet<>();
        moves.addAll(rookMove(board, myPosition));
        moves.addAll(bishopMove(board, myPosition));
        return moves;
    }
    private Set<ChessMove> pawnMove(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMove> moves = new HashSet<>();
        int direction = (this.pieceColor == ChessGame.TeamColor.WHITE) ? 1 : -1;

        boolean isFirstMove = (this.pieceColor == ChessGame.TeamColor.WHITE && myPosition.getRow() == 2) ||
                (this.pieceColor == ChessGame.TeamColor.BLACK && myPosition.getRow() == 7);

        int move1Row = myPosition.getRow() + direction;
        int move1Col= myPosition.getColumn();
        if (move1Row > 0 && move1Row <= 8) {
            ChessPiece piece = board.getPiece(new ChessPosition(move1Row, move1Col));
            if (piece == null) {
                if ((this.pieceColor == ChessGame.TeamColor.WHITE && move1Row == 8) ||
                        (this.pieceColor == ChessGame.TeamColor.BLACK && move1Row == 1)) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(move1Row, move1Col), PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, new ChessPosition(move1Row, move1Col), PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, new ChessPosition(move1Row, move1Col), PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, new ChessPosition(move1Row, move1Col), PieceType.BISHOP));
                } else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(move1Row, move1Col), null));
                }

                if (isFirstMove) {
                    int move2Row = myPosition.getRow() + 2 * direction;
                    ChessPiece pieceTwoSteps = board.getPiece(new ChessPosition(move2Row, move1Col));
                    if (pieceTwoSteps == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(move2Row, move1Col), null));
                    }
                }
            }
        }

        int move3Row = myPosition.getRow() + direction;
        int move3Col = myPosition.getColumn() + 1;
        if (move3Row > 0 && move3Row <= 8 && move3Col > 0 && move3Col <= 8) {
            ChessPiece piece = board.getPiece(new ChessPosition(move3Row, move3Col));
            if (piece != null && piece.getTeamColor() != this.pieceColor) {
                if ((this.pieceColor == ChessGame.TeamColor.WHITE && move3Row == 7) ||
                        (this.pieceColor == ChessGame.TeamColor.BLACK && move3Row == 1)) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(move3Row, move3Col), type.QUEEN));
                    moves.add(new ChessMove(myPosition, new ChessPosition(move3Row, move3Col), type.ROOK));
                    moves.add(new ChessMove(myPosition, new ChessPosition(move3Row, move3Col), type.BISHOP));
                    moves.add(new ChessMove(myPosition, new ChessPosition(move3Row, move3Col), type.KNIGHT));
                } else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(move3Row, move3Col), null));
                }
            }
        }

        int move4Row = myPosition.getRow() + direction;
        int move4Col = myPosition.getColumn() - 1;
        if (move4Row > 0 && move4Row <= 8 && move4Col > 0 && move4Col <= 8) {
            ChessPiece piece = board.getPiece(new ChessPosition(move4Row, move4Col));
            if (piece != null && piece.getTeamColor() != this.pieceColor) {
                if ((this.pieceColor == ChessGame.TeamColor.WHITE && move4Row == 8) ||
                        (this.pieceColor == ChessGame.TeamColor.BLACK && move4Row == 1)) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(move4Row, move4Col), type.QUEEN));
                    moves.add(new ChessMove(myPosition, new ChessPosition(move4Row, move4Col), type.ROOK));
                    moves.add(new ChessMove(myPosition, new ChessPosition(move4Row, move4Col), type.BISHOP));
                    moves.add(new ChessMove(myPosition, new ChessPosition(move4Row, move4Col), type.KNIGHT));
                } else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(move4Row, move4Col), null));
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
