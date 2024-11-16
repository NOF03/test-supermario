package org.grupogjl.model.game.elements.surprises;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.camera.Camera;

public class Mushroom1UP extends Mushroom {
    public Mushroom1UP(float x, float y) {
        super(x, y);
    }

    @Override
    public void activate(Mario mario) {
        mario.setLives(mario.getLives() + 1);
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
        return "lifeMushroom.png";
    }
}
