package org.grupogjl.model.game.elements.level;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.blocks.DestroyableBlock;
import org.grupogjl.model.game.elements.blocks.GoalBlock;
import org.grupogjl.model.game.elements.blocks.SurpriseBlock;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.enemies.Enemy;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.props.FireBall;
import org.grupogjl.model.game.elements.surprises.Coin;
import org.grupogjl.model.game.elements.surprises.Surprise;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Level {
    int height;
    private List<GameObject> objects;
    private Mario mario;
    private GoalBlock goalBlock;
    private int width;
    private Camera camera;
    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        this.objects = new ArrayList<>();
    }
    public List<GameObject> getObjects() {
        return objects;
    }
    public void setObjects(List<GameObject> objects) {
        this.objects = objects;
    }
    public GoalBlock getGoalBlock() {
        return goalBlock;
    }
    public void setGoalBlock(GoalBlock goalBlock) {
        this.goalBlock = goalBlock;
    }
    public Camera getCamera() {
        return camera;
    }
    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public Mario getMario() {
        return mario;
    }
    public void setMario(Mario mario) {
        this.mario = mario;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public List<DestroyableBlock> getDestroyableBlocks() {
        List<DestroyableBlock> destroyableBlocks = new ArrayList<>();
        for (GameObject gameObject : objects) {
            if (gameObject instanceof DestroyableBlock destroyableBlock) {
                destroyableBlocks.add(destroyableBlock);
            }
        }
        return destroyableBlocks;
    }
    public void setDestroyableBlocks(List<DestroyableBlock> destroyableBlocks) {
        Iterator<GameObject> iterator = objects.iterator();
        while (iterator.hasNext()) {
            GameObject object = iterator.next();
            if (object instanceof DestroyableBlock) {
                iterator.remove();
            }
        }
        for (GameObject destroyableBlock : destroyableBlocks) {
            objects.add(destroyableBlock);
        }
    }
    public List<SurpriseBlock> getSurpriseBlocks() {
        List<SurpriseBlock> blocks = new ArrayList<>();
        for (GameObject gameObject : objects) {
            if (gameObject instanceof SurpriseBlock block) blocks.add(block);
        }
        return blocks;
    }
    public List<Enemy> getEnemies() {
        List<Enemy> enemies = new ArrayList<>();
        for (GameObject gameObject : objects) {
            if (gameObject instanceof Enemy enemy) enemies.add(enemy);
        }
        return enemies;
    }
    public void setEnemies(List<Enemy> enemies) {
        Iterator<GameObject> iterator = objects.iterator();
        while (iterator.hasNext()) {
            GameObject object = iterator.next();
            if (object instanceof Enemy) {
                iterator.remove();
            }
        }
        for (GameObject enemy : enemies) {
            objects.add(enemy);
        }
    }
    public List<Surprise> getSurprises() {
        List<Surprise> surprises = new ArrayList<>();
        for (GameObject gameObject : objects) {
            if (gameObject instanceof Surprise surprise) surprises.add(surprise);
        }
        return surprises;
    }
    public void setSurprises(List<Surprise> surprises) {
        Iterator<GameObject> iterator = objects.iterator();
        while (iterator.hasNext()) {
            GameObject object = iterator.next();
            if (object instanceof Surprise) {
                iterator.remove();
            }
        }
        for (GameObject surprise : surprises) {
            objects.add(surprise);
        }
    }
    public List<Coin> getCoins() {
        List<Coin> coins = new ArrayList<>();
        for (GameObject gameObject : objects) {
            if (gameObject instanceof Coin coin) coins.add(coin);
        }
        return coins;
    }
    public List<FireBall> getFireBalls() {
        List<FireBall> fireBalls = new ArrayList<>();
        for (GameObject gameObject : objects) {
            if (gameObject instanceof FireBall fireBall) fireBalls.add(fireBall);
        }
        return fireBalls;
    }
    public void setFireBalls(List<FireBall> fireBalls) {
        Iterator<GameObject> iterator = objects.iterator();
        while (iterator.hasNext()) {
            GameObject object = iterator.next();
            if (object instanceof FireBall) {
                iterator.remove();
            }
        }
        for (GameObject fireball : fireBalls) {
            objects.add(fireball);
        }
    }
}
