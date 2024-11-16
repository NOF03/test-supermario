package org.grupogjl.model.game.elements.surprises;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.generalobjects.PhysicalObject;

public abstract class Surprise extends PhysicalObject {


    boolean activated;

    public Surprise(float x, float y) {
        super(x, y, 1, 1);
        activated = false;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public abstract void born();

    @Override
    public void handleWallColision(float leftCamLimit) {
        setX(leftCamLimit);
        setVx(-getVx());
    }

    public abstract void activate(Mario mario);
}
