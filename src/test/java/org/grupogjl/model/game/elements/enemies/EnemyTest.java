package org.grupogjl.model.game.elements.enemies;

import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.generalobjects.StaticObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EnemyTest {

    @Test
    void testEnemyInitialization() {
        Enemy enemy = createTestEnemy();

        // Verify initial values
        assertThat(enemy.getX()).isEqualTo(10);
        assertThat(enemy.getY()).isEqualTo(20);
        assertThat(enemy.getWidth()).isEqualTo(5);
        assertThat(enemy.getHeight()).isEqualTo(5);
        assertThat(enemy.getLives()).isEqualTo(1);
    }

    @Test
    void testEnemySettersAndGetters() {
        Enemy enemy = createTestEnemy();

        // Test lives
        enemy.setLives(2);
        assertThat(enemy.getLives()).isEqualTo(2);

        // Test revealed status
        enemy.setRevealed(true);
        assertThat(enemy.wasRevealed()).isTrue();
    }

    @Test
    void testMoveLeft() {
        Enemy enemy = createTestEnemy();
        enemy.moveLeft();

        // Verify velocity after moving left
        assertThat(enemy.getVx()).isEqualTo(-0.2f);
    }

    @Test
    void testMoveRight() {
        Enemy enemy = createTestEnemy();
        enemy.moveRight();

        // Verify velocity after moving right
        assertThat(enemy.getVx()).isEqualTo(0.2f);
    }

    @Test
    void testHandleWallCollision() {
        Enemy enemy = createTestEnemy();
        enemy.setVx(1.0f);

        // Handle collision with wall
        enemy.handleWallColision(5.0f);

        // Verify position and velocity
        assertThat(enemy.getX()).isEqualTo(5.0f);
        assertThat(enemy.getVx()).isEqualTo(-1.0f); // Velocity reversed
    }

    @Test
    void testHandleCollisionWithEnemy() {
        Enemy enemy = createTestEnemy();
        Enemy otherEnemy = mock(Enemy.class);

        // Collision from the right
        enemy.handleCollision(otherEnemy, 'R');
        assertThat(enemy.getVx()).isEqualTo(-0.2f);

        // Collision from the left
        enemy.handleCollision(otherEnemy, 'L');
        assertThat(enemy.getVx()).isEqualTo(0.2f);
    }

    @Test
    void testHandleCollisionWithStaticObject() {
        Enemy enemy = createTestEnemy();
        StaticObject staticObject = mock(StaticObject.class);

        // Collision from above
        when(staticObject.getY()).thenReturn(15.0f);
        when(staticObject.getHeight()).thenReturn(5.0f);
        enemy.handleCollision(staticObject, 'U');
        assertThat(enemy.getY()).isEqualTo(20.0f);
        assertThat(enemy.getVy()).isEqualTo(0.0f);

        // Collision from below
        when(staticObject.getY()).thenReturn(25.0f);
        enemy.handleCollision(staticObject, 'D');
        assertThat(enemy.getY()).isEqualTo(20.0f);
        assertThat(enemy.isFalling()).isFalse();
        assertThat(enemy.getVy()).isEqualTo(0.0f);
        assertThat(enemy.isJumping()).isFalse();

        // Collision from the left
        when(staticObject.getX()).thenReturn(5.0f);
        when(staticObject.getWidth()).thenReturn(5.0f);
        enemy.handleCollision(staticObject, 'L');
        assertThat(enemy.getX()).isEqualTo(10.0f);
        assertThat(enemy.getVx()).isEqualTo(-0.0f);

        // Collision from the right
        enemy.handleCollision(staticObject, 'R');
        assertThat(enemy.getX()).isEqualTo(0.0f);
        assertThat(enemy.getVx()).isEqualTo(0.0f);
    }

    @Test
    void testHandleCollisionWithOtherObject() {
        Enemy enemy = createTestEnemy();
        GameObject otherObject = mock(GameObject.class);

        // Collision with an unknown object type
        enemy.handleCollision(otherObject, 'R');
        // Verify no changes as "else" branch does nothing explicitly
        assertThat(enemy.getX()).isEqualTo(10.0f);
        assertThat(enemy.getVx()).isEqualTo(0.0f); // No velocity change
    }

    private Enemy createTestEnemy() {
        return new Enemy(10, 20, 5, 5) {
            @Override
            public void handleWallColision(float leftCamLimit) {
                super.handleWallColision(leftCamLimit);
            }

            @Override
            public String getImage() {
                return "enemy.png";
            }

            @Override
            public float getVirtX(Camera camera) {
                return 0;
            }

            @Override
            public float getVirtY() {
                return 0;
            }
        };
    }
}
