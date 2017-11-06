package com.mvc.core.viewresolver;

import com.mvc.core.viewresolver.modelandview.View;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 我啊 on 2017-11-05 12:43
 */
public interface ViewResolver {


    View resolve(Object modelAndView, HttpServletRequest request, HttpServletResponse response);

}
