package Util;


import Pojo.BoardGameModel;
import Pojo.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class Victory {
    int[][] chead = new int[5][5];//定义二维数组
    
    List<Position> positions = new  ArrayList<Position>(); //定义数组
    public boolean victory(BoardGameModel boardGameModel, String color){
        for (int i = 0; i < chead.length; i++) {
            for (int j = 0; j < chead[0].length; j++) {
                chead[i][j] = 0;
            }
        }
        
        for (int i = 0; i < boardGameModel.getStoneCount(); i++) {
            String colornow = String.valueOf(boardGameModel.getStoneType(i));
            if (color.equals(colornow)) {
                Position position = boardGameModel.getStonePosition(i);
                positions.add(position);
                chead[position.getRow()][position.getCol()] = 1;
            }
        }
            boolean result = win(color);

        return result;
    }
    //胜利的条件
    public boolean win(String color){
        log.info("this color:" + color);
        if (color.equals("RED")) {
            if (chead[0][0]==1&&chead[0][1]==1&&chead[0][2]==1&&chead[0][3]==1&&chead[0][4]==1&&chead[1][0]==1&&chead[1][4]==1) {
                return true;
            }
        }else {
            if (chead[4][0]==1&&chead[4][1]==1&&chead[4][2]==1&&chead[4][3]==1&&chead[4][4]==1&&chead[3][0]==1&&chead[3][4]==1) {
                return true;
            }

        }
        return  false;
    }


}
