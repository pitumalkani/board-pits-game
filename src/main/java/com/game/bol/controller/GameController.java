package com.game.bol.controller;

import com.game.bol.model.Player;
import com.game.bol.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private GameService gameService;

    public GameController(final GameService gameService){
        this.gameService = gameService;
    }

    @GetMapping("/greeting")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String greeting() {
        return "hello world";
    }

    @PostMapping("/init")
    public ResponseEntity init(@RequestParam int numberOfStones){
        return  ResponseEntity.status(HttpStatus.CREATED).body(gameService.initGame(numberOfStones));
    }

    @PostMapping("/{id}/move")
    public ResponseEntity move(@PathVariable final String gameId, @RequestParam final int pitIndex) {
        return ResponseEntity.ok().body(gameService.move(gameId, pitIndex));
    }
}
