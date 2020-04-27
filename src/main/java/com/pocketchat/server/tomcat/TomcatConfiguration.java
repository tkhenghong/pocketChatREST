package com.pocketchat.server.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// This is the general Tomcat configuration of the Spring Boot Server
// Note: You don't need to configure in the latest version of Spring Boot.
// You don't have to do anything with Tomcat after have keystores files, application.properties files -> WebSecurityConfiguration.java
//@Configuration
//public class TomcatConfiguration {
//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(getHttpConnector());
//        return tomcat;
//    }
//
//    private Connector getHttpConnector() {
//        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//        connector.setScheme("http");
//        // Default port: 8080
//        connector.setPort(8888);
//        connector.setSecure(false);
//        // You need to change the following port to another one(I set it beside the server port that I have)
//        // Default port in tutorial: 8443
//        connector.setRedirectPort(8888);
//        return connector;
//    }
//}
