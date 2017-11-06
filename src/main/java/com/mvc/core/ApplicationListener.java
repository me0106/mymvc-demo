package com.mvc.core;

import com.mvc.annotation.Controller;
import com.mvc.core.context.ApplicationContext;
import com.mvc.core.handlermapping.HandlerMapping;
import com.mvc.core.utils.PkgScanner;
import com.mvc.core.utils.ApplicationConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;

/**
 * Created by 我啊 on 2017-11-04 13:51
 */

public class ApplicationListener implements ServletContextListener {
    private static String DEFAULT_CONFIG_LOCATION = "application.xml";

    private static String CONFIG_LOCATION = DEFAULT_CONFIG_LOCATION;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String configLocation = servletContextEvent.getServletContext().getInitParameter("configLocation");
        CONFIG_LOCATION = configLocation == null ? CONFIG_LOCATION : configLocation;
        ApplicationConfig.initialize(CONFIG_LOCATION);
        String basePkg = ApplicationConfig.getBasePackage();
        ApplicationContext.newInstance(PkgScanner.getClassPath(basePkg, Controller.class));
        HandlerMapping.newInstance(basePkg);
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
