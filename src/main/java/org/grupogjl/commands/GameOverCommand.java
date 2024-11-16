package org.grupogjl.commands;

import org.grupogjl.Game;

public class GameOverCommand extends GameCommand {
    @Override
    public void execute(Game game) {
        game.getStateGame().setGameOver(true);
    }
}
