package com.epam.project.web.listener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ContextListener implements ServletContextListener {

    private static final Logger log = Logger.getLogger(ContextListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        log("Servlet context destruction starts");

        log("Servlet context destruction finished");
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        log("Servlet context initialization starts");

        ServletContext servletContext = event.getServletContext();
        initLog4J(servletContext);
        initCommandContainer();
        initI18N(servletContext);

        log("Servlet context initialization finished");
    }

    /**
     * Initializes i18n subsystem.
     */
    private void initI18N(ServletContext servletContext) {
        log.debug("I18N subsystem initialization started");

        String localesValue = servletContext.getInitParameter("locales");
        if (localesValue == null || localesValue.isEmpty()) {
            log.warn("'locales' init parameter is empty, the default encoding will be used");
        } else {
            List<String> locales = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(localesValue);
            while (st.hasMoreTokens()) {
                String localeName = st.nextToken();
                locales.add(localeName);
            }

            log.debug("Application attribute set: locales --> " + locales);
            servletContext.setAttribute("locales", locales);
        }

        log.debug("I18N subsystem initialization finished");
    }

    /**
     * Initializes log4j framework.
     *
     * @param servletContext
     */
    private void initLog4J(ServletContext servletContext) {
        log("Log4J initialization started");
        try {
            PropertyConfigurator.configure(servletContext.getRealPath(
                    "WEB-INF/log4j.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        log("Log4J initialization finished");
    }


    private void initCommandContainer() {
        log.debug("Command container initialization started");

        try {
            Class.forName("com.epam.project.web.command.CommandContainer");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        log.debug("Command container initialization finished");
    }

    private void log(String msg) {
        System.out.println("[ContextListener] " + msg);
    }

}