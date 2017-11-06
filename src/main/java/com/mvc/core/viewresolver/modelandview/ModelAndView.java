package com.mvc.core.viewresolver.modelandview;

import com.mvc.core.utils.StringUtils;
import com.mvc.enums.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 我啊 on 2017-11-05 11:32
 */
public class ModelAndView {

    private String viewName;

    private Map<String, Object> modelMap = new HashMap<>();

    private HttpStatus httpStatus = HttpStatus.OK;

    public ModelAndView() {
    }

    public ModelAndView setObject(String key, Object value) {
        modelMap.put(key, value);
        return this;
    }

    public Object getObject(String key) {
        return modelMap.get(key);
    }

    public boolean hasView() {
        return StringUtils.isNotEmpty(viewName);
    }


    public ModelAndView(String viewName, HttpStatus httpStatus) {
        this.viewName = viewName;
        this.httpStatus = httpStatus;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String,Object> getModelMap() {
        return modelMap;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
