package org.grupogjl.model.game.elements.enemies;

import org.grupogjl.model.game.elements.camera.Camera;

public class Goomba extends Enemy {
    public Goomba(float x, float y, float width, float height) {
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
        return "goomba.png";
    }
}
