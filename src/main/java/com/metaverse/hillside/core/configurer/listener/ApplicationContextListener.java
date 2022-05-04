package com.metaverse.hillside.core.configurer.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("ApplicationContextListener,init.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("ApplicationContextListener,destroy.");
    }

}
