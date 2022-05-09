package com.metaverse.hillside.core.configurer.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Slf4j
public class ApplicationSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("ApplicationSessionListener,init.");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("ApplicationSessionListener,destroy.");
    }

}
