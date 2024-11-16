package org.grupogjl.commands;

import org.grupogjl.Game;

public class PauseCommand extends GameCommand {
    @Override
    public void execute(Game game) {
        game.setStatePause();
    }
}
