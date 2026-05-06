package servlet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * Embedded Tomcat starter for Railway deployment
 * Runs the web application on port 8080 or PORT environment variable
 */
public class TomcatStarter {

    public static void main(String[] args) throws LifecycleException, IOException {
        // Get port from environment variable or default to 8080
        String portStr = System.getenv("PORT");
        int port = portStr != null ? Integer.parseInt(portStr) : 8080;
        
        System.out.println("Starting Embedded Tomcat on port " + port);
        
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.setBaseDir(new File("target/tomcat").getAbsolutePath());
        
        // Get application base directory
        String appBase = new File("target").getAbsolutePath();
        
        // Add context - deploy root at "/"
        Context context = tomcat.addWebapp("", appBase);
        context.setPath("");
        
        // Start Tomcat
        tomcat.start();
        System.out.println("Tomcat started successfully!");
        System.out.println("Application running at http://localhost:" + port);
        
        // Keep running
        tomcat.getServer().await();
    }
}
