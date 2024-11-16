package org.grupogjl.model.game.elements.surprises;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.generalobjects.GameObject;

public class Coin extends Surprise {
    int activationTimer = 8;

    public Coin(float x, float y) {
        super(x, y);
    }

    public int getActivationTimer() {
        return activationTimer;
    }

    public void setActivationTimer(int activationTimer) {
        this.activationTimer = activationTimer;
    }

    @Override
    public void born() {
        setVy(0.7f);
        setJumping(true);


    }

    @Override
    public void activate(Mario mario) {
        mario.setCoins(mario.getCoins() + 1);
    }


    @Override
    public void handleCollision(GameObject object, char r) {
        setVy(0);
        setVx(0);
        setFalling(false);
        setJumping(false);
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
        return "coin.png";
    }
}
