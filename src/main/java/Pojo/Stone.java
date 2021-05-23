package Pojo;


import Util.Direction;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Data;
/**
 class of piece.
 */
@Data
public class Stone {
    private final StoneType type;
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();
    /**
     *
     * @param type
     * @param position
     */
    public Stone(StoneType type, Position position) {
        this.type = type;
        this.position.set(position);
    }

    public void moveTo(Direction direction) {
        Position newPosition = position.get().moveTo(direction);
        position.set(newPosition);
    }
    /**
     *
     * @return
     */
    public Position getPosition() {
        return position.get();
    }
    /**
     *
     * @return
     */
    public ObjectProperty<Position> positionProperty() {
        return position;
    }
}
