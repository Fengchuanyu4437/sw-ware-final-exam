package Mapper;

import Pojo.GameResult;
import Util.xmlUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.List;
/**
 *The data layer is used for data acquisition and processing.
 */
public class GameResultMapper {
    /**
     *Write data,
     * @param gameResult  Generated entity class object
     * @throws JsonProcessingException The exception of Jackson data conversion
     * @throws IOException Data exception thrown
     */
    public void InputGameResult(GameResult gameResult) throws JsonProcessingException ,IOException {
        xmlUtil.BeanXml(gameResult);
    }

    /**
     *Get all the game data,
     * @return Return to game data list
     * @throws IOException Data exception thrown
     */
    public List<GameResult> OutputGameResult() throws IOException {
        List<GameResult> g = xmlUtil.GetGameResult();
        return g;
    }
}
