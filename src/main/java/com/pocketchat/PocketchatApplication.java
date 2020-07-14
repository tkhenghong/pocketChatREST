package com.pocketchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PocketchatApplication {

    public static void main(String[] args) {
        SpringApplication.run(PocketchatApplication.class, args);
    }

}

//@RestController
//class Hello {
//
//    @GetMapping("/")
//    String index() {
//        return "Hello world";
//    }
//}
