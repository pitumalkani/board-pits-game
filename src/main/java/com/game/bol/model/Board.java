package com.game.bol.model;

import lombok.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        //initialization of pits here
        initPit(initialStonesOnPit,player1,player2);
    }

    private Map<Integer, Pit> initPit(Integer initialStoneOnPit, Player player1, Player player2){

        this.pits = new ConcurrentHashMap<>();
        for(int i= Board.PIT_START_INDEX; i < Board.PLAYER1_HOUSE; i++){
            Pit pit = new Pit(i, initialStoneOnPit, player1.getPlayerIndex());
            pits.put(i, pit);
        }
        Pit house1 = new Pit(Board.PLAYER1_HOUSE, Board.INITIAL_STONE_ON_HOUSE, player1.getPlayerIndex());
        pits.put(Board.PLAYER1_HOUSE, house1);


        for(int i= Board.PLAYER1_HOUSE + 1; i < Board.PLAYER2_HOUSE; i++){
            Pit pit = new Pit(i, initialStoneOnPit, player2.getPlayerIndex());
            pits.put(i, pit);
        }
        Pit house2 = new Pit(Board.PLAYER2_HOUSE, Board.INITIAL_STONE_ON_HOUSE, player2.getPlayerIndex());
        pits.put(Board.PLAYER2_HOUSE, house2);

        return pits;
    }
}
