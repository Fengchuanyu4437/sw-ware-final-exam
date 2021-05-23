
import Pojo.GameResult;

import Util.xmlUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

@Slf4j
public class xmlutilTest {
    @Test
    public  void BeanXmltest() throws JsonProcessingException {
        GameResult g = new GameResult();
        g.setWiner("qwe");
        g.setCreateTime("2021-05-22T15:37:15.336119200Z");
        g.setSettlement(0);
        g.setPlayTime("00:00:01");
        g.setPlayone("test1");
        g.setPlaytwo("test2");
        xmlUtil.BeanXml(g);

    }


    @Test
    public void GetGameRecordtest() throws IOException {
        List<GameResult> g = xmlUtil.GetGameResult();
        for (int i = 0; i < g.size(); i++) {
            log.info(g.get(i).toString());
        }
    }
}
