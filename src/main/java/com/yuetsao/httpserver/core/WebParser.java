package com.yuetsao.httpserver.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 解析web.xml配置文件
 */
public class WebParser {
    public static Map<String, Map<String, String>>  servletMaps = new HashMap<String, Map<String, String>>();
    /**
     * 解析服务器中所有应用的web.xml
     * @param webAppNames 服务器中所有应用的名称
     */
    public static void parser(String [] webAppNames) throws DocumentException,MalformedURLException{
        for(String webAppName:webAppNames) {
            Map<String,String> servletMap = parser(webAppName);
            servletMaps.put(webAppName,servletMap);
        }
    }

    /**
     * 解析单个应用的web.xml配置文件
     * webappname应用名称
     * servletMap<String,String></String,String>
     * @param webAppName
     * @return
     */
    public static Map<String,String> parser(String webAppName) throws DocumentException, MalformedURLException {
        //获取web.xml的路径
        String webPath = webAppName + "/WEB-INF/web.xml";
        //创建解析器
        SAXReader saxReader = new SAXReader();
        //通过解析器的read方法将配置文件读取到内存中，生成一个Document【org.dom4j】对象树
        Document document = saxReader.read(new File(webPath));
        //获取servlet节点元素：web-app -》 servlet
        List<Element> servletNodes = document.selectNodes("/web-app/servlet");
        //创建一个servletInfoMap集合，将servlet-name和servlet-class 的值分别当作key 和 value 存放到该集合中
        Map<String, String> servletInfoMap = new HashMap<String, String>();
        //开始遍历servletNodes
        for(Element servletNode : servletNodes) {
            //获取servlet-name节点元素对象
            Element servletNameElt = (Element) servletNode.selectSingleNode("servlet-name");
            //获取servletNameElt节点元素对象的值
            String servletName = servletNameElt.getStringValue();

            Element servletClassElt = (Element) servletNode.selectSingleNode("servlet-class");
            //获取servlet-class节点元素对象
            String servletCalssElt =  servletClassElt.getStringValue();
            //将servletName和ServletClass分别当作key和value 放到map中
            servletInfoMap.put(servletName, servletCalssElt);
        }

        //获取servlet-mapping节点中的对象：web-app -》 servlet-mapping
        List<Element> servletMappingNodes = document.selectNodes("/web-app/servlet-mapping");
        //创建map
        Map<String, String>  servletMappingInfoMap = new HashMap<String, String>();
        //遍历
        for (Element servletMappingNode:  servletMappingNodes)  {
            //获取节点对象
            Element servletNameElt = (Element)servletMappingNode.selectSingleNode("servlet-name");
            //获取String值
            String servletName = servletNameElt.getStringValue();
            //获取url-pattern节点元素对象
            Element urlPatternElt = (Element)servletMappingNode.selectSingleNode("url-pattern");
            //获得String值
            String  urlPattern = urlPatternElt.getStringValue();
            //放入map
            servletMappingInfoMap.put(servletName, urlPattern);

        }
        //获取keyset
        Set<String> servletNames = servletInfoMap.keySet();
        //创建一个servletMap集合，将servletMappingInfoMap中的value和servletInfoMap中的value分别当作key和value存放到map中
        Map<String, String>  servletMap = new HashMap<String, String>();
        //遍历
        for (String servletName:  servletNames) {
            String urlPttern = servletMappingInfoMap.get(servletName);
            String servletClassName = servletInfoMap.get(servletName);
            servletMap.put(urlPttern,servletClassName);
        }

        return servletMap;
    }

}
