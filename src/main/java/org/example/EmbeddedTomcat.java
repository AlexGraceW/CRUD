package org.example;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class EmbeddedTomcat {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());
        System.out.println("Embedded Tomcat запущен на http://localhost:8080");

        tomcat.start();
        tomcat.getServer().await();
    }
}
