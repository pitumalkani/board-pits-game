package com.game.bol.processor;

import com.game.bol.model.Game;
import com.game.bol.model.Pit;
import com.game.bol.rules.*;
import org.springframework.stereotype.Component;

@Component
public class GameProcessor {
    private final GameRule chain;
    public GameProcessor() {
        this.chain = new StartRule();
        chain.setNext(new CirculateStonesRule()) // to ++ stone to the landing pit
                .setNext(new EndRule())         // to verify if the stone lands in empty pit, if so then capture opponents pit stones
                .setNext(new GameOverRule());  // to verify if all the pits are empty.
    }

    public void play(Game game, Pit pit) {
        this.chain.apply(game, pit);
    }

}
