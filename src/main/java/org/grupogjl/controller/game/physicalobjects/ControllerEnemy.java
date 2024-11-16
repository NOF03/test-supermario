package org.grupogjl.controller.game.physicalobjects;

import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.enemies.Enemy;
import org.grupogjl.model.game.elements.level.Level;

import java.util.Iterator;
import java.util.List;

public class ControllerEnemy {
    public void step(Level level, long time) {
        updateStatus(level, time);
        moveEnemies(level.getEnemies(), level.getCamera());
    }
    public void updateStatus(Level level, long time) {
        List<Enemy> enemies = level.getEnemies();
        Iterator<Enemy> iterator = enemies.listIterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (enemy.wasRevealed()) {
                enemy.updateLocation();
            }
            if (enemy.getLives() <= 0) {
                iterator.remove();
            }
        }
        level.setEnemies(enemies);
    }
    public void moveEnemies(List<Enemy> enemies, Camera camera) {
        for (Enemy enemy : enemies) {
            camera.isEnemyOnCam(enemy);
            if (enemy.wasRevealed()) {
                if (!enemy.isFalling()) {
                    if (enemy.getVx() <= 0) {
                        enemy.moveLeft();
                    } else {
                        enemy.moveRight();
                    }
                }
            }
        }
    }
}
