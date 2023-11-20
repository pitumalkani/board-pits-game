package com.game.bol.service;

import com.game.bol.model.Board;
import com.game.bol.model.Game;
import com.game.bol.model.Pit;
import com.game.bol.processor.GameProcessor;
import com.game.bol.repo.InMemoryGameRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameService {

    private InMemoryGameRepo repo;

    private GameProcessor processor;

    public GameService(final InMemoryGameRepo repo, final GameProcessor processor) {
        this.repo = repo;
        this.processor = processor;
    }

    public Game initGame(Integer initialPitStoneCount) {
        final var game = new Game(initialPitStoneCount);
        game.setId(UUID.randomUUID().toString());
        return repo.create(game);
    }


    public Game move(String gameId, Integer pitIndex) {
        if(pitIndex > Board.PIT_END_INDEX || pitIndex < Board.PIT_START_INDEX){
            throw new IllegalArgumentException("Incorrect position");
        }

        if(pitIndex.equals(Board.PLAYER1_HOUSE) || pitIndex.equals(Board.PLAYER2_HOUSE)){
            throw new IllegalArgumentException("House stone is not allow to distribute");
        }
        // when validation passes, then the player is allowed to make a move.
        Game game = repo.getById(gameId);
        Pit pit = game.getBoard().getPitByPitIndex(pitIndex);
        processor.play(game,pit);
        return game;
    }

}
