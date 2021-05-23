package Util;

/**
 *Direction control interface
 */
public interface Direction {
    /**
     *Get the return value of the row,
     * @return Return the number of rows
     */
    int getRowChange();

    /**
     *Get the return value of the col,
     * @return Return the number of cols
     */
    int getColChange();

}