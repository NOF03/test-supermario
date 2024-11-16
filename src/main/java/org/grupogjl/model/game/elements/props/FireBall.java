package org.grupogjl.model.game.elements.props;

import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.enemies.Enemy;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.generalobjects.PhysicalObject;
import org.grupogjl.model.game.elements.generalobjects.StaticObject;

public class FireBall extends PhysicalObject {
    boolean active;
    public FireBall(float x, float y) {
        super(x, y, 1, 1);
        setHeight(1);
        setVx(1);
        setVy(0);
        setG(0.1f);
        setFalling(true);
        setActive(true);
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
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
                    setActive(false);
                    break;
                case 'R':
                    setX(staticObject.getX() - getWidth());
                    setActive(false);
                    break;
            }
        }
        if (object instanceof Enemy) {
            ((Enemy) object).setLives(((Enemy) object).getLives() - 2);
            setActive(false);
        }
    }
    @Override
    public void handleWallColision(float leftCamLimit) {
        setX(leftCamLimit);
        setVx(0);
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
        return "fireBall.png";
    }
}
