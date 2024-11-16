package org.grupogjl.model.game.elements.enemies;

import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.generalobjects.PhysicalObject;
import org.grupogjl.model.game.elements.generalobjects.StaticObject;

public abstract class Enemy extends PhysicalObject {
    int lives;
    boolean gotRevealed;
    public Enemy(float x, float y, float width, float height) {

        super(x, y, width, height);
        lives = 1;
    }
    public boolean wasRevealed() {
        return gotRevealed;
    }
    public void setRevealed(boolean gotRevealed) {
        this.gotRevealed = gotRevealed;
    }
    @Override
    public void handleWallColision(float leftCamLimit) {
        setX(leftCamLimit);
        setVx(-getVx());
    }
    public void moveLeft() {
        setVx(-0.2f);
    }
    public void moveRight() {
        setVx(0.2f);
    }
    public int getLives() {
        return lives;
    }
    public void setLives(int lives) {
        this.lives = lives;
    }
    @Override
    public void handleCollision(GameObject object, char r) {
        if (object instanceof Enemy) {
            if (r == 'R') {
                setVx(-0.2f);
            }
            if (r == 'L') {
                setVx(0.2f);
            }
        } else if (object instanceof StaticObject staticObject) {
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
                    setVx(-getVx());
                    break;
                case 'R':
                    setX(staticObject.getX() - getWidth());
                    setVx(-getVx());
                    break;
            }
        }
    }
}
