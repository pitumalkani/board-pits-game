package com.game.bol.rules;

import com.game.bol.model.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartRule extends GameRule {
    @Override
    public void apply(Game game, Pit startPit) {
        log.debug("checking start rule for the first pit {}", startPit);
        if(startPit.getStoneCount() == 0){
            throw new IllegalArgumentException("Can not start game with an empty pit");
        }
        playerTurnRule(game,startPit);
        this.next.apply(game, startPit);
    }

    private void playerTurnRule(Game game, Pit startPit){
        // this validation is to ensure that the players play the game turn by turn.
        // if a player tries to play in illegitimate way then an exception is thrown.
        if(game.getStatus().equals(Status.ACTIVE)) {
            Status status =  startPit.getPlayerIndex().equals(Player.PLAYER1_INDEX) ? Status.PLAYER1_TURN : Status.PLAYER2_TURN;
            game.setStatus(status);
        }
        if((game.getStatus().equals(Status.PLAYER1_TURN) && startPit.getIndex() >= Board.PLAYER1_HOUSE) ||
                (game.getStatus().equals(Status.PLAYER2_TURN) && startPit.getIndex() <= Board.PLAYER1_HOUSE)){
            log.debug("Player started the game with incorrect pit {}", startPit);
            throw new IllegalArgumentException("Incorrect pit to start play");
        }
    }
}
