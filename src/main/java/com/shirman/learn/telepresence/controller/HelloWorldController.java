package com.shirman.learn.telepresence.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @describe：
 * @author：shirman
 * @date：2020-12-12 6:18 下午
 */
@RestController()
public class HelloWorldController {
    @GetMapping("/")
    public Object index(){
        System.out.println("request come in!");
        return "hello world, telepresence!";
    }
}