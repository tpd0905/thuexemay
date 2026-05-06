package servlet;

import java.io.File;
import java.io.IOException;

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
        System.out.flush();
        
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        
        // Set base directory
        String baseDir = System.getProperty("java.io.tmpdir");
        tomcat.setBaseDir(baseDir);
        
        // Get the webapp directory (should be in classpath)
        String appBase = new File(".").getAbsolutePath();
        
        // Add the webapp context at root path
        Context context = tomcat.addWebapp("", appBase);
        if (context == null) {
            throw new RuntimeException("Failed to add webapp context");
        }
        
        try {
            // Start Tomcat
            tomcat.start();
            System.out.println("✓ Tomcat started successfully!");
            System.out.println("✓ Application running at http://localhost:" + port);
            System.out.flush();
            
            // Keep running
            tomcat.getServer().await();
        } catch (Exception e) {
            System.err.println("✗ Error starting Tomcat: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
