package org.grupogjl.model.game.elements.surprises;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.generalobjects.StaticObject;

public class Flower extends Surprise {
    public Flower(float x, float y) {
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
                    setVy(0);
                    setJumping(false);
                    break;
                case 'L':
                    setX(staticObject.getX() + staticObject.getWidth());
                    break;
                case 'R':
                    setX(staticObject.getX() - getWidth());
                    break;
            }
        }
    }

    @Override
    public void born() {
        setY(getY() - 1);
        setFalling(true);

    }

    @Override
    public void activate(Mario mario) {
        mario.setHeight(2);
        mario.setStateFire(true);
        mario.setStateBig(false);
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
        return "flower.png";
    }
}
