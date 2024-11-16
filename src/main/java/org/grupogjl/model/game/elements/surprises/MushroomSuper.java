package org.grupogjl.model.game.elements.surprises;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.camera.Camera;

public class MushroomSuper extends Mushroom {
    public MushroomSuper(float x, float y) {
        super(x, y);
    }

    @Override
    public void activate(Mario mario) {
        if (!mario.isStateBig() && !mario.isStateFire()) {
            mario.setStateBig(true);
            mario.setHeight(2);
        }
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
        return "superMushroom.png";
    }
}
