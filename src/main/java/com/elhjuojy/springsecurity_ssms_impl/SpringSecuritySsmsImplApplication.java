package com.elhjuojy.springsecurity_ssms_impl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringSecuritySsmsImplApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringSecuritySsmsImplApplication.class, args);
    }




}

@RestController
class BasicController {
    @GetMapping("/hello")
    public ResponseEntity<String> get(){
        return ResponseEntity.ok("Hello");
    }
}
