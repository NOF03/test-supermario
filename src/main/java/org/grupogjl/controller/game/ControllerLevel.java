package org.grupogjl.controller.game;


import org.grupogjl.controller.game.blocks.ControllerBlocks;
import org.grupogjl.controller.game.physicalobjects.ControllerEnemy;
import org.grupogjl.controller.game.physicalobjects.ControllerFireBall;
import org.grupogjl.controller.game.physicalobjects.ControllerMario;
import org.grupogjl.controller.game.surprises.ControllerSurprises;
import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.blocks.BuildingBlock;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.generalobjects.PhysicalObject;
import org.grupogjl.model.game.elements.generalobjects.StaticObject;
import org.grupogjl.model.game.elements.level.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControllerLevel {
    private ControllerMario controllerMario = new ControllerMario();
    private ControllerEnemy controllerEnemy = new ControllerEnemy();
    private ControllerBlocks controllerBlocks = new ControllerBlocks();
    private ControllerSurprises controllerSurprises = new ControllerSurprises();
    private ControllerFireBall controllerFireBall = new ControllerFireBall();
    public ControllerMario getControllerMario() {
        return controllerMario;
    }
    public ControllerEnemy getControllerEnemy() {
        return controllerEnemy;
    }
    public ControllerBlocks getControllerBlocks() {
        return controllerBlocks;
    }
    public ControllerSurprises getControllerSurprises() {
        return controllerSurprises;
    }
    public ControllerFireBall getControllerFireBall() {
        return controllerFireBall;
    }
    public void step(Level level, GeneralGui.ACTION action, long time) {
        Mario mario = level.getMario();
        Camera camera = level.getCamera();
        List<GameObject> objects = level.getObjects();
        getControllerFireBall().step(level, action, time);
        getControllerMario().step(level, action, time);
        getControllerEnemy().step(level, time);
        getControllerSurprises().step(level, action, time);
        getControllerBlocks().step(level, action, time);
        checkCollisions(mario, objects, camera);
    }
    public void checkCollisions(Mario mario, List<GameObject> objects, Camera camera) {
        CheckPhysicalCollisionsX(mario, objects, camera);
        CheckPhysicalCollisionsY(mario, objects);
        for (GameObject object : objects) {
            if (!(object instanceof StaticObject)) {
                PhysicalObject physicalObject = (PhysicalObject) object;
                CheckPhysicalCollisionsX(physicalObject, objects, camera);
                CheckPhysicalCollisionsY(physicalObject, objects);
            }
        }
    }
    public void CheckPhysicalCollisionsY(PhysicalObject physicalObject, List<GameObject> objects) {
        boolean fcol = false, rcol = false, blockbelow = false;
        for (GameObject object : objects) {
            if (object instanceof BuildingBlock block) {
                if (physicalObject.getX() < block.getX() + block.getWidth() && physicalObject.getX() + physicalObject.getWidth() > block.getX()) {
                    if (physicalObject.getY() == block.getY() - block.getHeight()) {
                        blockbelow = true;
                    }
                }
            }
        }
        for (GameObject object : objects) {
            if (object != physicalObject) {
                if (physicalObject.getVy() != 0 && physicalObject.isJumping()) {
                    float startPosition = physicalObject.getY();
                    float totalDistance = physicalObject.getVy();
                    float remainingDistance = totalDistance;
                    float movementStep = physicalObject.getVy() / 3;
                    while (remainingDistance > 0) {
                        physicalObject.setY(physicalObject.getY() - movementStep);
                        if (physicalObject.collidesWith(object)) {
                            physicalObject.handleCollision(object, 'U');
                            rcol = true;
                            break;
                        }
                        remainingDistance -= movementStep;
                        movementStep = Math.min(remainingDistance, physicalObject.getVy() / 3);
                    }
                    if (!rcol) {
                        physicalObject.setY(startPosition);
                    } else {
                        break;
                    }
                } else if (physicalObject.getVy() != 0) {
                    float startPosition = physicalObject.getY();
                    float totalDistance = physicalObject.getVy();
                    float remainingDistance = totalDistance;
                    float movementStep = physicalObject.getVy() / 3;
                    while (remainingDistance > 0) {
                        physicalObject.setY(physicalObject.getY() + movementStep);
                        if (physicalObject.collidesWith(object)) {
                            physicalObject.handleCollision(object, 'D');
                            fcol = true;
                            break;
                        }
                        remainingDistance -= movementStep;
                        movementStep = Math.min(remainingDistance, physicalObject.getVy() / 3);
                    }
                    if (!fcol) {
                        physicalObject.setY(startPosition);
                    } else {
                        break;
                    }
                }

            }
        }
        if (blockbelow && !physicalObject.isJumping()) {
            physicalObject.setFalling(false);
        } else if (!blockbelow && !physicalObject.isJumping()) {
            physicalObject.setFalling(true);
        }
    }
    public void CheckPhysicalCollisionsX(PhysicalObject mainObject, List<GameObject> objects, Camera camera) {
        if (mainObject.getX() <= camera.getLeftCamLimit() && mainObject.getVx() < 0) {
            mainObject.handleWallColision(camera.getLeftCamLimit());
            return;
        }
        boolean colision = false;
        List<GameObject> robjects = new ArrayList<>(objects);
        Collections.reverse(robjects);
        for (GameObject object : objects) {
            if (mainObject.getVx() == 0 && object instanceof PhysicalObject enemy) {
                if (mainObject.collidesWithPhysical(enemy, Math.abs(enemy.getVx()), -0.2f)) {
                    if (enemy.getX() > mainObject.getX() && mainObject.getY() >= enemy.getY())
                        mainObject.handleCollision(enemy, 'L');
                    else if (enemy.getX() < mainObject.getX() && mainObject.getY() >= enemy.getY())
                        mainObject.handleCollision(enemy, 'R');
                }
            } else if (mainObject.getVx() > 0) {
                if (object != mainObject) {
                    float startPosition = mainObject.getX();
                    float totalDistance = mainObject.getVx();
                    float remainingDistance = totalDistance;
                    float movementStep = mainObject.getVx() / 3;
                    while (remainingDistance > 0) {
                        mainObject.setX(mainObject.getX() + movementStep);
                        if (mainObject.collidesWith(object)) {
                            colision = true;
                            mainObject.handleCollision(object, 'R');
                            break;
                        }
                        remainingDistance -= movementStep;
                        movementStep = Math.min(remainingDistance, mainObject.getVx() / 3);
                    }
                    if (!colision) {
                        mainObject.setX(startPosition);
                    } else {
                        break;
                    }
                }
            } else if (mainObject.getVx() < 0) {
                if (object != mainObject) {
                    float startPosition = mainObject.getX();
                    float totalDistance = mainObject.getVx();
                    float remainingDistance = totalDistance;
                    float movementStep = mainObject.getVx() / 3;
                    while (remainingDistance < 0) {
                        mainObject.setX(mainObject.getX() + movementStep);
                        if (mainObject.collidesWith(object)) {
                            colision = true;
                            mainObject.handleCollision(object, 'L');
                            break;
                        }
                        remainingDistance -= movementStep;
                        movementStep = Math.min(remainingDistance, mainObject.getVx() / 3);
                    }
                    if (!colision) {
                        mainObject.setX(startPosition);
                    } else {
                        break;
                    }
                }
            }
        }
    }
}
