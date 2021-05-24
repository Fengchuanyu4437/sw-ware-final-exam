import Pojo.BoardGameModel;
import Pojo.Position;
import Pojo.Stone;
import Pojo.StoneType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardGameModelTest {


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
