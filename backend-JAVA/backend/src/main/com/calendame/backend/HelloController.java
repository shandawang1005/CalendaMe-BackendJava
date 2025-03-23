package com.calendame.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class HelloController {
    @GetMapping("/api/hello")
    public String sayHello(){
        return "Hello From CalendaMe Spring Boot backend!";
    }
}
