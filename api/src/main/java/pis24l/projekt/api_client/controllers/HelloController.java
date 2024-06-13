package pis24l.projekt.api_client.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @GetMapping("/hello world")
    public String helloWorld() {
        return "Hello World!";
    }
}

