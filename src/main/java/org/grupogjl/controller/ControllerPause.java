package org.grupogjl.controller;

import org.grupogjl.Game;
import org.grupogjl.gui.GeneralGui;

public class ControllerPause implements Controller {
    @Override
    public void step(Game game, GeneralGui.ACTION action, long time) {
        if (action == GeneralGui.ACTION.DOWN) game.getStatePause().getModel().nextPosition();
        if (action == GeneralGui.ACTION.UP) game.getStatePause().getModel().lastPosition();
        if (action == GeneralGui.ACTION.SELECT) game.getStatePause().getModel().execute(game);
    }
}
