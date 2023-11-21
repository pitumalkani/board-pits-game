package com.game.bol.repo;

import com.game.bol.model.Game;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryGameRepo {
    private static final Map<String, Game> gameMap = new ConcurrentHashMap<>();

    public Game create(Game game){
        gameMap.put(game.getId(), game);
        return gameMap.get(game.getId());
    }

    public Game getById(String id){
        return gameMap.get(id);
    }


}
