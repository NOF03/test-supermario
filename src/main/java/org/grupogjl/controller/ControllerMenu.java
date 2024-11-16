package org.grupogjl.controller;

import org.grupogjl.Game;
import org.grupogjl.gui.GeneralGui;

public class ControllerMenu implements Controller {
    @Override
    public void step(Game game, GeneralGui.ACTION action, long time) {
        if (game.getStateMenu().getModel().isSelectedOption()) {
            if (action == GeneralGui.ACTION.SELECT) game.getStateMenu().getModel().setSelectedOption(false);
        } else {
            if (action == GeneralGui.ACTION.DOWN) game.getStateMenu().getModel().nextPosition();
            if (action == GeneralGui.ACTION.UP) game.getStateMenu().getModel().lastPosition();
            if (action == GeneralGui.ACTION.SELECT) game.getStateMenu().getModel().execute(game);
        }
    }
}
