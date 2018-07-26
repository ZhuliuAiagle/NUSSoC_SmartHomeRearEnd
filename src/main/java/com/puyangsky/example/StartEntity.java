package com.puyangsky.example;

import com.zijinshen.datadef.DataEngine;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.hibernate.SessionFactory;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

public class StartEntity {
     public static SessionFactory nowFactory;
     public static void main(String[] args) {
         Server server = new Server(8080);
         ServletHolder sh = new ServletHolder(ServletContainer.class);
         sh.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", PackagesResourceConfig.class.getCanonicalName());
         sh.setInitParameter("com.sun.jersey.config.property.packages", "com.puyangsky.example");
         //start server
         Context context = new Context(server, null);
         context.addServlet(sh, "/*");
         try {
                 nowFactory = DataEngine.RunDatabaseService();
                 server.start();
                 server.join();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
 }