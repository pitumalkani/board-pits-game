package com.game.bol.model;

import org.junit.jupiter.api.Test;
import org.junit.Assert;

class BoardTest {
    @Test
     void shouldCreateBoard(){

        //given & when
        Board board = initBoard();

        //then
        Assert.assertNotNull(board.getPits());
        Assert.assertEquals(14, board.getPits().size());
    }

    @Test
     void shouldGetStoneCountByPitIndex(){
        //given
        Board board = initBoard();

        //when
        Integer pit1Stone = board.getPitByPitIndex(1).getStoneCount();
        Integer house1Stone =board.getPitByPitIndex(7).getStoneCount();
        Integer pit8Stone = board.getPitByPitIndex(8).getStoneCount();
        Integer house2Stone = board.getPitByPitIndex(14).getStoneCount();

        //then
        Assert.assertEquals(Integer.valueOf(6), pit1Stone);
        Assert.assertEquals(Integer.valueOf(0), house1Stone);
        Assert.assertEquals(Integer.valueOf(6), pit8Stone);
        Assert.assertEquals(Integer.valueOf(0), house2Stone);
    }

    @Test
     void shouldGetPlayerHouse(){
        //given
        Board board = initBoard();

        //when
        Pit house1 = board.getPlayerHouse(Player.PLAYER1_INDEX);
        Pit house2 = board.getPlayerHouse(Player.PLAYER2_INDEX);

        //then
        Assert.assertEquals(Integer.valueOf(7), house1.getIndex());
        Assert.assertEquals(Integer.valueOf(14), house2.getIndex());
    }

    @Test
     void shouldGetPitByPitIndex(){
        //given
        Board board = initBoard();

        //when
        Pit pit = board.getPitByPitIndex(2);

        //then
        Assert.assertEquals(Integer.valueOf(2), pit.getIndex());
        Assert.assertEquals(Integer.valueOf(1), pit.getPlayerIndex());
    }

    @Test
     void shouldGetNextPit() {

        //given
        Board board = initBoard();

        //when
        Pit pit1 = board.getPitByPitIndex(1);
        Pit pit2 = board.getNextPit(pit1);
        Pit pit14 = board.getPitByPitIndex(14);
        Pit pit1Again = board.getNextPit(pit14);

        //then
        Assert.assertEquals(Integer.valueOf(2), pit2.getIndex());
        Assert.assertEquals(pit1, pit1Again);
    }

    @Test
     void shouldGetOppositePit() {
        //given
        Board board = initBoard();

        //when
        Pit pit1 = board.getPitByPitIndex(1);
        Pit oppositePit = board.getOppositePit(pit1);
        Pit pit1Again = board.getOppositePit(oppositePit);

        //then
        Assert.assertEquals(Integer.valueOf(13), oppositePit.getIndex());
        Assert.assertEquals(pit1, pit1Again);
    }

    @Test
     void shouldGetPlayer1PitStoneCount(){
        //given
        Board board = initBoard();

        //when
        Integer player1PitStoneCount = board.getPlayer1PitStoneCount();

        //then
        Assert.assertEquals(Integer.valueOf(36), player1PitStoneCount);
    }

    @Test
     void shouldGetPlayer2PitStoneCount(){
        //given
        Board board = initBoard();

        //when
        board.getPits().get(8).setStoneCount(0);
        Integer player2PitStoneCount = board.getPlayer2PitStoneCount();

        //then
        Assert.assertEquals(Integer.valueOf(30), player2PitStoneCount);
    }


    private static Board initBoard(){
        Player player1 = new Player(Player.PLAYER1_INDEX, "player1");
        Player player2 = new Player(Player.PLAYER2_INDEX, "player2");
        return new Board(Board.INITIAL_STONE_ON_PIT, player1, player2);
    }
}
