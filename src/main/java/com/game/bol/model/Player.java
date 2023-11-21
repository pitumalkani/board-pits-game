package com.game.bol.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player{

    public static final Integer PLAYER1_INDEX = 1;
    public static final Integer PLAYER2_INDEX = 2;

    private Integer playerIndex;

    private String playerName;
}
