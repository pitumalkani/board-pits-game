package com.game.bol.service;

import com.game.bol.model.Board;
import com.game.bol.model.Game;
import com.game.bol.model.Pit;
import com.game.bol.model.Player;
import com.game.bol.processor.GameProcessor;
import com.game.bol.repo.InMemoryGameRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class GameService {

    private InMemoryGameRepo repo;

    private GameProcessor processor;

    private String TEMPLATE = """
                       Player Two        
             | %02d | %02d | %02d | %02d | %02d | %02d |
        (%02d)                                 (%02d)
             | %02d | %02d | %02d | %02d | %02d | %02d |
                       Player One
        """;


    public GameService(final InMemoryGameRepo repo, final GameProcessor processor) {
        this.repo = repo;
        this.processor = processor;
    }

    public Game initGame(Integer initialPitStoneCount) {
        final var game = new Game(initialPitStoneCount);
        game.setId(UUID.randomUUID().toString());
        System.out.println(printBoard(game));
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
        System.out.println(printBoard(game));
        return game;
    }


    private String printBoard(Game game) {
        List<Integer> p2Rev =  game.getBoard().getPlayer2Pits();
        Collections.reverse(p2Rev);

        List<Integer> pits = new ArrayList<>(p2Rev);
        pits.add(game.getBoard().getPlayerHouse(Player.PLAYER2_INDEX).getStoneCount());
        pits.add(game.getBoard().getPlayerHouse(Player.PLAYER1_INDEX).getStoneCount());
        pits.addAll(game.getBoard().getPlayer1Pits());

        return String.format(TEMPLATE, pits.stream()
                .toList()
                .toArray());
    }
}
