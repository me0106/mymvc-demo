package com.mvc.core;

import com.alibaba.fastjson.JSON;
import com.mvc.annotation.RequestMapping;
import com.mvc.annotation.ResponseBody;
import com.mvc.core.context.ApplicationContext;
import com.mvc.core.exception.ViewNotFoundException;
import com.mvc.core.handlermapping.HandlerMapping;
import com.mvc.core.utils.StringUtils;
import com.mvc.core.viewresolver.AbstractViewResolver;
import com.mvc.core.viewresolver.ViewResolver;
import com.mvc.core.viewresolver.modelandview.View;
import com.mvc.enums.HttpMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.ResourceBundle;

import static com.mvc.core.converter.HttpMessageConverter.getParams;

/**
 * Created by 我啊 on 2017-11-03 14:47
 */
@SuppressWarnings("unchecked")
public class DispatcherServlet extends HttpServlet {


    private ApplicationContext context = ApplicationContext.getInstance();


    private ViewResolver viewResolver = context.getViewResolver();

    private static ResourceBundle lStrings = ResourceBundle.getBundle("javax.servlet.http.LocalStrings");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Method method = HandlerMapping.getInstance().getPathMethod(request.getRequestURI());
        if (method == null) {
            response.sendError(404, request.getRequestURI());
            return;
        }
        HttpMethod[] methods = method.getAnnotation(RequestMapping.class).method();
        if (methods.length != 0) {
            if ((!Arrays.toString(methods).contains(request.getMethod())) && request.getMethod().equals("GET")) {
                String msg = lStrings.getString("http.method_get_not_supported");
                response.sendError(405, msg);
                return;
            }
        }
        Parameter[] parameters = method.getParameters();
        Object[] params = getParams(parameters, request, response);
        Object o = method.invoke(context.getBean(method.getDeclaringClass()), params);
        if (o==null){
            return;
        }
        if (method.isAnnotationPresent(ResponseBody.class)) {
            response.getWriter().print(JSON.toJSONString(o));
            return;
        }
        View view = viewResolver.resolve(o, request, response);
        if (StringUtils.isEmpty(view.getViewName())){
            return;
        }
        request.getRequestDispatcher(view.getViewName()).forward(request, response);
    }
}
