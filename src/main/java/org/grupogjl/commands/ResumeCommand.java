package org.grupogjl.commands;

import org.grupogjl.Game;

public class ResumeCommand extends GameCommand {
    @Override
    public void execute(Game game) {
        game.setStateGame(game.getStatePause().getParent());
    }
}
