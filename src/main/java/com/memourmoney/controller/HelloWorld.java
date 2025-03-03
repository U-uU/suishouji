package com.memourmoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RequestMapping("/hello")
//@ResponseBody
@RestController
public class HelloWorld {
    @RequestMapping("/hello-world")
    public String helloWorld() {
        return "hello world!";
    }
}
