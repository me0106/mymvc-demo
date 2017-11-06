package com.mvc.core.exception;

/**
 * Created by 我啊 on 2017-11-05 14:28
 */
public class ViewNotFoundException extends Exception {
    public ViewNotFoundException() {
    }

    public ViewNotFoundException(String message) {
        super(message);
    }
}
