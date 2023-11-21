package com.game.bol.processor;

import com.game.bol.model.Game;
import com.game.bol.model.Pit;
import com.game.bol.model.Status;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameProcessorTest {

    @Autowired
    private GameProcessor gameProcessor;

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotStartWithEmptyPit(){
        //given
        Game game = new Game(6);
        Pit pit = game.getBoard().getPits().get(2);
        pit.setStoneCount(0);

        //when
        gameProcessor.play(game, game.getBoard().getPitByPitIndex(2));

    }
    @Test(expected = IllegalArgumentException.class)
    public void shouldNotStartWithOpponentPit(){
        //given
        Game game = new Game(6);
        game.setStatus(Status.PLAYER2_TURN);

        //when
        gameProcessor.play(game, game.getBoard().getPitByPitIndex(2));
    }

    @Test
    public void shouldGameOver() {

        //given
        Game game = new Game(6);
        for(Integer key : game.getBoard().getPits().keySet()){
            Pit pit = game.getBoard().getPits().get(key);
            if(!pit.isHouse()) {
                pit.setStoneCount(0);
            }
        }
        game.getBoard().getPits().get(6).setStoneCount(1);

        //when
        gameProcessor.play(game, game.getBoard().getPitByPitIndex(6));

        //then
        Assert.assertEquals(Status.FINISHED, game.getStatus());
        Assert.assertEquals(game.getWinner(), game.getPlayer1());
    }

    @Test
    public void shouldPlayer1Win() {

        //given
        Game game = new Game(6);
        for(Integer key : game.getBoard().getPits().keySet()){
            Pit pit = game.getBoard().getPits().get(key);
            if(!pit.isHouse()) {
                pit.setStoneCount(0);
            }
        }
        Pit lastPit = game.getBoard().getPits().get(6);
        lastPit.setStoneCount(1);

        //when
        gameProcessor.play(game, game.getBoard().getPitByPitIndex(6));

        //then
        Assert.assertEquals(Status.FINISHED, game.getStatus());
        Assert.assertEquals(game.getWinner(), game.getPlayer1());
    }

    @Test
    public void shouldPlayer2Win(){

        //given
        Game game = new Game(6);
        for(Integer key : game.getBoard().getPits().keySet()){
            Pit pit = game.getBoard().getPits().get(key);
            if(!pit.isHouse()) {
                pit.setStoneCount(0);
            }
        }
        game.getBoard().getPits().get(13).setStoneCount(1);

        //when
        gameProcessor.play(game, game.getBoard().getPitByPitIndex(13));

        //then
        Assert.assertEquals(Status.FINISHED, game.getStatus());
        Assert.assertEquals(game.getWinner(), game.getPlayer2());
    }
}
