package com.game.bol.rules;

import com.game.bol.model.Game;
import com.game.bol.model.Pit;

public abstract class GameRule {

    protected GameRule next;
    public abstract void apply(Game game, Pit currentPit);

    public GameRule setNext(GameRule next) {
        this.next = next;
        return next;
    }
}
