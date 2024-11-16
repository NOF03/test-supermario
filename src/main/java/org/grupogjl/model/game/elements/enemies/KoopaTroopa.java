package org.grupogjl.model.game.elements.enemies;

import org.grupogjl.model.game.elements.camera.Camera;

public class KoopaTroopa extends Enemy {
    public KoopaTroopa(float x, float y, float width, float height) {
        super(x, y, width, height);
        lives = 2;
    }
    @Override
    public void moveLeft() {
        if (getLives() == 2) {
            setVx(-0.2f);
        } else {
            setVx(-2f);
        }
    }
    @Override
    public void moveRight() {
        if (getLives() == 2) {
            setVx(0.2f);
        } else {
            setVx(2f);
        }
    }
    @Override
    public float getVirtX(Camera camera) {
        return getX() - camera.getLeftCamLimit();
    }
    @Override
    public float getVirtY() {
        if (lives == 1) {
            return getY();
        } else {
            return getY() - 0.3125f;
        }
    }
    @Override
    public String getImage() {
        if (lives == 1) {
            return "koopaShell.png";
        } else {
            return "koopaTroopa.png";
        }
    }
}
