package com.appspot.data_base_1298.backend;

import com.googlecode.objectify.ObjectifyService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by rk521 on 1/05/16.
 */
public class ListenerBackend implements ServletContextListener {
    /** Se invoca cuando el servidor se levanta. */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    // Registra las entity en objectify.
        ObjectifyService.register(Conocido.class);
    }
    /** Se invoca cuando el servidor se dá de baja. */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
