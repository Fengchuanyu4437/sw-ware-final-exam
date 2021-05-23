package Pojo;

import Util.PawnDirection;
import javafx.beans.property.ObjectProperty;

import java.util.*;
/**
 *Chessboard generation class。
 */

public class BoardGameModel {

    public static int BOARD_SIZE = 5;  //棋盘大小

    private final Stone[] stones; //棋子数组

    /**
     *Chessboard initialization.
     */
    public BoardGameModel() {
        this(new Stone(StoneType.BLUE, new Position(0, 0)),
                new Stone(StoneType.BLUE, new Position(0, 1)),
                new Stone(StoneType.BLUE, new Position(0, 2)),
                new Stone(StoneType.BLUE, new Position(0, 3)),
                new Stone(StoneType.BLUE, new Position(0, 4)),
                new Stone(StoneType.BLUE, new Position(1, 0)),
                new Stone(StoneType.BLUE, new Position(1, 4)),
                new Stone(StoneType.RED, new Position(BOARD_SIZE - 2, 0)),
                new Stone(StoneType.RED, new Position(BOARD_SIZE - 2, 4)),
                new Stone(StoneType.RED, new Position(BOARD_SIZE - 1, 0)),
                new Stone(StoneType.RED, new Position(BOARD_SIZE - 1, 1)),
                new Stone(StoneType.RED, new Position(BOARD_SIZE - 1, 2)),
                new Stone(StoneType.RED, new Position(BOARD_SIZE - 1, 3)),
                new Stone(StoneType.RED, new Position(BOARD_SIZE - 1, 4))
                );
        //棋子初始化
    }
    /**
     *Chessboard initialization,
     * @param stone
     */
    public BoardGameModel(Stone... stone) {
        checkStones(stone);
        this.stones = stone.clone();
    }
    //？
    private void checkStones(Stone[] stones) {
        var seen = new HashSet<Position>();
        for (var stone : stones) {
            if (! isOnBoard(stone.getPosition()) || seen.contains(stone.getPosition())) {
                throw new IllegalArgumentException();
            }
            seen.add(stone.getPosition());
        }
    }
    /**
     *Get the number of pieces,
     * @return number of pieces
     */
    public int getStoneCount() {
        return stones.length;
    }
    /**
     *number of PieceType,
     * @param stoneNumber number of pieceNumber
     * @return number of pieceNumber
     */
    public StoneType getStoneType(int stoneNumber) {
        return stones[stoneNumber].getType();
    }
    /**
     *number of Position,
     * @param stoneNumber number of pieceNumber
     * @return number of pieceNumber
     */
    public Position getStonePosition(int stoneNumber) {
        return stones[stoneNumber].getPosition();
    }
    /**
     *Get operation object,
     * @param stoneNumber
     * @return Operation object
     */
    public ObjectProperty<Position> positionProperty(int stoneNumber) {
        return stones[stoneNumber].positionProperty();
    }
    /**
     *Chess pieces and direction control,
     * @param stoneNumber piece Number
     * @param direction Direction of movement
     * @return Can it move
     */
    //检查移动是否可以移动
    public boolean isValidMove(int stoneNumber, PawnDirection direction) {
        if (stoneNumber < 0 || stoneNumber >= stones.length) {
            throw new IllegalArgumentException();
        }
        Position newPosition = stones[stoneNumber].getPosition().moveTo(direction);
        if (! isOnBoard(newPosition)) {
            return false;
        }
        for (var stone : stones) {
            if (stone.getPosition().equals(newPosition)) {
                return false;
            }
        }
        return true;
    }
    /**
     *Get mobile data,
     * @param stoneNumber piece Number
     * @return Can it move piece
     */
    public Set<PawnDirection> getValidMoves(int stoneNumber) {
        EnumSet<PawnDirection> validMoves = EnumSet.noneOf(PawnDirection.class);
        for (var direction : PawnDirection.values()) {
            if (isValidMove(stoneNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }
    /**
     *How to move,
     * @param stoneNumber piece Number
     * @param direction direction
     */
    public void move(int stoneNumber, PawnDirection direction) {
        stones[stoneNumber].moveTo(direction);
    }
    /**
     *Direction movement judgment,
     * @param position
     * @return result
     */
    //是否移动出了棋盘外
    public static boolean isOnBoard(Position position) {
        return 0 <= position.getRow() && position.getRow() < BOARD_SIZE
                && 0 <= position.getCol() && position.getCol() < BOARD_SIZE;
    }
    /**
     *Get mobile collection
     * @return Get mobile collection return
     */
    public List<Position> getStonePositions() {
        List<Position> positions = new ArrayList<>(stones.length);
        for (var stone : stones) {
            positions.add(stone.getPosition());
        }
        return positions;
    }
    /**
     *Get all the directions you can move
     * @param position Direction object
     * @return data
     */
    public OptionalInt getStoneNumber(Position position) {
        for (int i = 0; i < stones.length; i++) {
            if (stones[i].getPosition().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }
    /**
     *All data are printed out
     * @return character string
     */
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (var stone : stones) {
            joiner.add(stone.toString());
        }
        return joiner.toString();
    }

    public static void main(String[] args) {
        BoardGameModel model = new BoardGameModel();
        System.out.println(model);
    }

}
