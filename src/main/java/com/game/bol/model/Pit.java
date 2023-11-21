package com.game.bol.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pit {

    @NotNull
    private Integer index;

    @NotNull
    private Integer stoneCount;

    @NotNull
    private Integer playerIndex;

    public boolean isDistributable(Status gameStatus){
        return (!gameStatus.equals(Status.PLAYER1_TURN) || !this.index.equals(Board.PLAYER2_HOUSE))
                && (!gameStatus.equals(Status.PLAYER2_TURN) || !this.index.equals(Board.PLAYER1_HOUSE));
    }

    public Integer nextPitIndex() {
        return (this.index % Board.PLAYER2_HOUSE) + 1;
    }

    public boolean isHouse(){
        return this.index.equals(Board.PLAYER1_HOUSE) || this.index.equals(Board.PLAYER2_HOUSE);
    }
    public boolean isPlayerPit(Status gameStatus) {
        return (gameStatus.equals(Status.PLAYER1_TURN) && this.playerIndex.equals(Player.PLAYER1_INDEX))
                || (gameStatus.equals(Status.PLAYER2_TURN) && this.playerIndex.equals(Player.PLAYER2_INDEX));
    }
    public Integer getOppositePitIndex(){
        return  (Board.PIT_START_INDEX + Board.PIT_END_INDEX - 1) - this.getIndex();
    }

    public boolean isPlayer1House(){
        return this.playerIndex.equals(Player.PLAYER1_INDEX) && this.index.equals(Board.PLAYER1_HOUSE);

    }
    public boolean isPlayer2House(){
        return this.playerIndex.equals(Player.PLAYER2_INDEX) && this.index.equals(Board.PLAYER2_HOUSE);
    }
}
