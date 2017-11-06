package com.mvc.core.viewresolver;

import com.mvc.core.context.ApplicationContext;
import com.mvc.core.viewresolver.modelandview.ModelAndView;
import com.mvc.core.viewresolver.modelandview.View;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 我啊 on 2017-11-06 18:40
 */
public abstract class AbstractViewResolver implements ViewResolver {


    @Override
    public View resolve(Object modelAndView, HttpServletRequest request, HttpServletResponse response) {
        View view = ApplicationContext.getInstance().getBean(View.class);
        if (modelAndView == null) {
            view.setViewName("");
            return view;
        }
        if (modelAndView.getClass().equals(ModelAndView.class)) {
            view.setViewName(((ModelAndView) modelAndView).getViewName());
            ((ModelAndView) modelAndView).getModelMap().forEach(request::setAttribute);
            return view;
        }
        if (modelAndView.getClass().equals(String.class)) {
            view.setViewName(modelAndView.toString());
            return view;
        }
        return view;
    }
}
