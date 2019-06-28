package com.km086.admin.wx.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class XMLUtil {

    public static Map<String, String> parseRequestXmlToMap(HttpServletRequest request)
            throws Exception {
        InputStream inputStream = request.getInputStream();
        Map<String, String> resultMap = parseInputStreamToMap(inputStream);
        return resultMap;
    }


    public static Map<String, String> parseInputStreamToMap(InputStream inputStream)
            throws DocumentException, IOException {
        Map<String, String> map = new HashMap();

        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);

        Element root = document.getRootElement();

        List<Element> elementList = root.elements();

        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }

        inputStream.close();
        return map;
    }


    public static Map<String, String> parseXmlStringToMap(String str)
            throws Exception {
        InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        Map<String, String> resultMap = parseInputStreamToMap(inputStream);
        return resultMap;
    }
}
