package com.yuetsao.httpserver.core;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 解析Server.xml 配置文件
 */
public class ServerParser {

    public static int getPort() {
        int port = 8081;
        //创建解析器
        SAXReader saxReader = new SAXReader();
        //通过解析器的read方法将配置文件读取到内存只能够，生成一个Document【org.dom4j】对象树
        try {
            Document document = saxReader.read("conf/server.xml");
            //获取connector节点元素的路径 server - service - connector
            Element connectorElt = (Element)document.selectSingleNode("//connector");
            //获取port属性
            port = Integer.parseInt(connectorElt.attributeValue("port"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return port;
    }

}
