package org.grupogjl.model.game.elements.blocks;

import org.grupogjl.model.game.elements.Mario;

public abstract class InteractableBlock extends BuildingBlock {
    public InteractableBlock(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
    public abstract void gotHit(Mario mario);

}
