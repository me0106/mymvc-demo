package com.mvc.core.converter;

import com.alibaba.fastjson.JSONObject;
import com.mvc.annotation.RequestBody;
import com.mvc.core.utils.ExtendsUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * Created by 我啊 on 2017-11-05 12:56
 */
public class HttpMessageConverter {

    /**
     * @param parameters 方法形参集合
     * @param request    请求体
     * @param response   响应体
     * @return 返回方法形参实例集合
     * @throws ReflectiveOperationException 反射异常
     */
    public static Object[] getParams(Parameter[] parameters, HttpServletRequest request, HttpServletResponse response) throws ReflectiveOperationException, IOException {
        Object[] params = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            if (ExtendsUtils.isAssignFrom(parameters[i], request.getClass())) {
                params[i] = request;
            }
            if (ExtendsUtils.isAssignFrom(parameters[i], response.getClass())) {
                params[i] = response;
            }
            if (ExtendsUtils.isAssignFrom(parameters[i], request.getSession().getClass())) {
                params[i] = request.getSession();
            }
            if (params[i] == null) {
                Annotation annotation = parameters[i].getAnnotation(RequestBody.class);
                if (annotation != null) {
                    params[i] = getParamInstance(parameters[i], request);
                }
            }
        }
        return params;
    }

    /**
     * 将request中的参数自动注入到对应对象的field中
     *
     * @param parameter 标有@RequestBody注解的参数
     * @param request   请求体
     * @return 返回的是已经注入request参数的对象
     * @throws ReflectiveOperationException 反射异常
     */
    private static Object getParamInstance(Parameter parameter, HttpServletRequest request) throws ReflectiveOperationException, IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        StringBuilder JSONString = new StringBuilder();
        String temp;
        while ((temp = bufferedReader.readLine()) != null) {
            JSONString.append(temp);
        }
        return JSONObject.parseObject(JSONString.toString(), parameter.getType());
    }
}
