package Pojo;

import Util.Direction;
import lombok.Data;
/**
 * class of position.
 */
@Data
public class Position {
    int row;
    int col;
    /**
     *Class initialization load,
     * @param row
     * @param col
     */
    public Position(int row, int col){
        this.col = col;
        this.row = row;
    }
    /**
     *Mobile method,
     * @param direction
     * @return
     */
    public Position moveTo(Direction direction) {
        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }
    /**
     *Output printing,
     * @return
     */
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

}