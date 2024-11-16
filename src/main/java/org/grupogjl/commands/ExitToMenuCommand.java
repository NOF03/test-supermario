package org.grupogjl.commands;

import org.grupogjl.Game;

public class ExitToMenuCommand extends GameCommand {
    @Override
    public void execute(Game game) {
        game.setStateMenu();
    }
}
