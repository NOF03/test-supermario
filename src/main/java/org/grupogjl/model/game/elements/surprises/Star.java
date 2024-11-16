package org.grupogjl.model.game.elements.surprises;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.generalobjects.StaticObject;

public class Star extends Surprise {
    public Star(float x, float y) {
        super(x, y);
    }

    @Override
    public void handleCollision(GameObject object, char r) {
        if (object instanceof StaticObject staticObject) {
            switch (r) {
                case 'U':
                    setY(staticObject.getY() + getHeight());
                    setVy(0);
                    break;
                case 'D':
                    setY(staticObject.getY() - staticObject.getHeight());
                    setFalling(false);
                    setVy(-0.1f);
                    setJumping(true);
                    break;
                case 'L':
                    setX(staticObject.getX() + staticObject.getWidth());
                    setVx(0.2f);
                    break;
                case 'R':
                    setX(staticObject.getX() - getWidth());
                    setVx(-0.2f);
                    break;
            }
        }


    }

    @Override
    public void born() {
        setVx(0.2f);

        setFalling(true);

    }

    @Override
    public void activate(Mario mario) {
        mario.setInvencibleTime(600);
        mario.setStateInvencible(true);
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
        return "star.png";
    }
}
