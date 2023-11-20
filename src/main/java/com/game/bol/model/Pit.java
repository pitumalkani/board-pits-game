package com.game.bol.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pit {

    @NotNull
    private Integer pitIndex;

    @NotNull
    private Integer stoneCount;

    @NotNull
    private Integer playerIndex;
}
