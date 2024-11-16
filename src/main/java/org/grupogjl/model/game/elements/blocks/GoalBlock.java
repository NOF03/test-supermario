package org.grupogjl.model.game.elements.blocks;

import org.grupogjl.model.game.elements.camera.Camera;

public class GoalBlock extends BuildingBlock {
    public GoalBlock(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
    @Override
    public float getVirtX(Camera camera) {
        return getX() - camera.getLeftCamLimit();
    }
    @Override
    public float getVirtY() {
        return getY() - (getHeight() - 1);
    }
    @Override
    public String getImage() {
        return "goalBlock.png";
    }
}
