package com.game.bol.controller;

import com.game.bol.model.Game;
import com.game.bol.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<Game> init(@RequestParam(name = "stone", defaultValue = "6", required = false)  int numberOfStones){
        return  ResponseEntity.status(HttpStatus.CREATED).body(gameService.initGame(numberOfStones));
    }

    @PostMapping("/{gameId}/move")
    public ResponseEntity<Game> move(@PathVariable final String gameId, @RequestParam final int pitIndex) {
        try{
            return ResponseEntity.ok().body(gameService.move(gameId, pitIndex));
        }
        catch(final Exception exception){
            ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, exception.getMessage(), exception);
        }
    }
}
