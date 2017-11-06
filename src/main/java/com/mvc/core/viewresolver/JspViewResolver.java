package com.mvc.core.viewresolver;

import com.mvc.core.context.ApplicationContext;
import com.mvc.core.viewresolver.modelandview.ModelAndView;
import com.mvc.core.viewresolver.modelandview.View;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 我啊 on 2017-11-04 14:07
 */
public class JspViewResolver extends AbstractViewResolver {

    private String prefix;
    private String suffix;

    public View resolve(Object modelAndView, HttpServletRequest request, HttpServletResponse response) {
        View view = super.resolve(modelAndView, request, response);
        view.setViewName(getPrefix() + view.getViewName() + getSuffix());
        return view;
    }


    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
