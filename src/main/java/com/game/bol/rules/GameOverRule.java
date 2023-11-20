package com.game.bol.rules;

import com.game.bol.model.Board;
import com.game.bol.model.Game;
import com.game.bol.model.Pit;
import com.game.bol.model.Status;

public class GameOverRule extends GameRule {
    @Override
    public void apply(Game game, Pit currentPit) {
        Integer player1StoneCount = game.getBoard().getPlayer1PitStoneCount();
        Integer player2StoneCount = game.getBoard().getPlayer2PitStoneCount();

        if( player1StoneCount == 0 || player2StoneCount == 0){

            game.setStatus(Status.FINISHED);

            Pit house1 = game.getBoard().getPits().get(Board.PLAYER1_HOUSE);
            house1.setStoneCount(house1.getStoneCount() + player1StoneCount);

            Pit house2 = game.getBoard().getPits().get(Board.PLAYER2_HOUSE);
            house2.setStoneCount(house2.getStoneCount() + player2StoneCount);

            setWinner(game, house1.getStoneCount(), house2.getStoneCount());

            resetBoard(game);

        }
    }

    private void resetBoard(Game game) {
        for (Pit pit : game.getBoard().getPits().values()) {
            if (pit.isHouse()) {
                continue;
            }
            pit.setStoneCount(0);
        }
    }

    private void setWinner(Game game, Integer house1StoneCount, Integer house2StoneCount){
        if(house1StoneCount > house2StoneCount){
            game.setWinner(game.getPlayer1());
        }else if(house1StoneCount < house2StoneCount){
            game.setWinner(game.getPlayer2());
        }else{
            game.setWinner(null);
        }
    }
}
