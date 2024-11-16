package org.grupogjl.controller.game.blocks;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.level.Level;

public class ControllerBlocks {
    public void step(Level level, GeneralGui.ACTION action, long time) {
        ControllerDestroyableBlocks controllerDestroyableBlocks = new ControllerDestroyableBlocks();
        ControllerSurpriseBlocks controllerSurpriseBlocks = new ControllerSurpriseBlocks();
        controllerSurpriseBlocks.step(level, action, time);
        controllerDestroyableBlocks.step(level, action, time);

    }
}
