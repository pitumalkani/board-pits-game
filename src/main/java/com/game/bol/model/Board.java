package com.game.bol.model;

import lombok.*;

import java.util.Map;

@Data
public class Board {

    public static final Integer PIT_START_INDEX = 1;
    public static final Integer PIT_END_INDEX = 14;
    public static final Integer PLAYER1_HOUSE = 7;
    public static final Integer PLAYER2_HOUSE = 14;
    public static final Integer INITIAL_STONE_ON_PIT = 6;
    public static final Integer INITIAL_STONE_ON_HOUSE = 0;

    private Map<Integer, Pit> pits;

    public Board(Integer initialStonesOnPit, Player player1, Player player2) {
        //initialization of Game here.
    }
}
