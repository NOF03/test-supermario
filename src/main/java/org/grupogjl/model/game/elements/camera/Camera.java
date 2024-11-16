package org.grupogjl.model.game.elements.camera;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.enemies.Enemy;

public class Camera {
    private float xl, xr;
    public Camera() {
        xl = 0;
        xr = xl + 26;
    }
    public void updateCamera(Mario mario) {
        if (xl < mario.getX() - 13) {
            xl = mario.getX() - 13;
            xr = xl + 26;
        }
    }
    public void isEnemyOnCam(Enemy enemy) {
        if (enemy.getX() + enemy.getWidth() < xr && enemy.getX() > xl && !enemy.wasRevealed()) {
            enemy.setRevealed(true);
        }
    }
    public float getLeftCamLimit() {
        return xl;
    }
    public float getRightCamLimit() {
        return xr;
    }
}
