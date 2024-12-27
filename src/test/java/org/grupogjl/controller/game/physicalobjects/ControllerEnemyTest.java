package org.grupogjl.controller.game.physicalobjects;

import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.enemies.Enemy;
import org.grupogjl.model.game.elements.level.Level;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerEnemyTest {

    @Spy
    @InjectMocks
    private ControllerEnemy controllerEnemy;

    @Mock
    private Level level;

    @Mock
    private Camera camera;

    @Test
    void testStep() {
        List<Enemy> enemies = new ArrayList<>();
        when(level.getEnemies()).thenReturn(enemies);
        when(level.getCamera()).thenReturn(camera);

        controllerEnemy.step(level, 1000L);

        verify(controllerEnemy).updateStatus(level, 1000L);
        verify(controllerEnemy).moveEnemies(enemies, camera);
    }

    @Test
    void testUpdateStatus_removesDeadEnemies() {
        Enemy aliveEnemy = mock(Enemy.class);
        Enemy deadEnemy = mock(Enemy.class);

        when(aliveEnemy.wasRevealed()).thenReturn(true);
        when(aliveEnemy.getLives()).thenReturn(1);
        when(deadEnemy.getLives()).thenReturn(0);

        List<Enemy> enemies = new ArrayList<>();
        enemies.add(aliveEnemy);
        enemies.add(deadEnemy);

        when(level.getEnemies()).thenReturn(enemies);

        controllerEnemy.updateStatus(level, 1000L);

        verify(aliveEnemy).updateLocation();
        verify(deadEnemy, never()).updateLocation();
        verify(level).setEnemies(anyList());
    }

    @Test
    void testMoveEnemies() {
        Enemy fallingEnemy = mock(Enemy.class);
        Enemy movingEnemy = mock(Enemy.class);

        List<Enemy> enemies = List.of(fallingEnemy, movingEnemy);

        when(fallingEnemy.wasRevealed()).thenReturn(true);
        when(fallingEnemy.isFalling()).thenReturn(true);

        when(movingEnemy.wasRevealed()).thenReturn(true);
        when(movingEnemy.isFalling()).thenReturn(false);
        when(movingEnemy.getVx()).thenReturn(-1f);

        controllerEnemy.moveEnemies(enemies, camera);

        verify(movingEnemy).moveLeft();
        verify(fallingEnemy, never()).moveLeft();
    }

    @Test
    void testMoveEnemies_notRevealed() {
        Enemy hiddenEnemy = mock(Enemy.class);

        List<Enemy> enemies = List.of(hiddenEnemy);

        when(hiddenEnemy.wasRevealed()).thenReturn(false);

        controllerEnemy.moveEnemies(enemies, camera);

        verify(hiddenEnemy, never()).isFalling();
        verify(hiddenEnemy, never()).moveLeft();
        verify(hiddenEnemy, never()).moveRight();
    }

    @Test
    void testMoveEnemies_movingRight() {
        Enemy rightMovingEnemy = mock(Enemy.class);

        List<Enemy> enemies = List.of(rightMovingEnemy);

        when(rightMovingEnemy.wasRevealed()).thenReturn(true);
        when(rightMovingEnemy.isFalling()).thenReturn(false);
        when(rightMovingEnemy.getVx()).thenReturn(1f);

        controllerEnemy.moveEnemies(enemies, camera);

        verify(rightMovingEnemy).moveRight();
    }

    @Test
    void testMoveEnemies_noMovement() {
        Enemy stationaryEnemy = mock(Enemy.class);

        List<Enemy> enemies = List.of(stationaryEnemy);

        when(stationaryEnemy.wasRevealed()).thenReturn(true);
        lenient().when(stationaryEnemy.isFalling()).thenReturn(true);
        lenient().when(stationaryEnemy.getVx()).thenReturn(0f);

        controllerEnemy.moveEnemies(enemies, camera);

        verify(stationaryEnemy, never()).moveLeft();
        verify(stationaryEnemy, never()).moveRight();
    }
}
