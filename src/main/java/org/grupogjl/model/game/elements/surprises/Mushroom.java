package org.grupogjl.model.game.elements.surprises;

import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.generalobjects.StaticObject;

public abstract class Mushroom extends Surprise {
    public Mushroom(float x, float y) {
        super(x, y);
    }

    @Override
    public void born() {
        setVx(0.2f);

        setFalling(true);

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
                    setVx(0.2f);
                    break;
                case 'R':
                    setX(staticObject.getX() - getWidth());
                    setVx(-0.2f);
                    break;
            }
        }
    }


}
