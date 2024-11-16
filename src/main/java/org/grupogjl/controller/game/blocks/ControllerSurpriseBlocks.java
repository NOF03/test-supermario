package org.grupogjl.controller.game.blocks;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.blocks.SurpriseBlock;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.level.Level;

import java.util.List;

public class ControllerSurpriseBlocks {
    public void step(Level level, GeneralGui.ACTION action, long time) {
        List<GameObject> objects = level.getObjects();
        List<SurpriseBlock> surpriseBlocks = level.getSurpriseBlocks();
        for (SurpriseBlock surpriseBlock : surpriseBlocks) {
            if (surpriseBlock.isUsed() && !objects.contains(surpriseBlock.getSurprise()) && surpriseBlock.getSurprise().isActivated()) {
                objects.add(surpriseBlock.getSurprise());
            }
        }
        level.setObjects(objects);
    }
}
