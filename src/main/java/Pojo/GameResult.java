package Pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
/**
 record the data by game.
 */
@Data
@JacksonXmlRootElement(localName = "Result") //转换
public class GameResult  {
    /**
     * create time.
     */
    public String CreateTime;//创建时间
    /**
     * Player one.
     */
    public String Playone;//玩家一
    /**
     * Player two.
     */
    public String Playtwo;//玩家二
    /**
     * the time of gameplay.
     */
    public String PlayTime;//游戏时间
    /**
     * player of win.
     */
    public String Winer;//胜者
    /**
     * step.
     */
    public int Settlement;//回合

}
