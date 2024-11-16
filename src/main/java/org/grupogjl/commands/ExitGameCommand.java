package org.grupogjl.commands;

import org.grupogjl.Game;

public class ExitGameCommand extends GameCommand {


    @Override
    public void execute(Game game) {
        game.setStateNull();
    }
}
