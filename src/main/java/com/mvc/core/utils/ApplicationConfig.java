package com.mvc.core.utils;

import com.mvc.core.ApplicationListener;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.mvc.core.converter.TypeConverter;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 我啊 on 2017-11-05 13:03
 */
public class ApplicationConfig {

    private static Element element;

    private static List<Object> xmlObject=new ArrayList<>();

    public static void initialize(String configLocation) {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(ApplicationListener.class.getClassLoader().getResourceAsStream(configLocation));
            element = document.getRootElement();
            parseConfigXML(element.elements("bean"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取扫描基包
     * @return 包名
     */
    public static String getBasePackage() {
        Element element = ApplicationConfig.element.element("component-scan");
        return element.attributeValue("base-package");
    }

    /**
     * 获取xml解析的对象
     * @return list集合
     */
    public static List<Object> getXMLObjectList() {
            return xmlObject;
    }

    /**
     * 解析xml为对象
     *
     * @param nodes 根子节点列表
     * @return xml对象列表
     * @throws ReflectiveOperationException 异常
     */
    private static void parseConfigXML(List<Element> nodes) throws ReflectiveOperationException {
        for (Element element : nodes) {
            Class clazz = Class.forName(element.attributeValue("class"));
            Object o = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            List<Element> elements = element.elements();
            for (Field field : fields) {
                field.setAccessible(true);
                for (Element element1 : elements) {
                    String value = element1.attributeValue("name");
                    if (value != null && value.equals(field.getName())) {
                        field.set(o, TypeConverter.converter(element1.attributeValue("value"), field.getType().getTypeName()));
                        break;
                    }
                }
            }
            xmlObject.add(o);
        }
    }


}
