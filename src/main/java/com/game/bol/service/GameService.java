package com.game.bol.service;

import com.game.bol.model.Board;
import com.game.bol.model.Game;
import com.game.bol.repo.InMemoryGameRepo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameService {

    private InMemoryGameRepo repo;

    public GameService(InMemoryGameRepo repo) {
        this.repo = repo;
    }

    public Game initGame(Integer initialPitStoneCount) {
        final var game = new Game(initialPitStoneCount);
        game.setId(UUID.randomUUID().toString());
        return repo.create(game);
    }
}
