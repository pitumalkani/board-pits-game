package com.game.bol.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @GetMapping("/greeting")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String greeting() {
        return "hello world";
    }
}
