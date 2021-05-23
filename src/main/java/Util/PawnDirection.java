package Util;

/**
 * pawn direction.
 */
public enum PawnDirection implements Direction {
    /**
     * up left.
     */
    UP_LEFT(-1, -1),
    /**
     * up.
     */
    UP(-1, 0),
    /**
     * up right.
     */
    UP_RIGHT(-1, 1),
    /**
     * right.
     */
    RIGHT(0, 1),
    /**
     * down right.
     */
    DOWN_RIGHT(1, 1),
    /**
     * down.
     */
    DOWN(1, 0),
    /**
     * down left.
     */
    DOWN_LEFT(1, -1),
    /**
     * left.
     */
    LEFT(0, -1);

    private final int rowChange;
    private final int colChange;

    PawnDirection(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     *
     * Get the return value of the row,
     * @return Return the number of rows
     */
    public int getRowChange() {
        return rowChange;
    }

    /**
     *
     * Get the return value of the col,
     *@return Return the number of cols
     */
    public int getColChange() {
        return colChange;
    }

    /**
     *Direction control
     * @param rowChange
     * @param colChange
     * @return Return row and column data
     */
    public static PawnDirection of(int rowChange, int colChange) {
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }


}