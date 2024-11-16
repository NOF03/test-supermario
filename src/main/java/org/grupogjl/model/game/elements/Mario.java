package org.grupogjl.model.game.elements;


import org.grupogjl.model.game.elements.blocks.BuildingBlock;
import org.grupogjl.model.game.elements.blocks.GoalBlock;
import org.grupogjl.model.game.elements.blocks.InteractableBlock;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.enemies.Enemy;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.generalobjects.PhysicalObject;
import org.grupogjl.model.game.elements.surprises.Surprise;
import org.grupogjl.state.StateGame;

import java.io.IOException;
import java.util.Random;

public class Mario extends PhysicalObject {
    private StateGame observer;
    private boolean stateBig;
    private boolean hitCooldown;
    private boolean stateFire;
    private int invencibleTime;
    private boolean stateInvencible;
    private int coins;
    private int lives;
    public Mario(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.lives = 3;
        coins = 0;
    }
    public int getInvencibleTime() {
        return invencibleTime;
    }
    public void setInvencibleTime(int invencibleTime) {
        this.invencibleTime = invencibleTime;
    }
    public boolean isStateInvencible() {
        return stateInvencible;
    }
    public void setStateInvencible(boolean stateInvencible) {
        this.stateInvencible = stateInvencible;
    }
    public int getCoins() {
        return coins;
    }
    public void setCoins(int coins) {
        this.coins = coins;
    }
    public void setObserver(StateGame observer) {
        this.observer = observer;
    }
    public boolean isHitCooldown() {
        return hitCooldown;
    }
    public void setHitCooldown(boolean hitCooldown) {
        this.hitCooldown = hitCooldown;
    }
    public boolean isStateFire() {
        return stateFire;
    }
    public void setStateFire(boolean stateFire) {
        this.stateFire = stateFire;
    }
    public boolean isStateBig() {
        return stateBig;
    }
    public void setStateBig(boolean stateBig) {
        this.stateBig = stateBig;
    }
    @Override
    public void handleWallColision(float leftCamLimit) {
        setX(leftCamLimit);
        setVx(0);
    }
    public void jump() {
        if (!isJumping() && !isFalling()) {
            setJumping(true);
            setVy(1.3f);
        }
    }
    public void moveLeft() {
        setVx(-0.5f); // Set a negative value to move left
    }
    public void moveRight() {
        setVx(0.5f); // Set a positive value to move right
    }
    public int getLives() {
        return lives;
    }
    public void setLives(int lives) {
        this.lives = lives;
    }
    public void notifyState(String notification) {
        try {
            if (notification.equals("lives")) {
                observer.resetLevel();
            } else if (notification.equals("goal")) {
                observer.nextLevel();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void handleCollision(GameObject object, char r) {
        if (object instanceof Surprise surprise) {
            surprise.activate(this);
            surprise.setActivated(false);
        } else if (object instanceof Enemy) {
            if (isStateInvencible()) {
                if (!isHitCooldown()) {
                    Enemy enemy = (Enemy) object;
                    enemy.setLives(0);
                    setVx(0);
                }
            } else if (r != 'D') {
                if (isStateBig() || isStateFire()) {
                    setStateBig(false);
                    setStateFire(false);
                    setHeight(getHeight() / 2);
                    setInvencibleTime(15);
                    setStateInvencible(true);
                    setHitCooldown(true);
                } else {
                    reset();
                }
            } else {
                setJumping(true);
                setFalling(false);
                setVy(0.7f);
                Enemy enemy = (Enemy) object;
                enemy.setLives(enemy.getLives() - 1);
            }
        } else if (object instanceof BuildingBlock block) {
            if (block instanceof GoalBlock) {
                notifyState("goal");
            } else {
                switch (r) {
                    case 'U':
                        if (block instanceof InteractableBlock interactableBlock) {
                            interactableBlock.gotHit(this);
                        }
                        setVy(0);
                        setJumping(false);
                        setFalling(true);
                        setY(block.getY() + getHeight());
                        break;
                    case 'D':
                        setVy(0);
                        setFalling(false);
                        setJumping(false);
                        setY(block.getY() - block.getHeight());
                        break;
                    case 'L':
                        setX(block.getX() + block.getWidth());
                        setVx(0);
                        break;
                    case 'R':
                        setX(block.getX() - getWidth());
                        setVx(0);
                        break;
                }
            }
        }
    }
    public void reset() {
        setVx(0);
        setLives(getLives() - 1);
        if (isStateBig() || isStateFire()) {
            setHeight(1);
        }
        setStateBig(false);
        setStateFire(false);
        setStateInvencible(false);
        setHitCooldown(false);
        setInvencibleTime(0);
        notifyState("lives");
    }
    @Override
    public float getVirtX(Camera camera) {
        return getX() - camera.getLeftCamLimit();
    }
    @Override
    public float getVirtY() {
        return getY() - (getHeight() - 1);
    }
    @Override
    public String getImage() {
        Random random = new Random();
        boolean randomBoolean = random.nextBoolean();
        if (isStateBig()) {
            if (isStateInvencible() && !isHitCooldown() && randomBoolean) {
                return "marioStarBig.png";
            } else {
                return "marioSuper.png";
            }
        } else if (isStateFire()) {
            if (isStateInvencible() && !isHitCooldown() && randomBoolean) {
                return "marioStarBig.png";
            } else {
                return "marioFlower.png";
            }
        } else {
            if (isStateInvencible() && randomBoolean) {
                if (isHitCooldown()) {
                    return "hitMario.png";
                } else if (isStateInvencible()) {
                    return "marioStar.png";
                }
            } else {
                return "mario.png";
            }
        }
        return null;
    }
}
