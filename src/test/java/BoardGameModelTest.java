import Pojo.BoardGameModel;
import Pojo.Position;
import Pojo.Stone;
import Pojo.StoneType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardGameModelTest {
    @Test
    void testSetInitial() {
        BoardGameModel boardGameModel = new BoardGameModel();
        assertEquals(new BoardGameModel(new Stone(StoneType.BLUE, new Position(0, 0)),
                new Stone(StoneType.BLUE, new Position(0, 1)),
                new Stone(StoneType.BLUE, new Position(0, 2)),
                new Stone(StoneType.BLUE, new Position(0, 3)),
                new Stone(StoneType.BLUE, new Position(0, 4)),
                new Stone(StoneType.BLUE, new Position(1, 0)),
                new Stone(StoneType.BLUE, new Position(1, 4)),
                new Stone(StoneType.RED, new Position(5 - 2, 0)),
                new Stone(StoneType.RED, new Position(5 - 2, 4)),
                new Stone(StoneType.RED, new Position(5 - 1, 0)),
                new Stone(StoneType.RED, new Position(5 - 1, 1)),
                new Stone(StoneType.RED, new Position(5 - 1, 2)),
                new Stone(StoneType.RED, new Position(5 - 1, 3)),
                new Stone(StoneType.RED, new Position(5 - 1, 4))
        ),boardGameModel);
    }




    @Test
    void testToString() {
        BoardGameModel boardGameModel = new BoardGameModel();
        assertEquals("[Stone(type=BLUE, position=(0,0))," +
                "Stone(type=BLUE, position=(0,1))," +
                "Stone(type=BLUE, position=(0,2))," +
                "Stone(type=BLUE, position=(0,3))," +
                "Stone(type=BLUE, position=(0,4))," +
                "Stone(type=BLUE, position=(1,0))," +
                "Stone(type=BLUE, position=(1,4))," +
                "Stone(type=RED, position=(3,0))," +
                "Stone(type=RED, position=(3,4))," +
                "Stone(type=RED, position=(4,0))," +
                "Stone(type=RED, position=(4,1))," +
                "Stone(type=RED, position=(4,2))," +
                "Stone(type=RED, position=(4,3))," +
                "Stone(type=RED, position=(4,4))]", boardGameModel.toString());
    }
}
