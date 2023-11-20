package com.game.bol.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pit {

    @NotNull
    private Integer pitIndex;

    @NotNull
    private Integer stoneCount;

    @NotNull
    private Integer playerIndex;

    public Boolean isDistributable(Status gameStatus){
        return (!gameStatus.equals(Status.PLAYER1_TURN) || !this.pitIndex.equals(Board.PLAYER2_HOUSE))
                && (!gameStatus.equals(Status.PLAYER2_TURN) || !this.pitIndex.equals(Board.PLAYER1_HOUSE));
    }

    public Integer nextPitIndex() {
        return (this.pitIndex % Board.PLAYER2_HOUSE) + 1;
    }

    public Boolean isHouse(){
        return this.pitIndex.equals(Board.PLAYER1_HOUSE) || this.pitIndex.equals(Board.PLAYER2_HOUSE);
    }
    public boolean isPlayerPit(Status gameStatus) {
        return (gameStatus == Status.PLAYER1_TURN && this.playerIndex == Player.PLAYER1_INDEX)
                || (gameStatus == Status.PLAYER2_TURN && this.playerIndex == Player.PLAYER2_INDEX);
    }
    public Integer getOppositePitIndex(){
        return  (Board.PIT_START_INDEX + Board.PIT_END_INDEX - 1) - this.getPitIndex();
    }

    public Boolean isPlayer1House(){
        return this.playerIndex.equals(Player.PLAYER1_INDEX) && this.pitIndex.equals(Board.PLAYER1_HOUSE);

    }
    public Boolean isPlayer2House(){
        return this.playerIndex.equals(Player.PLAYER2_INDEX) && this.pitIndex.equals(Board.PLAYER2_HOUSE);
    }
}
