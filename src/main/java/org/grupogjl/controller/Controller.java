package org.grupogjl.controller;

import org.grupogjl.Game;
import org.grupogjl.gui.GeneralGui;

import java.io.IOException;

public interface Controller {
    void step(Game game, GeneralGui.ACTION action, long time) throws IOException;
}
