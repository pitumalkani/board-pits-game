package com.game.bol.rules;

import com.game.bol.model.*;

public class StartRule extends GameRule {
    @Override
    public void apply(Game game, Pit startPit) {
        if(startPit.getStoneCount() == 0){
            throw new IllegalArgumentException("Can not start game with an empty pit");
        }
        playerTurnRule(game,startPit);
    }

    private void playerTurnRule(Game game, Pit startPit){
        if(game.getStatus().equals(Status.ACTIVE)) {
            Status status =  startPit.getPlayerIndex().equals(Player.PLAYER1_INDEX) ? Status.PLAYER1_TURN : Status.PLAYER2_TURN;
            game.setStatus(status);
        }
        if((game.getStatus().equals(Status.PLAYER1_TURN) && startPit.getPitIndex() >= Board.PLAYER1_HOUSE) ||
                (game.getStatus().equals(Status.PLAYER2_TURN) && startPit.getPitIndex() <= Board.PLAYER1_HOUSE)){
            throw new IllegalArgumentException("Incorrect pit to start play");
        }
    }
}
