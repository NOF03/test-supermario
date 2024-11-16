package org.grupogjl.controller.game.blocks;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.blocks.DestroyableBlock;
import org.grupogjl.model.game.elements.level.Level;

import java.util.Iterator;
import java.util.List;

public class ControllerDestroyableBlocks {
    public void step(Level level, GeneralGui.ACTION action, long time) {
        updateStatus(level, time);
    }
    public void updateStatus(Level level, long time) {
        List<DestroyableBlock> blocks = level.getDestroyableBlocks();
        Iterator<DestroyableBlock> iterator = blocks.listIterator();
        while (iterator.hasNext()) {
            DestroyableBlock block = iterator.next();
            if (block.getStrenght() <= 0) {
                iterator.remove();
            }
        }
        level.setDestroyableBlocks(blocks);
    }
}
