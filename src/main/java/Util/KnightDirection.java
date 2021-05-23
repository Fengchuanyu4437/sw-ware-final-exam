package Util;

/**
 * knight direction.
 */
public enum KnightDirection implements Direction {
    /**
     * left up.
     */
    LEFT_UP(-1, -2),

    /**
     * up left.
     */
    UP_LEFT(-2, -1),
    /**
     *  up right.
     */
    UP_RIGHT(-2, 1),
    /**
     * right up.
     */
    RIGHT_UP(-1, 2),
    /**
     * right down.
     */
    RIGHT_DOWN(1, 2),
    /**
     * down right.
     */
    DOWN_RIGHT(2, 1),
    /**
     * down left.
     */
    DOWN_LEFT(2, -1),
    /**
     * left down.
     */
    LEFT_DOWN(1, -2);

    private final int rowChange;
    private final int colChange;

    KnightDirection(int rowChange, int colChange) {
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
    public static KnightDirection of(int rowChange, int colChange) {
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }

}
