package com.game.bol.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
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
        initPit(initialStonesOnPit, player1, player2);
    }

    private Map<Integer, Pit> initPit(Integer initialStoneOnPit, Player player1, Player player2) {

        this.pits = new ConcurrentHashMap<>();
        for (int i = Board.PIT_START_INDEX; i < Board.PLAYER1_HOUSE; i++) {
            Pit pit = new Pit(i, initialStoneOnPit, player1.getPlayerIndex());
            pits.put(i, pit);
        }
        Pit house1 = new Pit(Board.PLAYER1_HOUSE, Board.INITIAL_STONE_ON_HOUSE, player1.getPlayerIndex());
        pits.put(Board.PLAYER1_HOUSE, house1);


        for (int i = Board.PLAYER1_HOUSE + 1; i < Board.PLAYER2_HOUSE; i++) {
            Pit pit = new Pit(i, initialStoneOnPit, player2.getPlayerIndex());
            pits.put(i, pit);
        }
        Pit house2 = new Pit(Board.PLAYER2_HOUSE, Board.INITIAL_STONE_ON_HOUSE, player2.getPlayerIndex());
        pits.put(Board.PLAYER2_HOUSE, house2);

        return pits;
    }

    public Pit getPitByPitIndex(Integer pitIndex) {
        return pits.get(pitIndex);
    }

    public Pit getNextPit(Pit pit) {
        return pits.get(pit.nextPitIndex());
    }

    public Pit getOppositePit(Pit pit) {
        return pits.get(pit.getOppositePitIndex());
    }

    public Pit getPlayerHouse(Integer playerIndex) {
        return pits.get(playerIndex.equals(Player.PLAYER1_INDEX) ? Board.PLAYER1_HOUSE : Board.PLAYER2_HOUSE);
    }

    public Integer getPlayer1PitStoneCount() {
        Integer player1PitStoneCount = 0;
        for (int i = Board.PIT_START_INDEX; i < Board.PLAYER1_HOUSE; i++) {
            player1PitStoneCount += this.getPits().get(i).getStoneCount();
        }
        return player1PitStoneCount;
    }

    public Integer getPlayer2PitStoneCount() {
        Integer player2PitStoneCount = 0;
        for (int i = Board.PLAYER1_HOUSE + 1; i < Board.PLAYER2_HOUSE; i++) {
            player2PitStoneCount += this.getPits().get(i).getStoneCount();
        }
        return player2PitStoneCount;
    }


    public List<Integer> getPlayer1Pits() {
        return this.getPits().values().stream()
                .filter(pit -> pit.getIndex() >= Board.PIT_START_INDEX && pit.getIndex() < Board.PLAYER1_HOUSE)
                .map(Pit::getStoneCount)
                .collect(Collectors.toList());
    }

    public List<Integer> getPlayer2Pits() {
        return this.getPits().values().stream()
                .filter(pit -> pit.getIndex() >= Board.PLAYER1_HOUSE + 1 && pit.getIndex() < Board.PLAYER2_HOUSE)
                .map(Pit::getStoneCount)
                .collect(Collectors.toList());
    }

}
