package org.grupogjl.controller.game.physicalobjects;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.blocks.Pipe;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.level.Level;
import org.grupogjl.model.game.elements.props.FireBall;

import java.util.List;

public class ControllerMario {
    public void step(Level level, GeneralGui.ACTION action, long time) {
        Camera camera = level.getCamera();
        Mario mario = level.getMario();
        updateMarioStatus(level);
        moveMario(action, level.getMario(), level.getObjects());
        camera.updateCamera(mario);
    }
    public void updateMarioStatus(Level level) {
        Mario mario = level.getMario();
        if (mario.getY() > level.getHeight()) {
            mario.reset();
        }
        mario.updateLocation();
        if (mario.getCoins() == 10) {
            mario.setLives(mario.getLives() + 1);
            mario.setCoins(0);
        }
        if (mario.getInvencibleTime() == 0 && mario.isStateInvencible()) {
            mario.setStateInvencible(false);
            if (mario.isHitCooldown()) {
                mario.setHitCooldown(false);
            }
        } else if (mario.getInvencibleTime() != 0 && mario.isStateInvencible()) {
            mario.setInvencibleTime(mario.getInvencibleTime() - 1);
        }
    }
    public void moveMario(GeneralGui.ACTION action, Mario mario, List<GameObject> objects) {
        switch (action) {
            case UP:
                mario.jump();
                break;
            case RIGHT:
                mario.moveRight();
                break;
            case LEFT:
                mario.moveLeft();
                break;
            case NONE:
                if (!mario.isJumping() && !mario.isFalling()) {
                    mario.setVx(0);
                } else {
                    mario.setVx(mario.getVx() / 4);
                }
                break;
            case DOWN:
                transportMario(mario, objects);
                break;
            case THROWBALL:
                if (mario.isStateFire()) {
                    objects.add(new FireBall(mario.getX(), mario.getY()));
                }
                break;
            default:
                break;
        }
    }
    public void transportMario(Mario mario, List<GameObject> objects) {
        for (GameObject object : objects) {
            if (object instanceof Pipe) {
                Pipe pipe = (Pipe) object;
                if (Math.abs(mario.getX() - pipe.getX()) < 1.2) {
                    boolean isAbovePipe = Math.abs(pipe.getY() - pipe.getHeight() - mario.getY()) < 0.5;
                    if (isAbovePipe) {
                        float newX = pipe.getConection().getX();
                        float newY = pipe.getConection().getY();
                        mario.setX(newX);
                        mario.setY(newY - pipe.getConection().getHeight());
                        break;
                    }
                }
            }
        }
    }
}
