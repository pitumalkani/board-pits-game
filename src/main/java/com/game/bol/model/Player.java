package com.game.bol.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player{

    public static final Integer PLAYER1_INDEX = 1;
    public static final Integer PLAYER2_INDEX = 2;

    @NotNull
    private Integer playerIndex;

    private String playerName;
}
