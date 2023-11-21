package com.game.bol.rules;


import com.game.bol.model.Game;
import com.game.bol.model.Pit;
import com.game.bol.model.Status;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is responsible to check the last stone placing rules.
 *
 */
@Slf4j
public class EndRule extends GameRule {


    @Override
    public void apply(Game game, Pit currentPit) {
        log.debug("check the rules for circulating stone to the next pit(s)");
        handleLastEmptyPit(game,currentPit);
        nextPlayerTurn(game,currentPit);
        this.next.apply(game, currentPit);// Next verify the winner
    }

    private void handleLastEmptyPit(Game game, Pit endPit){
        if (endPit.isHouse() || !endPit.isPlayerPit(game.getStatus()) || !endPit.getStoneCount().equals(1)) {
            return;
        }
        Pit oppositePit = game.getBoard().getOppositePit(endPit);
        if (oppositePit.getStoneCount() == 0) {
            return;
        }
        captureOpponetsPitStones(game,endPit,oppositePit);

    }

    private void captureOpponetsPitStones(final Game game, final Pit endPit, final Pit oppositePit){
        Pit house = game.getBoard().getPlayerHouse(endPit.getPlayerIndex());
        house.setStoneCount(house.getStoneCount() + oppositePit.getStoneCount() + endPit.getStoneCount());
        oppositePit.setStoneCount(0);
        endPit.setStoneCount(0);
    }

    private void nextPlayerTurn(Game game, Pit endPit) {
        Status currentStatus = game.getStatus();
        Status newStatus = null;

        if (endPit.isPlayer1House() && currentStatus == Status.PLAYER1_TURN) {
            newStatus = Status.PLAYER1_TURN;
        } else if (endPit.isPlayer2House() && currentStatus == Status.PLAYER2_TURN) {
            newStatus = Status.PLAYER2_TURN;
        } else {
            newStatus = (currentStatus == Status.PLAYER1_TURN) ? Status.PLAYER2_TURN : Status.PLAYER1_TURN;
        }

        game.setStatus(newStatus);
    }
}
