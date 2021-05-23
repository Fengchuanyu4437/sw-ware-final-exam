package Util;

import Pojo.GameResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class xmlUtil {
    public static void BeanXml(GameResult gameResult) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);
        //字段为null，自动忽略，不再序列化
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //XML标签名:使用骆驼命名的属性名，
        xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        //设置转换模式
        xmlMapper.enable(MapperFeature.USE_STD_BEAN_NAMING);

        String result = xmlMapper.writeValueAsString(gameResult);
        log.info("Game Data:"+result.toString());
        String filePath = xmlUtil.class.getClassLoader().getResource("xml/GameResult.xml").getFile();
        BufferedWriter bw = null;
        //删除不必要的字符
        try {
            filePath = URLDecoder.decode(filePath,"utf-8");
            log.debug("FilePath:" + filePath);
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath,true)));//每次都往下重叠
            bw.write(result+"?"+"\r\n");
        } catch (IOException e){
            log.error(e.getMessage());
        }finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *
     * @return
     * @throws IOException
     */
    public  static List<GameResult> GetGameResult() throws IOException {
        InputStream is = xmlUtil.class.getClassLoader().getResourceAsStream("xml/GameResult.xml");
        Properties prop = new Properties();
        prop.load(is);
        String xml = prop.toString();
        String regExp = ",";
        if (xml.length()>0) {
            xml = xml.substring(1);
            xml = xml.substring(0,xml.length()-1);
        }
        xml = xml.replaceAll("<\\?xml=version=\"1.0\" encoding=\"UTF-8\" \\?>","");
        xml = xml.replaceAll(regExp,"");

        xml = xml.replaceAll("=",":");
        String[] xmls = xml.split("\\?");
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);
        /* 字段为null，自动忽略，不再序列化 */
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        /* 设置转换模式 */
        xmlMapper.enable(MapperFeature.USE_STD_BEAN_NAMING);
        List<GameResult> g = new ArrayList<>();
        for (int i = 0; i < xmls.length; i++) {
            g.add(xmlMapper.readValue(xmls[i], GameResult.class));
            xmlMapper.readValue(xmls[i],GameResult.class);
        }
        for (int i = 0; i <g.size() ; i++) {
            for (int j = i; j <g.size(); j++) {
                if (g.get(i).getSettlement() > g.get(j).getSettlement()) {
                    GameResult m = g.get(i);
                    g.set(i , g.get(j));
                    g.set(j , m);
                }
            }
        }
//        for (int i = 0; i < g.size(); i++) {
//            System.out.println(g.get(i).toString());
//        }
        List<GameResult> gameResults = new ArrayList<>();
        int count = 0;
        if (g.size() >= 5) {
            count = 5;
        }else {
            count = g.size();
        }
        for (int i = 0; i < count; i++) {
            gameResults.add(g.get(i));
        }



        return gameResults;
    }
}
