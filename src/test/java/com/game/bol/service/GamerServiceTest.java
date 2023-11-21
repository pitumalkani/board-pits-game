package com.game.bol.service;

import com.game.bol.model.Board;
import com.game.bol.model.Game;
import com.game.bol.model.Pit;
import com.game.bol.model.Player;
import com.game.bol.repo.InMemoryGameRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)

@SpringBootTest
public class GamerServiceTest {
    @Mock
    private InMemoryGameRepo repo;

    @Autowired
    private GameService gameService;

    @Test
    public void shouldInitGame() {

        Player player1 = new Player(Player.PLAYER1_INDEX, "Player 1");
        Player player2 = new Player(Player.PLAYER2_INDEX, "Player 2");

        Board board = new Board();
        board.setPits(initPit());

        Game game = new Game(Board.INITIAL_STONE_ON_PIT);
        game.setId(UUID.randomUUID().toString());
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setBoard(board);

        //given
        when(repo.create(any())).thenReturn(game);

        //when
        Game mockGame = gameService.initGame(6);

        //then
        Assert.assertEquals(game.getBoard(), mockGame.getBoard());

    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldPlayGameWhenPitsAreMovedWithoutInitializingGame() {
        String id = UUID.randomUUID().toString();
        Game game = new Game(Board.INITIAL_STONE_ON_PIT);
        game.setId(id);

        //given1
        Mockito.when(repo.getById(id)).thenReturn(null);

        gameService.move(game.getId(), game.getBoard().getPits().get(1).getIndex());
    }


    private Map<Integer, Pit> initPit() {
        Map<Integer, Pit> pits = new HashMap<>();
        for (int i = Board.PIT_START_INDEX; i < Board.PLAYER1_HOUSE; i++) {
            Pit pit = new Pit(i, Board.INITIAL_STONE_ON_PIT, Player.PLAYER1_INDEX);
            pits.put(i, pit);
        }
        Pit house1 = new Pit(Board.PLAYER1_HOUSE, Board.INITIAL_STONE_ON_HOUSE, Player.PLAYER1_INDEX);
        pits.put(Board.PLAYER1_HOUSE, house1);

        for (int i = Board.PLAYER1_HOUSE + 1; i < Board.PLAYER2_HOUSE; i++) {
            Pit pit = new Pit(i, Board.INITIAL_STONE_ON_PIT, Player.PLAYER2_INDEX);
            pits.put(i, pit);
        }
        Pit house2 = new Pit(Board.PLAYER2_HOUSE, Board.INITIAL_STONE_ON_HOUSE, Player.PLAYER2_INDEX);
        pits.put(Board.PLAYER2_HOUSE, house2);

        return pits;
    }
}
