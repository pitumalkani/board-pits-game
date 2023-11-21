package com.game.bol.rules;

import com.game.bol.model.Game;
import com.game.bol.model.Pit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CirculateStonesRule extends GameRule {

    @Override
    public void apply(Game game, Pit currentPit) {
        log.debug("check the rules for circulating stone to the next pit(s)");

        Integer stoneToCirculate = currentPit.getStoneCount();
        currentPit.setStoneCount(0);

        for (int i = 0; i < stoneToCirculate; i++) {

            currentPit = game.getBoard().getNextPit(currentPit);
            log.debug("next pit {}", currentPit);

            if (currentPit.isDistributable(game.getStatus())) {
                currentPit.setStoneCount(currentPit.getStoneCount() + 1);
            } else {
                i--;
            }
        }

        this.next.apply(game, currentPit);
    }


}
