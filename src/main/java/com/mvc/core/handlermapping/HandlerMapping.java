package com.mvc.core.handlermapping;

import com.mvc.annotation.RequestMapping;
import com.mvc.core.utils.PkgScanner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by 我啊 on 2017-11-03 14:49
 */
public class HandlerMapping {


    private static HandlerMapping handlerMapping;

    private Map<String, Method> mapping = new HashMap<>();

    /**
     * 初始化所有的handlerMapping 使方法和路径产生映射
     */
    private void init(String basePkg) {
        List<Class> list = PkgScanner.getClassPath(basePkg, RequestMapping.class);
        for (Class clazz : list) {
            RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            String pathClass = requestMapping.value().replace("/", "");
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    String pathMethod = method.getDeclaredAnnotation(RequestMapping.class).value().replace("/", "");
                    mapping.put(pathClass + "/" + pathMethod, method);
                }
            }
        }

    }

    public static void newInstance(String basePkg) {
        handlerMapping = new HandlerMapping();
        handlerMapping.init(basePkg);
    }

    public static HandlerMapping getInstance() {
        return handlerMapping;
    }

    private HandlerMapping() {
    }

    public Method getPathMethod(String url) {
        return mapping.get(url);
    }

    private static class HandlerMethod {

        private String handlerPath;

        private Method method;

        private List<String> params;


        public HandlerMethod(String handlerPath, Method method) {
            this.handlerPath = handlerPath;
            this.method = method;

        }


        private void initPathParam() {
            String[] path = handlerPath.split("/");
            for (String s : path) {
                if (Pattern.matches("^\\{\\S+}$", s)) {
                    params = (params == null) ? new ArrayList<>() : params;
                }
            }

        }
    }

}
