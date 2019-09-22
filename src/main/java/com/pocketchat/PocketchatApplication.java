package com.pocketchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class PocketchatApplication {

    public static void main(String[] args) {
        SpringApplication.run(PocketchatApplication.class, args);
    }

}

@RestController
class Hello {

    @RequestMapping("/")
    String index() {
        return "Hello world";
    }
}