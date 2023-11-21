package com.game.bol.utils;

import com.game.bol.model.Game;
import com.game.bol.model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util {

    private Util(){}
    private static final String TEMPLATE = """
                       Player Two        
             | %02d | %02d | %02d | %02d | %02d | %02d |
        (%02d)                                 (%02d)
             | %02d | %02d | %02d | %02d | %02d | %02d |
                       Player One
        """;

    public static String printBoard(Game game) {
        List<Integer> player2Pits =  game.getBoard().getPlayer2Pits();
        Collections.reverse(player2Pits);

        List<Integer> pits = new ArrayList<>(player2Pits);
        pits.add(game.getBoard().getPlayerHouse(Player.PLAYER2_INDEX).getStoneCount());
        pits.add(game.getBoard().getPlayerHouse(Player.PLAYER1_INDEX).getStoneCount());
        pits.addAll(game.getBoard().getPlayer1Pits());

        return String.format(TEMPLATE, pits.stream()
                .toList()
                .toArray());
    }
}
