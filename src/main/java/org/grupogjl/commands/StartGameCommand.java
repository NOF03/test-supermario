package org.grupogjl.commands;

import org.grupogjl.Game;

import java.io.IOException;

public class StartGameCommand extends GameCommand {
    @Override
    public void execute(Game game) {
        try {
            game.setStateGame();
            game.getStateGame().setGameOver(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
