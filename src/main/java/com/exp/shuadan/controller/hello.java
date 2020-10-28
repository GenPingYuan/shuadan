package com.exp.shuadan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hello {

    @RequestMapping("/say")
    public String say(){
        return "hello world";
    }
}
