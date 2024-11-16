package org.grupogjl.model.game.elements.blocks;

import org.grupogjl.model.game.elements.camera.Camera;

public class UnbreakableBlock extends BuildingBlock {
    public UnbreakableBlock(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
    @Override
    public float getVirtX(Camera camera) {
        return getX() - camera.getLeftCamLimit();
    }
    @Override
    public float getVirtY() {
        return getY();
    }
    @Override
    public String getImage() {
        return "unbreakableBlock.png";
    }
}
