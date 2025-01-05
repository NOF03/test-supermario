package org.grupogjl.model.game.elements.props;

import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.enemies.Enemy;
import org.grupogjl.model.game.elements.generalobjects.StaticObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FireBallTest {

    @Test
    public void testFireBallInitialization() {
        FireBall fireBall = new FireBall(10, 20);

        // Verify initial properties
        assertThat(fireBall.getX()).isEqualTo(10);
        assertThat(fireBall.getY()).isEqualTo(20);
        assertThat(fireBall.getWidth()).isEqualTo(1);
        assertThat(fireBall.getHeight()).isEqualTo(1);
        assertThat(fireBall.getVx()).isEqualTo(1);
        assertThat(fireBall.getVy()).isEqualTo(0);
        assertThat(fireBall.getG()).isEqualTo(0.1f);
        assertThat(fireBall.isFalling()).isTrue();
        assertThat(fireBall.isActive()).isTrue();
    }

    @Test
    public void testSetAndGetActive() {
        FireBall fireBall = new FireBall(10, 20);
        fireBall.setActive(false);

        // Verify active status
        assertThat(fireBall.isActive()).isFalse();
    }

    @Test
    public void testHandleCollisionWithStaticObjectUp() {
        FireBall fireBall = new FireBall(10, 20);
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getY()).thenReturn(25f);

        // Handle collision from above
        fireBall.handleCollision(mockStaticObject, 'U');

        // Verify changes
        assertThat(fireBall.getY()).isEqualTo(25 + fireBall.getHeight());
        assertThat(fireBall.getVy()).isEqualTo(0);
    }

    @Test
    public void testHandleCollisionWithStaticObjectDown() {
        FireBall fireBall = new FireBall(10, 20);
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getY()).thenReturn(25f);
        when(mockStaticObject.getHeight()).thenReturn(5f);

        // Handle collision from below
        fireBall.handleCollision(mockStaticObject, 'D');

        // Verify changes
        assertThat(fireBall.getY()).isEqualTo(25 - 5);
        assertThat(fireBall.isFalling()).isFalse();
        assertThat(fireBall.isJumping()).isTrue();
        assertThat(fireBall.getVy()).isEqualTo(-0.1f);
    }

    @Test
    public void testHandleCollisionWithStaticObjectLeft() {
        FireBall fireBall = new FireBall(10, 20);
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getX()).thenReturn(5f);
        when(mockStaticObject.getWidth()).thenReturn(2f);

        // Handle collision from the left
        fireBall.handleCollision(mockStaticObject, 'L');

        // Verify changes
        assertThat(fireBall.getX()).isEqualTo(5 + 2);
        assertThat(fireBall.isActive()).isFalse();
    }

    @Test
    public void testHandleCollisionWithStaticObjectRight() {
        FireBall fireBall = new FireBall(10, 20);
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getX()).thenReturn(15f);

        // Handle collision from the right
        fireBall.handleCollision(mockStaticObject, 'R');

        // Verify changes
        assertThat(fireBall.getX()).isEqualTo(15 - fireBall.getWidth());
        assertThat(fireBall.isActive()).isFalse();
    }

    @Test
    public void testHandleCollisionWithStaticObjectNoKey() {
        FireBall fireBall = new FireBall(10, 20);
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getX()).thenReturn(15f);

        // Handle collision from the right
        fireBall.handleCollision(mockStaticObject, 'A');

        // Verify changes
        assertThat(fireBall.getX()).isEqualTo(10);
    }

    @Test
    public void testHandleCollisionWithEnemy() {
        FireBall fireBall = new FireBall(10, 20);
        Enemy mockEnemy = mock(Enemy.class);
        when(mockEnemy.getLives()).thenReturn(5);

        // Handle collision with an enemy
        fireBall.handleCollision(mockEnemy, 'U');

        // Verify changes
        verify(mockEnemy).setLives(3); // Lives reduced by 2
        assertThat(fireBall.isActive()).isFalse();
    }

    @Test
    public void testHandleWallCollision() {
        FireBall fireBall = new FireBall(10, 20);

        // Handle wall collision
        fireBall.handleWallColision(5);

        // Verify position and velocity
        assertThat(fireBall.getX()).isEqualTo(5);
        assertThat(fireBall.getVx()).isEqualTo(0);
    }

    @Test
    public void testGetVirtX() {
        FireBall fireBall = new FireBall(10, 20);
        Camera mockCamera = mock(Camera.class);
        when(mockCamera.getLeftCamLimit()).thenReturn(5f);

        // Verify virtual X coordinate
        assertThat(fireBall.getVirtX(mockCamera)).isEqualTo(5f); // X - left camera limit
    }

    @Test
    public void testGetVirtY() {
        FireBall fireBall = new FireBall(10, 20);

        // Verify virtual Y coordinate
        assertThat(fireBall.getVirtY()).isEqualTo(20);
    }

    @Test
    public void testGetImage() {
        FireBall fireBall = new FireBall(10, 20);

        // Verify image
        assertThat(fireBall.getImage()).isEqualTo("fireBall.png");
    }
}
